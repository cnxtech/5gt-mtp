/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.events.resourcemanagement.NetworkAllocation;

import com.mtp.extinterface.nbi.swagger.model.AllocateNetworkRequest;
import com.mtp.extinterface.nbi.swagger.model.AllocateNetworkResult;


/**
 *
 * @author efabuba
 */
public class IntraPoPAllocateVIMReply {
    private long reqid;
    private long servid;
    private long nfvipopid;
    private long vimid;
    private AllocateNetworkRequest intrapopreq;
    private AllocateNetworkResult intrapoprep;

    public IntraPoPAllocateVIMReply(long reqid, long servid, long nfvipopid, long vimid, AllocateNetworkRequest intrapopreq, AllocateNetworkResult intrapoprep) {
        this.reqid = reqid;
        this.servid = servid;
        this.nfvipopid = nfvipopid;
        this.vimid = vimid;
        this.intrapopreq = intrapopreq;
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

    public long getVimid() {
        return vimid;
    }

    public void setVimid(long vimid) {
        this.vimid = vimid;
    }

    public AllocateNetworkResult getIntrapoprep() {
        return intrapoprep;
    }

    public void setIntrapoprep(AllocateNetworkResult intrapoprep) {
        this.intrapoprep = intrapoprep;
    }

    public AllocateNetworkRequest getIntrapopreq() {
        return intrapopreq;
    }

    public void setIntrapopreq(AllocateNetworkRequest intrapopreq) {
        this.intrapopreq = intrapopreq;
    }
    
    
}
