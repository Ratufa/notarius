package com.munzbit.notarius.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.munzbit.notarius.R;
import com.munzbit.notarius.modal.WorkOutModal;

import java.util.ArrayList;

/**
 * Created by Ratufa.Manish on 8/18/2015.
 */
public class GridAdapter extends BaseAdapter {

    private ArrayList<WorkOutModal> workOutData;

    private LayoutInflater layoutInflater;

    public GridAdapter(Context context, ArrayList<WorkOutModal> workOutList){
        this.workOutData = workOutList;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return workOutData.size();
    }

    @Override
    public Object getItem(int position) {
        return workOutData.get(position);
    }

    @SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.grid_adapter, null);

        TextView textView =(TextView) view.findViewById(R.id.workBtn);

        if(workOutData.get(position).getWorkOutTitle().equals("+")){
            textView.setTextSize(25f);
        }else{
            textView.setTextSize(15f);
        }
        textView.setText(workOutData.get(position).getWorkOutTitle());

        return view;
    }
}
