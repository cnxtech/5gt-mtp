# MTP Core
This  flder includes the code for the MTP Core. It is released open source under [GPLv3)](https://www.gnu.org/licenses/gpl-3.0.en.html) license 

## INSTALLATION REQUIREMENTS
Tools needed to compile and run MTP:
1. [JAVA JRE 1.8.x or higher](https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html)
2. [Maven](https://maven.apache.org/) 
3. [NETBEANS 9.x or higher (optional)](https://netbeans.apache.org/download/nb90/nb90.html) 
4. [MySQL 8.0 server](https://www.mysql.com/)
5. [JDBC SQL connector](https://dev.mysql.com/downloads/connector/j/)
6. [MySQL Workbench (optional)](https://dev.mysql.com/downloads/workbench/)


## COMPILATION PROCEDURE
MTP is a maven project that can be compiled via command line or via a Java IDE ( [Netbeans](https://netbeans.apache.org/download/nb90/nb90.html) is recommended)

### COMPILATION VIA CLI
Compile the swagger java rest client (used by MTP as library)
1. enter in the ` java-client-generated ` directory
2. execute ` mvn install package `

Compile the mtp
1. enter in the ` mtp ` directory
2. execute ` mvn install package `


### COMPILATION VIA NETBEANS
Compile the swagger java rest client (used by MTP as library)
1. Open ` java-client-generated ` in Netbeans (under ` File->Open Project `)
2. Compile the java rest client (click on build button)

Compile the mtp
1. Open ` mtp ` in Netbeans (under ` File->Open Project `)
2. Compile the mtp (click on build button)

 

# RUNNING
## PREPARATION
MTP needs some sql scripts to configure specific aspects. Below is described how to configure properly the MySQL server and how to prepare the sql script.

### MySQL DB Server 
Install MySQL DB server (refer to MySQL online wiki for installation according the Operative System)

#### Configure MySQL DB Server access
MTP is configured to access the MySQL DB with login "mtp" and pwd "mtp").
The server installation provides a "root@localhost" access, with a generated temporary password; so after the installation you need to change the root password to "mtp" (please refer to MySQL wiki for this operation as it depends from the Operative System)



#### Prepare interdomainlinks.sql file
MTP needs a file containing all interdomain links, i.e. the physical links connecting VIM and WIM domains. Each link has the following format:
```sql
INSERT INTO mtpdomdb.interdomainlink
(srcDomId, 	/* Source domain Id of the link */
dstDomId,  	/* Destination domain Id of the link */
srcGwId,   	/* Source node Id of the link */
dstGwId,	/* Destination node Id of the link */
srcGWIp,	/* Source interface IP of the link */
dstGwIp,	/* Destination interface IP of the link */
delay,		/* Delay associated to the link */
availableBandwidth,	/* Available BW of the link */
reservedBandwidth,	/* Reserved BW of the link */
totalBandwidth,		/* Total BW of the link */
allocatedBandwidth)	/* Allocated BW of the link */
VALUES
(1, 
 5,
"16842753",	/* 32 bit representation of Ip 1.1.0.1*/ 
"83951617",	/* 32 bit representation of Ip 5.1.0.1*/
"15.1.0.1",
"15.1.0.5",
"10",
"10000000",
"0",
"10000000",
"0");
```

An example of interdomainlinks.sql script is already present. The file represents the interdomain links of the reference topology (ReferenceTopology.pptx under the folder ` dbscripts/test_topology `) 
 that is used for test. 

####  Prepare computeFlavour.sql file
MTP needs a file containing all compute flavours available for each NfviPop. Each flavour has the following format (refer to [IFA005 Section 8.4.2](https://www.etsi.org/deliver/etsi_gs/NFV-IFA/001_099/005/02.01.01_60/gs_NFV-IFA005v020101p.pdf) for the description of the fields):

```sql
INSERT INTO mtpdomdb.computeflavour
(computeFlavourId,		/* Unique key to identify the flavor (used by SO) */
flavourId,			/* Datacenter local identifier of the flavor */
accelerationCapability,		/* See IFA005 */
NfviPopId)			/* Identifier of the NfviPop using the flavor */
VALUES (1,1,"accCapability1",11);

INSERT INTO mtpdomdb.virtualstoragedata
(typeOfStorage,		/* See IFA005 */
sizeOfStorage,		/* See IFA005 */
computeFlavourId)	/* Key used in mtpdomdb.computeflavour table */
VALUES ("typeStorage1",10,1);


INSERT INTO mtpdomdb.virtualcpu
(cpuArchitecture,			/* See IFA005 */
numVirtualCpu,				/* See IFA005 */
cpuClock,				/* See IFA005 */
virtualCpuOversubscriptionPolicy,	/* See IFA005 */
computeFlavourId) 			/* Key used in mtpdomdb.computeflavour table */
VALUES ("x86",4,1,"policy1",1);

INSERT INTO mtpdomdb.virtualmemorydata
(virtualMemSize,			/* See IFA005 */
virtualMemOversubscriptionPolicy,	/* See IFA005 */
numaEnabled,				/* See IFA005 */
computeFlavourId)			/* Key used in mtpdomdb.computeflavour table */
VALUES (100,"policy1",true, 1);


INSERT INTO mtpdomdb.virtualnetworkinterfacedata
(networkId,			/* See IFA005 */
networkPortId,			/* See IFA005 */
typeVirtualNic,			/* See IFA005 */
typeConfiguration,		/* See IFA005 */
bandwidth,			/* See IFA005 */
accelerationCapability,		/* See IFA005 */
metadata,			/* See IFA005 */
computeFlavourId)		/* Key used in mtpdomdb.computeflavour table */
VALUES (0,0,0,"",100,"","",1);
```

An example of computeFlavour.sql script is already present. The file represents the flavours of the NfviPoPs of the reference topology (` ReferenceTopology.pptx ` under the folder  ` dbscripts/test_topology `) 
 that is used for test. 


   
### Domain configuration file 
as input MTP needs to know the list of VIM and WIM domains that can controls. The syntax is:

```
<?xml version="1.0" encoding="UTF-8"?>
<Domains>
    <Domain >
        <Type>type</Type>
        <Name>name</Name>
        <Id>id</Id>
        <Ip>127.0.0.1</Ip>
        <Port>10000</Port>
    </Domain>
	.....

    <Domain >
        <Type>type</Type>
        <Name>name</Name>
        <Id>id</Id>
        <Ip>127.0.0.1</Ip>
        <Port>10000</Port>
    </Domain>
</Domains>
```
XML file is a list of "Domain" entries where each entry represent the information for a specific domain. Specifically:
<Type>: identifies the domain type ("T-WIM" or "VIM")
<Name>: identifies the domain name (same reported in IFA005)
<Id>:   identifies the domain id (same reported in IFA005)
<Ip>:   identifies the IP of the server HTTP (use for REST call)
<Port>: identifies the port of the server HTTP (use for REST call)
 

## RUN MTP IN STUB MODE
MTP can run in two ways:
- Normal mode: It expects to have for each domain a corresponding MTP plugin to contact (detail of contact described in xml file)
- Stub mode: The domains are simulated as Stub threads (useful for test and debug). The reference domain topology is shown in ` ReferenceTopology.ppt ` file under ` dbscripts/test_topology `

The mode is enabled by a System properties (` STUB_ENABLE `) configured at the startup (see below)


## RUN MTP
Output of the compilation is a self-contained jar file. So to run it, just type ` java -D"STUB_ENABLE=<yes/no>" -jar <jarfile> <xmlfilename> <ip> <port> ` 
where:
- <jarfile> is the name of the jar file (with the local path to reach it)
- <xmlfilename> is the the file xml describing in Section 1.3 
- <ip> is the ip address where MTP is listening for HTTP REST calls (used for SO communication)
- <port> is the port number where MTP is listening for HTTP REST calls (used for SO communication)
- STUB_ENABLE=<yes/no> is a flag that enable/disable the stub mode
