/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.events.placement;

/**
 *
 * @author efabuba
 */
public class PAComputeReply {
    private long reqid;
    private long servid; //service identifiers
    private long nfvipopid;
    private long domid;
    private long zoneid;

    public PAComputeReply(long reqid, long servid, long nfvipopid, long domid, long zoneid) {
        this.reqid = reqid;
        this.servid = servid;
        this.nfvipopid = nfvipopid;
        this.domid = domid;
        this.zoneid = zoneid;
    }


    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public long getServid() {
        return servid;
    }

    public void setServid(long servid) {
        this.servid = servid;
    }

    public long getNfvipopid() {
        return nfvipopid;
    }

    public void setNfvipopid(long nfvipopid) {
        this.nfvipopid = nfvipopid;
    }

    public long getDomid() {
        return domid;
    }

    public void setDomid(long domid) {
        this.domid = domid;
    }

    public long getZoneid() {
        return zoneid;
    }

    public void setZoneid(long zoneid) {
        this.zoneid = zoneid;
    }
    
    
}
