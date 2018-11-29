/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.crosshaulplugin.sbi.objects;

/**
 *
 * @author efabuba
 */
public class RANService {
    private String resid;
    private String provid;
    private String tenid;
    private String srcid;
    private String dstid;
    private String qosid;
    private String servid;
    /*TODO: Insert CrosshaulInfo*/
    
    public RANService (String id, String prov, String ten, String srcip, String dstip, String qos, String serv) {
        resid = id;
        provid = prov;
        tenid = ten;
        srcid = srcip;
        dstid = dstip;
        qosid = qos;
        servid = serv;
    }
    
    
    public String getResId() {
        return resid;
    }
    public String getProvId() {
        return provid;
    }
    public String getTenId() {
        return tenid;
    }
    public String getSrcId() {
        return srcid;
    }
    public String getDstId() {
        return dstid;
    }
    public String getQosId() {
        return qosid;
    }
    public String getServId() {
        return servid;
    } 
    
    
}
    
