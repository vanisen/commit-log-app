package com.test.commitlog.business;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * File Writer Implementation
 *
 */
public class FileWriter implements IFileOps {

    private String name;
    private int numThread;
    private int startThread;
    private Semaphore lock;
    private AtomicInteger counter = new AtomicInteger(0);
    private FileQueue queue;
    private boolean shutdown;

    public FileWriter(String name, int numThread, int startThread, FileQueue queue) {
        this.name = name;
        this.numThread = numThread;
        this.startThread = startThread;
        this.queue = queue;
        lock = new Semaphore(1);
    }

    public void start() {
        for (int i = 0; i < numThread; i++) {
            WriterThread t = new WriterThread(startThread + i);
            t.start();
        }
    }

    public void shutdown() {
        this.shutdown = true;
    }

    private class WriterThread extends Thread {

        WriterThread(int n) {
            this.setName("T" + n);
        }

        public void run() {
            while (!shutdown) {
                try {
                    lock.acquire();
                    // System.out.println(name + ":" + counter.getAndIncrement()
                    // + ":" + "Data from " + getName());
                    queue.add(name + ":" + counter.getAndIncrement() + ":" + "Data from " + getName());
                    lock.release();
                } catch (InterruptedException ignored) {
                }
            }
        }
    }
}
