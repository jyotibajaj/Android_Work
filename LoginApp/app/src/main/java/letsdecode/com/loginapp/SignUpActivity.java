package letsdecode.com.loginapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {


    SQLiteDataAdapter dataBaseHelper;
    //Button reference
    Button signUP_button;
    //editText reference
    EditText editText_userName, editText_password, editText_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        dataBaseHelper = new SQLiteDataAdapter(SignUpActivity.this);
        editText_name = (EditText) findViewById(R.id.editText_emailId);
        editText_userName = (EditText) findViewById(R.id.editText_usernameSignUp);
        editText_password = (EditText) findViewById(R.id.editText_passwordSignUp);
        signUP_button = (Button) findViewById(R.id.button_SignUp);


         /*
        for this it checks whether the data is already there, if not it lets the user sign up otherwise it prompts with please sign in message.
         */
        signUP_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText_name.getText().toString();
                String userName = editText_userName.getText().toString();
                String password = editText_password.getText().toString();
                String userNameAndPassword = dataBaseHelper.validateEnteredInfoWhileSignUp(userName, password, name);
                //if not empty, means match with already entered data base values
                if (!(userNameAndPassword.isEmpty())) {
                    LogMessage.logInfo(SignUpActivity.this, "You have already Signup, please login");

                    // if user didnt enter anything
                } else if (userName.isEmpty() || password.isEmpty()) {
                    LogMessage.logInfo(SignUpActivity.this, "please enter the complete information");
                } else if (userNameAndPassword.isEmpty()) {

                    long id = dataBaseHelper.insertLoginData(userName, password, name);
                    // check id is less than one, not inserted
                    if (id < 0) {
                        LogMessage.logInfo(SignUpActivity.this, "Unsuccessful insertion of a row");

                    } else {
                        LogMessage.logInfo(SignUpActivity.this, "Successful insertion of a row");

                    }

                }
                Intent intentLogin = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intentLogin);
                finish();
            }

        });

    }
}
