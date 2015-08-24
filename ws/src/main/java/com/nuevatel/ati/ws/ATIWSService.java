package com.nuevatel.ati.ws;

import com.nuevatel.ati.core.service.AppServerFactory;
import com.nuevatel.base.appconn.AppMessages;
import com.nuevatel.cf.appconn.AnytimeInterrogationCall;
import com.nuevatel.cf.appconn.AnytimeInterrogationRet;
import com.nuevatel.common.exception.OperationException;
import com.nuevatel.common.exception.OperationRuntimeException;
import com.nuevatel.common.util.StringUtils;
import com.nuevatel.common.util.UniqueID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jws.WebService;
import java.security.NoSuchAlgorithmException;

/**
 * Created by asalazar on 7/21/15.
 */
@WebService(endpointInterface = "com.nuevatel.ati.ws.ATIWS", serviceName = "ati")
public class ATIWSService implements ATIWS {

    private static Logger logger = LogManager.getLogger(ATIWSService.class);

    private static int MAX_VALUE_FOR_GENERATED_ID = Integer.MAX_VALUE - 1;

    private UniqueID uniqueID;

    private AppServerFactory appServerFactory = new AppServerFactory();

    public ATIWSService() {
        try {
            uniqueID = new UniqueID();
        } catch (NoSuchAlgorithmException ex) {
            throw new OperationRuntimeException("Failed to get New UniqueId instance for ATIWSService", ex);
        }
    }

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
            return new ATIResponse(new Response("NO_APP_CLIENTS", "No clients connected..."));
        }

        try {
            Integer id = uniqueID.nextInt(MAX_VALUE_FOR_GENERATED_ID);
            AnytimeInterrogationCall atiCall = new AnytimeInterrogationCall(id, name, type);
            AnytimeInterrogationRet atiRet = new AnytimeInterrogationRet(appServerFactory.get().dispatch(atiCall.toMessage()));
            logger.debug("For name={} type={} returned {}", name, type, atiRet.toString());
            ATIResponse resp = new ATIResponse(name, type, atiRet.getCellId());

            if (AppMessages.FAILED == atiRet.getRetCode()) {
                logger.error("Not found. name={} or type={}", name, type);
                return new ATIResponse(new Response("NOT_FOUND", String.format("Not found cell id for %s...", name)));
            }

            return new ATIResponse(name, type, atiRet.getCellId());
        } catch (Exception ex) {
            logger.error("Failed to dispatch message. name={} or type={}", name, type, ex);
            return new ATIResponse(Response.forFailedResponse("Failed to dispatch message..."));
        }
    }
}
