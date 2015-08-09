package com.nuevatel.ati.ws;

import com.nuevatel.ati.core.service.AppServerFactory;
import com.nuevatel.cf.appconn.AnytimeInterrogationCall;
import com.nuevatel.cf.appconn.AnytimeInterrogationRet;
import com.nuevatel.common.exception.OperationException;
import com.nuevatel.common.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jws.WebService;

/**
 * Created by asalazar on 7/21/15.
 */
@WebService(endpointInterface = "com.nuevatel.ati.ws.ATIWS", serviceName = "ati")
public class ATIWSService implements ATIWS {

    private static Logger logger = LogManager.getLogger(ATIWSService.class);

    private AppServerFactory appServerFactory = new AppServerFactory();

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public ATIResponse findCellId(String name, Byte type) throws OperationException {
        if (StringUtils.isBlank(name) || type == null) {
            logger.error("name={} or type={} is null", name, type);
            return new ATIResponse(Response.forFailedResponse("'name' or 'type' is null"));
        }

        if (appServerFactory.get().isEmpty()) {
            logger.error("No appconn clients are registered. name={} or type={}", name, type);
            return new ATIResponse(new Response("NULL_CONN", "No clients connected..."));
        }

        try {
            AnytimeInterrogationCall atiCall = new AnytimeInterrogationCall(name, type);
            AnytimeInterrogationRet atiRet = new AnytimeInterrogationRet(appServerFactory.get().dispatch(atiCall.toMessage()));
            logger.debug("For name={} type={} returned {}", name, type, atiRet.toString());
            return new ATIResponse(atiRet.getName(), atiRet.getType(), atiRet.getCellId());
        } catch (Exception ex) {
            logger.error("Failed to dispatch message. name={} or type={}", name, type, ex);
            return new ATIResponse(Response.forFailedResponse("Failed to dispatch message..."));
        }
    }
}
