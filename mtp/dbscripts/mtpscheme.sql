CREATE DATABASE mtpappddb;

CREATE TABLE mtpappddb.appd (
  	
appDId INT UNSIGNED NOT NULL AUTO_INCREMENT,
appName VARCHAR (50), 
appProvider VARCHAR (50),
appSoftVersion VARCHAR (50),
appDVersion VARCHAR (50),
mecVersion VARCHAR (50),
appInfoName VARCHAR (50),
appDescription VARCHAR (50),
/*virtualComputeDescriptor*/ 
/*swImageDescriptor*/
/*virtualStorageDescriptor*/
/*appExtCpd *//*This is a struct, See IFA011  */


/*appServiceRequired*/ 
/*appServiceOptional*/ 
/*appServiceProduced*/    /*not used  */  
/*appFeatureRequired */   /*not used  */ 
/*appFeatureOptional*/    /*not used  */ 
/*transportDependencies*/
/*appTrafficRule*/ 
/*appDNSRule */
/*appLatency*/ 
/*terminateAppInstanceOpConfig*/
/*changeAppInstanceStateOpConfig*/	
PRIMARY KEY (appDId) 
);


/*********************appServiceRequired*********************************************/
	
	CREATE TABLE mtpappddb.appservicerequired  (

	serviceRequiredId  INT UNSIGNED NOT NULL AUTO_INCREMENT,
	serName VARCHAR (50),
	/*serCategory VARCHAR (50),*/ /* this is a struct. See  ETSI GS MEC 011 */
	version VARCHAR (50),
	/* serTransportDependencies*/
	requestedPermissions VARCHAR (50), /*not specified in MEC010-02 */

	appDId INT UNSIGNED, /*key used for stored in 		domain tables */
	FOREIGN KEY (appDId) REFERENCES mtpappddb.appd (appDId),
	PRIMARY KEY (serviceRequiredId)
				);


		CREATE TABLE mtpappddb.sercategory  (

		href VARCHAR (50),
		id  VARCHAR (50),
		name VARCHAR (50),
		version VARCHAR (50),

		serviceRequiredId INT UNSIGNED, /* */
		FOREIGN KEY (serviceRequiredId) REFERENCES mtpappddb.appservicerequired (serviceRequiredId),
		PRIMARY KEY (serviceRequiredId)
					);			
			

		CREATE TABLE mtpappddb.sertransportdependencies  (

		serTransportDependenciesId INT UNSIGNED NOT NULL AUTO_INCREMENT,
		/*transport*/ 
		serializers ENUM('JSON', 'XML', 'PROTOBUF3') NOT NULL,
		labels VARCHAR (50),

		serviceRequiredId INT UNSIGNED, /*key used for stored in 		domain tables */
		FOREIGN KEY (serviceRequiredId) REFERENCES mtpappddb.appservicerequired (serviceRequiredId),
		PRIMARY KEY (serTransportDependenciesId)

					);

			
			CREATE TABLE mtpappddb.sertransport  (

			transportType ENUM('REST_HTTP', 'MB_TOPIC_BASED', 'MB_ROUTING','MB_PUBSUB','RPC','RPC_STREAMING','WEBSOCKET') NOT NULL,
			protocol  VARCHAR (50),
			version  VARCHAR (50),
			security  VARCHAR (50), /* this is a struct. See  ETSI GS MEC 011 */

			serTransportDependenciesId INT UNSIGNED,
			FOREIGN KEY (serTransportDependenciesId) REFERENCES mtpappddb.sertransportdependencies (serTransportDependenciesId),
				PRIMARY KEY (serTransportDependenciesId)
						);
/******************************************************************/

/****************appFeatureOptional**************************************************/
	
	CREATE TABLE mtpappddb.appserviceoptional  (

	serviceRequiredId  INT UNSIGNED NOT NULL AUTO_INCREMENT,
	serName VARCHAR (50),
	/*serCategory VARCHAR (50),*/ /* this is a struct. See  ETSI GS MEC 011 */
	version VARCHAR (50),
	/* serTransportDependencies*/
	requestedPermissions VARCHAR (50), /*not specified in MEC010-02 */

	appDId INT UNSIGNED, /*key used for stored in 		domain tables */
	FOREIGN KEY (appDId) REFERENCES mtpappddb.appd (appDId),
	PRIMARY KEY (serviceRequiredId)
				);


		CREATE TABLE mtpappddb.sercategoryoptional  (

		href VARCHAR (50),
		id  VARCHAR (50),
		name VARCHAR (50),
		version VARCHAR (50),

		serviceRequiredId INT UNSIGNED, /* */
		FOREIGN KEY (serviceRequiredId) REFERENCES mtpappddb.appserviceoptional (serviceRequiredId),
		PRIMARY KEY (serviceRequiredId)
					);			
			

		CREATE TABLE mtpappddb.sertransportdependenciesoptional  (

		serTransportDependenciesId INT UNSIGNED NOT NULL AUTO_INCREMENT,
		/*transport*/ 
		serializers ENUM('JSON', 'XML', 'PROTOBUF3') NOT NULL,
		labels VARCHAR (50),

		serviceRequiredId INT UNSIGNED, /*key used for stored in 		domain tables */
		FOREIGN KEY (serviceRequiredId) REFERENCES mtpappddb.appserviceoptional (serviceRequiredId),
		PRIMARY KEY (serTransportDependenciesId)
					);

			
			CREATE TABLE mtpappddb.transportdescriptoroptional  (

			transportType ENUM('REST_HTTP', 'MB_TOPIC_BASED', 'MB_ROUTING','MB_PUBSUB','RPC','RPC_STREAMING','WEBSOCKET') NOT NULL,
			protocol  VARCHAR (50),
			version  VARCHAR (50),
			security  VARCHAR (50), /* this is a struct. See  ETSI GS MEC 011 */

			serTransportDependenciesId INT UNSIGNED,
			FOREIGN KEY (serTransportDependenciesId) REFERENCES mtpappddb.sertransportdependenciesoptional (serTransportDependenciesId),
				PRIMARY KEY (serTransportDependenciesId)
						);
/******************************************************************/


