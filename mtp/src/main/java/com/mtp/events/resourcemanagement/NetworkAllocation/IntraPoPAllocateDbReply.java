/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.events.resourcemanagement.NetworkAllocation;

import com.mtp.extinterface.nbi.swagger.model.AllocateNetworkResult;


/**
 *
 * @author efabuba
 */
public class IntraPoPAllocateDbReply {
    private long reqid;
    private long servid;
    private long nfvipopid;
    private AllocateNetworkResult intrapoprep;

    public IntraPoPAllocateDbReply(long reqid, long servid, long nfvipopid, AllocateNetworkResult intrapoprep) {
        this.reqid = reqid;
        this.servid = servid;
        this.nfvipopid = nfvipopid;
        this.intrapoprep = intrapoprep;
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

    public AllocateNetworkResult getIntrapoprep() {
        return intrapoprep;
    }

    public void setIntrapoprep(AllocateNetworkResult intrapoprep) {
        this.intrapoprep = intrapoprep;
    }
    
    
}
