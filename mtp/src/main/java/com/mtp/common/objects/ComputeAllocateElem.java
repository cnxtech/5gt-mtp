/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.common.objects;

import com.mtp.events.resourcemanagement.ComputeAllocation.E2EComputeAllocateRequest;
import com.mtp.extinterface.nbi.swagger.model.VirtualCompute;


/**
 *
 * @author efabuba
 */
public class ComputeAllocateElem {
    private E2EComputeAllocateRequest input;
    private VirtualCompute output;
    public ComputeAllocateElem() {
        output = null;
        input = null;
    }
    
    public void setAllocateRequest(E2EComputeAllocateRequest in) {
        input = in;
    }
    public E2EComputeAllocateRequest getAllocateRequest() {
        return input;
    }
    public void setAllocateReply(VirtualCompute out) {
        output = out;
    }
    public VirtualCompute getAllocateReply() {
        return output;
    }
}