/****************transportDependencies**************************************************/
	
	CREATE TABLE mtpappddb.transportdependencies  (

	transportdependenciesId INT UNSIGNED NOT NULL AUTO_INCREMENT,
	/*transport*/
	/*serializers*/
    serializers ENUM('JSON', 'XML', 'PROTOBUF3') NOT NULL,
	labels VARCHAR (50),
	
	appDId INT UNSIGNED, /*key used for stored in 		domain tables */
	FOREIGN KEY (appDId) REFERENCES mtpappddb.appd (appDId),
	PRIMARY KEY (transportdependenciesId)	
				);


				
		CREATE TABLE mtpappddb.transport  (

			transportType ENUM('REST_HTTP', 'MB_TOPIC_BASED', 'MB_ROUTING','MB_PUBSUB','RPC','RPC_STREAMING','WEBSOCKET') NOT NULL,
			protocol  VARCHAR (50),
			version  VARCHAR (50),
			security  VARCHAR (50), /* this is a struct. See  ETSI GS MEC 011 */

			transportDependenciesId INT UNSIGNED,
			FOREIGN KEY (transportDependenciesId) REFERENCES mtpappddb.transportdependencies (transportDependenciesId),
				PRIMARY KEY (transportDependenciesId)
						);		
		
/******************************************************************/


/****************appTrafficRule**************************************************/

	CREATE TABLE mtpappddb.apptrafficrule  (

	trafficRuleId  INT UNSIGNED NOT NULL AUTO_INCREMENT,
	filterType  ENUM('FLOW', 'PACKET') NOT NULL,
	priority   VARCHAR (50),
	/*trafficFilter*/
	action ENUM('DROP', 'FORWARD','DECAPSULATED','FORWARD_AS_IS', 'PASSTHROUGH', 'DUPLICATED_DECAPSULATED', 'DUPLICATE_AS_IS') NOT NULL,
	/*dstInterface*/
	
	appDId INT UNSIGNED, /*key used for stored in 		domain tables */
	FOREIGN KEY (appDId) REFERENCES mtpappddb.appd (appDId),
	PRIMARY KEY (trafficRuleId)	
				);
		
				
		CREATE TABLE mtpappddb.trafficfilter  (
		
		trafficFilterId INT UNSIGNED NOT NULL AUTO_INCREMENT,
		srcAddress VARCHAR (50),
		dstAddress VARCHAR (50),
		srcPort VARCHAR (50),
		dstPort VARCHAR (50),
		protocol VARCHAR (50),
		token VARCHAR (50),
		srcTunnelAddress VARCHAR (50),
		dstTunnelAddress VARCHAR (50),
		srcTunnelPort VARCHAR (50),
		dstTunnelPort VARCHAR (50),
		qci VARCHAR (50),
		dscp VARCHAR (50),
		tc VARCHAR (50),
		
		trafficRuleId INT UNSIGNED,
		FOREIGN KEY (trafficRuleId) REFERENCES mtpappddb.apptrafficrule (trafficRuleId),
		PRIMARY KEY (trafficFilterId)
						);


		CREATE TABLE mtpappddb.dstinterface  (
		
		dstInterfaceId INT UNSIGNED NOT NULL AUTO_INCREMENT,
		interfaceType ENUM('TUNNEL', 'MAC','IP') NOT NULL,
		/*TunnelInfo tunnelInfo */
		srcMACAddress VARCHAR (50),
		dstMACAddress VARCHAR (50),
		dstIPAddress VARCHAR (50),
		
		trafficRuleId INT UNSIGNED,
		FOREIGN KEY (trafficRuleId) REFERENCES mtpappddb.apptrafficrule (trafficRuleId),
		PRIMARY KEY (dstInterfaceId)
						);		
									
						
			CREATE TABLE mtpappddb.tunnelinfo  (
		
			tunnelType ENUM('GTP-U','GRE') NOT NULL,
			tunnelDstAddress  VARCHAR (50),
			tunnelSrcAddress  VARCHAR (50),
			tunnelSpecificData  VARCHAR (50),
		
			dstInterfaceId INT UNSIGNED,
			FOREIGN KEY (dstInterfaceId) REFERENCES mtpappddb.dstinterface (dstInterfaceId),
			PRIMARY KEY (dstInterfaceId)
						);				
		
/******************************************************************/
 

/****************appDNSRule**************************************************/

	CREATE TABLE mtpappddb.appdnsrule  (

	dnsRuleId 	INT UNSIGNED NOT NULL AUTO_INCREMENT,
	domainName  VARCHAR (50),
	ipAddressType ENUM('IP_V6','IP_V4') NOT NULL,
    ipAddress VARCHAR (50),
    ttl VARCHAR (50),
	
	appDId INT UNSIGNED, /*key used for stored in 		domain tables */
	FOREIGN KEY (appDId) REFERENCES mtpappddb.appd (appDId),
	PRIMARY KEY (dnsRuleId)	
				);			
/******************************************************************/

/****************appLatency**************************************************/

	CREATE TABLE mtpappddb.applatency  (

	timeUnit VARCHAR (50),
	latency VARCHAR (50),

	appDId INT UNSIGNED, /*key used for stored in 		domain tables */
	FOREIGN KEY (appDId) REFERENCES mtpappddb.appd (appDId),
	PRIMARY KEY (appDId)	
				);			
		
/******************************************************************/

/****************terminateAppInstanceOpConfig**************************************************/

	CREATE TABLE mtpappddb.terminateappinstanceopconfig  (
	  
	minGracefulTerminationTimeout  VARCHAR (50),
	macRecommendedGracefulTerminationTimeout  VARCHAR (50),

	appDId INT UNSIGNED, /*key used for stored in 		domain tables */
	FOREIGN KEY (appDId) REFERENCES mtpappddb.appd (appDId),
	PRIMARY KEY (appDId)	
				);			
		
/******************************************************************/


/****************changeAppInstanceStateOpConfig**************************************************/

	CREATE TABLE mtpappddb.changeappinstancestateopconfig  (
	  
	minGracefulTerminationTimeout  VARCHAR (50),
	macRecommendedGracefulTerminationTimeout  VARCHAR (50),

	appDId INT UNSIGNED, /*key used for stored in 		domain tables */
	FOREIGN KEY (appDId) REFERENCES mtpappddb.appd (appDId),
	PRIMARY KEY (appDId)	
				);			
		
/******************************************************************/


