package com.springmvc.thread;

public class TestSync {
    public static void main(String[] args) {
//        SyncThread syncThread = new SyncThread();
//        Thread thread1 = new Thread(syncThread, "thread1");
//        Thread thread2 = new Thread(syncThread, "thread2");
        Thread thread1 = new Thread(new SyncThread(), "thread1");
        Thread thread2 = new Thread(new SyncThread(), "thread2");
        thread1.start();
        thread2.start();
    }
}
