package com.test.commitlog;

import java.util.Properties;

import com.test.commitlog.business.Worker;
import com.test.commitlog.entity.Config;
import com.test.commitlog.utils.AppConstants;
import com.test.commitlog.utils.Utils;

/**
 * Main Entrypoint
 *
 */
public class Application {

    public static void main(String[] args) {

        String configFileName = null;
        if (args != null && args.length > 0) {
            configFileName = args[0];
        }

        Properties props = Utils.loadProperties(configFileName);

        Config config = initConfig(props);

        Worker worker = new Worker(config);
        worker.execute();
    }

    /**
     * Initialize configuration
     * 
     * @param props
     * @return config object
     */
    private static Config initConfig(Properties props) {
        Config config = new Config();
        config.setNumOfWriterForA(Integer.valueOf(props.getProperty(AppConstants.A)));
        config.setNumOfWriterForB(Integer.valueOf(props.getProperty(AppConstants.B)));
        config.setConfigFileName(props.getProperty(AppConstants.NAME));

        return config;
    }
}