/****************appExtCpd**************************************************/

	CREATE TABLE mtpappddb.appextcpd  (
	  
	cpdId 	INT UNSIGNED NOT NULL AUTO_INCREMENT,
	layerProtocol ENUM('Ethernet','MPLS','ODU2','IPV4','IPV6','Pseudo-Wire') NOT NULL,
	cpRole VARCHAR (50),
	description VARCHAR (50),
	/*addressData*/
	/*virtualNetworkInterfaceRequirements*/
	
	appDId INT UNSIGNED, /*key used for stored in 		domain tables */
	FOREIGN KEY (appDId) REFERENCES mtpappddb.appd (appDId),
	PRIMARY KEY (cpdId )	
				);			
	
	
		CREATE TABLE mtpappddb.addressdata  (
	
	    addressdataId INT UNSIGNED NOT NULL AUTO_INCREMENT,
		addressType ENUM('MAC','IPV4','IPV6') NOT NULL,
		l2AddressData VARCHAR (50), 
		l3AddressData  VARCHAR (50),
	
		cpdId INT UNSIGNED, /*key used for stored in 		domain tables */
		FOREIGN KEY (cpdId) REFERENCES mtpappddb.appextcpd (cpdId),
		PRIMARY KEY (addressdataId)	
				);	
		
		
		CREATE TABLE mtpappddb.virtualnetworkinterfacerequirements  (
		
		virtualNetworkInterfaceRequirementsId INT UNSIGNED NOT NULL AUTO_INCREMENT,
	    name  VARCHAR (50),
		description  VARCHAR (50),
		supportMandatory BOOL NOT NULL,
		requirement VARCHAR (50),
		
		cpdId INT UNSIGNED, /*key used for stored in 		domain tables */
		FOREIGN KEY (cpdId) REFERENCES mtpappddb.appextcpd (cpdId),
		PRIMARY KEY (virtualNetworkInterfaceRequirementsId)	
				);
		
/******************************************************************/

/****************virtualStorageDescriptor**************************************************/

	CREATE TABLE mtpappddb.virtualstoragedescriptor (
	  
	id 	INT UNSIGNED NOT NULL AUTO_INCREMENT,
	typeOfStorage VARCHAR (50),
	sizeOfStorage VARCHAR (50),
	rdmaEnabled BOOL,
	swImageDesc VARCHAR (50),
	
	appDId INT UNSIGNED, /*key used for stored in 		domain tables */
	FOREIGN KEY (appDId) REFERENCES mtpappddb.appd (appDId),
	PRIMARY KEY (id)	
				);			
/***************************************************************************/

/****************swImageDescriptor**************************************************/

	CREATE TABLE mtpappddb.swimagedescriptor (
	  
	id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
	name  VARCHAR (50),
	version  VARCHAR (50),
	checksum_   VARCHAR (50),
	containerFormat  VARCHAR (50),
	diskFormat  VARCHAR (50),
	minDisk  VARCHAR (50),
	minRam  VARCHAR (50),
	size_  VARCHAR (50),
	swImage  VARCHAR (50),
	operatingSystem  VARCHAR (50),
	supportedVirtualizationEnvironment VARCHAR (50),
	
	appDId INT UNSIGNED, /*key used for stored in 		domain tables */
	FOREIGN KEY (appDId) REFERENCES mtpappddb.appd (appDId),
	PRIMARY KEY (id)	
				);	

/***************************************************************************/

/****************virtualComputeDescriptor**************************************************/

	CREATE TABLE mtpappddb.virtualcomputedescriptor  (
	  
	virtualComputeDescId  VARCHAR (50),
	/*List<RequestAdditionalCapabilityData> requestAdditionalCapabilities*/
	/*VirtualMemoryData virtualMemory*/
	/*VirtualCpuData virtualCpu*/

	appDId INT UNSIGNED, /*key used for stored in 		domain tables */
	FOREIGN KEY (appDId) REFERENCES mtpappddb.appd (appDId),
	PRIMARY KEY (appDId)	
				);		

		CREATE TABLE mtpappddb.requestadditionalcapabilities  (
	  
		capabilitiesId INT UNSIGNED NOT NULL AUTO_INCREMENT,
		requestedAdditionalCapabilityName VARCHAR (50),
		supportMandatory  BOOL NOT NULL,
		minRequestedAdditionalCapabilityVersion VARCHAR (50),
		preferredRequestedAdditionalCapabilityVersion VARCHAR (50),
		targetPerformanceParameters VARCHAR (50),

		appDId INT UNSIGNED, /*key used for stored in 		domain tables */
		FOREIGN KEY (appDId) REFERENCES mtpappddb.virtualcomputedescriptor (appDId),
		PRIMARY KEY (capabilitiesId)	
					);			

					
		CREATE TABLE mtpappddb.virtualmemory  (
	  
		virtualMemSize VARCHAR (50),
		numaEnabled BOOL,
		virtualMemOversubscriptionPolicy VARCHAR (50),

		appDId INT UNSIGNED, /*key used for stored in 		domain tables */
		FOREIGN KEY (appDId) REFERENCES mtpappddb.virtualcomputedescriptor (appDId),
		PRIMARY KEY (appDId)	
					);	
		
		
		CREATE TABLE mtpappddb.virtualcpu  (
	  
		cpuArchitecture VARCHAR (50),
		numVirtualCpu VARCHAR (50),
		virtualCpuClock VARCHAR (50),
		virtualCpuOversubscriptionPolicy VARCHAR (50),
		/*VirtualCpuPinningData virtualCpuPinning*/
	
		appDId INT UNSIGNED, /*key used for stored in 		domain tables */
		FOREIGN KEY (appDId) REFERENCES mtpappddb.virtualcomputedescriptor (appDId),
		PRIMARY KEY (appDId)	
					);	
					
					
			CREATE TABLE mtpappddb.virtualcpupinning  (
	
		    cpuPinningPolicy ENUM('static','dynamic'),
			cpuPinningMap VARCHAR (50),
		
			appDId INT UNSIGNED, /*key used for stored in 		domain tables */
			FOREIGN KEY (appDId) REFERENCES mtpappddb.virtualcpu (appDId),
			PRIMARY KEY (appDId)	
					);	
		
		
/******************************************************************/




CREATE DATABASE mtpabstrdb;



/* List of abstract Domain */
CREATE TABLE mtpabstrdb.abstrdomain (
    	abstrDomId  INT UNSIGNED NOT NULL AUTO_INCREMENT,/*correspond to vimid*/
    	tenantName VARCHAR (50), 
    	tenantId INT UNSIGNED,
PRIMARY KEY (abstrDomId)    		
);

