/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.common.objects;

/**
 *
 * @author efabuba
 */
public class MapResources {
    private long popid;
    private long zoneid;
    private long resourceid;
    
    public MapResources(long pid, long zid, long resid) {
        popid = pid;
        zoneid = zid;
        resourceid = resid;   
    }
    
    public void setPopId(long id) {
        popid = id;
    }
    
    public long getPopId() {
        return popid;
    }
    
    public void setZoneId(long id) {
        zoneid = id;
    }
    
    public long getZoneId() {
        return zoneid;
    }
    
    public void setResourceId(long id) {
        resourceid = id;
    }
    
    public long getResourceId() {
        return resourceid;
    }
}
