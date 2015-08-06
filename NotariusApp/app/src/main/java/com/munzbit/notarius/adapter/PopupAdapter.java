package com.munzbit.notarius.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.munzbit.notarius.R;
import com.munzbit.notarius.activity.SettingsActivity;
import com.munzbit.notarius.modal.TimerModal;

import java.util.ArrayList;

/**
 * Created by Ratufa.Manish on 8/6/2015.
 */
public class PopupAdapter extends BaseAdapter {

    private ArrayList<TimerModal> dataList;

    private Context context;

    private LayoutInflater layoutInflater;

    public PopupAdapter(Context ctx,ArrayList<TimerModal> arrayList) {
        this.context = ctx;
        this.dataList = arrayList;
        layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public TimerModal getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public int getCount() {

        return dataList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        TimerModal timerModal = getItem(position);

        View view = layoutInflater.inflate(R.layout.popup_layout,null);

        TextView textView =(TextView) view.findViewById(R.id.dayTv);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.repeatCheckbox);

        textView.setText(timerModal.getTimerType());

        checkBox.setTag(timerModal);

        if(position==0){
            checkBox.setVisibility(View.INVISIBLE);
        }else{
            checkBox.setVisibility(View.VISIBLE);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                TimerModal timerModal =(TimerModal) buttonView.getTag();
                timerModal.setIsSelected(isChecked);

                if(isChecked){
                    dataList.get(position).setIsSelected(true);
                }if(!isChecked){
                    dataList.get(position).setIsSelected(false);
                }
                ((SettingsActivity)context).getSelectedItems(dataList);
            }
        });

        checkBox.setChecked(timerModal.getIsSelected());

        return view;
    }
}
