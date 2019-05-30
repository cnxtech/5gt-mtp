/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.events.abstraction;

import com.ericsson.dummyplugin.nbi.swagger.model.InlineResponse2002;

/**
 *
 * @author efabuba
 */
public class WIMAbstractionReply {
    private long req;
    private InlineResponse2002 resp;

    public WIMAbstractionReply(long req, InlineResponse2002 resp) {
        this.req = req;
        this.resp = resp;
    }

    public long getReq() {
        return req;
    }

    public void setReq(long req) {
        this.req = req;
    }

    public InlineResponse2002 getResp() {
        return resp;
    }

    public void setResp(InlineResponse2002 resp) {
        this.resp = resp;
    }
    
    
}
