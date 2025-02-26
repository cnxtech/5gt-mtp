/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.events.resourcemanagement.NetworkAllocation;

import com.mtp.extinterface.nbi.swagger.model.InterNfviPopConnectivityRequest;
import com.mtp.extinterface.nbi.swagger.model.VirtualNetwork;
import java.util.ArrayList;



public class NetworkAllocateDBQueryOutcome {

    private long reqid;
    private long servid; //service identifiers 
    private boolean outcome; //result of the request
    private long logicalPathId;
    private InterNfviPopConnectivityRequest networkRequest;
    private ArrayList<Long> wimdomlist;
    private ArrayList<Long> vimdomlist; //contains domain managing the VIM abstract resources
    private ArrayList<Long> interdomainLinks;
    private ArrayList<Long> intraPopLinks;
    private ArrayList<Long> wanLinks;
    private ArrayList<Long> wimPopList;
    private ArrayList<Long> vimPopList;
    private ArrayList<String> wimNetworkType;
    private ArrayList<String> vimNetworkType;
    private ArrayList<VirtualNetwork> wimnetlist;
    private ArrayList<VirtualNetwork> vimnetlist;

    public NetworkAllocateDBQueryOutcome(long reqid, long servid, boolean outcome, long logicalPathId, InterNfviPopConnectivityRequest networkRequest, ArrayList<Long> wimdomlist, ArrayList<Long> vimdomlist, ArrayList<Long> interdomainLinks, ArrayList<Long> intraPopLinks, ArrayList<Long> wanLinks, ArrayList<Long> wimPopList, ArrayList<Long> vimPopList, ArrayList<String> wimNetworkType, ArrayList<String> vimNetworkType, ArrayList<VirtualNetwork> wimnetlist, ArrayList<VirtualNetwork> vimnetlist) {
        this.reqid = reqid;
        this.servid = servid;
        this.outcome = outcome;
        this.logicalPathId = logicalPathId;
        this.networkRequest = networkRequest;
        this.wimdomlist = wimdomlist;
        this.vimdomlist = vimdomlist;
        this.interdomainLinks = interdomainLinks;
        this.intraPopLinks = intraPopLinks;
        this.wanLinks = wanLinks;
        this.wimPopList = wimPopList;
        this.vimPopList = vimPopList;
        this.wimNetworkType = wimNetworkType;
        this.vimNetworkType = vimNetworkType;
        this.wimnetlist = wimnetlist;
        this.vimnetlist = vimnetlist;
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

    public boolean isOutcome() {
        return outcome;
    }

    public void setOutcome(boolean outcome) {
        this.outcome = outcome;
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

    public ArrayList<Long> getVimPopList() {
        return vimPopList;
    }

    public void setVimPopList(ArrayList<Long> vimPopList) {
        this.vimPopList = vimPopList;
    }

    public ArrayList<String> getWimNetworkType() {
        return wimNetworkType;
    }

    public void setWimNetworkType(ArrayList<String> wimNetworkType) {
        this.wimNetworkType = wimNetworkType;
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

    public void setWimnetlist(ArrayList<VirtualNetwork> wimnetlist) {
        this.wimnetlist = wimnetlist;
    }

    public ArrayList<VirtualNetwork> getVimnetlist() {
        return vimnetlist;
    }

    public void setVimnetlist(ArrayList<VirtualNetwork> vimnetlist) {
        this.vimnetlist = vimnetlist;
    }

}
