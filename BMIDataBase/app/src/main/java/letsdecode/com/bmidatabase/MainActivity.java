package letsdecode.com.bmidatabase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;


public class MainActivity extends Activity {

    Intent intentDisplayData;
    BMITableAdapter db;
    String currentDateTimeString;
    DisplayBMIHistory displayBMIHistory;

// textView is the TextView view that should display it

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            final EditText weight_EditText = (EditText) findViewById(R.id.weight_EditText);
            final EditText height_EditText = (EditText) findViewById(R.id.height_EditText);
            final Button calculateBMIButton = (Button) findViewById(R.id.BMI_Button);
            final TextView displayBMITextView = (TextView) findViewById(R.id.displayResult_textView);

            db = new BMITableAdapter(getApplicationContext());
    displayBMIHistory = new DisplayBMIHistory();

        calculateBMIButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String height = height_EditText.getText().toString();
                    String weight = weight_EditText.getText().toString();
                    double heightValue = Double.parseDouble(height);
                    double weightValue = Double.parseDouble(weight);
                    double result = calBMI(weightValue, heightValue);
                    displayBMITextView.setText("    BMI  " + result);
                    db.openDB();
                    currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                    db.insertValues(currentDateTimeString, "" + result);
//                    db.closeDB();

                    Toast.makeText(getApplicationContext(), "record saved", Toast.LENGTH_SHORT).show();


                }
            });
        }

        private double calBMI(double weight, double height) {
            double bmi = (weight * 703) / (height * height);
            double temp = bmi * 10;//53.333
            int value = (int) temp; // 53
            bmi = ((double) value) / 10; // 53.0 /10
            return bmi;
        }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        db.openDB();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        db.closeDB();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.see_history_item:
                intentDisplayData =  new Intent(getApplicationContext(), DisplayBMIHistory.class);
                startActivity(intentDisplayData);
                break;

            case R.id.delete_all_item:
                db.deleteRecord("11");
                Toast.makeText(getApplicationContext(), "all records deleted", Toast.LENGTH_SHORT).show();
//                displayBMIHistory.updateListView();
                break;


        }


        return super.onOptionsItemSelected(item);
    }




}