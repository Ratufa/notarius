package com.munzbit.notarius.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.munzbit.notarius.R;
import com.munzbit.notarius.modal.WorkOutModal;


/**
 * Created by Ratufa.Manish on 8/18/2015.
 */
public class QuickListAdapter extends BaseAdapter {


    private ArrayList<WorkOutModal> workOutData;

    private LayoutInflater layoutInflater;

    public QuickListAdapter(Context context, ArrayList<WorkOutModal> workOutList){

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

        WorkOutModal workOutModal = workOutData.get(position);

        View view = layoutInflater.inflate(R.layout.quick_list_adapter, null);

        TextView textView =(TextView) view.findViewById(R.id.workOutTitle);

        TextView whiteTv =(TextView) view.findViewById(R.id.whiteIcon);

     
        if(position==0){
            whiteTv.setVisibility(View.GONE);
            textView.setTextSize(22f);
        }else{
            textView.setTextSize(18f);
            whiteTv.setVisibility(View.VISIBLE);
        }
        textView.setText(workOutModal.getWorkOutTitle());
     

        return view;
    }
}
