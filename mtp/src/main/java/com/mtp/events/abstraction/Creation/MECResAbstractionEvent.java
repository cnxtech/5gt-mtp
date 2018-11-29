/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.events.abstraction.Creation;

/**
 *
 * @author efabuba
 */
public class MECResAbstractionEvent {
    private long id; //id of the VIM domain
    //TODO: Create MEC abstraction event
    
    public MECResAbstractionEvent(long val) {
        id = val;
    }
    
    public void setId(long val) {
        id = val;
    }
    public long getId() {
        return id;
    }
}