/* List of NFVI POP (node of abstract domains)*/
CREATE TABLE mtpabstrdb.abstrnfvipop  (
    	abstrNfviPopId  INT UNSIGNED NOT NULL AUTO_INCREMENT,
    	vimId INT UNSIGNED, 
    	geographicalLocationInfo VARCHAR (50),
    	networkConnectivityEndpoint VARCHAR (50),
	abstrDomId INT UNSIGNED, /*key used for stored in 		domain tables */
FOREIGN KEY (abstrDomId) REFERENCES mtpabstrdb.abstrdomain (abstrDomId),
PRIMARY KEY (abstrNfviPopId)
    		);


	/* Radio specific table*/
	/**********Radio coverage area table********************************************************/

		CREATE TABLE mtpabstrdb.radio_coverage_area (
		
			coverageAreaId INT UNSIGNED NOT NULL AUTO_INCREMENT,
			coverageAreaMinBandwidth VARCHAR (50), 
			coverageAreaMaxBandwidth VARCHAR (50), 
			coverageAreaDelay VARCHAR (50), 
  
			latitude  VARCHAR (50), 
			longitude  VARCHAR (50), 
			altitude  VARCHAR (50), 
			range_  VARCHAR (50), 
				
			abstrNfviPopId INT UNSIGNED, /*key used for stored in 		domain tables */
			FOREIGN KEY (abstrNfviPopId) REFERENCES mtpabstrdb.abstrnfvipop (abstrNfviPopId),
			PRIMARY KEY (coverageAreaId)		
		);
	/******************************************************************/	

/* MEC specific table*/
/**********MEC Regions table********************************************************/

	CREATE TABLE mtpabstrdb.mec_region_info (
	
			regionId INT UNSIGNED NOT NULL AUTO_INCREMENT,
			latitude  VARCHAR (50), 
			longitude  VARCHAR (50), 
			altitude  VARCHAR (50), 
			range_  VARCHAR (50), 
			
			abstrNfviPopId INT UNSIGNED, /*key used for stored in 		domain tables */
			FOREIGN KEY (abstrNfviPopId) REFERENCES mtpabstrdb.abstrnfvipop (abstrNfviPopId),
			PRIMARY KEY (regionId)		
	);
/******************************************************************/
			
					
			
			
/* List of zoneid (useful for TD1.3) */
CREATE TABLE mtpabstrdb.abstrzoneid (
 abstrResourceZoneId INT UNSIGNED NOT NULL AUTO_INCREMENT,
abstrZoneId INT UNSIGNED NOT NULL,
zoneName		VARCHAR (50),
zoneState		VARCHAR (50),
zoneProperty	VARCHAR (50),
metadata 		VARCHAR (50),
/*FOREIGN and Primary Keys*/	
abstrNfviPopId INT UNSIGNED,
FOREIGN KEY (abstrNfviPopId) REFERENCES mtpabstrdb.abstrnfvipop  (abstrNfviPopId),
	PRIMARY KEY (abstrResourceZoneId)
    		);

/*table including all memory resources*/
/*table including capacity information of virtual memory*/
CREATE TABLE mtpabstrdb.abstrmemoryresources (
	

    	/*memory parameter according IFA005*/
	availableCapacity	VARCHAR (50),
reservedCapacity		VARCHAR (50),
totalCapacity 		VARCHAR (50),
allocatedCapacity	VARCHAR (50),
	
/**/
abstrResourceZoneId INT UNSIGNED,
FOREIGN KEY (abstrResourceZoneId) REFERENCES mtpabstrdb.abstrzoneid (abstrResourceZoneId),
	PRIMARY KEY (abstrResourceZoneId)
    		);

/*table including all storage resources*/
/*table including capacity information of virtual storage*/

CREATE TABLE mtpabstrdb.abstrstorageresources (
	/*Capacity info according IFA005*/
availableCapacity	VARCHAR (50),
reservedCapacity		VARCHAR (50),
totalCapacity 		VARCHAR (50),
allocatedCapacity	VARCHAR (50),
    	/*storage parameter according IFA005*/
/**/
abstrResourceZoneId INT UNSIGNED,
FOREIGN KEY (abstrResourceZoneId) REFERENCES mtpabstrdb.abstrzoneid (abstrResourceZoneId),
	PRIMARY KEY (abstrResourceZoneId)
     		);

/*table including all CPU resources*/
/*table including capacity information of virtual cpu*/

CREATE TABLE mtpabstrdb.abstrcpuresources (
    	/*capacity info according IFA005*/
availableCapacity	VARCHAR (50),
reservedCapacity		VARCHAR (50),
totalCapacity 		VARCHAR (50),
allocatedCapacity	VARCHAR (50),

/**/
abstrResourceZoneId INT UNSIGNED,
FOREIGN KEY (abstrResourceZoneId) REFERENCES mtpabstrdb.abstrzoneid (abstrResourceZoneId),	
PRIMARY KEY (abstrResourceZoneId)
		);


/*table including all memory resources*/
CREATE TABLE mtpabstrdb.logicallink (
	logicalLinkId INT UNSIGNED NOT NULL AUTO_INCREMENT,    	
abstrSrcNfviPopId INT UNSIGNED,
abstrDestNfviPopId INT UNSIGNED,
srcRouterId 	VARCHAR (50),
	dstRouterId	VARCHAR (50),
    	srcRouterIp 	VARCHAR (50),
	dstRouterIp 	VARCHAR (50),
delay 			VARCHAR (50),
/*Network capacity info according IFA005*/
availableBandwidth	VARCHAR (50),
reservedBandwidth	VARCHAR (50),
totalBandwidth		VARCHAR (50),
allocatedBandwidth	VARCHAR (50),
/*Primary and Foreign Keys*/
	PRIMARY KEY (logicalLinkId)
     		);







/*table including all memory resources*/
CREATE TABLE mtpabstrdb.logicalpath (
	logicalPathId INT UNSIGNED NOT NULL AUTO_INCREMENT,    	
abstrSrcNfviPopId INT UNSIGNED,
abstrDestNfviPopId INT UNSIGNED,
srcRouterId 	VARCHAR (50),
	dstRouterId	VARCHAR (50),
    	srcRouterIp 	VARCHAR (50),
	dstRouterIp 	VARCHAR (50),
delay 			VARCHAR (50),
/*Network capacity info according IFA005*/
availableBandwidth	VARCHAR (50),
reservedBandwidth	VARCHAR (50),
totalBandwidth		VARCHAR (50),
allocatedBandwidth	VARCHAR (50),
/*Primary and Foreign Keys*/
logicalLinkId INT UNSIGNED, 
	FOREIGN KEY (logicalLinkId) REFERENCES mtpabstrdb.logicallink (logicalLinkId),
	PRIMARY KEY (logicalPathId)
     		);


/******************************************************************/

CREATE DATABASE mtpdomdb;


/*//////// TABLE CONTAINS ELEMENTS ///////////////////*/



