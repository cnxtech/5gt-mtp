/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.events.resourcemanagement.NetworkAllocation;


import com.mtp.extinterface.nbi.swagger.model.VirtualNetwork;
import java.util.ArrayList;

public class NetworkAllocateWIMReply {

    private long reqid;
    private long servid; //service identifiers 
    private long logicallinkid;
    private boolean outcome; //result of the request
    private int errorcode;
    private String errormsg; //result of the request
    private ArrayList<VirtualNetwork> wimnetlist; //TODO: Fix in swagger return of network allocate message

    public NetworkAllocateWIMReply() {
        reqid = 0;
        servid = 0;
        outcome = false;
        errorcode = 0;
        errormsg = null;
        wimnetlist = new ArrayList();
        logicallinkid = 0;
    }

    public NetworkAllocateWIMReply(long reqid, long servid, boolean outcome, int errorcode, String errormsg, ArrayList<VirtualNetwork> elemlist, long getLogicalLinkId) {
        this.reqid = reqid;
        this.servid = servid;
        this.outcome = outcome;
        this.errorcode = errorcode;
        this.errormsg = errormsg;
        this.wimnetlist = elemlist;
        this.logicallinkid = getLogicalLinkId;
    }

    public ArrayList<VirtualNetwork> getWimnetlist() {
        return wimnetlist;
    }

    public void setWimnetlist(ArrayList<VirtualNetwork> wimnetlist) {
        this.wimnetlist = wimnetlist;
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

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public long getLogicallinkid() {
        return logicallinkid;
    }

    public void setLogicallinkid(long logicallinkid) {
        this.logicallinkid = logicallinkid;
    }
    

}
