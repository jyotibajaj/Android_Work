package letsdecode.com.loginapp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddItemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddItemFragment extends Fragment {
    private Button addButton, listButton;
    private EditText nameItemEditText, quantityEditText;
    SQLiteDataAdapter sqLiteDataAdapter;

    private OnFragmentInteractionListener mListener;

    public AddItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddItemFragment newInstance(String param1, String param2) {
        AddItemFragment fragment = new AddItemFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqLiteDataAdapter = new SQLiteDataAdapter(getActivity().getApplicationContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enter_item, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    //taking reference of buttons and edit texts.
    //onclick event of add button
       /*
       getting text from edit texts and inserting into data base.
        */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addButton = (Button) view.findViewById(R.id.button_addItem);
        listButton = (Button) view.findViewById(R.id.button_displayNotPurchasedList);

        nameItemEditText = (EditText) view.findViewById(R.id.editText_addItem);
        quantityEditText = (EditText) view.findViewById(R.id.editText_addQuantity);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemNameFromEdit = nameItemEditText.getText().toString();
                String quantityFromEdit = quantityEditText.getText().toString();
                if(itemNameFromEdit.isEmpty() || quantityFromEdit.isEmpty()) {
                    LogMessage.logInfo(getActivity().getApplicationContext(), "unsuccessful insertion of row");

                }
                else {
                    long id = sqLiteDataAdapter.insertItemData(itemNameFromEdit, quantityFromEdit);
                    if (id < 0) {
                        LogMessage.logInfo(getActivity().getApplicationContext(), "couldn't insert the data");
                    } else {
                        LogMessage.logInfo(getActivity().getApplicationContext(), "successful insertion of row");
                    }
                }


            }
        });


        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction2 = getFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.fragment_container, new NotPurchasedFragment()).commit();
            }
        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
