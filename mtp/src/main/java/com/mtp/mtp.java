/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp;

import com.google.common.eventbus.EventBus;
import com.mtp.DbConnectionPool.DbProperties;
import com.mtp.abstraction.E2EAbstractionLogic;
import com.mtp.abstraction.ResourceSelectionLogic;
import com.mtp.common.DatabaseDriver;
import com.mtp.extinterface.sbi.SBIIF;
import com.mtp.resourcemanagement.ResouceOrchestration;
//import java.lang.String;
import com.mtp.events.abstraction.Creation.Parsedomainlist;
import com.mtp.events.abstraction.Creation.StopServer;
import com.mtp.extinterface.nbi.swagger.api.AbstractComputeResourcesApi;
import com.mtp.extinterface.nbi.swagger.api.AbstractNetworkResourcesApi;
import com.mtp.extinterface.nbi.swagger.api.AbstractResourcesApi;
import com.mtp.extinterface.nbi.swagger.api.NBIIF;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.ibatis.jdbc.ScriptRunner;

//import com.mtp.DeadEventListener;
/**
 * @author user
 */
public class mtp {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        boolean onexit = false;
        boolean stub_en = true; //enable stubmode
        Scanner scanner = new Scanner(System.in);
        String prompt;
        //Create eventbus
        System.out.println("MTP START");
        EventBus evbus;
        DbProperties dbPropertires = DbProperties.getInstance();
        NBIIF soif = new NBIIF(args[1], args[2]);
        evbus = SingletonEventBus.getBus();

        //see if STUB_MODE is set as properties
        String stubmode;
        stubmode = System.getProperty("STUB_ENABLE");

        if ((stubmode == null) || (!stubmode.equals("yes"))) {
            //Execute MTP using IFA005 as SBI
            System.out.println("///////STUB MODE DISABLE /////////");
            stub_en = false;
        } else {
            System.out.println("///////STUB MODE ENABLE /////////");
        }

        //DROP ALL TABLES FROM DB
//SCRIPT PATH
        String aSQLScriptFilePath = "..\\dbscripts\\utility\\mtpdb_drop.sql";
//Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtpdomdb?allowPublicKeyRetrieval=true&useSSL=false", "mtp", "mtp");
        Connection con = DriverManager.getConnection(dbPropertires.getProperty("DbMainURL"), dbPropertires.getProperty("DbMainUsername"), dbPropertires.getProperty("DbMainPassword"));
        try {
            // Initialize object for ScripRunner
            ScriptRunner sr = new ScriptRunner(con);
            // Give the input file to Reader
            Reader reader = new BufferedReader(
                    new FileReader(aSQLScriptFilePath));
            // Exctute script
            sr.runScript(reader);
        } catch (Exception e) {
            System.err.println("Failed to Execute" + aSQLScriptFilePath
                    + " The error is " + e.getMessage());
        }

        //CREATE DB SCHEME
//SCRIPT PATH
        aSQLScriptFilePath = "..\\dbscripts\\mtpscheme.sql";
//Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtpdomdb?allowPublicKeyRetrieval=true&useSSL=false", "mtp", "mtp");
        // con = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "mtp", "mtp");
        try {
            // Initialize object for ScripRunner
            ScriptRunner sr = new ScriptRunner(con);
            // Give the input file to Reader
            Reader reader = new BufferedReader(
                    new FileReader(aSQLScriptFilePath));
            // Exctute script
            sr.runScript(reader);
        } catch (Exception e) {
            System.err.println("Failed to Execute" + aSQLScriptFilePath
                    + " The error is " + e.getMessage());
        }
        //INSERT INTER_DOMAIN_LINKS into DB
//SCRIPT PATH
        aSQLScriptFilePath = "..\\dbscripts\\test_topology\\interdomainlinks.sql";
//Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtpdomdb?allowPublicKeyRetrieval=true&useSSL=false", "mtp", "mtp");
        // con = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "mtp", "mtp");
        try {
            // Initialize object for ScripRunner
            ScriptRunner sr = new ScriptRunner(con);
            // Give the input file to Reader
            Reader reader = new BufferedReader(
                    new FileReader(aSQLScriptFilePath));
            // Exctute script
            sr.runScript(reader);
        } catch (Exception e) {
            System.err.println("Failed to Execute" + aSQLScriptFilePath
                    + " The error is " + e.getMessage());
        }

        //INSERT COMPUTE_FALVOURS into DB
//SCRIPT PATH
        aSQLScriptFilePath = "..\\dbscripts\\test_topology\\computeFlavour.sql";
//Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtpdomdb?allowPublicKeyRetrieval=true&useSSL=false", "mtp", "mtp");
        // con = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "mtp", "mtp");
        try {
            // Initialize object for ScripRunner
            ScriptRunner sr = new ScriptRunner(con);
            // Give the input file to Reader
            Reader reader = new BufferedReader(
                    new FileReader(aSQLScriptFilePath));
            // Exctute script
            sr.runScript(reader);
        } catch (Exception e) {
            System.err.println("Failed to Execute" + aSQLScriptFilePath
                    + " The error is " + e.getMessage());
        }

        //register all modules
        System.out.println("MTP registering modules ...");
        evbus.register(new E2EAbstractionLogic());
        evbus.register(new ResourceSelectionLogic());
        evbus.register(new DatabaseDriver());
        evbus.register(soif);
        evbus.register(new SBIIF(stub_en));
        evbus.register(new ResouceOrchestration());
        //register jaxrs api
//        evbus.register(new ComputeResourcesApi());
//        evbus.register(new HealthzApi());
//        evbus.register(new InformationApi());
//        evbus.register(new NetworkResourcesApi());
//        evbus.register(new QuotasApi());
//        evbus.register(new ReservationsApi());
//        evbus.register(new SoftwareImagesApi());
        evbus.register(new AbstractComputeResourcesApi());
        evbus.register(new AbstractNetworkResourcesApi());
        evbus.register(new AbstractResourcesApi());

        //class to register dead event
        evbus.register(new DeadEventListener());
        //register deadevent for debug
        System.out.println("Done !!!");
        System.out.println("Start acquiring abstract domain info... !!!");
        String domfile = args[0];
        Parsedomainlist domlist = new Parsedomainlist(domfile);
        evbus.post(domlist);

        //System.out.println("MTP ready ");
        System.out.println("Start MTP feature TEST:");
        while (!onexit) {

            System.out.println("-- Type EXIT to exit the program:");

            prompt = scanner.next();
            if (prompt.equals("EXIT")) {
                StopServer servreq = new StopServer();
                evbus.post(servreq);
                onexit = true;
                continue;
            }

        }

    }
}
