package com.test.commitlog.business;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

/**
 * File Reader Implementation
 *
 */
public class FileReader implements IFileOps {

    private BlockingQueue<String> readerQ;
    private Semaphore lock;
    private int numThread;

    public FileReader(int numThread) {
        this.numThread = numThread;
        this.lock = new Semaphore(1);
        this.readerQ = new ArrayBlockingQueue<>(100);
    }

    public void start() {
        for (int i = 0; i < numThread; i++) {
            ReaderThread t = new ReaderThread();
            t.start();
        }
    }

    private boolean shutdown;

    public void shutdown() {
        this.shutdown = true;
    }

    public void add(String line) {
        readerQ.offer(line);
    }

    private class ReaderThread extends Thread {

        public void run() {
            while (!shutdown) {
                try {
                    lock.acquire();
                    String data = readerQ.poll();
                    if (data != null) System.out.println(data);
                    lock.release();
                } catch (InterruptedException ignored) {
                }
            }
        }
    }
}
