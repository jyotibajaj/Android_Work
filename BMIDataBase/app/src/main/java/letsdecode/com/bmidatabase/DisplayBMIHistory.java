package letsdecode.com.bmidatabase;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class DisplayBMIHistory extends Activity {
    ListView listView_BMI_display;
    Cursor curosr;
    BMITableAdapter tableAdapter;
    int rPostion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_bmihistory);
        setUI();
    }


    private void setUI() {
        // reference of list view
        listView_BMI_display = (ListView) findViewById(R.id.listView_bmi_data);
        tableAdapter = new BMITableAdapter(getApplicationContext());
        tableAdapter.openDB();
        updateListView();



    }


    void updateListView() {
            curosr = tableAdapter.getAllRecords();
            CustomAdapter adapter = new CustomAdapter();
            listView_BMI_display.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        menu.add("delete all");
        return super.onCreateOptionsMenu(menu);
    }



    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return curosr.getCount();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            convertView = getLayoutInflater().inflate(R.layout.single_row, null);
            TextView t_rowid = (TextView) convertView.findViewById(R.id.textView_row_id);
            TextView t_name = (TextView) convertView.findViewById(R.id.textView_bmi);
            TextView t_date = (TextView) convertView.findViewById(R.id.textView_date);
            curosr.moveToPosition(position);
            String id = curosr.getString(0);
            String date = curosr.getString(1);
            String name  = curosr.getString(2);
            t_rowid.setText(id);
            t_date.setText(date);
            t_name.setText(name);


            return convertView;
        }

    }
}




