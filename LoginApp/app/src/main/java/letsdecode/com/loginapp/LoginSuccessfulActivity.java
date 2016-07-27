package letsdecode.com.loginapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class LoginSuccessfulActivity extends Activity implements NotPurchasedFragment.OnFragmentInteractionListener, DoneFragment.OnFragmentInteractionListener, AddItemFragment.OnFragmentInteractionListener {
    private Button doneButton;
    private ImageButton addItemButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final NotPurchasedFragment fragmentNotPurchased = new NotPurchasedFragment();
        if (savedInstanceState == null) {
            fragmentTransaction.add(R.id.fragment_container, fragmentNotPurchased);
            fragmentTransaction.commit();
        }


        //reference to buttons
        doneButton = (Button) findViewById(R.id.done_button);
        addItemButton = (ImageButton) findViewById(R.id.button_addItem);


        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction2 = getFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.fragment_container, new DoneFragment()).commit();

            }
        });

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction3 = getFragmentManager().beginTransaction();
                fragmentTransaction3.replace(R.id.fragment_container, new AddItemFragment()).commit();

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
