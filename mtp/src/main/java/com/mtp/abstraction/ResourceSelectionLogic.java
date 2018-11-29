/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.abstraction;

import com.google.common.eventbus.Subscribe;
import com.mtp.DbConnectionPool.DbAbstractionDatasource;
import com.mtp.DbConnectionPool.DbDomainDatasource;
import com.mtp.SingletonEventBus;
import com.mtp.common.objects.ComputeAllocateElem;
import com.mtp.common.objects.ComputeTerminateElem;
import com.mtp.common.objects.NetworkAllocateElem;
import com.mtp.common.objects.NetworkTerminateElem;
//import com.mtp.common.objects.VIMAbstractElem;
import com.mtp.events.resourcemanagement.ComputeAllocation.ComputeAllocateDBQuery;
import com.mtp.events.resourcemanagement.ComputeAllocation.ComputeAllocateDBQueryReply;
import com.mtp.events.resourcemanagement.ComputeAllocation.E2EComputeAllocateInstance;
import com.mtp.events.resourcemanagement.ComputeAllocation.E2EComputeAllocateInstanceOutcome;
import com.mtp.events.resourcemanagement.ComputeAllocation.E2EComputeAllocateReply;
import com.mtp.events.resourcemanagement.ComputeAllocation.E2EComputeAllocateRequest;
import com.mtp.events.resourcemanagement.ComputeTermination.ComputeTerminateDBQuery;
import com.mtp.events.resourcemanagement.ComputeTermination.ComputeTerminateDBQueryReply;
import com.mtp.events.resourcemanagement.ComputeTermination.E2EComputeTerminateInstance;
import com.mtp.events.resourcemanagement.ComputeTermination.E2EComputeTerminateInstanceOutcome;
import com.mtp.events.resourcemanagement.ComputeTermination.E2EComputeTerminateReply;
import com.mtp.events.resourcemanagement.ComputeTermination.E2EComputeTerminateRequest;
import com.mtp.events.resourcemanagement.NetworkAllocation.E2ENetworkAllocateInstance;
import com.mtp.events.resourcemanagement.NetworkAllocation.E2ENetworkAllocateInstanceOutcome;
import com.mtp.events.resourcemanagement.NetworkAllocation.E2ENetworkAllocateReply;
import com.mtp.events.resourcemanagement.NetworkAllocation.E2ENetworkAllocateRequest;
import com.mtp.events.resourcemanagement.NetworkAllocation.NetworkAllocateDBQuery;
import com.mtp.events.resourcemanagement.NetworkAllocation.NetworkAllocateDBQueryReply;
import com.mtp.events.resourcemanagement.NetworkTermination.E2ENetworkTerminateInstance;
import com.mtp.events.resourcemanagement.NetworkTermination.E2ENetworkTerminateInstanceOutcome;
import com.mtp.events.resourcemanagement.NetworkTermination.E2ENetworkTerminateReply;
import com.mtp.events.resourcemanagement.NetworkTermination.E2ENetworkTerminateRequest;
import com.mtp.events.resourcemanagement.NetworkTermination.NetworkTerminateDBQuery;
import com.mtp.events.resourcemanagement.NetworkTermination.NetworkTerminateDBQueryReply;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResourceSelectionLogic {

    private HashMap<Long, ComputeAllocateElem> compallmap;
    private HashMap<Long, NetworkAllocateElem> netallmap;
    private HashMap<Long, ComputeTerminateElem> comptermmap;
    private HashMap<Long, NetworkTerminateElem> nettermmap;

    public ResourceSelectionLogic() {
        compallmap = new HashMap();
        netallmap = new HashMap();
        comptermmap = new HashMap();
        nettermmap = new HashMap();
    }

    @Subscribe
    public void handle_E2EComputeAllocateRequest(E2EComputeAllocateRequest allreqev) {
        System.out.println("ResourceSelectionLogic --> Handle E2EComputeAllocateRequest Event");
        //insert element in hasmap of pening allocate req
        ComputeAllocateElem compel = new ComputeAllocateElem();
        compel.setAllocateRequest(allreqev);
        compallmap.put(allreqev.getReqid(), compel);

//// START - Insert servID into Service table
//        try {
//            
//    java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
//            PreparedStatement ps = conn.prepareStatement("INSERT INTO service (servId) VALUES(?)");
//            ps.setLong(1,allreqev.getServId());
//
//            ps.executeUpdate();
//            ps.close();
//
//        } catch (SQLIntegrityConstraintViolationException ex) {
////            Logger.getLogger(ResourceSelectionLogic.class
////                    .getName()).log(Level.SEVERE, null, ex);
//             System.out.println("ResourceSelectionLogic.handle_E2EComputeAllocateRequest ---> SQLIntegrityConstraintViolationException: Duplicate entry");
//        }
//     catch (SQLException ex) {
//          Logger.getLogger(ResourceSelectionLogic.class
//                   .getName()).log(Level.SEVERE, null, ex);
//      }
//// END - Insert
        //Prepare and perform the query
        ComputeAllocateDBQuery dbev = new ComputeAllocateDBQuery(allreqev.getReqid(),
                allreqev.getServid(), allreqev.getRequest());
        //post event
        System.out.println("ResourceSelectionLogic --> Post ComputeAllocateDBQuery Event");
        SingletonEventBus.getBus().post(dbev);
    }

    @Subscribe
    public void handle_ComputeAllocateDBQueryReply(ComputeAllocateDBQueryReply dbreply) {
        System.out.println("ResourceSelectionLogic --> Handle ComputeAllocateDBQueryReply Event");

        
        // Select a nfviPopId from the list
        // Selection Criterion: retrieve the first elementn of the list 
        // Upgrade the selection criteria if needed 
        ArrayList<Long> vimlist = dbreply.getPoplist(); //Retrieve the first element (nfviPopId) of the list 
        long nfviPopId = vimlist.get(0);
        // START - Retrieve from DB the DomId related to the nfviPoPId
        Integer domId = null;
        System.out.println("ResourceSelectionLogic.handle_ComputeAllocateDBQueryReply ---> selected "
                + "nfviPopId: " + nfviPopId + "");
        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select domId from nfvipop where nfviPopId=?");
            ps.setLong(1, nfviPopId);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                domId = rs.getInt("domId");
                System.out.println("ResourceSelectionLogic.handle_ComputeAllocateDBQueryReply ---> "
                        + "domId: " + domId + "");
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(ResourceSelectionLogic.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        //END - Retrieve

        //Retrieve ComputeAllocateElem instance  from compallmap using the reqId
        ComputeAllocateElem compAllocElem = compallmap.get(dbreply.getReqid());
        E2EComputeAllocateInstance allinst = new E2EComputeAllocateInstance(dbreply.getReqid(),
                dbreply.getServid(), domId, nfviPopId, compAllocElem);
        System.out.println("ResourceSelectionLogic --> Post E2EComputeAllocateInstance Event");
        SingletonEventBus.getBus().post(allinst);

    }

    @Subscribe
    public void handle_E2EComputeAllocateInstanceOutcome(E2EComputeAllocateInstanceOutcome allinstout) {
        System.out.println("ResourceSelectionLogic --> Handle E2EComputeAllocateInstanceOutcome Event");

        E2EComputeAllocateReply allrepout = new E2EComputeAllocateReply(allinstout.getReqid(),
                allinstout.getServid(), allinstout.getComputereply(), allinstout.isOutcome());
        System.out.println("ResourceSelectionLogic --> Post E2EComputeAllocateReply Event");
        SingletonEventBus.getBus().post(allrepout);
    }

    @Subscribe
    public void handle_E2ENetworkAllocateRequest(E2ENetworkAllocateRequest allreqev) {
        System.out.println("ResourceSelectionLogic --> Handle E2ENetworkAllocateRequest Event");
        long physicalpath = -1, logicallink = -1;
        float availablebw = -1;

        //insert element in hasmap of pening allocate req
        NetworkAllocateElem netel = new NetworkAllocateElem();
        netel.setAllocateRequest(allreqev);
        netallmap.put(allreqev.getReqid(), netel);

        for (int i = 0; i < allreqev.getNetworkreq().getLogicalLinkPathList().size(); i++) {
            //Get the logicalLinkId from dbquery
            logicallink = Long.parseLong(allreqev.getNetworkreq().getLogicalLinkPathList().get(i).getLogicalLinkAttributes().getLogicalLinkId());
            System.out.println("handle_E2ENetworkAllocateRequest ----> LogicalLinkId:" + logicallink + "");
            //rerieve from DB logical paths related to the logicalLinkId 
            try {
                java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT logicalPathId,availableBandwidth  FROM logicalpath where logicalLinkId=?");
                ps.setLong(1, logicallink);
                java.sql.ResultSet rs = ps.executeQuery();
                //Select the path with minimum available bandwidth
                while (rs.next()) {
                    if (availablebw == -1) {
                        //first element is selected
                        physicalpath = rs.getLong("logicalPathId");
                        availablebw = rs.getFloat("availableBandwidth");
                    } else {
                        //check if tmp_available bw is bigger and select it
                        float tmp_availablebw = rs.getFloat("availableBandwidth");
                        //TODO: insert control on bw request when IFA005 is fixed
                        //if ( /*(tmp_availablebw >= allreqev.getE2EWIMElem().) &&*/ 
                        //        (tmp_availablebw > availablebw)) {
                        if (tmp_availablebw > availablebw) {
                            //select this as candidate
                            physicalpath = rs.getLong("logicalPathId");
                            availablebw = tmp_availablebw;
                        }
                    }
                }
                System.out.println("handle_E2ENetworkAllocateRequest ---> physicalpath: " + physicalpath + "");

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Prepare and perform the query
            NetworkAllocateDBQuery dbev = new NetworkAllocateDBQuery(allreqev.getReqid(),
                    allreqev.getServid(), allreqev.getNetworkreq(), physicalpath);
            //post event
            System.out.println("ResourceSelectionLogic --> Post NetworkAllocateDBQuery Event");
            SingletonEventBus.getBus().post(dbev);
        }
    }

    @Subscribe
    public void handle_NetworkAllocateDBQueryReply(NetworkAllocateDBQueryReply dbreply) {
        System.out.println("ResourceSelectionLogic --> Handle NetworkAllocateDBQueryReply Event");

        E2ENetworkAllocateInstance allinst = new E2ENetworkAllocateInstance(dbreply.getReqid(),
                dbreply.getServid(), dbreply.getLogicalLPathId(), dbreply.getWimdomlist(), 
                dbreply.getVimdomlist(), dbreply.getInterdomainLinks(), dbreply.getIntraPopLinks(),
                dbreply.getWanLinks(), dbreply.getWimPopList(), dbreply.getVimPopList(), 
                netallmap.get(dbreply.getReqid()).getAllocateRequest().getNetworkreq(),
                dbreply.getWimNetworkType(), dbreply.getVimNetworkType());

        System.out.println("ResourceSelectionLogic --> Post E2ENetworkAllocateInstance Event");
        SingletonEventBus.getBus().post(allinst);

    }

    @Subscribe
    public void handle_E2ENetworkAllocateInstanceOutcome(E2ENetworkAllocateInstanceOutcome allinstout) {
        System.out.println("ResourceSelectionLogic --> Handle E2ENetworkAllocateInstanceOutcome Event");
        
        if (allinstout.isOutcome() == false ) {
            //send negative response
            E2ENetworkAllocateReply allrepout = new E2ENetworkAllocateReply(allinstout.getReqid(),
                    allinstout.getServid(), null, allinstout.isOutcome());
            System.out.println("ResourceSelectionLogic --> Post E2ENetworkAllocateReply Event");
            SingletonEventBus.getBus().post(allrepout);
            return;
        }
        
        long physicalpath = -1, logicallink = -1;
        float availablebw = -1, ll_availablebw = -1;
        //retrieve from abstract DB the logical link id from the logical paths, 
        try {
            java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT logicalLinkId  FROM logicalpath where logicalPathId=?");
            ps.setLong(1, allinstout.getLogicalpath());
            java.sql.ResultSet rs = ps.executeQuery();
            //Select the path with maximum available bandwidth
            while (rs.next()) {
                logicallink = rs.getLong("logicalLinkId");  
            }
            System.out.println("handle_E2ENetworkAllocateRequest ---> logicallink: " + logicallink + "");
            
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(ResourceSelectionLogic.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        //retrieve from abstract DB the available bw from logicallink
        try {
            java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT availableBandwidth  FROM logicallink where logicalLinkId=?");
            ps.setLong(1, logicallink);
            java.sql.ResultSet rs = ps.executeQuery();
            //Select the path with maximum available bandwidth
            while (rs.next()) {
                ll_availablebw = rs.getLong("availableBandwidth");
            }
            System.out.println("handle_E2ENetworkAllocateRequest ---> ll_availablebw: " + ll_availablebw + "");
            
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(ResourceSelectionLogic.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        //get the maximum available bw from logical path associated to the same logicallink id        
        try {
            java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT availableBandwidth  FROM logicalpath where logicalLinkId=?");
            ps.setLong(1, logicallink);
            java.sql.ResultSet rs = ps.executeQuery();
            //Select the path with maximum available bandwidth
            while (rs.next()) {
                
                if (availablebw == -1) {
                    //first element is selected
                    availablebw = rs.getFloat("availableBandwidth");
                } else {
                    //check if available bw is greater and select it
                    float tmp_availablebw = rs.getFloat("availableBandwidth");
                    if (tmp_availablebw > availablebw) {
                        //select this as candidate
                        availablebw = tmp_availablebw;
                    }
                }   
            }
            System.out.println("handle_E2ENetworkAllocateRequest ---> availablebw: " + availablebw + "");
            
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(ResourceSelectionLogic.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        //check if aavailable bw is the same as represented in the logical link. 
        //If not change the bandwidth capacity values in logical link in DB 
        if (availablebw != ll_availablebw) {
            //change bandwidth capacity in logical link
            try {
                long availableBandwidth;
                long allocatedBandwidth;
                long totalBandwidth;

                java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("SELECT "
                        + "logicalLinkId, "
                        + "availableBandwidth,"
                        + "totalBandwidth,"
                        + "allocatedBandwidth "
                        + "FROM logicallink WHERE logicalLinkId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setLong(1, logicallink);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    availableBandwidth = rs.getLong("availableBandwidth");
                    totalBandwidth = rs.getLong("totalBandwidth");
                    allocatedBandwidth = rs.getLong("allocatedBandwidth");
                    rs.updateString("availableBandwidth", Float.toString(availablebw));
                    rs.updateString("allocatedBandwidth", Float.toString(totalBandwidth-availablebw));
                    rs.updateRow();

                    System.out.println("handle_E2ENetworkAllocateRequest ---> Bandwidth updated in Abstract logical link table!");
                    System.out.println("handle_E2ENetworkAllocateRequest ---> new available bandwidth " + availablebw + "");
                    
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        } 
        
        E2ENetworkAllocateReply allrepout = new E2ENetworkAllocateReply(allinstout.getReqid(),
                allinstout.getServid(), allinstout.getVn(), allinstout.isOutcome());
        System.out.println("ResourceSelectionLogic --> Post E2ENetworkAllocateReply Event");
        SingletonEventBus.getBus().post(allrepout);
    }

    @Subscribe
    public void handle_E2EComputeTerminateRequest(E2EComputeTerminateRequest allreqev) {
        System.out.println("ResourceSelectionLogic --> Handle E2EComputeTerminateRequest Event");
        //insert element in hasmap of pening allocate req
        ComputeTerminateElem compel = new ComputeTerminateElem();
        compel.setTerminateRequest(allreqev.getComputereq());
        comptermmap.put(allreqev.getReqid(), compel);

        //Prepare and perform the query
        ComputeTerminateDBQuery dbev = new ComputeTerminateDBQuery(allreqev.getReqid(),
                allreqev.getServid(), allreqev.getComputereq());
        //post event
        System.out.println("ResourceSelectionLogic --> Post ComputeTerminateDBQuery Event");
        SingletonEventBus.getBus().post(dbev);
    }

    @Subscribe
    public void handle_ComputeTerminateDBQueryReply(ComputeTerminateDBQueryReply dbreply) {
        System.out.println("ResourceSelectionLogic --> Handle ComputeTerminateDBQueryReply Event");

        ComputeTerminateElem compTermElem = comptermmap.get(dbreply.getReqid());
        E2EComputeTerminateInstance allinst = new E2EComputeTerminateInstance(dbreply.getReqid(),
                dbreply.getServid(), dbreply.getDomlist(), dbreply.getPoplist(), 
                compTermElem);

        System.out.println("ResourceSelectionLogic --> Post E2EComputeTerminateInstance Event");
        SingletonEventBus.getBus().post(allinst);

    }

    @Subscribe
    public void handle_E2EComputeTerminateInstanceOutcome(E2EComputeTerminateInstanceOutcome allinstout) {
       System.out.println("ResourceSelectionLogic --> Handle E2EComputeTerminateInstanceOutcome Event");
        
       E2EComputeTerminateReply allrepout = new E2EComputeTerminateReply(allinstout.getReqid(),
               allinstout.getServid(), allinstout.getComputeIdList());

       System.out.println("ResourceSelectionLogic --> Post E2EComputeTerminateReply Event");
       SingletonEventBus.getBus().post(allrepout);
    }

    @Subscribe
    public void handle_E2ENetworkTerminateRequest(E2ENetworkTerminateRequest allreqev) {
        System.out.println("ResourceSelectionLogic --> Handle E2ENetworkTerminateRequest Event");
        //insert element in hasmap of pening allocate req
        NetworkTerminateElem compel = new NetworkTerminateElem();
        compel.setTerminateRequest(allreqev.getNetServIdList());
        nettermmap.put(allreqev.getReqid(), compel);
        //Prepare and perform the query
        NetworkTerminateDBQuery dbev = new NetworkTerminateDBQuery(allreqev.getReqid(),
                allreqev.getServid(), allreqev.getNetServIdList());
        //post event
        System.out.println("ResourceSelectionLogic --> Post NetworkTerminateDBQuery Event");
        SingletonEventBus.getBus().post(dbev);
    }

    @Subscribe
    public void handle_NetworkTerminateDBQueryReply(NetworkTerminateDBQueryReply dbreply) {
        System.out.println("ResourceSelectionLogic --> Handle NetworkTerminateDBQueryReply Event");
        E2ENetworkTerminateInstance allinst = new E2ENetworkTerminateInstance(dbreply.getReqid(),
                dbreply.getServid(), dbreply.getWimdomlistMap(), dbreply.getVimdomlistMap(), dbreply.getInterdomainLinksMap(),
                dbreply.getIntraPopLinksMap(), dbreply.getWanLinksMap(), dbreply.getWimPopListMap(), dbreply.getVimPopListMap(),
                dbreply.getWimNetworkTypeMap(), dbreply.getVimNetworkTypeMap(), dbreply.getWanResourceIdListMap(),
                dbreply.getNetServIdList(), dbreply.getLogicalPathList());

        System.out.println("ResourceSelectionLogic --> Post E2ENetworkTerminateInstance Event");
        SingletonEventBus.getBus().post(allinst);

    }

    @Subscribe
    public void handle_E2ENetworkTerminateInstanceOutcome(E2ENetworkTerminateInstanceOutcome allinstout) {
        System.out.println("ResourceSelectionLogic --> Handle E2ENetworkTerminateInstanceOutcome Event");
        long physicalpath = -1, logicallink = -1;
        float availablebw = -1, ll_availablebw = -1;
        //retrieve from abstract DB the logical link id from the logical paths,
        for (int i = 0; i < allinstout.getLogicalPathList().size(); i++) {
            try {
                java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT logicalLinkId  FROM logicalpath where logicalPathId=?");
                ps.setLong(1, Long.valueOf(allinstout.getLogicalPathList().get(i)));
                java.sql.ResultSet rs = ps.executeQuery();
                //Select the path with maximum available bandwidth
                while (rs.next()) {
                    logicallink = rs.getLong("logicalLinkId");
                }
                System.out.println("handle_E2ENetworkAllocateRequest ---> logicallink: " + logicallink + "");

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            //retrieve from abstract DB the available bw from logicallink
            try {
                java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT availableBandwidth  FROM logicallink where logicalLinkId=?");
                ps.setLong(1, logicallink);
                java.sql.ResultSet rs = ps.executeQuery();
                //Select the path with maximum available bandwidth
                while (rs.next()) {
                    ll_availablebw = rs.getLong("availableBandwidth");
                }
                System.out.println("handle_E2ENetworkAllocateRequest ---> ll_availablebw: " + ll_availablebw + "");

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            //get the maximum available bw from logical path associated to the same logicallink id        
            try {
                java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT availableBandwidth  FROM logicalpath where logicalLinkId=?");
                ps.setLong(1, logicallink);
                java.sql.ResultSet rs = ps.executeQuery();
                //Select the path with maximum available bandwidth
                while (rs.next()) {

                    if (availablebw == -1) {
                        //first element is selected
                        availablebw = rs.getFloat("availableBandwidth");
                    } else {
                        //check if available bw is greater and select it
                        float tmp_availablebw = rs.getFloat("availableBandwidth");
                        if (tmp_availablebw > availablebw) {
                            //select this as candidate
                            availablebw = tmp_availablebw;
                        }
                    }
                }
                System.out.println("handle_E2ENetworkAllocateRequest ---> availablebw: " + availablebw + "");

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            //check if aavailable bw is the same as represented in the logical link. 
            //If not change the bandwidth capacity values in logical link in DB 
            if (availablebw != ll_availablebw) {
                //change bandwidth capacity in logical link
                try {
                    long availableBandwidth;
                    long allocatedBandwidth;
                    long totalBandwidth;

                    java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

                 

                    PreparedStatement ps = conn.prepareStatement("SELECT "
                        + "logicalLinkId, "
                        + "availableBandwidth,"
                        + "totalBandwidth,"
                        + "allocatedBandwidth "
                        + "FROM logicallink WHERE logicalLinkId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    
                    
                    
                    
                    ps.setLong(1, logicallink);
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        availableBandwidth = rs.getLong("availableBandwidth");
                        totalBandwidth = rs.getLong("totalBandwidth");
                        allocatedBandwidth = rs.getLong("allocatedBandwidth");
                        rs.updateString("availableBandwidth", Float.toString(availablebw));
                        rs.updateString("allocatedBandwidth", Float.toString(totalBandwidth - availablebw));
                        rs.updateRow();

                        System.out.println("handle_E2ENetworkAllocateRequest ---> Bandwidth updated in Abstract logical link table!");
                        System.out.println("handle_E2ENetworkAllocateRequest ---> new available bandwidth " + availablebw + "");

                    }

                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(ResourceSelectionLogic.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
        }

        E2ENetworkTerminateReply allrepout = new E2ENetworkTerminateReply(allinstout.getReqid(),
                allinstout.getServid(), allinstout.getNetServIdList());
        System.out.println("ResourceSelectionLogic --> Post E2ENetworkTerminateReply Event");
        SingletonEventBus.getBus().post(allrepout);
    }

}
