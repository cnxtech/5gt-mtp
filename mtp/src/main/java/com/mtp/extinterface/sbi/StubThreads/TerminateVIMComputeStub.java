/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.extinterface.sbi.StubThreads;

import com.mtp.SingletonEventBus;
import com.mtp.common.objects.DomainElem;
import com.mtp.events.resourcemanagement.ComputeAllocation.ComputeAllocateVIMReply;
import com.mtp.events.resourcemanagement.ComputeTermination.ComputeTerminateVIMReply;
import com.mtp.events.resourcemanagement.ComputeTermination.ComputeTerminateVIMReq;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.VirtualisedComputeResourcesApi;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class TerminateVIMComputeStub extends Thread {
    //private DomainElem dominfo;
    private List<DomainElem> dominfolist;
    private ComputeTerminateVIMReq request;
    
    
//    public  TerminateVIMComputeStub (DomainElem val, ComputeTerminateVIMReq req) {
//        dominfo = val;
//        request = req;
//    }
    
       public  TerminateVIMComputeStub (List<DomainElem> val, ComputeTerminateVIMReq req) {
        dominfolist = val;
        request = req;
    }
    
    
    @Override
    public void run() {

        ComputeTerminateVIMReply termvimrep;
        List<Boolean> outcomeList=new ArrayList();  
        List<Integer> errcodeList=new ArrayList();
        List<String> errmsgList=new ArrayList();
        long size=request.getDomlist().size();
       
        
        
        
        for (int i = 0; i < size; i++) {
		
            outcomeList.add(Boolean.TRUE);
            errcodeList.add(0);
            errmsgList.add("");
       
        }
        
        
        
        
        
        
        //send event with all ok
//        termvimrep = new ComputeTerminateVIMReply( request.getReqId(),request.getServId(), 
//                        request.getDomId(), true);
//        termvimrep.setVIMElem(request.getVIMElem());
      
        termvimrep = new ComputeTerminateVIMReply( request.getReqid(),request.getServid(), 
                        request.getDomlist(),outcomeList, request.getPoplist(),
                        request.getComputeTermElem().getTerminateRequest(), errcodeList, errmsgList);
      



        
        
        SingletonEventBus.getBus().post(termvimrep);
    }
}
