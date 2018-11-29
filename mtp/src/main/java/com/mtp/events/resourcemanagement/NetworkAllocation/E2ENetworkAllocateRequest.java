/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.events.resourcemanagement.NetworkAllocation;


import com.mtp.extinterface.nbi.swagger.model.InterNfviPopConnectivityRequest;

public class E2ENetworkAllocateRequest {

    private long reqid;
    private long servid; //service identifiers
    private InterNfviPopConnectivityRequest networkreq; //contains vim computation info 

    public E2ENetworkAllocateRequest(long reqid, long servid, InterNfviPopConnectivityRequest networkreq) {
        this.reqid = reqid;
        this.servid = servid;
        this.networkreq = networkreq;
    }



    //insert set/get methods
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

    public InterNfviPopConnectivityRequest getNetworkreq() {
        return networkreq;
    }

    public void setNetworkreq(InterNfviPopConnectivityRequest networkreq) {
        this.networkreq = networkreq;
    }

    
}
