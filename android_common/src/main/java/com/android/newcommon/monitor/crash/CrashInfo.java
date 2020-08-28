package com.android.newcommon.monitor.crash;

public class CrashInfo {
    public String errorContent;

    public long time;

    public CrashInfo(String errorContent, long time) {
        this.errorContent = errorContent;
        this.time = time;
    }
}