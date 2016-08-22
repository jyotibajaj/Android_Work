package letsdecode.com.macyapp;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.StatFs;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Created by aashi on 20/08/16.
 */
public class ScanService extends Service {
    private final String TAG = ScanService.class.getSimpleName();
    private final IBinder mBinder = new LocalBinder();
    private ScanWorker thread;
    static final int MSG_REGISTER_CLIENT = 1;
    static final int MSG_UNREGISTER_CLIENT = 2;
    static final int MSG_SET_INT_VALUE = 3;
    static final int MSG_SET_STRING_VALUE = 4;
    static final int MSG_SCAN_FINISHED_SUCCESS = 5;
    static final int MSG_SCAN_STOP = 6;
    ArrayList<Messenger> mClients = new ArrayList<Messenger>();
    private boolean isScanning;


    public synchronized boolean isScanning() {
        return isScanning;
    }

    public synchronized void setScanning(boolean scanning) {
        isScanning = scanning;
    }

    IncomingHandler incomingHandler = new IncomingHandler();
    final Messenger mMessenger = new Messenger(incomingHandler); // Target we publish for clients to send messages to IncomingHandler.

    class IncomingHandler extends Handler { // Handler of incoming messages from clients.
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_REGISTER_CLIENT:
                    mClients.add(msg.replyTo);
                    break;
                case MSG_UNREGISTER_CLIENT:
                    mClients.remove(msg.replyTo);
                    if (mClients.size() == 0) {
                        stopScanning();
                    }
                    break;
                case MSG_SCAN_STOP: {
                    stopScanning();
                    break;
                }
                case MSG_SET_INT_VALUE:
//                    incrementby = msg.arg1;
                    break;
                default:
                    super.handleMessage(msg);
            }
        }

        private void sendMessageToUI(long intvaluetosend) {
            Log.d(TAG, "Size so far  :" + intvaluetosend);
            for (int i = mClients.size() - 1; i >= 0; i--) {
                try {
//                 Send data as an Integer
                    mClients.get(i).send(Message.obtain(null, MSG_SET_INT_VALUE, (int) intvaluetosend, 0));

                    //Send data as a String
                    Bundle b = new Bundle();
                    b.putString("str1", "ab" + intvaluetosend + "cd");
                    Message msg = Message.obtain(null, MSG_SET_STRING_VALUE);
                    msg.setData(b);
                    mClients.get(i).send(msg);
                } catch (RemoteException e) {
                    // The client is dead. Remove it from the list; we are going through the list from back to front so this is safe to do inside the loop.
                    mClients.remove(i);
                }
            }
        }

        private void sendMessageToUI(Message msg) {
            for (int i = mClients.size() - 1; i >= 0; i--) {
                try {
                    mClients.get(i).send(msg);
                } catch (RemoteException e) {
                    // The client is dead. Remove it from the list; we are going through the list from back to front so this is safe to do inside the loop.
                    mClients.remove(i);
                }
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    //returns the instance of the service
    public class LocalBinder extends Binder {
        public ScanService getServiceInstance() {
            return ScanService.this;
        }

        public IBinder getBinder() {
            return mMessenger.getBinder();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //Do what you need in onStartCommand when service has been started
        return START_NOT_STICKY;
    }

    public void startScanning(String path) {
        Log.d(TAG, " startScanning");
        if (this.thread == null) {
            // Creating a connection worker thread instance.
            // Not starting it yet.
            this.thread = new ScanWorker(path);
            this.thread.start();
        }
    }

    public void stopScanning() {
        Log.d(TAG, " stopScanning");
        if (thread != null) {
            this.thread.stopScan();
            this.thread.interrupt();
            this.thread = null;
        }
    }

    public class ScanWorker extends Thread {
        private String path;
        private long totalSize;
        private long sizeSoFar;
        private ArrayList<FileSize> biggestFiles = new ArrayList<>();
        private ArrayList<FileSize> mostFrequentExtentions = new ArrayList<>();

        public ScanWorker(String path) {
            super(ScanWorker.class.getName());
            this.path = path;
        }

        @Override
        public void run() {

            super.run();
            totalSize = size();
            setScanning(true);
            Log.d(TAG, "Thread started");
            try {
                startScanning();
            } catch (Exception e) {
                e.printStackTrace();
            }
            setScanning(false);
            Bundle b = new Bundle();

            b.putParcelableArrayList("BIGGEST_FILES", biggestFiles);
            b.putParcelableArrayList("FREQUENT_EXTENSIONS", mostFrequentExtentions);
            Message msg = Message.obtain(null, MSG_SCAN_FINISHED_SUCCESS);
            msg.setData(b);

            incomingHandler.sendMessageToUI(msg);
            stopScanning();
        }

        private long size() {
            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
            long bytesAvailable = (long) stat.getBlockSize() * (long) stat.getAvailableBlocks();
            long megAvailable = bytesAvailable / (1024 * 1024);
            Log.e("", "Available MB : " + megAvailable);
            ActivityManager actManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
            actManager.getMemoryInfo(memInfo);
            long totalMemory = memInfo.totalMem;
            return bytesAvailable;
        }

        public void startScanning() {
            if (Environment.isExternalStorageEmulated()) {
                File extStore = Environment.getExternalStorageDirectory();
                String mPath = extStore.getAbsolutePath() + "/";
                final File f = new File(mPath);
                String[] ls = null;
                if (f.isDirectory()) {
                    try {
//                        totalSize = Integer.parseInt(String.valueOf(file.length()));
                        ls = f.list();
                        if (ls != null) {
                            for (String str : ls) {
                                scanDirectory(mPath + "/" + str);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (isStopped() == false) {
                    while (minHeap.isEmpty() == false) {
                        FileSize fff = minHeap.poll();
                        Log.d(TAG, " Final file size " + fff.path + " " + fff.size);
                        biggestFiles.add(fff);
                    }
                    //Most Frequent file Extensions
                    for (String key : countMap.keySet()) {
                        int frequency = countMap.get(key);
                        if (fileExt.size() < 5) {
                            fileExt.add(new FileSize(frequency, key));
                        } else {
                            FileSize ff = fileExt.peek();
                            if (ff.size < frequency) {
                                fileExt.poll();
                                fileExt.add(new FileSize(frequency, key));
                            }
                        }
                    }
                    while (fileExt.isEmpty() == false) {
                        FileSize fff = fileExt.poll();
                        mostFrequentExtentions.add(fff);
                        Log.d(TAG, " Extenstion to frequencey " + fff.path + " " + fff.size);
                    }
                }
            }
        }

        PriorityQueue<FileSize> minHeap = new PriorityQueue<>(10, new Comparator<FileSize>() {
            @Override
            public int compare(FileSize lhs, FileSize rhs) {
                return (int) (lhs.size - rhs.size);
            }
        });


        PriorityQueue<FileSize> fileExt = new PriorityQueue<>(10, new Comparator<FileSize>() {
            @Override
            public int compare(FileSize lhs, FileSize rhs) {
                return (int) (lhs.size - rhs.size);
            }
        });
        HashMap<String, Integer> countMap = new HashMap<>();
        private File file;

        private boolean stop = false;

        synchronized public void stopScan() {
            stop = true;
        }

        private synchronized boolean isStopped() {
            return stop;
        }

        public void scanDirectory(String path) {
            file = new File(path);
            File list[] = file.listFiles();
            if (list != null) {
                for (int i = 0; i < list.length; i++) {
                    if (isStopped()) {
                        return;
                    }
                    if (list[i].isDirectory()) {
                        Log.d(TAG, "Directory :" + list[i].getAbsolutePath());
                        scanDirectory(path + "/" + list[i].getName());
                    } else {
                        String filename = list[i].getName();
                        File f = new File(filename);

                        Log.d(TAG, "file Size:" + " " + file.length());
                        int file_size = Integer.parseInt(String.valueOf(file.length()));
                        sizeSoFar += file_size;
                        long mb = sizeSoFar / (1024 * 1024);

                        incomingHandler.sendMessageToUI((sizeSoFar / totalSize) * 100);
                        if (minHeap.size() < 10) {
                            minHeap.add(new FileSize(file_size, path + "/" + filename));
                        } else {
                            FileSize fileSize = minHeap.peek();
                            if (fileSize.size < file_size) {
                                minHeap.poll();
                                minHeap.add(new FileSize(file_size, path + "/" + filename));
                            }
                        }
                        long bytes = f.length();
                        long kb = bytes / 1024;

                        Log.d(TAG, "file:" + list[i].getAbsolutePath() + " " + file_size);

                        String filenameArray[] = filename.split("\\.");
                        String extension = filenameArray[filenameArray.length - 1];
                        if (extension != null && extension.isEmpty() == false) {
                            Integer c = countMap.get(extension);
                            if (c == null) {
                                countMap.put(extension, 1);
                            } else {
                                countMap.put(extension, c + 1);
                            }
                        }
                    }
                }
            }
        }
    }


}


