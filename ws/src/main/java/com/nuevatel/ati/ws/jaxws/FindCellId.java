
package com.nuevatel.ati.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "findCellId", namespace = "http://ws.ati.nuevatel.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findCellId", namespace = "http://ws.ati.nuevatel.com/", propOrder = {
    "name",
    "type"
})
public class FindCellId {

    @XmlElement(name = "name", namespace = "")
    private String name;
    @XmlElement(name = "type", namespace = "")
    private Byte type;

    /**
     * 
     * @return
     *     returns String
     */
    public String getName() {
        return this.name;
    }

    /**
     * 
     * @param name
     *     the value for the name property
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     returns Byte
     */
    public Byte getType() {
        return this.type;
    }

    /**
     * 
     * @param type
     *     the value for the type property
     */
    public void setType(Byte type) {
        this.type = type;
    }

}
