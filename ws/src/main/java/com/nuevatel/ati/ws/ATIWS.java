package com.nuevatel.ati.ws;

import com.nuevatel.common.exception.OperationException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.BindingType;

/**
 * Web Service Interface for any time interrogation.
 * <br/><br/>
 * <li>findCellId: Return the cell id in which is located the <code>name</code></li>
 */
@WebService(name = "AtiWSPort")
@BindingType(value="http://java.sun.com/xml/ns/jaxws/2003/05/soap/bindings/HTTP/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface ATIWS {

    /**
     *
     * @param name MSISDN or IMSI, the kind must to specifying in <code>type</code> parameter.
     * @param type Identify the kind of the <code>name</code> parameter. <b>0x1<b/> indicates <b>MSISDN</b>. <b>0x2</b>
     *             indicates <b>IMSI</b>.
     * @return CellId in which is located the <code>name</code>
     * @throws OperationException An severe error has been occurred.
     */
    @WebMethod(operationName = "findCellId")
    ATIResponse findCellId(@WebParam(name = "name") String name,
                           @WebParam(name = "type") Byte type) throws OperationException;
}
