package com.scu.ztz.yierschedulerutils.enums;
// Inspired by XXL-job
public enum TriggerTypeEnum {

    SCHEDULE("Scheduled-Fired"),
    MANUAL("Manual Fired"),
    FAIL_RETRY("Fail Retry"),
    MISFIRE("Misfire");

    private TriggerTypeEnum(String title){
        this.title = title;
    }
    private String title;
    public String getTitle() {
        return title;
    }

}