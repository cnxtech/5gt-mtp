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
import com.mtp.events.placement.PAComputeReply;
import com.mtp.events.placement.PAComputeRequest;
import com.mtp.events.placement.PAComputeStatus;
import com.mtp.events.placement.PANetworkReply;
import com.mtp.events.placement.PANetworkRequest;
import com.mtp.events.placement.PANetworkStatus;
//import com.mtp.common.objects.VIMAbstractElem;
import com.mtp.events.resourcemanagement.ComputeAllocation.ComputeAllocateDBQuery;
import com.mtp.events.resourcemanagement.ComputeAllocation.ComputeAllocateDBQueryReply;
import com.mtp.events.resourcemanagement.ComputeAllocation.E2EComputeAllocateInstance;
import com.mtp.events.resourcemanagement.ComputeAllocation.E2EComputeAllocateInstanceOutcome;
import com.mtp.events.resourcemanagement.ComputeAllocation.E2EComputeAllocateReply;
import com.mtp.events.resourcemanagement.ComputeAllocation.E2EComputeAllocateRequest;
import com.mtp.events.resourcemanagement.ComputeTermination.ComputeTerminateDBQueryReply;
import com.mtp.events.resourcemanagement.ComputeTermination.ComputeTerminateMECQuery;
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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResourceSelectionLogic {

    private HashMap<Long, ComputeAllocateElem> compallmap;
    private HashMap<Long, NetworkAllocateElem> netallmap;
    private HashMap<Long, ComputeTerminateElem> comptermmap;
    private HashMap<Long, NetworkTerminateElem> nettermmap;
    private boolean pacompstatus;
    private boolean panetstatus;
    private long panetpathcount;

    public ResourceSelectionLogic() {
        compallmap = new HashMap();
        netallmap = new HashMap();
        comptermmap = new HashMap();
        nettermmap = new HashMap();
        pacompstatus = false;
        panetstatus = false;
        panetpathcount = 1000; //From 1000 to on are the computed logical paths from pa network
    }
    
    @Subscribe
    public void handle_PAComputeStatus(PAComputeStatus pacompst) {
        System.out.println("ResourceSelectionLogic --> Handle PAComputeStatus Event");
        String compstring = new String ("enable");
        if (pacompst.getStatus().compareTo(compstring) == 0) {
            pacompstatus = true;
        } else {
            pacompstatus = false;
        }
    }
    
    @Subscribe
    public void handle_PANetworkStatus(PANetworkStatus panetst) {
        System.out.println("ResourceSelectionLogic --> Handle PANetworkStatus Event");
        String compstring = new String ("enable");
        if (panetst.getStatus().compareTo(compstring) == 0) {
            panetstatus = true;
        } else {
            panetstatus = false;
        }
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
        //XXX: TODO R2: Check Policy for PA algorithm, not select the first
        
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
        ComputeAllocateElem compAllocElem = compallmap.get(dbreply.getReqid());
        if (pacompstatus == true ) {
            PAComputeRequest compreq = new PAComputeRequest(dbreply.getReqid(),
                dbreply.getServid(), domId, nfviPopId, compAllocElem);
            System.out.println("ResourceSelectionLogic --> Post PAComputeRequest Event");
            SingletonEventBus.getBus().post(compreq);
            return;
        }
        //Retrieve ComputeAllocateElem instance  from compallmap using the reqId
        
        E2EComputeAllocateInstance allinst = new E2EComputeAllocateInstance(dbreply.getReqid(),
                dbreply.getServid(), domId, nfviPopId, compAllocElem);
        System.out.println("ResourceSelectionLogic --> Post E2EComputeAllocateInstance Event");
        SingletonEventBus.getBus().post(allinst);

    }

    @Subscribe
    public void handle_PAComputeReply(PAComputeReply pacomprep) {
        System.out.println("ResourceSelectionLogic --->  Handle PAComputeReply Event");
        //Retrieve ComputeAllocateElem instance  from compallmap using the reqId
        ComputeAllocateElem compAllocElem = compallmap.get(pacomprep.getReqid());
        E2EComputeAllocateInstance allinst = new E2EComputeAllocateInstance(pacomprep.getReqid(),
                pacomprep.getServid(), pacomprep.getDomid(), pacomprep.getNfvipopid(), compAllocElem);
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
            allreqev.getNetworkreq().getLogicalLinkPathList().get(i).getReqBandwidth();
            System.out.println("handle_E2ENetworkAllocateRequest ----> LogicalLinkId:" + logicallink + "");
            if (pacompstatus == true) {
                //Prepare and perform the query
                PANetworkRequest panetreq = new PANetworkRequest(allreqev.getReqid(),
                        allreqev.getServid(), allreqev.getNetworkreq(), logicallink, 
                        allreqev.getNetworkreq().getLogicalLinkPathList().get(i).getReqBandwidth(),
                        allreqev.getNetworkreq().getLogicalLinkPathList().get(i).getReqLatency(),
                        allreqev.getNetworkreq().getLogicalLinkPathList().get(i).getLogicalLinkAttributes().getSrcGwIpAddress(),
                        allreqev.getNetworkreq().getLogicalLinkPathList().get(i).getLogicalLinkAttributes().getDstGwIpAddress());
                //post event
                System.out.println("ResourceSelectionLogic --> Post PANetworkRequest Event");
                SingletonEventBus.getBus().post(panetreq);
                continue;
            }        
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
    public void handle_PANetworkReply(PANetworkReply panetrep) {
        System.out.println("ResourceSelectionLogic --->  Handle PANetworkReply Event");
        long logicalpathid = -1; //key used to insert the phisicalpath
        Integer AbstrSrcPoPId = -1, AbstrDstPoPId = -1;
        String srcid = null, dstid = null;
        //Select abstract src and dst popid, src and dst

        try {
            java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT abstrSrcNfviPopId,abstrDestNfviPopId,"
                    + "srcRouterId,dstRouterId  FROM logicallink where logicalLinkId=?");
            ps.setLong(1, panetrep.getLogicalpathid());
            java.sql.ResultSet rs = ps.executeQuery();
            //Select the path with minimum available bandwidth
            while (rs.next()) {
               AbstrSrcPoPId = rs.getInt("abstrSrcNfviPopId");
               AbstrDstPoPId = rs.getInt("abstrDestNfviPopId");
               srcid = rs.getString("srcRouterId");
               dstid = rs.getString("dstRouterId");
            }
            System.out.println("handle_PANetworkReply ---> AbstrSrcPoPId: " + AbstrSrcPoPId + "");
            System.out.println("handle_PANetworkReply ---> AbstrDstPoPId: " + AbstrDstPoPId + "");
            System.out.println("handle_PANetworkReply ---> srcid: " + srcid + "");
            System.out.println("handle_PANetworkReply ---> dstid: " + dstid + "");

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(ResourceSelectionLogic.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        //retrieve interdomain link id delay and bw
        ArrayList<Integer> interdomainkeys = new ArrayList<Integer>();
        ArrayList<Integer> wanlinkkeys = new ArrayList<Integer>();
        ArrayList<Float> delayvals = new ArrayList<Float>();
        ArrayList<Float> bwvals = new ArrayList<Float>();
        
        for (int i =0; i < panetrep.getInterWanLinks().size(); i++) {
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select interDomainLinkId,delay,availableBandwidth from interdomainlink "
                        + "where srcDomId=? AND dstDomId=? AND srcGwId=? AND dstGwId=? AND srcGWIp=? AND dstGwIp=?");
                ps.setInt(1, Integer.valueOf(panetrep.getInterWanLinks().get(i).getAWimId()));
                ps.setInt(2, Integer.valueOf(panetrep.getInterWanLinks().get(i).getZWimId()));
                ps.setString(3, panetrep.getInterWanLinks().get(i).getAPEId());
                ps.setString(4, panetrep.getInterWanLinks().get(i).getZPEId());
                ps.setString(5, String.valueOf(panetrep.getInterWanLinks().get(i).getALinkId()));
                ps.setString(6, String.valueOf(panetrep.getInterWanLinks().get(i).getZLinkId()));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    interdomainkeys.add(rs.getInt("interDomainLinkId"));
                    delayvals.add(Float.valueOf(rs.getString("delay")));
                    bwvals.add(Float.valueOf(rs.getString("availableBandwidth")));
                    System.out.println("handle_PANetworkReply ---> "
                            + "interdomainkeys: " + rs.getInt("interDomainLinkId") + "");
                    System.out.println("handle_PANetworkReply ---> "
                            + "delayvals: " + rs.getString("delay") + "");
                    System.out.println("handle_PANetworkReply ---> "
                            + "bwvals: " + rs.getString("availableBandwidth") + "");
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        //retrieve wan paths link id, delay and bw
        for (int i =0; i < panetrep.getWanPaths().size(); i++) {
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select networkResId,delay,availableCapacity from networkresources "
                        + "where  srcGwId=? AND dstGwId=? AND srcGWIp=? AND dstGwIp=?");
                ps.setString(1, panetrep.getWanPaths().get(i).getWanPathElements().get(0).getANodeId());
                ps.setString(2, panetrep.getWanPaths().get(i).getWanPathElements().get(0).getZNodeId());
                ps.setString(3, String.valueOf(panetrep.getWanPaths().get(i).getWanPathElements().get(0).getALinkId()));
                ps.setString(4, String.valueOf(panetrep.getWanPaths().get(i).getWanPathElements().get(0).getZLinkId()));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    wanlinkkeys.add(rs.getInt("networkResId"));
                    delayvals.add(Float.valueOf(rs.getString("delay")));
                    bwvals.add(Float.valueOf(rs.getString("availableCapacity")));
                    System.out.println("handle_PANetworkReply ---> "
                            + "wanlinkkeys: " + rs.getInt("networkResId") + "");
                    System.out.println("handle_PANetworkReply ---> "
                            + "delayvals: " + rs.getString("delay") + "");
                    System.out.println("handle_PANetworkReply ---> "
                            + "bwvals: " + rs.getString("availableCapacity") + "");
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        
        //compute delay as sum of values in delayvals
        double latencyval;
        latencyval = 0.0;
        for (int i = 0; i < delayvals.size(); i++) {
            latencyval += delayvals.get(i);
        }
        double bwval;
        bwval = bwvals.get(0); //set to first value
        for (int i = 1; i < delayvals.size(); i++) { //not check first value
            if (bwvals.get(i) <bwval) {
                bwval = bwvals.get(i);
            }
        }
        
        //Insert new logicalpath associated to the logicalpathid
        try {
            java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

            // Insert  data into abstract logicallink table
            PreparedStatement ps = conn.prepareStatement("INSERT INTO logicalpath"
                    + "(abstrSrcNfviPopId,abstrDestNfviPopId,srcRouterId,dstRouterId,srcRouterIp,dstRouterIp,delay,"
                    + "availableBandwidth,reservedBandwidth,totalBandwidth,allocatedBandwidth,LogicalLinkId)"
                    + "VALUES(?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, AbstrSrcPoPId);
            ps.setLong(2, AbstrDstPoPId);
            ps.setString(3, srcid);
            ps.setString(4, dstid);
            ps.setString(5, Long.toString(panetpathcount));
            ps.setString(6, Long.toString(panetpathcount));
            ps.setString(7, Double.toString(latencyval));
            ps.setString(8, Double.toString(bwval));
            ps.setString(8, Double.toString(0.0));
            ps.setString(10, Double.toString(0.0));
            ps.setString(11, Double.toString(bwval));
            ps.setLong(12, panetrep.getLogicalpathid());
            ps.executeUpdate();
            //increment logicalPathCount;
            panetpathcount++;
            System.out.println("handle_PANetworkReply ---> Abstract logicalpath resources data inserted into DB");
            ResultSet rs = ps.getGeneratedKeys();

            if (rs != null && rs.next()) {
                logicalpathid = rs.getLong(1);
                System.out.println("handle_PANetworkReply ---> logicalpathid:" + logicalpathid + "");
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(E2EAbstractionLogic.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        //create association logicalpathid and iterdomain links
        for (int i = 0; i < interdomainkeys.size(); i++) {
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                // Insert  data into abstract logicallink table
                PreparedStatement ps = conn.prepareStatement("INSERT INTO logicalpath_interdomainlink"
                        + "(logicalPathId,interDomainLinkId)"
                        + "VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, logicalpathid);
                ps.setLong(2, interdomainkeys.get(i));

                ps.executeUpdate();
                System.out.println("handle_PANetworkReply ---> interdomain resources data inserted into DB");
                ResultSet rs = ps.getGeneratedKeys();

                if (rs != null && rs.next()) {
                    System.out.println("handle_PANetworkReply ---> interdomain association key:" + rs.getLong(1) + "");
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(E2EAbstractionLogic.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        //create association logical path link and wan links
        for (int i = 0; i < wanlinkkeys.size(); i++) {
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                // Insert  data into abstract logicallink table
                PreparedStatement ps = conn.prepareStatement("INSERT INTO logicalpath_networkresources"
                        + "(logicalPathId,networkResId)"
                        + "VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, logicalpathid);
                ps.setLong(2, wanlinkkeys.get(i));

                ps.executeUpdate();
                System.out.println("handle_PANetworkReply ---> netresources resources data inserted into DB");
                ResultSet rs = ps.getGeneratedKeys();

                if (rs != null && rs.next()) {
                    System.out.println("handle_PANetworkReply ---> netresources association key:" + rs.getLong(1) + "");
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(E2EAbstractionLogic.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        //Call NetworkAllocateDBQuery
        NetworkAllocateDBQuery dbev = new NetworkAllocateDBQuery(panetrep.getReqid(),
               panetrep.getServid(), 
                netallmap.get(panetrep.getReqid()).getAllocateRequest().getNetworkreq(), 
                logicalpathid);
       //post event
       System.out.println("ResourceSelectionLogic --> Post NetworkAllocateDBQuery Event");
       SingletonEventBus.getBus().post(dbev);
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
//        ComputeTerminateDBQuery dbev = new ComputeTerminateDBQuery(allreqev.getReqid(),
//                allreqev.getServid(), allreqev.getComputereq());
//        System.out.println("ResourceSelectionLogic --> Post ComputeTerminateDBQuery Event");      
//        SingletonEventBus.getBus().post(dbev);
          
          ComputeTerminateMECQuery dbev = new ComputeTerminateMECQuery(allreqev.getReqid(),
                allreqev.getServid(), allreqev.getComputereq());
        //post event
        System.out.println("ResourceSelectionLogic --> Post ComputeTerminateMECQuery Event");
        SingletonEventBus.getBus().post(dbev);
    }

    @Subscribe
    public void handle_ComputeTerminateDBQueryReply(ComputeTerminateDBQueryReply dbreply) {
        System.out.println("ResourceSelectionLogic --> Handle ComputeTerminateDBQueryReply Event");

        ComputeTerminateElem compTermElem = comptermmap.get(dbreply.getReqid());
        E2EComputeTerminateInstance allinst = new E2EComputeTerminateInstance(dbreply.getReqid(),
                dbreply.getServid(), dbreply.getDomlist(), dbreply.getPoplist(), 
                compTermElem, dbreply.getVmIdList());

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
