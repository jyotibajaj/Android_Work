package letsdecode.com.loginapp;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by aashi on 20/04/16.
 */
public class LogMessage {

    public static void logInfo(Context c, String message){
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();

    }
}
