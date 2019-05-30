/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.events.resourcemanagement.NetworkAllocation;

import com.mtp.extinterface.nbi.swagger.model.AllocateNetworkRequest;

/**
 *
 * @author efabuba
 */
public class IntraPoPAllocateDbRequest {
    private long reqid;
    private long servid;
    private long nfvipopid;
    private AllocateNetworkRequest intrapopreq;

    public IntraPoPAllocateDbRequest(long reqid, long servid, long nfvipopid, AllocateNetworkRequest intrapopreq) {
        this.reqid = reqid;
        this.servid = servid;
        this.nfvipopid = nfvipopid;
        this.intrapopreq = intrapopreq;
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

    public AllocateNetworkRequest getIntrapopreq() {
        return intrapopreq;
    }

    public void setIntrapopreq(AllocateNetworkRequest intrapopreq) {
        this.intrapopreq = intrapopreq;
    }
    
    
}
