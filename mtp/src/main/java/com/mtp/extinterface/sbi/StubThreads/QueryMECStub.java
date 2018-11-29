/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.extinterface.sbi.StubThreads;

import com.mtp.SingletonEventBus;
import com.mtp.common.objects.DomainElem;
import com.mtp.events.abstraction.Creation.MECResAbstractionEvent;

/**
 *
 * @author efabuba
 */
public class QueryMECStub extends Thread {
    private DomainElem dominfo;
    public QueryMECStub (DomainElem val) {
        dominfo = val;
    }
    
    @Override
    public void run() {
        //TODO: perform query list for MEC domain
        
        
        MECResAbstractionEvent ev = new MECResAbstractionEvent(dominfo.getId());
        //TODO: prepare event to collect info
        //send event
        SingletonEventBus.getBus().post(ev);
    }    
}
