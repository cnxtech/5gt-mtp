/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.extinterface.sbi.IFA005Threads;

import com.mtp.SingletonEventBus;
import com.mtp.common.objects.DomainElem;
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
public class TerminateVIMComputeThread extends Thread {

    //private DomainElem dominfo;
    private List<DomainElem> dominfoList;
    private ComputeTerminateVIMReq request;
//    public  TerminateVIMComputeThread (DomainElem val, ComputeTerminateVIMReq req) {
//        dominfo = val;
//        request = req;
//    }

    public TerminateVIMComputeThread(List<DomainElem> val, ComputeTerminateVIMReq req) {
        dominfoList = val;
        request = req;
    }

    @Override
    public void run() {

        //START - Retrieve from DB the list of domains (domId) to call for compute resource termination 
        //   ArrayList <VIMAbstractElem> vimlist = new ArrayList();
        ComputeTerminateVIMReply termvimrep;
        ArrayList<Boolean> outcomeList = new ArrayList();
        ArrayList<Integer> errorcodeList = new ArrayList();
        ArrayList<String> errormsgList = new ArrayList();
        long size = outcomeList.size();

        for (int i = 0; i < size; i++) {
            DomainElem dominfo = dominfoList.get(i);
            List<String> compid = new ArrayList();
            compid.add(request.getComputeTermElem().getTerminateRequest().get(i));
   
            String basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort() + "/" + dominfo.getName();
            ApiClient capi = new ApiClient();
            capi.setBasePath(basepath);
            VirtualisedComputeResourcesApi api = new VirtualisedComputeResourcesApi(capi);

            List<String> complist = new ArrayList();;
            //List<ComputeIds> dummy = new ArrayList();

            
            try {

                complist = api.terminateAbstractResources(compid);
                outcomeList.add(Boolean.TRUE);
                errorcodeList.add(0);
                errormsgList.add("");

            } catch (ApiException e) {
                System.out.println("ApiException inside allocateCompute().");
                System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                outcomeList.add(Boolean.FALSE);
                errorcodeList.add(e.getCode());
                errormsgList.add(e.getMessage());
            }

        }

        //send event
        termvimrep = new ComputeTerminateVIMReply(request.getReqid(), request.getServid(), 
                request.getDomlist(), outcomeList, request.getPoplist(), 
                request.getComputeTermElem().getTerminateRequest(), errorcodeList, errormsgList);

        SingletonEventBus.getBus().post(termvimrep);
    }
}