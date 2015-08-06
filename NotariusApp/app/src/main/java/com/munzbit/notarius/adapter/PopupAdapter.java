package com.munzbit.notarius.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.munzbit.notarius.R;

import java.util.ArrayList;

/**
 * Created by Ratufa.Manish on 8/6/2015.
 */
public class PopupAdapter extends BaseAdapter {

    private ArrayList<String> dataList;

    private Context context;

    private LayoutInflater layoutInflater;

    public PopupAdapter(Context ctx,ArrayList<String> arrayList) {
        this.context = ctx;
        this.dataList = arrayList;
        layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public int getCount() {

        return dataList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.popup_layout,null);

        TextView textView =(TextView) view.findViewById(R.id.dayTv);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.repeatCheckbox);

        textView.setText(dataList.get(position));

        checkBox.setTag(position);

        if(position==0){
            checkBox.setVisibility(View.INVISIBLE);
        }else{
            checkBox.setVisibility(View.VISIBLE);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        return view;
    }
}
