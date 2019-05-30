/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.extinterface.sbi.IFA005Threads;

import com.mtp.SingletonEventBus;
import com.mtp.events.resourcemanagement.ComputeTermination.ComputeTerminateRadioReply;



/**
 *
 * @author efabuba
 */
public class TerminateRadioThread extends Thread {
     public TerminateRadioThread () {

    }
    
    @Override
    public void run() {
        //TODO: perform action to MEC list for MEC domain
        
        
        ComputeTerminateRadioReply ev = new ComputeTerminateRadioReply();
        //TODO: prepare event to collect info
        //send event
        SingletonEventBus.getBus().post(ev);
    }   
}
