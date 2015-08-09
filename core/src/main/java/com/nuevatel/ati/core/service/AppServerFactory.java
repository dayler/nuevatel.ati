package com.nuevatel.ati.core.service;

import com.nuevatel.base.appconn.TaskSet;
import com.nuevatel.ati.core.appconn.ATIAppServer;

import java.util.Properties;

/**
 * Initialize and provide an interface for a single instance for AppServer.
 *
 * @author Ariel D. Salazar H.
 */
public final class AppServerFactory {

    private static ATIAppServer server = null;

    public AppServerFactory(ATIAppServer srv) {
        AppServerFactory.server = srv;
    }

    public AppServerFactory() {
        // No op. used to prevent instantiation.
    }

    /**
     * Initialize an instance of AppServer and start it.
     *
     * @param id Id for the AppServer.
     * @param taskSet Set of tasks to handle the events in the AppServer.
     * @param prop Properties to configure AppServer.
     */
    public synchronized void start(Integer id, TaskSet taskSet, Properties prop) throws Exception {
        server = new ATIAppServer(id, taskSet, prop);
        server.start();
    }

    public synchronized void interrupt() {
        if (server == null) {
            return;
        }
        server.interrupt();
    }

    /**
     *
     * @return Return the started service. If the service is not started yet returns null.
     */
    public synchronized ATIAppServer get() {
        return server;
    }
}
