package com.munzbit.notarius.modal;

/**
 * Created by Ratufa.Manish on 8/18/2015.
 */
public class WorkOutModal {

    private String workOutTitle;

    private boolean selected;

    public String getWorkOutTitle() {
        return workOutTitle;
    }

    public void setWorkOutTitle(String workOutTitle) {
        this.workOutTitle = workOutTitle;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "WorkOutModal{" +
                "workOutTitle='" + workOutTitle + '\'' +
                ", selected=" + selected +
                '}';
    }
}