/* List of WIM/VIM/MEC/RADIO Domain */
CREATE TABLE mtpdomdb.domain (
    	domId INT UNSIGNED NOT NULL AUTO_INCREMENT, /*From xml files correspond to vimid IFA005 */
    	name VARCHAR (50), /*From xml files*/
        type VARCHAR (50), /*Radio, Transport, VIM, MEC*/
        ip VARCHAR (50), /*From xml files*/
    	port VARCHAR (50), /*From xml files*/
		mecAssociatedDomainID VARCHAR (50), /*From xml files*/
		
	abstrDomId INT UNSIGNED, 
	FOREIGN KEY (AbstrDomId) REFERENCES mtpabstrdb.abstrdomain (AbstrDomId),
    	PRIMARY KEY (domId) 
    		);

/* MEC specific table*/
/**********MEC Regions table********************************************************/

	CREATE TABLE mtpdomdb.mec_region_info (
	
			regionId INT UNSIGNED NOT NULL AUTO_INCREMENT,
			latitude  VARCHAR (50), 
			longitude  VARCHAR (50), 
			altitude  VARCHAR (50), 
			range_  VARCHAR (50), 
			
			domId INT UNSIGNED, /*key used for stored in 		domain tables */
			FOREIGN KEY (domId) REFERENCES mtpdomdb.domain (domId),
			PRIMARY KEY (regionId)		
	);
/******************************************************************/

			
			
			
			
			
			
/* List of NFVI POP */
CREATE TABLE mtpdomdb.nfvipop (
    	nfviPopId INT UNSIGNED NOT NULL AUTO_INCREMENT,
    	vimId INT UNSIGNED, 
    	geographicalLocationInfo VARCHAR (50),
    	networkConnectivityEndpoint VARCHAR (50),
	domId INT UNSIGNED, /*key used for stored in domain tables */
	FOREIGN KEY (domId) REFERENCES mtpdomdb.domain (domId),	
abstrNfviPopId INT UNSIGNED, /*key used for stored in domain tables */
	FOREIGN KEY (abstrNfviPopId) REFERENCES mtpabstrdb.abstrnfvipop  (abstrNfviPopId),
    	PRIMARY KEY (nfviPopId)
    		);

			
	/* Radio specific table*/
	/**********Radio coverage area table********************************************************/

		CREATE TABLE mtpdomdb.radio_coverage_area (
		
			coverageAreaId INT UNSIGNED NOT NULL AUTO_INCREMENT,
			coverageAreaMinBandwidth VARCHAR (50), 
			coverageAreaMaxBandwidth VARCHAR (50), 
			coverageAreaDelay VARCHAR (50), 
  
			latitude  VARCHAR (50), 
			longitude  VARCHAR (50), 
			altitude  VARCHAR (50), 
			range_  VARCHAR (50), 
				
			nfviPopId INT UNSIGNED, /*key used for stored in 		domain tables */
			FOREIGN KEY (nfviPopId) REFERENCES mtpdomdb.nfvipop (nfviPopId),
			PRIMARY KEY (coverageAreaId)		
		);
	/******************************************************************/			
			
			
			
/* List of Resource Zones */
CREATE TABLE mtpdomdb.zoneid (
    	resourceZoneId INT UNSIGNED NOT NULL AUTO_INCREMENT,
	/*List of zoneid IFA005 parameter + extension*/
zoneId INT UNSIGNED NOT NULL,
zoneName		VARCHAR (50),
zoneState		VARCHAR (50),
zoneProperty	VARCHAR (50),
metadata 		VARCHAR (50),
	/*Foreign and Primary Keys*/
nfviPopId INT UNSIGNED, /*key used for stored in domain tables */
FOREIGN KEY (nfviPopId) REFERENCES mtpdomdb.nfvipop (nfviPopId),
PRIMARY KEY (resourceZoneId)
    		);


/*table including all memory resources*/
CREATE TABLE mtpdomdb.memoryresources (
    	/*Capacity info according IFA005*/
availableCapacity	VARCHAR (50),
reservedCapacity		VARCHAR (50),
totalCapacity 		VARCHAR (50),
allocatedCapacity	VARCHAR (50),
/* IFA 005 VirtualMemory parameters*/
virtualMemSize 	DECIMAL,
virtualMemOversubscriptionPolicy		VARCHAR (50),
numaEnabled 	BIT(1),
/* IFA 005 VirtualComputeResourceInformation parameters*/
computeResourceTypeId 	VARCHAR (50),
accelerationCapability	VARCHAR (50),

      /**/
	resourceZoneId INT UNSIGNED, /*key used for stored in domain tables */
	FOREIGN KEY (resourceZoneId) REFERENCES mtpdomdb.zoneid (resourceZoneId),
	PRIMARY KEY (resourceZoneId)
    		);



/*table including all CPU resources*/
CREATE TABLE mtpdomdb.cpuresources (
    	/*cpu parameter according IFA005*/
availableCapacity	VARCHAR (50),
reservedCapacity		VARCHAR (50),
totalCapacity 		VARCHAR (50),
allocatedCapacity	VARCHAR (50),
/* IFA 005 VirtualCpu parameters*/
cpuArchitecture VARCHAR (50),
numVirtualCpu  	VARCHAR (50),
cpuClock 		VARCHAR (50),
virtualCpuOversubscriptionPolicy VARCHAR (50),
virtualCpuPinningSupported		VARCHAR (50),	
/* IFA 005 VirtualComputeResourceInformation parameters*/
computeResourceTypeId 	VARCHAR (50),
accelerationCapability	VARCHAR (50),
	/**/
	resourceZoneId INT UNSIGNED, /*key used for stored in domain tables */
	FOREIGN KEY (resourceZoneId) REFERENCES mtpdomdb.zoneid (resourceZoneId),
	PRIMARY KEY (resourceZoneId)
		);





/*table including all storage resources*/
CREATE TABLE mtpdomdb.storageresources (
    	/*storage parameter according IFA005*/
     /*Capacity info according IFA005*/
availableCapacity	VARCHAR (50),
reservedCapacity		VARCHAR (50),
totalCapacity 		VARCHAR (50),
allocatedCapacity	VARCHAR (50), 
/**/
	resourceZoneId INT UNSIGNED, /*key used for stored in domain tables */
	FOREIGN KEY (resourceZoneId) REFERENCES mtpdomdb.zoneid (resourceZoneId),
	PRIMARY KEY (resourceZoneId)
     		);



















