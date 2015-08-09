package com.nuevatel.ati.ws;

import com.nuevatel.common.util.Parameters;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Base Response class
 */
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.NONE)
public class Response {

    public static final String OK = "OK";

    public static final String FAILED = "FAILED";

    @XmlElement(name = "result")
    private String result;

    @XmlElement(name = "message")
    private String message;

    public Response () {
        // No op
    }

    public Response(Response response) {
        Parameters.checkNull(response, "response");

        result = response.getResult();
        message = response.getMessage();
    }

    public Response(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static Response forFailedResponse(String message) {
        return new Response(FAILED, message);
    }
}
