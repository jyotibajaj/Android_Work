package letsdecode.com.loginapp;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link NotPurchasedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotPurchasedFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = NotPurchasedFragment.class.getSimpleName();
    public static final String ITEM = "SELECTED_ITEM_VALUE";
    private OnFragmentInteractionListener mListener;

    private List<String> items = new ArrayList<>();
    private ListView listView;
    private ArrayAdapter<String> itemsAdapter;
    private Button addButton;


    //private OnFragmentInteractionListener mListener;

    public NotPurchasedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotPurchasedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotPurchasedFragment newInstance(String param1, String param2) {
        NotPurchasedFragment fragment = new NotPurchasedFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_not_purchased, container, false);
    }

    //    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
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

    //
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.listView_notPurchased);
        SQLiteDataAdapter sqLiteDataAdapterNotPurchased = new SQLiteDataAdapter(getActivity().getApplicationContext());
        ArrayList<Item> itemList = sqLiteDataAdapterNotPurchased.getToDoItemData();
        ListViewAdapter listViewAdapter = new ListViewAdapter(getActivity(), R.layout.listview_layout);
        listViewAdapter.addAll(itemList);
        listView.setAdapter(listViewAdapter);


//                    new entry added in the dataBase
//                    //By this time 'items' should already be updated with recent entry
//                    writeItemsToFile(items);
//                }
//            }
//        });
    }

    //        //reference of list view
//        listView = (ListView) view.findViewById(R.id.listView_notPurchased);
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d(TAG, "setupListViewLongListener. onItemLongClick: @@@@@@@" + position);
//                Log.d(TAG, "onItemLongClick: removed" + position);
//                //remove the selected item from the linked list.
//                items.remove(position);
//                itemsAdapter.notifyDataSetChanged();
//                listView.setSelection(itemsAdapter.getCount() - 1);
//                writeItemsToFile(items);
//                return true;
//            }
//        });
//
//
////              //reference to button
//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String newItemText = editText.getText().toString();
//                if (newItemText != null && newItemText.trim().isEmpty() == false) {
//                    //following code updates 'items' as well
//                    itemsAdapter.add(newItemText);
//                    Log.d(TAG, "onAddItem: iTemTxt " + newItemText);
//                    editText.setText("");
//                    //new entry added in the file
//                    //By this time 'items' should already be updated with recent entry
//                    writeItemsToFile(items);
//                }
//            }
//        });
//        //button disabled initially
//        addButton.setEnabled(false);
//
//        //read items from file
//        items = readItemsFromFile();
//        //initializing arrayadapter
//        itemsAdapter = new ArrayAdapter<>(this,R.layout.list_layout, items);
//        listView.setAdapter(itemsAdapter);
//
//        Log.d(TAG, "onCreate: Items list" + items);
//
//
//
//        //for keyboard focus
//        getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//    }
//

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
//
//
//
//
//
//    private List<String> readItemsFromFile() {
//        File fileDir = getFilesDir();
//        File toDoFile = new File(fileDir, "todo.txt");
//        List<String> res = new ArrayList<>();
//        try {
//            res = FileUtils.readLines(toDoFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return res;
//    }
//
//    private void writeItemsToFile(List<String> listToSave) {
//        File fileDir = getFilesDir();
//        File toDoFile = new File(fileDir, "todo.txt");
//        try {
//            if (listToSave != null) {
//                FileUtils.writeLines(toDoFile, listToSave);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Call Back method  to get the edited text  form other Activity
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        // check if the request code is same as what is passed  here it is 1
//        if (resultCode == RESULT_OK) {
//            if (requestCode == EditActivity.REQUEST_CODE_EDIT) {
//                if (null != data) {
//                    // fetch the data String
//                    String textForList = data.getStringExtra(EditActivity.EDITED_ITEM_VALUE);
//                    if (textForList != null && textForList.trim().isEmpty() == false) {
//                        items.set(savedPositionOfEditedItem, textForList);
//                        itemsAdapter.notifyDataSetChanged();
//                        writeItemsToFile(items);
//                    }
//                }
//            }
//        }
//    }
//
//
//}
//

}