/*table including all memory resources*/
CREATE TABLE mtpdomdb.networkresources (
	networkResId INT UNSIGNED NOT NULL AUTO_INCREMENT,
srcGwId 	VARCHAR (50),
	dstGwId	VARCHAR (50),
    	srcGWIp 	VARCHAR (50),
	dstGwIp 	VARCHAR (50),
delay 			VARCHAR (50),
/*Capacity info according IFA005*/
availableCapacity	VARCHAR (50),
reservedCapacity		VARCHAR (50),
totalCapacity 		VARCHAR (50),
allocatedCapacity	VARCHAR (50),
/*Parameters according IFA005 VirtualNetworkResourceInformation information element */
networkResourceTypeId 	VARCHAR (50),
networkType 		VARCHAR (50),
bandwidth DECIMAL,

/*Primary and Foreign Keys*/
	resourceZoneId INT UNSIGNED, /*key used for stored in domain tables */
	FOREIGN KEY (resourceZoneId) REFERENCES mtpdomdb.zoneid (resourceZoneId),	
PRIMARY KEY (networkResId)
     		);









/*table containing interdomain connection*/
CREATE TABLE mtpdomdb.interdomainlink (
	interDomainLinkId INT UNSIGNED NOT NULL AUTO_INCREMENT,
	srcDomId 	INT UNSIGNED,
	dstDomId 	INT UNSIGNED,
	srcGwId 	VARCHAR (50),
	dstGwId	VARCHAR (50),
    	srcGWIp 	VARCHAR (50),
	dstGwIp 	VARCHAR (50),
delay		VARCHAR (50),
availableBandwidth	VARCHAR (50),
reservedBandwidth	VARCHAR (50),
totalBandwidth 		VARCHAR (50),
allocatedBandwidth	VARCHAR (50),

	PRIMARY KEY (interDomainLinkId)
     		);


/* List of VIRTUAL COMPUTE FLAVOUR */
CREATE TABLE mtpdomdb.computeflavour (
computeFlavourId INT UNSIGNED NOT NULL AUTO_INCREMENT,
/* Parameters of IFA005 ComputeFlavour Information Element*/
flavourId INT UNSIGNED NOT NULL,
accelerationCapability 	VARCHAR (50),
/* Primary and Foreign Keys*/
nfviPopId INT UNSIGNED, /*key used for stored in domain tables */
FOREIGN KEY (nfviPopId) REFERENCES mtpdomdb.nfvipop (nfviPopId),    
PRIMARY KEY (computeFlavourId) 
    		);


CREATE TABLE mtpdomdb.virtualcpu (
cpuArchitecture VARCHAR (50),
numVirtualCpu  	VARCHAR (50),
cpuClock 		VARCHAR (50),
virtualCpuOversubscriptionPolicy VARCHAR (50),
virtualCpuPinningSupported		VARCHAR (50),	
/**/
computeFlavourId INT UNSIGNED,
FOREIGN KEY (computeFlavourId) REFERENCES mtpdomdb.computeflavour (computeFlavourId),

	PRIMARY KEY (computeFlavourId)
		);


CREATE TABLE mtpdomdb.virtualnetworkinterfacedata (
netInterfaceDataId INT UNSIGNED NOT NULL AUTO_INCREMENT,
/**/
networkId 		INT UNSIGNED,
networkPortId 	INT UNSIGNED,
typeVirtualNic 	VARCHAR (50),
typeConfiguration	VARCHAR (50),
bandwidth	VARCHAR (50),
accelerationCapability VARCHAR (50),
metadata	VARCHAR (50),
/* Primary and Foreign Keys*/
computeFlavourId INT UNSIGNED,
FOREIGN KEY (computeFlavourId) REFERENCES mtpdomdb.computeflavour (computeFlavourId),
PRIMARY KEY (netInterfaceDataId) 
    		);



CREATE TABLE mtpdomdb.virtualmemorydata (
/**/
virtualMemSize 	DECIMAL,
virtualMemOversubscriptionPolicy		VARCHAR (50),
numaEnabled 	BIT(1),
/* Primary and Foreign Keys*/
computeFlavourId INT UNSIGNED,
FOREIGN KEY (computeFlavourId) REFERENCES mtpdomdb.computeflavour (computeFlavourId),
PRIMARY KEY (computeFlavourId) 
    		);

CREATE TABLE mtpdomdb.virtualstoragedata (
storageDataId INT UNSIGNED NOT NULL AUTO_INCREMENT,
/**/
typeOfStorage	VARCHAR (50),
sizeOfStorage 	DECIMAL,

/* Primary and Foreign Keys*/
computeFlavourId INT UNSIGNED,
FOREIGN KEY (computeFlavourId) REFERENCES mtpdomdb.computeflavour (computeFlavourId),
PRIMARY KEY (storageDataId) 
    		);




/*********************************************************************/

CREATE DATABASE mtpservdb;

/*//////// TABLE CONTAINS ELEMENTS ///////////////////*/

/* List of SERVICE ID */
CREATE TABLE mtpservdb.service (
    	servId INT UNSIGNED NOT NULL AUTO_INCREMENT, /*use resourcegroupid IFA005?*/
	/*list of service parameter(TBD)*/
     
    	PRIMARY KEY (servId) 
    		);








/* List of compute allocation request */
CREATE TABLE mtpservdb.computeservices (
    	computeServiceId INT UNSIGNED NOT NULL AUTO_INCREMENT,
    	reqId INT UNSIGNED, /*used for retrieve and set the outcome*/
	status VARCHAR (50), /*status of request “pending”, “OK”, “NOK”*/
	vnfName VARCHAR (50),
	vmName VARCHAR (50),
	floatingIp VARCHAR (50),
     outcome VARCHAR (50),
	/*Primary and Foreign Keys*/
      servId INT UNSIGNED, /*key used for stored in service tables */
	FOREIGN KEY (servId) REFERENCES mtpservdb.service (servId),
nfviPopId INT UNSIGNED, /*key used for stored in domain tables */
	FOREIGN KEY (nfviPopId) REFERENCES mtpdomdb.nfvipop (nfviPopId),
    	PRIMARY KEY (computeServiceId) 
    		);
			
			

/* List of network allocation request */
CREATE TABLE mtpservdb.networkservices (
    	netServId INT UNSIGNED NOT NULL AUTO_INCREMENT, /*key used for stored in service tables */
    	reqId INT UNSIGNED,  /*used for retrieve and set the outcome*/
status VARCHAR (50), /*status of request “pending”, “OK”, “NOK”*/
/*list of parameters for IFA005 network allocate*/
	servId INT UNSIGNED, 
	FOREIGN KEY (ServId) REFERENCES mtpservdb.service (servId),
	logicalPathId INT UNSIGNED,
FOREIGN KEY (logicalPathId) REFERENCES mtpabstrdb.logicalpath (logicalPathId),
    	PRIMARY KEY (netServId) 
    		);

