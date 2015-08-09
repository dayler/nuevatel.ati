
package com.nuevatel.ati.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.nuevatel.ati.ws.ATIResponse;

@XmlRootElement(name = "findCellIdResponse", namespace = "http://ws.ati.nuevatel.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findCellIdResponse", namespace = "http://ws.ati.nuevatel.com/")
public class FindCellIdResponse {

    @XmlElement(name = "return", namespace = "")
    private ATIResponse _return;

    /**
     * 
     * @return
     *     returns ATIResponse
     */
    public ATIResponse getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(ATIResponse _return) {
        this._return = _return;
    }

}
