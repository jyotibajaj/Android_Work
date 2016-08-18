package letsdecode.com.loginapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


public class SavedDataActivity extends Activity {
    Intent intent;
    TextView textView_userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        textView_userInfo = (TextView)findViewById(R.id.textView_userInfo);
        intent = getIntent();
        String putInTextView =  intent.getStringExtra("User Data");
        textView_userInfo.setText(putInTextView);
    }


}
