/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.events.resourcemanagement.NetworkAllocation;



import com.mtp.extinterface.nbi.swagger.model.InterNfviPopConnectivityRequest;
import com.mtp.extinterface.nbi.swagger.model.VirtualNetwork;
import java.util.ArrayList;

public class NetworkAllocateVIMReq {

    private long reqid;
    private long servid; //service identifiers 
    private long logicalPathId;
    private InterNfviPopConnectivityRequest networkRequest; // wimelem renamed 
    private ArrayList<Long> vimdomlist; //contains domain managing the VIM abstract resources
    private ArrayList<Long> interdomainLinks;
    private ArrayList<Long> intraPopLinks;
    private ArrayList<Long> vimPopList;
    private ArrayList<String> vimNetworkType;
    private ArrayList<VirtualNetwork> wimnetlist;

    public NetworkAllocateVIMReq() {
        reqid = 0;
        servid = 0;
    }

    public NetworkAllocateVIMReq(long reqid, long servid, long logicalPathId, InterNfviPopConnectivityRequest networkRequest, ArrayList<Long> vimdomlist, ArrayList<Long> interdomainLinks, ArrayList<Long> intraPopLinks, ArrayList<Long> vimPopList, ArrayList<String> vimNetworkType, ArrayList<VirtualNetwork> vimnetlist) {
        this.reqid = reqid;
        this.servid = servid;
        this.logicalPathId = logicalPathId;
        this.networkRequest = networkRequest;
        this.vimdomlist = vimdomlist;
        this.interdomainLinks = interdomainLinks;
        this.intraPopLinks = intraPopLinks;
        this.vimPopList = vimPopList;
        this.vimNetworkType = vimNetworkType;
        this.wimnetlist = vimnetlist;
    }

    //insert set/get methods
    public long getReqId() {
        return reqid;
    }

    public void setReqId(long reqval) {
        reqid = reqval;
    }

    public long getServId() {
        return servid;
    }

    public void setServqId(long servval) {
        servid = servval;
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

    public long getLogicalPathId() {
        return logicalPathId;
    }

    public void setLogicalPathId(long logicalPathId) {
        this.logicalPathId = logicalPathId;
    }

    public InterNfviPopConnectivityRequest getNetworkRequest() {
        return networkRequest;
    }

    public void setNetworkRequest(InterNfviPopConnectivityRequest networkRequest) {
        this.networkRequest = networkRequest;
    }

    public ArrayList<Long> getVimdomlist() {
        return vimdomlist;
    }

    public void setVimdomlist(ArrayList<Long> vimdomlist) {
        this.vimdomlist = vimdomlist;
    }

    public ArrayList<Long> getInterdomainLinks() {
        return interdomainLinks;
    }

    public void setInterdomainLinks(ArrayList<Long> interdomainLinks) {
        this.interdomainLinks = interdomainLinks;
    }

    public ArrayList<Long> getIntraPopLinks() {
        return intraPopLinks;
    }

    public void setIntraPopLinks(ArrayList<Long> intraPopLinks) {
        this.intraPopLinks = intraPopLinks;
    }

    public ArrayList<Long> getVimPopList() {
        return vimPopList;
    }

    public void setVimPopList(ArrayList<Long> vimPopList) {
        this.vimPopList = vimPopList;
    }

    public ArrayList<String> getVimNetworkType() {
        return vimNetworkType;
    }

    public void setVimNetworkType(ArrayList<String> vimNetworkType) {
        this.vimNetworkType = vimNetworkType;
    }

    public ArrayList<VirtualNetwork> getWimnetlist() {
        return wimnetlist;
    }

    public void setWimnetlist(ArrayList<VirtualNetwork> vimnetlist) {
        this.wimnetlist = vimnetlist;
    }

}
