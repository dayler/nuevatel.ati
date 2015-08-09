package com.nuevatel.ati.core.appconn;

import com.nuevatel.base.appconn.AppServer;
import com.nuevatel.base.appconn.Conn;
import com.nuevatel.base.appconn.Message;
import com.nuevatel.base.appconn.TaskSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * Extension of {@link AppServer}, implements <code>dispatch(msg)</code> method. Dispatch messages without specifying
 * the remote id, selects remote id according its internal logic.
 */
public class ATIAppServer extends AppServer {

    /**
     * List for ids of connected remote clients.
     */
    private List<Integer>remoteIds = Collections.synchronizedList(new ArrayList<>());

    /**
     * Point to next remote id.
     */
    private int itIndex = -1;

    /**
     *
     * @param localId Id with which is registered the server.
     * @param taskSet List of handlers for the messages.
     * @param properties Properties to initialize the server.
     */
    public ATIAppServer(int localId, TaskSet taskSet, Properties properties) {
        super(localId, taskSet, properties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Conn conn) {
        if (conn == null) {
            return;
        }

        remoteIds.add(conn.getRemoteId());
        super.add(conn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(Conn conn) {
        if (conn == null) {
            return;
        }

        remoteIds.remove(conn.getRemoteId());
        super.remove(conn);
    }

    /**
     * Dispatch the message through any connection.
     *
     * @param msg Message to dispatch
     * @return Ret Message
     * @throws Exception
     */
    public synchronized Message dispatch(Message msg) throws Exception {
        if (remoteIds.isEmpty()) {
            // null if there ar no remote ids
            return null;
        }

        if (itIndex >= remoteIds.size()) {
            itIndex = 0;
        }
        // dispatch message
        return dispatch(remoteIds.get(itIndex++), msg);
    }

    /**
     *
     * @return <code>true</code> if have almost one client connected.
     */
    public boolean isEmpty() {
        return remoteIds.isEmpty();
    }
}