/* List MEC App instance ids  */

CREATE TABLE mtpservdb.mec_service_instances (
appInstanceId VARCHAR (50),
	mecdomId VARCHAR(50), /* used to retrieve mecid*/
/* Primary and Foreign Keys */
computeServiceId INT UNSIGNED UNIQUE, 
FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.computeservices (computeServiceId),
PRIMARY KEY (computeServiceId) 
    		);
			
			
/* List of network allocation request */

CREATE TABLE mtpservdb.virtualcompute (
/*list of parameters for IFA005 network allocate*/
computeId 		VARCHAR (50),
zoneId 		INT UNSIGNED,
virtualDisks 	VARCHAR (50),
vcImageId		VARCHAR (50),
flavourId 		INT UNSIGNED,
accelerationCapability 	VARCHAR (50),
computeName 	VARCHAR (50),
operationalState	VARCHAR (50),
hostId 		VARCHAR (50),
/* Primary and Foreign Keys */
computeServiceId INT UNSIGNED UNIQUE, 
FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.computeservices (computeServiceId),
PRIMARY KEY (computeServiceId) 
    		);


CREATE TABLE mtpservdb.virtualcpu (
cpuArchitecture VARCHAR (50),
numVirtualCpu  	VARCHAR (50),
cpuClock 		VARCHAR (50),
virtualCpuOversubscriptionPolicy VARCHAR (50),
/**/
computeServiceId INT UNSIGNED, 
FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.virtualcompute (computeServiceId),
PRIMARY KEY (computeServiceId) 
		);


CREATE TABLE mtpservdb.virtualnetworkinterface (
netInterfaceId INT UNSIGNED NOT NULL AUTO_INCREMENT,
/**/
resourceId		INT UNSIGNED,
ownerId 		INT UNSIGNED,
networkId 		INT UNSIGNED, /*Reference to VirtualNetwork (see ifa005)*/
networkPortId 	INT UNSIGNED, /*Reference to VirtualNetworkPort (see ifa005)*/
ipAddress		VARCHAR (50),
typeVirtualNic 	VARCHAR (50),
typeConfiguration	VARCHAR (50),
macAddress		VARCHAR (50),
bandwidth	VARCHAR (50),
accelerationCapability VARCHAR (50),
operationalState		VARCHAR (50),
metadata	VARCHAR (50),
/* Primary and Foreign Keys*/
computeServiceId INT UNSIGNED, 
FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.virtualcompute (computeServiceId),
PRIMARY KEY (netInterfaceId) 
    		);



CREATE TABLE mtpservdb.virtualmemory (
/**/
virtualMemSize 	DECIMAL,
virtualMemOversubscriptionPolicy		VARCHAR (50),
numaEnabled 	BIT(1),
/* Primary and Foreign Keys*/
computeServiceId INT UNSIGNED, 
FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.virtualcompute (computeServiceId),
PRIMARY KEY (computeServiceId) 
    		);


CREATE TABLE mtpservdb.virtualcpupinning (
cpuPinningPolicy VARCHAR (50),
cpuPinningRules   	VARCHAR (50),
cpuMap		VARCHAR (50),	
/**/
computeServiceId INT UNSIGNED, 
FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.virtualcpu (computeServiceId),
PRIMARY KEY (computeServiceId) 
		);



/*list of parameters for IFA005 compute allocate*/
CREATE TABLE mtpservdb.computeservicerequestdata (
	/*list of parameters for IFA005 compute allocate*/
locationConstraints VARCHAR (50),
  reservationId VARCHAR (50),
  computeFlavourId VARCHAR (50),
  resourceGroupId VARCHAR (50),
  metadata VARCHAR (50), /*it is a list*/
  vcImageId VARCHAR (50),
  computeName VARCHAR (50),
  appDId VARCHAR (50),
/*Primary and Foreign Keys*/
	computeServiceId INT UNSIGNED NOT NULL,	
FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.computeservices (computeServiceId),
    	PRIMARY KEY (computeServiceId) 
    		);


CREATE TABLE mtpservdb.virtualinterfacedata (
interfaceDataId INT UNSIGNED NOT NULL AUTO_INCREMENT,
ipAddress VARCHAR (50),
macAddress VARCHAR (50),
	/*Primary and Foreign Keys*/
computeServiceId INT UNSIGNED NOT NULL,
	FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.computeservicerequestdata (computeServiceId),
PRIMARY KEY (interfaceDataId) 
    		);


CREATE TABLE mtpservdb.affinityorantiaffinityconstraint (
affinityConstraintId INT UNSIGNED NOT NULL AUTO_INCREMENT,
type VARCHAR (50),
scope VARCHAR (50),
affinityAntiAffinityResourceList VARCHAR (50),	
affinityAntiAffinityResourceGroup VARCHAR (50),

/*Primary and Foreign Keys*/
computeServiceId INT UNSIGNED NOT NULL,
FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.computeservicerequestdata (computeServiceId),
    	PRIMARY KEY (affinityConstraintId) 
    		);

CREATE TABLE mtpservdb.userdata (
content VARCHAR (50),
method VARCHAR (50),
/*Primary and Foreign Keys*/
computeServiceId INT UNSIGNED NOT NULL,
FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.computeservicerequestdata (computeServiceId),
 PRIMARY KEY (computeServiceId) 
    		);







CREATE TABLE mtpservdb.networkservicerequestdata (
/*list of parameters for IFA005 network allocate*/
  locationConstraints VARCHAR (50),
  reservationId VARCHAR (50),
  affinityOrAntiAffinityConstraints VARCHAR (50), /*it is a list*/
  resourceGroupId VARCHAR (50),
metadata VARCHAR (50), /*it is a List*/
networkResourceType VARCHAR (50),
  networkResourceName VARCHAR (50),
	/*Primary and Foreign Keys*/
netServId INT UNSIGNED,
FOREIGN KEY (netServId) REFERENCES mtpservdb.networkservices (netServId),
PRIMARY KEY (netServId) 
    		);

