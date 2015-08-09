package com.nuevatel.ati;

import com.nuevatel.common.ShutdownHook;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Main class initialize standalone app.
 */
public class ATI {

    private static Logger logger = LogManager.getLogger(ATI.class);

    private static Properties properties = null;

    public static void main(String[]args) {
        try {
            String log4JPath = System.getProperty("log4j.configurationFile");
            System.out.println("Log4j2: " + log4JPath);
            logger.info("Log4j2: {}", log4JPath);

            loadProperties(args);
            // wait 1 minute to finish all process, 1 thread to do.
            ShutdownHook hook = new ShutdownHook(60, 1);
            ATIProcessor atiProcessor = new ATIProcessor(properties);
            hook.appendProcess(atiProcessor);
            atiProcessor.execute();

            logger.info("ATI Stand alone app was started properly...");
            Runtime.getRuntime().addShutdownHook(hook);
        } catch (Throwable ex) {
            logger.fatal("Failed to initialize ATI Stand alone app...", ex);
            System.exit(-1);
        }
    }

    private static void loadProperties(String[] args) throws IOException {
        InputStream is = null;

        try {
            if (args.length == 0) {
                is = ATI.class.getResourceAsStream("/ati.10.properties");
                logger.warn("Loading embedded resource properties...");
            } else {
                is = new FileInputStream(args[0]);
                logger.info("Loading properties from {}", args[0]);
            }

            properties = new Properties();
            properties.load(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public static Properties getProperties() {
        return properties;
    }
}
