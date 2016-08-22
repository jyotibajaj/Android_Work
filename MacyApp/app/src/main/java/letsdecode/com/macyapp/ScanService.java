package letsdecode.com.macyapp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
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
        stop();
        this.thread.interrupt();
        this.thread = null;
    }

    public class ScanWorker extends Thread {
        private String path;

        public ScanWorker(String path) {
            super(ScanWorker.class.getName());
            this.path = path;
        }

        @Override
        public void run() {

            super.run();

            Log.d(TAG, "Thread started");
            startScanning();
        }
    }

    class FileSize {
        long size;
        String path;

        public FileSize(long size, String path) {
            this.size = size;
            this.path = path;
        }
    }

    public void startScanning() {
        if (Environment.isExternalStorageEmulated()) {
            File extStore = Environment.getExternalStorageDirectory();
            String mPath = extStore.getAbsolutePath() + "/";
            final File f = new File(mPath);
            String[] ls = null;
            if (f.isDirectory()) {
                try {
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
                }
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

    synchronized public void stop() {
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
                    int file_size = Integer.parseInt(String.valueOf(file.length()));
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


