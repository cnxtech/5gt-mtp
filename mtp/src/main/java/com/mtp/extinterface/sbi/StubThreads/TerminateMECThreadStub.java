/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.extinterface.sbi.StubThreads;

import com.mtp.SingletonEventBus;
import com.mtp.common.objects.DomainElem;
import com.mtp.events.resourcemanagement.ComputeTermination.ComputeTerminateMECReply;
import com.mtp.events.resourcemanagement.ComputeTermination.ComputeTerminateMECReq;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class TerminateMECThreadStub extends Thread {
     private List<DomainElem> dominfolist;
    private ComputeTerminateMECReq request;
    public TerminateMECThreadStub (List<DomainElem> val, ComputeTerminateMECReq req) {
        dominfolist = val;
        request = req;
    }
    
    @Override
    public void run() {
        
        
        
        ComputeTerminateMECReply ev = new ComputeTerminateMECReply(request.getReqid(),request.getServid(),
                    request.getComputereq(),request.getMecreq());
        //send event
        SingletonEventBus.getBus().post(ev);
    }   
}
