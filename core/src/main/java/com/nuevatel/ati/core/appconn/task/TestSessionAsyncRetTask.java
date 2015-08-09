package com.nuevatel.ati.core.appconn.task;

import com.nuevatel.base.appconn.Conn;
import com.nuevatel.base.appconn.Message;
import com.nuevatel.base.appconn.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Handle test TestSessionAsyncRet event. Return null to responds.
 *
 * @author Ariel Salazar
 */
public class TestSessionAsyncRetTask implements Task {

    private static Logger logger = LogManager.getLogger(TestSessionAsyncRetTask.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Message execute(Conn conn, Message message) throws Exception {
        if (logger.isDebugEnabled() || logger.isTraceEnabled()) {
            logger.debug("TestSessionAsyncTask message:{}", message == null ? null : message.toXML());
        }
        return null;
    }
}
