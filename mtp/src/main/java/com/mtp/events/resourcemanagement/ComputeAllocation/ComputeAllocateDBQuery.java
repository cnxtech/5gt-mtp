/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.events.resourcemanagement.ComputeAllocation;

import com.mtp.extinterface.nbi.swagger.model.AllocateComputeRequest;

public class ComputeAllocateDBQuery {

    private long reqid;
    private long servid; //service identifiers
    private AllocateComputeRequest computereq; //contains vim computation info

    public ComputeAllocateDBQuery(long reqid, long servid, AllocateComputeRequest computereq) {
        this.reqid = reqid;
        this.servid = servid;
        this.computereq = computereq;
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

    public AllocateComputeRequest getComputereq() {
        return computereq;
    }

    public void setComputereq(AllocateComputeRequest computereq) {
        this.computereq = computereq;
    }


}
