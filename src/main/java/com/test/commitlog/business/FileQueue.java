package com.test.commitlog.business;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Queue system for adding commit log items to queue and sync writer and reader
 * accordingly
 *
 */
public class FileQueue {

    private final BlockingQueue<String> writeQueue;
    private String logFile;
    private Map<String, FileReader> readerMap;
    private static int batchSize = 1000;

    public FileQueue(String logFile) {
        this.logFile = logFile;
        this.writeQueue = new ArrayBlockingQueue<>(100);
        Writer writer = new Writer();
        writer.start();
    }

    private boolean shutdown = false;


    public void add(String data) {
        synchronized (writeQueue) {
            this.writeQueue.offer(data);
        }
    }

    public void setReaderMap(Map<String, FileReader> map) {
        this.readerMap = map;
        Reader reader = new Reader();
        reader.start();
    }

    private class Writer extends Thread {
        @Override
        public void run() {
            int batch = 0;
            List<String> dataList = new ArrayList<>();
            while (!shutdown) {
                try {
                    String data = writeQueue.take();
                    if (batch < batchSize) {
                        dataList.add(data);
                        batch++;
                        continue;
                    }
                    try (FileWriter writer = new FileWriter(logFile, true)) {
                        writer.write(String.join("\n", dataList));
                        dataList = new ArrayList<>();
                        batch = 1;
                        dataList.add("\n" + data);
                    }
                } catch (InterruptedException | IOException e) {
                    System.err.println("Exception" + e.getMessage());
                }
            }
        }
    }


    private class Reader extends Thread {
        @Override
        public void run() {
            while (!shutdown) {
                try (BufferedReader br = new BufferedReader(new java.io.FileReader(logFile))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] data = line.split(":");
                        FileReader reader = readerMap.get(data[0].trim());
                        if (reader != null) reader.add(line);
                    }
                } catch (FileNotFoundException ignored) {

                } catch (IOException e) {
                    System.err.println("Exception during Log File Read" + e.getMessage());
                }
            }
        }
    }

    public void shutdown() {
        this.shutdown = true;
    }
}
