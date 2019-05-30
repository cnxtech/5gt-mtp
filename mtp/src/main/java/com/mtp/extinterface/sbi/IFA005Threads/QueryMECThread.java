/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.extinterface.sbi.IFA005Threads;

import com.mtp.SingletonEventBus;
import com.mtp.common.objects.DomainElem;
import com.mtp.events.abstraction.Creation.MECResAbstractionEvent;
import com.mtp.extinterface.nbi.swagger.model.InlineResponse200;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.MecResourcesApi;

/**
 *
 * @author efabuba
 */
public class QueryMECThread extends Thread {
     private DomainElem dominfo;
    public  QueryMECThread (DomainElem val) {
        dominfo = val;
    }
    
    
    @Override
    public void run() {
       //String basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort() + "/" + dominfo.getName();
        String basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort();
        ApiClient capi = new ApiClient();
        capi.setBasePath(basepath);
        
        MecResourcesApi api = new MecResourcesApi(capi);
        InlineResponse200 response = new InlineResponse200();
        try {
            response = api.serviceRegionsGet();
        } catch (ApiException e) {
            System.out.println("ApiException inside QueryMECThread()."); 
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            return;
        }
        
        MECResAbstractionEvent ev = new MECResAbstractionEvent(dominfo.getId(),response.getRegions());
        //send event
        SingletonEventBus.getBus().post(ev);
        
    }
}