CREATE TABLE mtpservdb.networksubnetdata (
networkId		INT UNSIGNED,
ipVersion		VARCHAR (50),
gatewayIp		VARCHAR (50),		
cidr			VARCHAR (50),
isDhcpEnabled    BIT(1),
addressPool	VARCHAR (50),
metadata		VARCHAR (50),
	/*Primary and Foreign Keys*/
netServId INT UNSIGNED,
FOREIGN KEY (netServId) REFERENCES mtpservdb.networkservicerequestdata (netServId),
PRIMARY KEY (netServId) 
    		);



CREATE TABLE mtpservdb.virtualnetworkportdata (
portType		VARCHAR (50),
networkId		INT UNSIGNED,	
segmentId		INT UNSIGNED,
bandwidth	DECIMAL,
metadata	VARCHAR (50),
	/*Primary and Foreign Keys*/
netServId INT UNSIGNED,
	FOREIGN KEY (netServId) REFERENCES mtpservdb.networkservicerequestdata (netServId),
    	PRIMARY KEY (netServId) 
    		);






CREATE TABLE mtpservdb.virtualnetworkdata (
/*list of parameters for IFA005 network allocate*/
  bandwidth Decimal,
networkType	VARCHAR (50),
segmentType	VARCHAR (50),
isShared      	BIT(1),
sharingCriteria VARCHAR (50),
metadata VARCHAR (50),
	/*Primary and Foreign Keys*/
netServId INT UNSIGNED,
	FOREIGN KEY (netServId) REFERENCES mtpservdb.networkservicerequestdata (netServId),
    	PRIMARY KEY (netServId) 
    		);




CREATE TABLE mtpservdb.networkqos
 (
     netQosId 					INT UNSIGNED NOT NULL AUTO_INCREMENT,
	qosName VARCHAR (50),
qosValue VARCHAR (50),
/*Primary and Foreign Keys*/
netServId INT UNSIGNED,
	FOREIGN KEY (netServId) REFERENCES mtpservdb.virtualnetworkdata (netServId),
PRIMARY KEY (netQosId)
     		);




CREATE TABLE mtpservdb.layer3connectivityinformation (
layer3ConnInfoId INT UNSIGNED NOT NULL AUTO_INCREMENT,
networkId		INT UNSIGNED,
ipVersion		VARCHAR (50),
gatewayIp		VARCHAR (50),		
cidr			VARCHAR (50),
isDhcpEnabled    BIT(1),
addressPool	VARCHAR (50),
metadata		VARCHAR (50),
	/*Primary and Foreign Keys*/
netServId INT UNSIGNED,
FOREIGN KEY (netServId) REFERENCES mtpservdb.virtualnetworkdata (netServId),
PRIMARY KEY (layer3ConnInfoId)
    		);




CREATE TABLE mtpservdb.virtualnetwork (
/*list of parameters for IFA005 network allocate*/
  virtualNetworkId INT UNSIGNED NOT NULL AUTO_INCREMENT,
  networkResourceName VARCHAR (50),
  segmentType VARCHAR (50),  
  isShared BOOL NOT NULL,
  zoneId INT UNSIGNED,
  networkResourceId INT UNSIGNED NOT NULL,
  networkType VARCHAR (50),  
  operationalState VARCHAR (50),  
  sharingCriteria VARCHAR (50),  
  bandwidth DECIMAL,
  netResIdRef INT UNSIGNED,  
	/*Primary and Foreign Keys*/
nfviPopId INT UNSIGNED, /*key used for stored in domain tables */
	FOREIGN KEY (nfviPopId) REFERENCES mtpdomdb.nfvipop (nfviPopId),

netServId INT UNSIGNED NOT NULL,
	FOREIGN KEY (netServId) REFERENCES mtpservdb.networkservices (netServId),
    	PRIMARY KEY (virtualNetworkId) 
    		);


CREATE TABLE mtpservdb.supportednetworkqos
 (
     supportedNetQosId 					INT UNSIGNED NOT NULL AUTO_INCREMENT,
	qosName VARCHAR (50),
qosValue VARCHAR (50),

/*Primary and Foreign Keys*/
virtualNetworkId INT UNSIGNED NOT NULL,
FOREIGN KEY (virtualNetworkId) REFERENCES mtpservdb.virtualnetwork (virtualNetworkId),
PRIMARY KEY (supportedNetQosId)
     		);




CREATE TABLE mtpservdb.virtualnetworkport (
netPortId		INT UNSIGNED NOT NULL AUTO_INCREMENT,

resourceId 	INT UNSIGNED,
portType		VARCHAR (50),
attachedResourceId	INT UNSIGNED, /* Reference to VirtualNetworkInterface */
segmentId		INT UNSIGNED,
bandwidth	DECIMAL,
metadata	VARCHAR (50),
	/*Primary and Foreign Keys*/
virtualNetworkId INT UNSIGNED,
	FOREIGN KEY (virtualNetworkId) REFERENCES mtpservdb.virtualnetwork (virtualNetworkId),
    	
PRIMARY KEY (netPortId) 
    		);



CREATE TABLE mtpservdb.networksubnet (
netSubnetId INT UNSIGNED NOT NULL AUTO_INCREMENT,
resourceId 	INT UNSIGNED,
ipVersion		VARCHAR (50),
gatewayIp		VARCHAR (50),		
cidr			VARCHAR (50),
isDhcpEnabled    BIT(1),
addressPool	VARCHAR (50),
metadata		VARCHAR (50),
	/*Primary and Foreign Keys*/
virtualNetworkId INT UNSIGNED,
	FOREIGN KEY (virtualNetworkId) REFERENCES mtpservdb.virtualnetwork (virtualNetworkId), 
PRIMARY KEY (netSubnetId)
    		);











/*IFA005 Information Elements */







/* //////////// ASSOCIATION TABLE /////////////////////////// */


CREATE TABLE mtpdomdb.logicalpath_networkresources (
    	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	logicalPathId INT UNSIGNED,
	networkResId INT UNSIGNED,
	FOREIGN KEY (logicalPathId) REFERENCES mtpabstrdb.logicalpath (logicalPathId),
	FOREIGN KEY (networkResId) REFERENCES mtpdomdb.networkresources (networkResId),
PRIMARY KEY (id)
	     	);

CREATE TABLE mtpdomdb.logicalpath_interdomainlink (
    	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	logicalPathId INT UNSIGNED,
	interDomainLinkId INT UNSIGNED,
	FOREIGN KEY (logicalPathId) REFERENCES mtpabstrdb.logicalpath (logicalPathId),
	FOREIGN KEY (interDomainLinkId) REFERENCES mtpdomdb.interdomainlink (interDomainLinkId),
PRIMARY KEY (id)

	     	);




