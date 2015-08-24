package com.munzbit.notarius.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.munzbit.notarius.R;
import com.munzbit.notarius.activity.WorkOutActivity;
import com.munzbit.notarius.activity.WorkOutList;
import com.munzbit.notarius.datamanager.DataManager;
import com.munzbit.notarius.modal.WorkOutModal;

import java.util.ArrayList;

/**
 * Created by Ratufa.Manish on 8/18/2015.
 */
public class WorkoutAdapter extends BaseAdapter {

    private ArrayList<WorkOutModal> workOutData;

    private LayoutInflater layoutInflater;

    private DataManager dataManager;

    private Context ctx;

    public static int numberOfCheckboxesChecked = 0;

    public ArrayList<Boolean> arrayList = new ArrayList<>();

    public WorkoutAdapter(Context context, ArrayList<WorkOutModal> workOutList) {
        this.workOutData = workOutList;
        this.ctx = context;
        dataManager = new DataManager(context);
        arrayList = new ArrayList<>();
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        WorkOutModal workOutModal = workOutData.get(position);

        View view = layoutInflater.inflate(R.layout.workout_adapter, null);

        TextView textView = (TextView) view.findViewById(R.id.workOutTitle);

        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkboxWork);

        textView.setText(workOutModal.getWorkOutTitle());

        checkBox.setTag(workOutModal);

        checkBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        // TODO Auto-generated method stub
                        WorkOutModal workOutModal = (WorkOutModal) buttonView.getTag();
                        workOutModal.setSelected(isChecked);

                        if (isChecked) {
                            // numberOfCheckboxesChecked++;
                            if (!((WorkOutList) ctx).sizeExceeds(true)) {
                                dataManager.updateActivity(workOutModal.getWorkOutTitle(), "true");
                            } else {
                                checkBox.setChecked(false);
                            }
                        }
                        if (!isChecked) {
                            //numberOfCheckboxesChecked--;
                            ((WorkOutList) ctx).sizeExceeds(false);
                            dataManager.updateActivity(workOutModal.getWorkOutTitle(), "false");
                        }
                    }
                });
                return false;
            }
        });


        checkBox.setChecked(workOutModal.isSelected());

        return view;
    }


}
