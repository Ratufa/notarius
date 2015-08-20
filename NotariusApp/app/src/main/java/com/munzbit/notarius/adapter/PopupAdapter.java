package com.munzbit.notarius.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.munzbit.notariusdemo.R;

/**
 * Created by Ratufa.Manish on 8/6/2015.
 */
public class PopupAdapter extends BaseAdapter {

    private CharSequence[] dataList;

    private Context context;

    private LayoutInflater layoutInflater;

    private boolean[] checkedItemsList;

    public PopupAdapter(Context ctx, CharSequence[] arrayList,boolean[] checkedItems) {
        this.context = ctx;
        this.dataList = arrayList;
        layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);     
        this.checkedItemsList = checkedItems;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public CharSequence getItem(int position) {
        return dataList[position];
    }

    @Override
    public int getCount() {

        return dataList.length;
    }

    @SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        CharSequence timerModal = getItem(position);

        View view = layoutInflater.inflate(R.layout.popup_layout,null);

        TextView textView =(TextView) view.findViewById(R.id.dayTv);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.repeatCheckbox);

        textView.setText("Every " + timerModal);

        checkBox.setTag(timerModal);

        checkBox.setChecked(checkedItemsList[position]);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // TimerModal timerModal =(TimerModal) buttonView.getTag();
                //timerModal.setIsSelected(isChecked);

                if (isChecked) {
                    ((AlarmPreferencesActivity) context).updateDays(position,true);
                    // dataList.get(position).setIsSelected(true);
                    //dataManager.updateFrequency(dataList.get(position).getTimerType(),"true");
                }
                if (!isChecked) {
                    ((AlarmPreferencesActivity) context).updateDays(position,false);
                    // dataList.get(position).setIsSelected(false);
                    //dataManager.updateFrequency(dataList.get(position).getTimerType(), "false");
                }


            }
        });

        //checkBox.setChecked(timerModal.getIsSelected());

        return view;
    }
}
