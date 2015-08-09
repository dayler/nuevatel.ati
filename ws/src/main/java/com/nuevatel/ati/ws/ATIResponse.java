package com.nuevatel.ati.ws;

import com.nuevatel.common.util.Parameters;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * WS Response
 */
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.NONE)
public class ATIResponse extends Response {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "type")
    private Byte type;

    @XmlElement(name = "cellId")
    private String cellId;

    public ATIResponse() {
        //
    }

    public ATIResponse(Response resp) {
        super(resp);
    }

    public ATIResponse(String name, Byte type, String cellId) {
        super(OK, null);
        this.name = name;
        this.type = type;
        this.cellId = cellId;
    }

    public String getCellId() {
        return cellId;
    }

    public String getName() {
        return name;
    }

    public Byte getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("ATIResponse{name=%s, type=%s, cellId=%s}", name, type, cellId);
    }
}
