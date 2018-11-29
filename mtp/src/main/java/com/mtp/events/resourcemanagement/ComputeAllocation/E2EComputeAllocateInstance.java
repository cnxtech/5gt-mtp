/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.events.resourcemanagement.ComputeAllocation;

import com.mtp.common.objects.*;

public class E2EComputeAllocateInstance {

    private long reqid;
    private long servid; //service identifiers 
    private long domid; //id of vim domain to contact
    //private long computeservid;
    //private VIMAbstractElem vimelem;
    private long nfvipopid;
    private ComputeAllocateElem compAllocElem;

    public E2EComputeAllocateInstance(long reqid, long servid, long domid, long nfvipopid, ComputeAllocateElem compAllocElem) {
        this.reqid = reqid;
        this.servid = servid;
        this.domid = domid;
        this.nfvipopid = nfvipopid;
        this.compAllocElem = compAllocElem;
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

    public long getDomid() {
        return domid;
    }

    public void setDomid(long domid) {
        this.domid = domid;
    }

    public long getNfvipopid() {
        return nfvipopid;
    }

    public void setNfvipopid(long nfvipopid) {
        this.nfvipopid = nfvipopid;
    }

    public ComputeAllocateElem getCompAllocElem() {
        return compAllocElem;
    }

    public void setCompAllocElem(ComputeAllocateElem compAllocElem) {
        this.compAllocElem = compAllocElem;
    }

   
}
