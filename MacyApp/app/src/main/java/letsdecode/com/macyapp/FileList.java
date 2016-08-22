package letsdecode.com.macyapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class FileList extends Activity {
    private final static String TAG = FileList.class.getSimpleName();
    private File file;
    private List<String> myList;
    private static ScanService scanService;
    private static boolean isBound;
    Button scan;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, ScanService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        TextView fileScanning = (TextView) findViewById(R.id.fileScanning);
        mConnection.activity = this;
        scan = (Button) findViewById(R.id.scan);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBound) {
                    scan.setEnabled(false);
                    File extStore = Environment.getExternalStorageDirectory();
                    String path = extStore.getAbsolutePath() + "/";
                    scanService.startScanning(path);
                }
            }
        });

        if (Environment.isExternalStorageEmulated()) {
            File extStore = Environment.getExternalStorageDirectory();
            String mPath = extStore.getAbsolutePath() + "/";
            final File f = new File(mPath);
            String[] ls = null;
            if (f.isDirectory()) {
                try {
                    ls = f.list();
                    if (ls != null) {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private static ScanServiceConnection mConnection = new ScanServiceConnection();
    private static class ScanServiceConnection implements ServiceConnection {
        public Activity activity;

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            Log.d(TAG, "onServiceConnected");
            ScanService.LocalBinder binder = (ScanService.LocalBinder) service;
            scanService = binder.getServiceInstance();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.d(TAG, "onServiceDisconnected");
            isBound = false;
            scanService = null;
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "onServiceDisconnected");
        if (isBound) {
            scanService.stopScanning();
        }
    }
}