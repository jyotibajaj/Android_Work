package letsdecode.com.loginapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by aashi on 17/06/16.
 */
public class DoneListViewAdapter extends ArrayAdapter<Item> {

    public DoneListViewAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(Item itemJava) {
        super.add(itemJava);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }


    @Override
    public Item getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater itemInflater = LayoutInflater.from(getContext());
        View customView = itemInflater.inflate(R.layout.layout_done, parent, false);
        final Item item = getItem(position);
        TextView itemDoneDetailText = (TextView) customView.findViewById(R.id.textView_doneDetail);
        itemDoneDetailText.setText(item.getItemName() + " " + item.getQuantity());
        return customView;

    }
}



