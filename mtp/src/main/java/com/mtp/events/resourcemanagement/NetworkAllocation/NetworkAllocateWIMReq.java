/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.events.resourcemanagement.NetworkAllocation;


import com.mtp.extinterface.nbi.swagger.model.InterNfviPopConnectivityRequest;
import java.util.ArrayList;

public class NetworkAllocateWIMReq {

    private long reqid;
    private long servid; //service identifiers
    private long logicalPathId;
    private InterNfviPopConnectivityRequest networkRequest;
    private ArrayList<Long> wimdomlist;
    private ArrayList<Long> interdomainLinks;
    private ArrayList<Long> wanLinks;
    private ArrayList<Long> wimPopList;
    private ArrayList<String> wimNetworkType;

    public NetworkAllocateWIMReq() {
        reqid = 0;
        servid = 0;
        logicalPathId = 0;
        networkRequest = new InterNfviPopConnectivityRequest();
        wimdomlist = new ArrayList();
        interdomainLinks = new ArrayList();
        wanLinks = new ArrayList();
        wimPopList = new ArrayList();
        wimNetworkType = new ArrayList();
    }

    public NetworkAllocateWIMReq(long reqid, long servid, long logicalPathId, InterNfviPopConnectivityRequest networkRequest, ArrayList<Long> wimdomlist, ArrayList<Long> interdomainLinks, ArrayList<Long> wanLinks, ArrayList<Long> wimPopList, ArrayList<String> wimNetworkType) {
        this.reqid = reqid;
        this.servid = servid;
        this.logicalPathId = logicalPathId;
        this.networkRequest = networkRequest;
        this.wimdomlist = wimdomlist;
        this.interdomainLinks = interdomainLinks;
        this.wanLinks = wanLinks;
        this.wimPopList = wimPopList;
        this.wimNetworkType = wimNetworkType;
    }

    public ArrayList<String> getWimNetworkType() {
        return wimNetworkType;
    }

    public void setWimNetworkType(ArrayList<String> wimNetworkType) {
        this.wimNetworkType = wimNetworkType;
    }

    public long getLogicalPathId() {
        return logicalPathId;
    }

    public void setLogicalPathId(long logicalPathId) {
        this.logicalPathId = logicalPathId;
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
    public InterNfviPopConnectivityRequest getNetworkRequest() {
        return networkRequest;
    }

    public void setNetworkRequest(InterNfviPopConnectivityRequest networkRequest) {
        this.networkRequest = networkRequest;
    }

    public ArrayList<Long> getWimdomlist() {
        return wimdomlist;
    }

    public void setWimdomlist(ArrayList<Long> wimdomlist) {
        this.wimdomlist = wimdomlist;
    }

    public ArrayList<Long> getInterdomainLinks() {
        return interdomainLinks;
    }

    public void setInterdomainLinks(ArrayList<Long> interdomainLinks) {
        this.interdomainLinks = interdomainLinks;
    }

    public ArrayList<Long> getWanLinks() {
        return wanLinks;
    }

    public void setWanLinks(ArrayList<Long> wanLinks) {
        this.wanLinks = wanLinks;
    }

    public ArrayList<Long> getWimPopList() {
        return wimPopList;
    }

    public void setWimPopList(ArrayList<Long> wimPopList) {
        this.wimPopList = wimPopList;
    }
    
}
