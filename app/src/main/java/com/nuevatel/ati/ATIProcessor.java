package com.nuevatel.ati;

import com.nuevatel.ati.core.appconn.task.TestSessionAsyncRetTask;
import com.nuevatel.ati.core.service.AppServerFactory;
import com.nuevatel.ati.ws.ATIWSService;
import com.nuevatel.base.appconn.TaskSet;
import com.nuevatel.cf.appconn.CFMessage;
import com.nuevatel.common.Processor;

import com.nuevatel.common.exception.OperationException;
import com.nuevatel.common.exception.OperationRuntimeException;
import com.nuevatel.common.util.IntegerUtil;
import com.nuevatel.common.util.Parameters;
import com.nuevatel.common.wsi.EndpointSet;
import com.nuevatel.common.wsi.WSIPublisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.ws.Endpoint;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Create and initialize services.
 */
public class ATIProcessor implements Processor {

    private static Logger logger = LogManager.getLogger(ATIProcessor.class);

    private ExecutorService service = Executors.newScheduledThreadPool(1);

    private Properties properties;

    private WSIPublisher wsiPublisher = null;

    private AppServerFactory appServerFactory = new AppServerFactory();

    public ATIProcessor(Properties properties) {
        Parameters.checkNull(properties, "properties");
        this.properties = properties;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        service.execute(() -> {
            try {
                start();
            } catch (Exception ex) {
                // Propagate the exception, in order to stop the app.
                throw  new OperationRuntimeException("On ATIProcessor.execute()", ex);
            }
        });
    }

    /**
     * Start services and applications.
     *
     * @throws Exception
     */
    private void start() throws Exception {
        initializeAppServer();
        initializeWSIPublisher();
    }

    private void initializeAppServer() throws Exception {
        Integer appId = IntegerUtil.tryParse(properties.getProperty("ati.id"));
        if (appId == null) {
            throw new OperationException(String.format("Failed to initialize AppServer. AppId must be provided. ati.id=%s",
                                                        properties.getProperty("ati.id")));
        }
        TaskSet taskSet = new TaskSet();
        taskSet.add(CFMessage.TEST_SESSION_ASYNC_RET, new TestSessionAsyncRetTask());

        appServerFactory.start(appId, taskSet, properties);
        logger.info("AppServer was started...");
    }

    private void shutdownAppServer() {
        appServerFactory.get().interrupt();
        logger.info("Shutdown AppServer...");
    }

    private void initializeWSIPublisher() {
        try {
            EndpointSet endpointSet = new EndpointSet();
            endpointSet.add("ati", Endpoint.create(new ATIWSService()), null);
            wsiPublisher = new WSIPublisher(endpointSet, properties);
            wsiPublisher.start();
            logger.info("WSI was started. bindAddress:{} port:{} backlog:{}...",
                    properties.getProperty(WSIPublisher.BIND_ADDRESS),
                    properties.getProperty(WSIPublisher.PORT),
                    properties.getProperty(WSIPublisher.BACKLOG));
        } catch (Exception ex) {
            logger.error("Failed to initialize WSI. bindAddress:{} port:{} backlog:{}...",
                    properties.getProperty(WSIPublisher.BIND_ADDRESS),
                    properties.getProperty(WSIPublisher.PORT),
                    properties.getProperty(WSIPublisher.BACKLOG), ex);
        }
    }

    private void shutdownWSIPublisher() {
        wsiPublisher.interrupt();
        logger.info("Shutdown WSIPublisher...");
    }

    @Override
    public void shutdown(int i) {
        try {
            shutdownWSIPublisher();
            shutdownAppServer();
            // shutdown processor
            service.shutdown();
            service.awaitTermination(60, TimeUnit.SECONDS);
            logger.info("Shutdown ATIProcessor");
        } catch (InterruptedException ex) {
            logger.warn("ATIProcessor could not been finalized...");
        }
    }
}
