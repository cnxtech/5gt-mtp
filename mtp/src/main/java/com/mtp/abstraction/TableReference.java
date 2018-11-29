/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.abstraction;

/**
 *
 * @author efabuba
 */
public class TableReference {
    private long interdomainLinkId;
    private long networkResourcesId;

    public TableReference(long interdomainLinkId, long networkResourcesId) {
        this.interdomainLinkId = interdomainLinkId;
        this.networkResourcesId = networkResourcesId;
    }

    public long getInterdomainLinkId() {
        return interdomainLinkId;
    }

    public void setInterdomainLinkId(long interdomainLinkId) {
        this.interdomainLinkId = interdomainLinkId;
    }

    public long getNetworkResourcesId() {
        return networkResourcesId;
    }

    public void setNetworkResourcesId(long networkResourcesId) {
        this.networkResourcesId = networkResourcesId;
    }
    
    
}
