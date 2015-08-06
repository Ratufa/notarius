package com.munzbit.notarius.modal;

/**
 * Created by Ratufa.Manish on 8/6/2015.
 */
public class TimerModal {

    private  String timerType;
    private  Boolean isSelected;

    public String getTimerType() {
        return timerType;
    }

    public void setTimerType(String timerType) {
        this.timerType = timerType;
    }

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public String toString() {
        return "TimerModal{" +
                "timerType='" + timerType + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
