/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.extinterface.sbi;

import com.mtp.extinterface.sbi.IFA005Threads.QueryWIMThread;
import com.mtp.extinterface.sbi.IFA005Threads.QueryMECThread;
import com.mtp.extinterface.sbi.IFA005Threads.TerminateVIMComputeThread;
import com.mtp.extinterface.sbi.IFA005Threads.AllocateVIMNetworkThread;
import com.mtp.extinterface.sbi.IFA005Threads.AllocateVIMComputeThread;
import com.mtp.extinterface.sbi.IFA005Threads.QueryVIMThread;
import com.mtp.extinterface.sbi.IFA005Threads.AllocateWIMNetworkThread;
import com.mtp.extinterface.sbi.IFA005Threads.TerminateWIMNetworkThread;
import com.mtp.extinterface.sbi.IFA005Threads.QueryRadioThread;
import com.mtp.extinterface.sbi.IFA005Threads.TerminateVIMNetworkThread;
import com.google.common.eventbus.Subscribe;
import com.mtp.DbConnectionPool.DbDomainDatasource;
import com.mtp.SingletonEventBus;
import com.mtp.common.objects.DomainElem;
import com.mtp.common.objects.NetworkEndpoints;
import com.mtp.events.abstraction.Creation.DomainSubscriber;
import com.mtp.events.resourcemanagement.ComputeAllocation.ComputeAllocateVIMReply;
import com.mtp.events.resourcemanagement.ComputeAllocation.ComputeAllocateVIMReq;
import com.mtp.events.resourcemanagement.ComputeTermination.ComputeTerminateVIMReq;
import com.mtp.events.resourcemanagement.NetworkAllocation.NetworkAllocateVIMReply;
import com.mtp.events.resourcemanagement.NetworkAllocation.NetworkAllocateVIMReq;
import com.mtp.events.resourcemanagement.NetworkAllocation.NetworkAllocateWIMReply;
import com.mtp.events.resourcemanagement.NetworkAllocation.NetworkAllocateWIMReq;
import com.mtp.events.resourcemanagement.NetworkTermination.NetworkTerminateVIMReply;
import com.mtp.events.resourcemanagement.NetworkTermination.NetworkTerminateVIMReq;
import com.mtp.events.resourcemanagement.NetworkTermination.NetworkTerminateWIMReply;
import com.mtp.events.resourcemanagement.NetworkTermination.NetworkTerminateWIMReq;
import com.mtp.extinterface.sbi.StubThreads.AllocateVIMComputeStub;
import com.mtp.extinterface.sbi.StubThreads.AllocateVIMNetworkStub;
import com.mtp.extinterface.sbi.StubThreads.AllocateWIMNetworkStub;
import com.mtp.extinterface.sbi.StubThreads.QueryMECStub;
import com.mtp.extinterface.sbi.StubThreads.QueryRadioStub;
import com.mtp.extinterface.sbi.StubThreads.QueryVIMStub;
import com.mtp.extinterface.sbi.StubThreads.QueryWIMStub;
import com.mtp.extinterface.sbi.StubThreads.TerminateVIMComputeStub;
import com.mtp.extinterface.sbi.StubThreads.TerminateVIMNetworkStub;
import com.mtp.extinterface.sbi.StubThreads.TerminateWIMNetworkStub;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class SBIIF {

    //private HashMap<Long, DomainElem> dommap;
    private boolean stubmode;

    public SBIIF() {
        //dommap = new HashMap();
        stubmode = false;
    }

    public SBIIF(boolean stuben) {
        //dommap = new HashMap();
        stubmode = stuben;
    }

    public void setStubFlag(boolean val) {
        stubmode = val;
    }

    public boolean getStubFlag() {
        return stubmode;
    }
 
   
    @Subscribe
    public void handle_DomainSubscribeList(DomainSubscriber domlist) {
        System.out.println("SBIIF --> handle domainsubscriberEvent");
        DomainElem el;
        long portdom = 0;
        while (portdom != -1) {
            el = domlist.getDomainElem();
            System.out.println("DomainElem fields: type= " + el.getType()
                    + " ip= " + el.getIp() + " port= " + el.getPort() + " name= "
                    + el.getName() + " id= " + el.getId());

            /*
             * TODO: open HTTP connection towards domains and send IFA005 Query
             * and send IFA005 query to retrieve abstraction data
             */
            if (el.getType().equals("T-WIM")) {
                System.out.println("SBIIF --> Call WIM Query Thread: type= " + el.getType());
                if (stubmode) {
                    QueryWIMStub thr = new QueryWIMStub(el);
                    thr.start();
                } else {
                    QueryWIMThread thr = new QueryWIMThread(el);
                    thr.start();
                }

                //WIMResAbstractionEvent wabstrev = new WIMResAbstractionEvent(el.getId());
                //SingletonEventBus.getBus().post(wabstrev);
            } else if (el.getType().equals("VIM")) {
                System.out.println("SBIIF --> Call VIM Query Thread: type= " + el.getType());
                if (stubmode) {
                    QueryVIMStub thr = new QueryVIMStub(el);
                    thr.start();
                } else {
                    QueryVIMThread thr = new QueryVIMThread(el);
                    thr.start();
                }
                //VIMResAbstractionEvent vabstrev = new VIMResAbstractionEvent(el.getId());
                //SingletonEventBus.getBus().post(vabstrev);                
            } else if (el.getType().equals("MEC")) {
                System.out.println("SBIIF --> Call MEC Query Thread: type= " + el.getType());
                if (stubmode) {
                    QueryMECStub thr = new QueryMECStub(el);
                    thr.start();
                } else {
                    QueryMECThread thr = new QueryMECThread(el);
                    thr.start();
                }
                //VIMResAbstractionEvent vabstrev = new VIMResAbstractionEvent(el.getId());
                //SingletonEventBus.getBus().post(vabstrev);                
            } else if (el.getType().equals("R-WIM")) {
                System.out.println("SBIIF --> Call Radio Query Thread: type= " + el.getType());
                if (stubmode) {
                    QueryRadioStub thr = new QueryRadioStub(el);
                    thr.start();
                } else {
                    QueryRadioThread thr = new QueryRadioThread(el);
                    thr.start();
                }
                //VIMResAbstractionEvent vabstrev = new VIMResAbstractionEvent(el.getId());
                //SingletonEventBus.getBus().post(vabstrev);                
            } else {
                System.out.println("SBIIF --> Unknown Domain: type= " + el.getType());
            }
            //dommap.put(el.getId(), el);
            portdom = el.getPort();

            // 
        }

    }

    @Subscribe
    public void handle_ComputeAllocateVIMReq(ComputeAllocateVIMReq allvimreq) {
        System.out.println("SBIIF --> Handle ComputeAllocateVIMReq Event");

        //Retrieve Domain el from domid using domainlist.xml
        //DomainElem el = dommap.get(allvimreq.getDomId());
        //START - Retrieve Domain el from domid using database connection
        String typeval = null;
        String nameval = null;
        String ipval = null;
        Long portval = null;
        Long idval = null;

        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select domId,name,type,ip,port from domain where domId=?");
            ps.setLong(1, allvimreq.getDomid());
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                typeval = rs.getString("type");
                nameval = rs.getString("name");
                ipval = rs.getString("ip");
                portval = rs.getLong("port");
                idval = rs.getLong("domId");

            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(SBIIF.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        DomainElem el = new DomainElem(typeval, nameval, ipval, portval, idval);

        // END - Retrieve
        if (el == null) {
            ComputeAllocateVIMReply allvimrep = new ComputeAllocateVIMReply(allvimreq.getReqid(),
                    allvimreq.getServid(), allvimreq.getDomid(),
                    false, 0, "", null, allvimreq.getNfvipopid(), allvimreq.getComputereq());
            allvimrep.setErrorcode(-1);
            allvimrep.setErrormsg("Domid= " + allvimreq.getDomid() + " not present");

            SingletonEventBus.getBus().post(allvimrep);
        } else {
            if (stubmode) {
                AllocateVIMComputeStub thr = new AllocateVIMComputeStub(el, allvimreq);
                thr.start();
            } else {
                AllocateVIMComputeThread thr = new AllocateVIMComputeThread(el, allvimreq);
                thr.start();
            }
        }

        //R0.1 Just trigger the outcome (loopback)  
//        ComputeAllocateVIMReply allvimrep = new ComputeAllocateVIMReply(allvimreq.getReqId(),
//                                                    allvimreq.getServId(), allvimreq.getDomId(),
//                                                    allvimreq.getVIMElem(),true);
//        System.out.println("SBIIF --> Post ComputeAllocateVIMReply Event");
//        SingletonEventBus.getBus().post(allvimrep);
    }

    @Subscribe
    public void handle_NetworkAllocateVIMReq(NetworkAllocateVIMReq allvimreq) {
        System.out.println("SBIIF --> Handle NetworkAllocateVIMReq Event");
        //retrieve Domain el from domid
        //DomainElem el = dommap.get(allvimreq.getDomId());
                //retrieve Domain el from domid
        //DomainElem el = dommap.get(allwimreq.getDomId());
        //START - For each domid retrieve from DB the domain info
        ArrayList<DomainElem> domElemList = new ArrayList();
        ArrayList<Long> domList = allvimreq.getVimdomlist();
        long size = domList.size();

        for (int i = 0; i < size; i++) {
            //START - Retrieve Domain el from domid using database connection
            String typeval = null;
            String nameval = null;
            String ipval = null;
            Long portval = null;
            Long idval = null;

            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select name,type,ip,port from domain where domId=?");
                ps.setLong(1, allvimreq.getVimdomlist().get(i));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    typeval = rs.getString("type");
                    nameval = rs.getString("name");
                    ipval = rs.getString("ip");
                    portval = rs.getLong("port");
                    idval = allvimreq.getVimdomlist().get(i);

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(SBIIF.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            DomainElem el = new DomainElem(typeval, nameval, ipval, portval, idval);
            domElemList.add(el);
            // END - Retrieve

        }
        
        if (domElemList == null) {
            NetworkAllocateVIMReply allvimrep = new NetworkAllocateVIMReply(allvimreq.getReqId(),
                    allvimreq.getServId(), false, 0, null, null, -1);
            allvimrep.setErrorcode(-1);
            allvimrep.setErrormsg("A VIM DomId is not present during the allocation of LogicalLinkId: " + allvimreq.getLogicalPathId() + "");
            SingletonEventBus.getBus().post(allvimrep);
        } else {
            if (stubmode) {
                AllocateVIMNetworkStub thr = new AllocateVIMNetworkStub(domElemList, allvimreq);
                thr.start();
            } else {
                AllocateVIMNetworkThread thr = new AllocateVIMNetworkThread(domElemList, allvimreq);
                thr.start();
            }
        }

        //R0.1 Just trigger the outcome (loopback)
//        NetworkAllocateVIMReply allvimrep = new NetworkAllocateVIMReply(allvimreq.getReqId(),
//                                                    allvimreq.getServId(), allvimreq.getDomId(),
//                                                    allvimreq.getVIMElem(),true);
//        System.out.println("SBIIF --> Post NetworkAllocateVIMReply Event");
//        SingletonEventBus.getBus().post(allvimrep);
    }

    @Subscribe
    public void handle_NetworkAllocateWIMReq(NetworkAllocateWIMReq allwimreq) {
        System.out.println("SBIIF --> Handle NetworkAllocateWIMReq Event");
        //retrieve Domain el from domid
        //DomainElem el = dommap.get(allwimreq.getDomId());
        //START - For each domid retrieve from DB the domain info
        ArrayList<DomainElem> domElemList = new ArrayList();
        ArrayList<Long> domList = allwimreq.getWimdomlist();
        long size = domList.size();

        for (int i = 0; i < size; i++) {
            //START - Retrieve Domain el from domid using database connection
            String typeval = null;
            String nameval = null;
            String ipval = null;
            Long portval = null;
            Long idval = null;

            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select name,type,ip,port from domain where domId=?");
                ps.setLong(1, allwimreq.getWimdomlist().get(i));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    typeval = rs.getString("type");
                    nameval = rs.getString("name");
                    ipval = rs.getString("ip");
                    portval = rs.getLong("port");
                    idval = allwimreq.getWimdomlist().get(i);

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(SBIIF.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            DomainElem el = new DomainElem(typeval, nameval, ipval, portval, idval);
            domElemList.add(el);
            // END - Retrieve

        }

//        if (el == null) {
        if (domElemList == null) {
            NetworkAllocateWIMReply allwimrep = new NetworkAllocateWIMReply(allwimreq.getReqid(),
                    allwimreq.getServid(), false, 0, null, null, -1);
           
            
            allwimrep.setErrorcode(-1);
            //allwimrep.setErrorMsg("Domid= " + allwimreq.getDomId() + " not present");
            allwimrep.setErrormsg("A WIM DomId is not present during the allocation of LogicalLinkId: " + allwimreq.getLogicalPathId() + "");
            SingletonEventBus.getBus().post(allwimrep);
        } else {

            if (stubmode) {
                //AllocateWIMNetworkStub thr = new AllocateWIMNetworkStub(el, allwimreq);
                AllocateWIMNetworkStub thr = new AllocateWIMNetworkStub(domElemList, allwimreq);
                thr.start();
            } else {
                List <NetworkEndpoints> endpointlist = new ArrayList();
                //retrieve endpoints for each domain
                for (int i = 0; i < (allwimreq.getInterdomainLinks().size()-1); i++) {//last interdomainlinks take only the egress point 
                    String ingrip =new String();
                    String egrip = new String();
                    String ingrport = new String();
                    String egrport = new String();
                    try {
                        //select src endpoint
                        java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                        PreparedStatement ps = conn.prepareStatement("SELECT dstGwId,dstGwIp  FROM interdomainlink where interDomainLinkId=?");
                        ps.setLong(1, allwimreq.getInterdomainLinks().get(i));
                        java.sql.ResultSet rs = ps.executeQuery();
                        //Select the path with minimum available bandwidth
                        while (rs.next()) {
                            ingrip = rs.getString("dstGwId");
                            ingrport = rs.getString("dstGwIp");
                        }
                        rs.close();
                        ps.close();

                    } catch (SQLException ex) {
                        Logger.getLogger(SBIIF.class
                                .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                    }

                    try {
                        //select dst endpoint
                        java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                        PreparedStatement ps = conn.prepareStatement("SELECT srcGwId,srcGwIp  FROM interdomainlink where interDomainLinkId=?");
                        ps.setLong(1, allwimreq.getInterdomainLinks().get(i+1));
                        java.sql.ResultSet rs = ps.executeQuery();
                        //Select the path with minimum available bandwidth
                        while (rs.next()) {
                            egrip = rs.getString("srcGwId");
                            egrport = rs.getString("srcGwIp");
                        }
                        rs.close();
                        ps.close();

                    } catch (SQLException ex) {
                        Logger.getLogger(SBIIF.class
                                .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                    }
                    
                    //create endpoint
                    NetworkEndpoints endp = new NetworkEndpoints(ingrip, ingrport, egrip, egrport);
                    endpointlist.add(endp);
                }

                //AllocateWIMNetworkThread thr = new AllocateWIMNetworkThread(el, allwimreq);
                AllocateWIMNetworkThread thr = new AllocateWIMNetworkThread(domElemList, allwimreq, endpointlist);
                thr.start();
            }
        }

        //R0.1 Just trigger the outcome (loopback)
//        NetworkAllocateWIMReply allwimrep = new NetworkAllocateWIMReply(allwimreq.getReqId(),
//                                                    allwimreq.getServId(), allwimreq.getDomId(),
//                                                    allwimreq.getWIMElem(),true);
//        System.out.println("SBIIF --> Post NetworkAllocateWIMReply Event");
//        SingletonEventBus.getBus().post(allwimrep);
    }

    @Subscribe
    public void handle_ComputeTerminateVIMReq(ComputeTerminateVIMReq termvimreq) {
        System.out.println("SBIIF --> Handle ComputeTerminateVIMReq Event");
        //retrieve Domain el from domid
        //DomainElem el = dommap.get(termvimreq.getDomId());

        //START - For each domid retrieve from DB the domain info
        ArrayList<DomainElem> domElemList = new ArrayList();
        ArrayList<Long> domList = termvimreq.getDomlist();
        long size = domList.size();

        // while (!computeIdList.isEmpty()){
        for (int i = 0; i < size; i++) {

            String typeval = null;
            String nameval = null;
            String ipval = null;
            Long portval = null;
            Long idval = null;
            long domId = domList.get(i);

            try {

                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select domId,name,type,ip,port from domain where domId=?");
                ps.setLong(1, domId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    typeval = rs.getString("type");
                    nameval = rs.getString("name");
                    ipval = rs.getString("ip");
                    portval = rs.getLong("port");
                    idval = rs.getLong("domId");

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(SBIIF.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            DomainElem el = new DomainElem(typeval, nameval, ipval, portval, idval);

            domElemList.add(el);

            // END - Retrieve
        }


        if (stubmode) {
            //TerminateVIMComputeStub thr = new TerminateVIMComputeStub(el, termvimreq);
            TerminateVIMComputeStub thr = new TerminateVIMComputeStub(domElemList, termvimreq);
            thr.start();
        } else {
            //TerminateVIMComputeThread thr = new TerminateVIMComputeThread(el, termvimreq);
            TerminateVIMComputeThread thr = new TerminateVIMComputeThread(domElemList, termvimreq);
            thr.start();
        }

        //R0.1 Just trigger the outcome (loopback)
//        ComputeTerminateVIMReply allvimrep = new ComputeTerminateVIMReply(allvimreq.getReqId(),
//                                                    allvimreq.getServId(), allvimreq.getDomId(),
//                                                    allvimreq.getVIMElem(),true);
//        System.out.println("SBIIF --> Post ComputeTerminateVIMReply Event");
//        SingletonEventBus.getBus().post(allvimrep);
    }

    @Subscribe
    public void handle_NetworkTerminateVIMReq(NetworkTerminateVIMReq termvimreq) {
        System.out.println("SBIIF --> Handle NetworkTerminateVIMReq Event");

        //Retrieve Domain el 
         //DomainElem el = dommap.get(termwimreq.getDomId());
          
        //Retrieve Domain el from domid
        HashMap<Integer,List<DomainElem>> elMap= new HashMap();
         //Loop on logicalLinks       
        for (int j = 0; j < termvimreq.getVimdomlistMap().size(); j++) {            
        ArrayList<Long> domList = termvimreq.getVimdomlistMap().get(j); 
            ArrayList<DomainElem> domElemList = new ArrayList();
        
        
        
        

        //Loop on domainIds
        for (int i = 0; i < termvimreq.getVimdomlistMap().get(j).size(); i++) {
            //START - Retrieve Domain el from domid using database connection
            String typeval = null;
            String nameval = null;
            String ipval = null;
            Long portval = null;
            Long idval = null;

            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select name,type,ip,port from domain where domId=?");
                ps.setLong(1, termvimreq.getVimdomlistMap().get(j).get(i));
                
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    typeval = rs.getString("type");
                    nameval = rs.getString("name");
                    ipval = rs.getString("ip");
                    portval = rs.getLong("port");
                    idval = termvimreq.getVimdomlistMap().get(j).get(i);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(SBIIF.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            DomainElem el = new DomainElem(typeval, nameval, ipval, portval, idval);
            domElemList.add(el);
            // END - Retrieve

        } //END - Loop on domaIds
        
        elMap.put(j, domElemList);
        
        }//END - Loop on logicalLinks

        //TO DO - Fix this part inside the if block
        if (elMap == null) {
         
            NetworkTerminateVIMReply termvimrep = new NetworkTerminateVIMReply();
            termvimrep.setReqid(termvimreq.getReqid());
            termvimrep.setServid(termvimreq.getServid());
            SingletonEventBus.getBus().post(termvimrep);
        } else {
            if (stubmode) {
                TerminateVIMNetworkStub thr = new TerminateVIMNetworkStub(elMap, termvimreq);
                thr.start();
            } else {

                
                TerminateVIMNetworkThread thr = new TerminateVIMNetworkThread(elMap, termvimreq);
                thr.start();
            }
        }

        //R0.1 Just trigger the outcome (loopback)
//        NetworkTerminateVIMReply allvimrep = new NetworkTerminateVIMReply(allvimreq.getReqId(),
//                                                    allvimreq.getServId(), allvimreq.getDomId(),
//                                                    allvimreq.getVIMElem(),true);
//        System.out.println("SBIIF --> Post NetworkTerminateVIMReply Event");
//        SingletonEventBus.getBus().post(allvimrep);
    }

    @Subscribe
    public void handle_NetworkTerminateWIMReq(NetworkTerminateWIMReq termwimreq) {
        System.out.println("SBIIF --> Handle NetworkTerminateWIMReq Event");
        //retrieve Domain el from DB
             //Retrieve Domain el from domid
        HashMap<Integer,List<DomainElem>> elMap= new HashMap();
         //Loop on logicalLinks       
        for (int j = 0; j < termwimreq.getWimdomlistMap().size(); j++) {            
        ArrayList<Long> domList = termwimreq.getWimdomlistMap().get(j); 
            ArrayList<DomainElem> domElemList = new ArrayList();
        
        
        
        

        //Loop on domainIds
        for (int i = 0; i < termwimreq.getWimdomlistMap().get(j).size(); i++) {
            //START - Retrieve Domain el from domid using database connection
            String typeval = null;
            String nameval = null;
            String ipval = null;
            Long portval = null;
            Long idval = null;

            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select name,type,ip,port from domain where domId=?");
                ps.setLong(1, termwimreq.getWimdomlistMap().get(j).get(i));
                
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    typeval = rs.getString("type");
                    nameval = rs.getString("name");
                    ipval = rs.getString("ip");
                    portval = rs.getLong("port");
                    idval = termwimreq.getWimdomlistMap().get(j).get(i);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(SBIIF.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            DomainElem el = new DomainElem(typeval, nameval, ipval, portval, idval);
            domElemList.add(el);
           

        } //END - Loop on domaIds
        
        elMap.put(j, domElemList);
        
        }//END - Loop on logicalLinks
        
        //TO DO - Fix this part inside the if block
        if (elMap == null) {
//            NetworkTerminateWIMReply termwimrep = new NetworkTerminateWIMReply(termwimreq.getReqId(),
//                    termwimreq.getServId(), termwimreq.getDomId(), false);
//            termwimrep.setErrorCode(-1);
//            termwimrep.setErrorMsg("Domid= " + termwimreq.getDomId() + " not present");

//UNCOMMENT below line after fixing the if block
 NetworkTerminateWIMReply termwimrep = new NetworkTerminateWIMReply();
            SingletonEventBus.getBus().post(termwimrep);
        } else {
            if (stubmode) {
                TerminateWIMNetworkStub thr = new TerminateWIMNetworkStub(elMap, termwimreq);
                thr.start();
            } else {
                TerminateWIMNetworkThread thr = new TerminateWIMNetworkThread(elMap, termwimreq);
                thr.start();
            }
        }
        //R0.1 Just trigger the outcome (loopback)
//        NetworkTerminateWIMReply allwimrep = new NetworkTerminateWIMReply(allwimreq.getReqId(),
//                                                    allwimreq.getServId(), allwimreq.getDomId(),
//                                                    allwimreq.getWIMElem(),true);
//        System.out.println("SBIIF --> Post NetworkTerminateWIMReply Event");
//        SingletonEventBus.getBus().post(allwimrep);
    }

}
