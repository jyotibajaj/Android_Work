package letsdecode.com.macyapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileList extends Activity {

        private File file;
        private List<String> myList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.list);
        myList = new ArrayList<String>();
        if (Environment.isExternalStorageEmulated()) {
            String root_sd = Environment.getExternalStorageDirectory().toString();
            File f = Environment.getExternalStorageDirectory();
//        Map<String, File> externalLocations = ExternalStorage.getAllStorageLocations();
//        File sdCard = externalLocations.get(ExternalStorage.SD_CARD);
//        File externalSdCard = externalLocations.get(ExternalStorage.EXTERNAL_SD_CARD);
////        file = new File(root_sd + "/external_sd");
            file = new File(root_sd);
            File list[] = file.listFiles();
            list = f.listFiles();
            for (int i = 0; i < list.length; i++) {
                myList.add(list[i].getName());
            }

            listView.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, myList));

        }

        else {
            throw new NullPointerException("SD card is not mounted");
        }
    }

//    protected void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
//
//        File temp_file = new File(file, myList.get(position));
//
//        if (!temp_file.isFile()) {
//            file = new File(file, myList.get(position));
//            File list[] = file.listFiles();
//
//            myList.clear();
//
//            for (int i = 0; i < list.length; i++) {
//                myList.add(list[i].getName());
//            }
//            Toast.makeText(getApplicationContext(), file.toString(), Toast.LENGTH_LONG).show();
//            setListAdapter(new ArrayAdapter<String>(this,
//                    android.R.layout.simple_list_item_1, myList));
//
//        }
//
//    }


    @Override
    public void onBackPressed() {
        String parent = file.getParent().toString();
        file = new File(parent);
        File list[] = file.listFiles();

        myList.clear();

        for (int i = 0; i < list.length; i++) {
            myList.add(list[i].getName());
        }
        Toast.makeText(getApplicationContext(), parent, Toast.LENGTH_LONG).show();
//        setListAdapter(new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, myList));
//

    }


}
