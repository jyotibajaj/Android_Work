package letsdecode.com.macyapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileList extends Activity {
    private final static String TAG = FileList.class.getSimpleName();
    private File file;
    private List<String> myList;
    private static ScanService scanService;
    private Messenger mServiceMessenger = null;
    private static boolean isBound;
    private boolean isScanning;
    Button scan;
    Button stop;
    Messenger mMessenger;

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ScanService.MSG_SET_INT_VALUE:
//                    textIntValue.setText("Int Message: " + msg.arg1);
                    break;
                case ScanService.MSG_SET_STRING_VALUE:
                    String str1 = msg.getData().getString("str1");
//                    textStrValue.setText("Str Message: " + str1);
                    break;
                case ScanService.MSG_SCAN_FINISHED_SUCCESS:
                    Bundle data = msg.getData();
                    ArrayList<FileSize> biggestFiles = data.getParcelableArrayList("BIGGEST_FILES");
                    ArrayList<FileSize> mostFrequentExtentions = data.getParcelableArrayList("FREQUENT_EXTENSIONS");
                    if (biggestFiles != null) {
                        for (FileSize f : biggestFiles) {
                            Log.d(TAG, "RESULT " + f.path + " " + f.size);
                        }
                    }
                    if (mostFrequentExtentions != null) {
                        for (FileSize f : mostFrequentExtentions) {
                            Log.d(TAG, "RESULT freq" + f.path + " " + f.size);
                        }
                    }
                    scan.setEnabled(true);
                    scan.setEnabled(true);

                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, ScanService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        TextView fileScanning = (TextView) findViewById(R.id.fileScanning);
        mConnection.activity = this;
        scan = (Button) findViewById(R.id.scan);
        scan.setEnabled(false);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBound && scanService.isScanning() == false) {
                    scan.setEnabled(false);
                    stop.setEnabled(true);


                    File extStore = Environment.getExternalStorageDirectory();
                    String path = extStore.getAbsolutePath() + "/";
                    scanService.startScanning(path);
                }
            }
        });
        stop = (Button) findViewById(R.id.stopScan);
        stop.setEnabled(true);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBound &&  scanService.isScanning()) {
                    stop.setEnabled(false);
                    scan.setEnabled(true);
                    File extStore = Environment.getExternalStorageDirectory();
                    String path = extStore.getAbsolutePath() + "/";
                    Message msg = Message.obtain(null, ScanService.MSG_SCAN_STOP);
                    sendMessageToService(msg);
                }
            }
        });

        mMessenger = new Messenger(new IncomingHandler());
    }

    private void sendMessageToService(int msgType, int value) {
        Message msg = Message.obtain(null, msgType, value, 0);
        sendMessageToService(msg);
    }

    private void sendMessageToService(Message msg) {
        if (isBound) {
            if (scanService != null) {
                try {
                    mServiceMessenger.send(msg);
                } catch (RemoteException e) {
                }
            }
        }
    }

    private ScanServiceConnection mConnection = new ScanServiceConnection();

    private class ScanServiceConnection implements ServiceConnection {
        public Activity activity;

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {

            Log.d(TAG, "onServiceConnected");
            ScanService.LocalBinder binder = (ScanService.LocalBinder) service;
            scanService = binder.getServiceInstance();
            isScanning = scanService.isScanning();
            isBound = true;
            if (isScanning) {
                scan.setEnabled(false);
                scan.setText("Scanning...");
                stop.setEnabled(true);

            } else {
                scan.setEnabled(true);
                scan.setText("Scan");
                stop.setEnabled(false);
            }
            mServiceMessenger = new Messenger(binder.getBinder());
            Message msg = Message.obtain(null, ScanService.MSG_REGISTER_CLIENT);
            msg.replyTo = mMessenger;
            sendMessageToService(msg);

        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.d(TAG, "onServiceDisconnected");
            isBound = false;
            scanService = null;
        }
    }

    void doUnbindService() {
        if (isBound) {
//            scanService.stopScanning();
            Message msg = Message.obtain(null, ScanService.MSG_UNREGISTER_CLIENT);
            msg.replyTo = mMessenger;
            sendMessageToService(msg);
            unbindService(mConnection);
            isBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            doUnbindService();
        } catch (Throwable t) {
            Log.e("MainActivity", "Failed to unbind from the service", t);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "onServiceDisconnected");
    }
}