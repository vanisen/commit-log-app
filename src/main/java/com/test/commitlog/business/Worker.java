package com.test.commitlog.business;

import java.util.HashMap;
import java.util.Map;

import com.test.commitlog.entity.Config;
import com.test.commitlog.utils.AppConstants;

/**
 * Worker class to execute the core operation
 * 
 */
public class Worker {

    private Config config;

    public Worker(Config config) {
        this.config = config;
    }

    /**
     * Execute the core business logic
     */
    public void execute() {

        // Queue
        FileQueue queue = new FileQueue(config.getConfigFileName());

        // Kick off Writer
        int numStart;
        FileWriter a = new FileWriter(AppConstants.A, numStart = config.getNumOfWriterForA(), 1, queue);
        FileWriter b = new FileWriter(AppConstants.B, config.getNumOfWriterForB(), numStart + 1, queue);
        a.start();
        b.start();

        // Kick off Reader
        FileReader aReader = new FileReader(config.getNumOfWriterForA());
        FileReader bReader = new FileReader(config.getNumOfWriterForB());
        Map<String, FileReader> map = new HashMap<>();
        map.put(AppConstants.A, aReader);
        map.put(AppConstants.B, bReader);
        queue.setReaderMap(map);
        aReader.start();
        bReader.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }

        // Shutdown reader/writer/queue
        a.shutdown();
        b.shutdown();
        queue.shutdown();
        aReader.shutdown();
        bReader.shutdown();
    }
}
