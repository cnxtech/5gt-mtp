/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.extinterface.sbi.IFA005Threads;

import com.mtp.SingletonEventBus;
import com.mtp.common.objects.DomainElem;
import com.mtp.events.resourcemanagement.NetworkTermination.IntraPoPTerminateVIMReply;
import com.mtp.events.resourcemanagement.NetworkTermination.IntraPoPTerminateVIMRequest;
import com.mtp.extinterface.nbi.swagger.model.NetworkIds;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.WimNetworkResourcesApi;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class TerminateIntraPoPVIMNetworkThread extends Thread {
    private List<DomainElem> dominfoMap;
    private IntraPoPTerminateVIMRequest request;

    public TerminateIntraPoPVIMNetworkThread(List<DomainElem> val, IntraPoPTerminateVIMRequest req) {
        dominfoMap = val;
        request = req;
    }

    @Override
    public void run() {

        IntraPoPTerminateVIMReply termwimrep;
        List<String> netidlist = new ArrayList<String>();
        
        //Loop on logicaLinks
        for (int i = 0; i < dominfoMap.size(); i++) {
//            String basepath = "http://" + dominfoMap.get(i).getIp() + ":"
//                    + dominfoMap.get(i).getPort() + "/" + dominfoMap.get(i).getName();
            String basepath = "http://" + dominfoMap.get(i).getIp() + ":"
                    + dominfoMap.get(i).getPort();
            ApiClient capi = new ApiClient();
            capi.setBasePath(basepath);
            WimNetworkResourcesApi api = new WimNetworkResourcesApi(capi);

            List<NetworkIds> resplist = new ArrayList();


            try {
                //Filter nfviPopComputeInformationRequest = null;
                resplist = api.terminateNetworks(request.getNetreslist().get(i));
            } catch (ApiException e) {
                System.out.println("ApiException inside terminateNetworks() VIM.");
                System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                break;
            }
            
            //insert resources in netidlist
            for (int j =0; j < resplist.size(); j++) {
                netidlist.add(resplist.get(j).getNetworkId());
            }
            
        }//END loop on domIds

        //send event 
        termwimrep = new IntraPoPTerminateVIMReply(request.getReqid(), request.getDomlist(),
                        request.getNetreslist(), netidlist);
        SingletonEventBus.getBus().post(termwimrep);
    }
}
