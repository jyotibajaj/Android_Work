package letsdecode.com.loginapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    //private CallbackManager callBackManager;
    private TextView textViewData;
    SQLiteDataAdapter dataBaseHelper;
    private Button button_Login, button_SignUp, button_ViewDetails;
    private EditText editText_userName, editText_password;
    /*
    whether the login is successful or not, this need to be told to us for that we use
    FacebookCallBack class, this is a generic class.
     */


//    private FacebookCallback<LoginResult> callBack = new FacebookCallback<LoginResult>() {
//        @Override
//        public void onSuccess(LoginResult loginResult) {
//            AccessToken accessToken = loginResult.getAccessToken();
//            Profile profile = Profile.getCurrentProfile();
//            if (profile != null) {
////                textViewData.setText("Welcome " + profile.getName());
////            }
//                Intent intent = new Intent(getActivity().getApplicationContext(), LoginSuccessfulActivity.class);
//                startActivity(intent);
//
//            }
//        }

    //        @Override
//        public void onCancel() {
//
//        }
//
//        @Override
//        public void onError(FacebookException error) {
//
//        }
//    };
//
//
    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
//        callBackManager = CallbackManager.Factory.create();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        textViewData = (TextView) view.findViewById(R.id.textView_data);
        //loginButton.setReadPermissions("user_friends");
//        loginButton.setFragment(this);
//        loginButton.registerCallback(callBackManager, callBack);

        //
        dataBaseHelper = new SQLiteDataAdapter(getActivity().getApplicationContext());
        //reference of buttons
        button_Login = (Button) view.findViewById(R.id.button_login);
        button_SignUp = (Button) view.findViewById(R.id.button_signUP);
        //button_ViewDetails = (Button)view.findViewById(R.id.button_viewDetails);

        //reference of usernamext
        editText_userName = (EditText) view.findViewById(R.id.editText_username);
        editText_password = (EditText) view.findViewById(R.id.editText_password);
        // on login click it checks whether the data entered is already present in data base, based on that it performs action.
        button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userNameEntered = editText_userName.getText().toString();
                String password = editText_password.getText().toString();
                String userNameAndPassword = dataBaseHelper.validateEnteredInfoWhileLogin(userNameEntered, password);
                //if it matches with data base
                if (!(userNameAndPassword.trim().isEmpty())) {
                    LogMessage.logInfo(getActivity().getApplicationContext(), "Login Successfully");
                    Intent intent = new Intent(getActivity().getApplicationContext(), LoginSuccessfulActivity.class);
                    startActivity(intent);
                }
                // if user didn't enter anything
                else if (userNameEntered.isEmpty() || password.isEmpty()) {
                    LogMessage.logInfo(getActivity().getApplicationContext(), "please enter the complete information");
                }

                //if values entered doesn't match with the one in data base
                else if (userNameAndPassword == "") {
                    // if the entered info is incorrect or fields are empty.
                    LogMessage.logInfo(getActivity().getApplicationContext(), "Entered user Information is not correct, please enter it again");
                }
            }

        });


        button_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity().getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        callBackManager.onActivityResult(requestCode, resultCode, data);

    }
}


