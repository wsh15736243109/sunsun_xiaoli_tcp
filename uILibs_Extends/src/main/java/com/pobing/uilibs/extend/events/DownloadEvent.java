package com.pobing.uilibs.extend.events;

/**
 * Created by 释迦 on 14-10-4.
 */
public class DownloadEvent {

    public static class ErrorEvent extends DownloadEvent {
    }

    public static class SuccessEvent extends DownloadEvent {
    }

    public static class FailureEvent extends DownloadEvent {
    }
}
