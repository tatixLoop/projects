package com.jaapps.healthyrecipes;

/**
 * Created by jithin on 11/9/18.
 */

public class ListItemSteps {
    int stepNo;
    String step;

    public ListItemSteps(int stepNo, String step) {
        this.stepNo = stepNo;
        this.step = step;
    }

    public int getStepNo() {
        return stepNo;
    }

    public void setStepNo(int stepNo) {
        this.stepNo = stepNo;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }
}
