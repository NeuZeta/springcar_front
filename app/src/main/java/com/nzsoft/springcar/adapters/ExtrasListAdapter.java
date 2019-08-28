package com.nzsoft.springcar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nzsoft.springcar.R;
import com.nzsoft.springcar.activities.MainActivity;
import com.nzsoft.springcar.model.CommonExtra;

import java.util.List;

public class ExtrasListAdapter extends BaseAdapter {

    private LayoutInflater inflater = null;
    private List<CommonExtra> extras;

    public ExtrasListAdapter (Context context, List<CommonExtra> extras){
        this.extras = extras;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return extras.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.row_model_extra, null);

        TextView extraDescription = (TextView) view.findViewById(R.id.idExtraDescription);
        TextView extraPrice = (TextView) view.findViewById(R.id.idExtraPrice);

        CommonExtra extra = extras.get(position);

        extraDescription.setText(extra.getName());
        extraPrice.setText(extra.getPrice() + "€");

        return view;
    }
}
