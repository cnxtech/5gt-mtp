/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.events.resourcemanagement.ComputeTermination;

import com.mtp.common.objects.*;
import java.util.ArrayList;
import java.util.List;

public class ComputeTerminateVIMReq {

    private long reqid;
    private long servid; //service identifiers 
    private ArrayList<Long> domlist;  //
    private ArrayList<Long> poplist;
    private ComputeTerminateElem computeTermElem;

    public ComputeTerminateVIMReq(long reqid, long servid, ArrayList<Long> domlist, ArrayList<Long> poplist, ComputeTerminateElem computeTermElem) {
        this.reqid = reqid;
        this.servid = servid;
        this.domlist = domlist;
        this.poplist = poplist;
        this.computeTermElem = computeTermElem;
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

    public ArrayList<Long> getDomlist() {
        return domlist;
    }

    public void setDomlist(ArrayList<Long> domlist) {
        this.domlist = domlist;
    }

    public ArrayList<Long> getPoplist() {
        return poplist;
    }

    public void setPoplist(ArrayList<Long> poplist) {
        this.poplist = poplist;
    }

    public ComputeTerminateElem getComputeTermElem() {
        return computeTermElem;
    }

    public void setComputeTermElem(ComputeTerminateElem computeTermElem) {
        this.computeTermElem = computeTermElem;
    }


}
