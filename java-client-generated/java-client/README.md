# swagger-java-client

## Requirements

Building the API client library requires [Maven](https://maven.apache.org/) to be installed.

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn deploy
```

Refer to the [official documentation](https://maven.apache.org/plugins/maven-deploy-plugin/usage.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
    <groupId>io.swagger</groupId>
    <artifactId>swagger-java-client</artifactId>
    <version>1.0.0</version>
    <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "io.swagger:swagger-java-client:1.0.0"
```

### Others

At first generate the JAR by executing:

    mvn package

Then manually install the following JARs:

* target/swagger-java-client-1.0.0.jar
* target/lib/*.jar

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java

import io.swagger.client.*;
import io.swagger.client.auth.*;
import io.swagger.client.model.*;
import io.swagger.client.api.AbstractResourcesApi;

import java.io.File;
import java.util.*;

public class AbstractResourcesApiExample {

    public static void main(String[] args) {
        
        AbstractResourcesApi apiInstance = new AbstractResourcesApi();
        AllocateComputeRequest body = new AllocateComputeRequest(); // AllocateComputeRequest | 
        try {
            VirtualCompute result = apiInstance.allocateAbstractCompute(body);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling AbstractResourcesApi#allocateAbstractCompute");
            e.printStackTrace();
        }
    }
}

```

## Documentation for API Endpoints

All URIs are relative to *https://localhost*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*AbstractResourcesApi* | [**allocateAbstractCompute**](docs/AbstractResourcesApi.md#allocateAbstractCompute) | **POST** /abstract-compute-resources | Allocate abstract compute resources
*AbstractResourcesApi* | [**collectMtpCloudNetworkResourceInformation**](docs/AbstractResourcesApi.md#collectMtpCloudNetworkResourceInformation) | **GET** /abstract-resources | Retrieve aggregated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity
*AbstractResourcesApi* | [**createInterNfviPoPConnectivity**](docs/AbstractResourcesApi.md#createInterNfviPoPConnectivity) | **POST** /abstract-network-resources | Create inter-NFVI-PoP connectivity
*AbstractResourcesApi* | [**deleteInterNfviPoPConnectivity**](docs/AbstractResourcesApi.md#deleteInterNfviPoPConnectivity) | **DELETE** /abstract-network-resources | Delete inter-NFVI-PoP connectivity
*AbstractResourcesApi* | [**terminateResources**](docs/AbstractResourcesApi.md#terminateResources) | **DELETE** /abstract-compute-resources | Terminate abstract compute resources
*DefaultApi* | [**healthzGet**](docs/DefaultApi.md#healthzGet) | **GET** /healthz | Healthcheck
*SoftwareImagesApi* | [**addSoftwareImage**](docs/SoftwareImagesApi.md#addSoftwareImage) | **POST** /software_images | 
*SoftwareImagesApi* | [**deleteSoftwareImage**](docs/SoftwareImagesApi.md#deleteSoftwareImage) | **DELETE** /software_images/{id} | 
*SoftwareImagesApi* | [**querySoftwareImage**](docs/SoftwareImagesApi.md#querySoftwareImage) | **GET** /software_images/{id} | 
*SoftwareImagesApi* | [**querySoftwareImages**](docs/SoftwareImagesApi.md#querySoftwareImages) | **GET** /software_images | 
*VirtualisedComputeResourcesApi* | [**allocateCompute**](docs/VirtualisedComputeResourcesApi.md#allocateCompute) | **POST** /compute_resources | 
*VirtualisedComputeResourcesApi* | [**createFlavour**](docs/VirtualisedComputeResourcesApi.md#createFlavour) | **POST** /compute_resources/flavours | 
*VirtualisedComputeResourcesApi* | [**deleteFlavours**](docs/VirtualisedComputeResourcesApi.md#deleteFlavours) | **DELETE** /compute_resources/flavours/{id} | 
*VirtualisedComputeResourcesApi* | [**queryComputeCapacity**](docs/VirtualisedComputeResourcesApi.md#queryComputeCapacity) | **GET** /compute_resources/capacities | 
*VirtualisedComputeResourcesApi* | [**queryComputeInformation**](docs/VirtualisedComputeResourcesApi.md#queryComputeInformation) | **GET** /compute_resources/information | 
*VirtualisedComputeResourcesApi* | [**queryComputeResourceZone**](docs/VirtualisedComputeResourcesApi.md#queryComputeResourceZone) | **GET** /compute_resources/resource_zones | 
*VirtualisedComputeResourcesApi* | [**queryFlavors**](docs/VirtualisedComputeResourcesApi.md#queryFlavors) | **GET** /compute_resources/flavours | 
*VirtualisedComputeResourcesApi* | [**queryNFVIPoPComputeInformation**](docs/VirtualisedComputeResourcesApi.md#queryNFVIPoPComputeInformation) | **GET** /compute_resources/nfvi_pop_compute_information | 
*VirtualisedComputeResourcesApi* | [**queryResources**](docs/VirtualisedComputeResourcesApi.md#queryResources) | **GET** /compute_resources | 
*VirtualisedComputeResourcesApi* | [**terminateAbstractResources**](docs/VirtualisedComputeResourcesApi.md#terminateAbstractResources) | **DELETE** /compute_resources | 
*VirtualisedNetworkResourcesApi* | [**queryNFVIPoPNetworkInformation**](docs/VirtualisedNetworkResourcesApi.md#queryNFVIPoPNetworkInformation) | **GET** /network_resources/nfvi-pop-network-information | 
*VirtualisedNetworkResourcesApi* | [**queryNetworkCapacity**](docs/VirtualisedNetworkResourcesApi.md#queryNetworkCapacity) | **GET** /network_resources/capacities | 
*VirtualisedNetworkResourcesApi* | [**vIMallocateNetwork**](docs/VirtualisedNetworkResourcesApi.md#vIMallocateNetwork) | **POST** /network_resources | 
*VirtualisedNetworkResourcesApi* | [**vIMqueryNetworks**](docs/VirtualisedNetworkResourcesApi.md#vIMqueryNetworks) | **GET** /network_resources | 
*VirtualisedNetworkResourcesApi* | [**vIMterminateNetworks**](docs/VirtualisedNetworkResourcesApi.md#vIMterminateNetworks) | **DELETE** /network_resources | 
*VirtualisedNetworkResourcesInformationApi* | [**queryNetworkInformation**](docs/VirtualisedNetworkResourcesInformationApi.md#queryNetworkInformation) | **GET** /information | 
*VirtualisedResourceQuotaApi* | [**createComputeQuota**](docs/VirtualisedResourceQuotaApi.md#createComputeQuota) | **POST** /quotas/compute_resources | 
*VirtualisedResourceQuotaApi* | [**createNetworkQuota**](docs/VirtualisedResourceQuotaApi.md#createNetworkQuota) | **POST** /quotas/network_resources | 
*VirtualisedResourceQuotaApi* | [**queryComputeQuota**](docs/VirtualisedResourceQuotaApi.md#queryComputeQuota) | **GET** /quotas/compute_resources | 
*VirtualisedResourceQuotaApi* | [**queryNetworkQuota**](docs/VirtualisedResourceQuotaApi.md#queryNetworkQuota) | **GET** /quotas/network_resources | 
*VirtualisedResourceQuotaApi* | [**terminateComputeQuota**](docs/VirtualisedResourceQuotaApi.md#terminateComputeQuota) | **DELETE** /quotas/compute_resources | 
*VirtualisedResourceQuotaApi* | [**terminateNetworkQuota**](docs/VirtualisedResourceQuotaApi.md#terminateNetworkQuota) | **DELETE** /quotas/network_resources | 
*VirtualisedResourceReservationApi* | [**createComputeReservation**](docs/VirtualisedResourceReservationApi.md#createComputeReservation) | **POST** /reservations/compute_resources | 
*VirtualisedResourceReservationApi* | [**createNetworkReservation**](docs/VirtualisedResourceReservationApi.md#createNetworkReservation) | **POST** /reservations/network_resources | 
*VirtualisedResourceReservationApi* | [**queryComputeReservation**](docs/VirtualisedResourceReservationApi.md#queryComputeReservation) | **GET** /reservations/compute_resources | 
*VirtualisedResourceReservationApi* | [**queryNetworkReservation**](docs/VirtualisedResourceReservationApi.md#queryNetworkReservation) | **GET** /reservations/network_resources | 
*VirtualisedResourceReservationApi* | [**terminateComputeReservation**](docs/VirtualisedResourceReservationApi.md#terminateComputeReservation) | **DELETE** /reservations/compute_resources | 
*VirtualisedResourceReservationApi* | [**terminateNetworkReservation**](docs/VirtualisedResourceReservationApi.md#terminateNetworkReservation) | **DELETE** /reservations/network_resources | 
*WimNetworkResourcesApi* | [**allocateNetwork**](docs/WimNetworkResourcesApi.md#allocateNetwork) | **POST** /network-resources | 
*WimNetworkResourcesApi* | [**collectWimAbstractedInformation**](docs/WimNetworkResourcesApi.md#collectWimAbstractedInformation) | **GET** /abstract-network | Retrieve aggregated WAN Connectivity
*WimNetworkResourcesApi* | [**queryNetworks**](docs/WimNetworkResourcesApi.md#queryNetworks) | **GET** /network-resources | 
*WimNetworkResourcesApi* | [**terminateNetworks**](docs/WimNetworkResourcesApi.md#terminateNetworks) | **DELETE** /network-resources/{networkId} | 


## Documentation for Models

 - [AllocateComputeRequest](docs/AllocateComputeRequest.md)
 - [AllocateComputeRequestAffinityAntiAffinityResourceList](docs/AllocateComputeRequestAffinityAntiAffinityResourceList.md)
 - [AllocateComputeRequestAffinityOrAntiAffinityConstraints](docs/AllocateComputeRequestAffinityOrAntiAffinityConstraints.md)
 - [AllocateComputeRequestInterfaceData](docs/AllocateComputeRequestInterfaceData.md)
 - [AllocateComputeRequestUserData](docs/AllocateComputeRequestUserData.md)
 - [AllocateNetworkRequest](docs/AllocateNetworkRequest.md)
 - [AllocateNetworkResult](docs/AllocateNetworkResult.md)
 - [AllocateNetworkResultNetworkData](docs/AllocateNetworkResultNetworkData.md)
 - [AllocateNetworkResultNetworkDataNetworkPort](docs/AllocateNetworkResultNetworkDataNetworkPort.md)
 - [AllocateNetworkResultNetworkDataNetworkQoS](docs/AllocateNetworkResultNetworkDataNetworkQoS.md)
 - [AllocateNetworkResultNetworkPortData](docs/AllocateNetworkResultNetworkPortData.md)
 - [AllocateNetworkResultSubnetData](docs/AllocateNetworkResultSubnetData.md)
 - [AllocateParameters](docs/AllocateParameters.md)
 - [AllocateReply](docs/AllocateReply.md)
 - [CapacityInformation](docs/CapacityInformation.md)
 - [CreateComputeResourceQuotaRequest](docs/CreateComputeResourceQuotaRequest.md)
 - [CreateComputeResourceQuotaRequestVirtualComputeQuota](docs/CreateComputeResourceQuotaRequestVirtualComputeQuota.md)
 - [CreateComputeResourceReservationRequest](docs/CreateComputeResourceReservationRequest.md)
 - [CreateComputeResourceReservationRequestComputePoolReservation](docs/CreateComputeResourceReservationRequestComputePoolReservation.md)
 - [CreateComputeResourceReservationRequestComputePoolReservationComputeAttributes](docs/CreateComputeResourceReservationRequestComputePoolReservationComputeAttributes.md)
 - [CreateComputeResourceReservationRequestContainerFlavour](docs/CreateComputeResourceReservationRequestContainerFlavour.md)
 - [CreateComputeResourceReservationRequestContainerFlavourStorageAttributes](docs/CreateComputeResourceReservationRequestContainerFlavourStorageAttributes.md)
 - [CreateComputeResourceReservationRequestContainerFlavourVirtualCpu](docs/CreateComputeResourceReservationRequestContainerFlavourVirtualCpu.md)
 - [CreateComputeResourceReservationRequestContainerFlavourVirtualCpuPinning](docs/CreateComputeResourceReservationRequestContainerFlavourVirtualCpuPinning.md)
 - [CreateComputeResourceReservationRequestContainerFlavourVirtualMemory](docs/CreateComputeResourceReservationRequestContainerFlavourVirtualMemory.md)
 - [CreateComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface](docs/CreateComputeResourceReservationRequestContainerFlavourVirtualNetworkInterface.md)
 - [CreateComputeResourceReservationRequestVirtualisationContainerReservation](docs/CreateComputeResourceReservationRequestVirtualisationContainerReservation.md)
 - [CreateNetworkResourceQuotaRequest](docs/CreateNetworkResourceQuotaRequest.md)
 - [CreateNetworkResourceQuotaRequestVirtualComputeQuota](docs/CreateNetworkResourceQuotaRequestVirtualComputeQuota.md)
 - [CreateNetworkResourceReservationRequest](docs/CreateNetworkResourceReservationRequest.md)
 - [CreateNetworkResourceReservationRequestNetworkReservation](docs/CreateNetworkResourceReservationRequestNetworkReservation.md)
 - [CreateNetworkResourceReservationRequestNetworkReservationNetworkAttributes](docs/CreateNetworkResourceReservationRequestNetworkReservationNetworkAttributes.md)
 - [CreateNetworkResourceReservationRequestNetworkReservationNetworkPorts](docs/CreateNetworkResourceReservationRequestNetworkReservationNetworkPorts.md)
 - [DeleteInterNfviPopConnectivityRequest](docs/DeleteInterNfviPopConnectivityRequest.md)
 - [Filter](docs/Filter.md)
 - [Gateways](docs/Gateways.md)
 - [GatewaysInner](docs/GatewaysInner.md)
 - [GatewaysInnerGatewayAttributes](docs/GatewaysInnerGatewayAttributes.md)
 - [GatewaysInnerGatewayAttributesNetworkConnectivityEndpoint](docs/GatewaysInnerGatewayAttributesNetworkConnectivityEndpoint.md)
 - [InlineResponse200](docs/InlineResponse200.md)
 - [InlineResponse2001](docs/InlineResponse2001.md)
 - [InlineResponse201](docs/InlineResponse201.md)
 - [InterNfviPopConnectivityRequest](docs/InterNfviPopConnectivityRequest.md)
 - [InterNfviPopConnnectivityIdList](docs/InterNfviPopConnnectivityIdList.md)
 - [InterNfviPopConnnectivityIdListInner](docs/InterNfviPopConnnectivityIdListInner.md)
 - [LogicalLinkAttributes](docs/LogicalLinkAttributes.md)
 - [LogicalLinkInterNfviPops](docs/LogicalLinkInterNfviPops.md)
 - [LogicalLinkInterNfviPopsInner](docs/LogicalLinkInterNfviPopsInner.md)
 - [LogicalLinkInterNfviPopsInnerLogicalLinks](docs/LogicalLinkInterNfviPopsInnerLogicalLinks.md)
 - [LogicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS](docs/LogicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS.md)
 - [LogicalLinkPathList](docs/LogicalLinkPathList.md)
 - [LogicalLinkPathListInner](docs/LogicalLinkPathListInner.md)
 - [MetaData](docs/MetaData.md)
 - [MetaDataInner](docs/MetaDataInner.md)
 - [NetworkIds](docs/NetworkIds.md)
 - [NfviPop](docs/NfviPop.md)
 - [NfviPops](docs/NfviPops.md)
 - [NfviPopsInner](docs/NfviPopsInner.md)
 - [NfviPopsInnerNfviPopAttributes](docs/NfviPopsInnerNfviPopAttributes.md)
 - [NfviPopsInnerNfviPopAttributesCpuResourceAttributes](docs/NfviPopsInnerNfviPopAttributesCpuResourceAttributes.md)
 - [NfviPopsInnerNfviPopAttributesMemoryResourceAttributes](docs/NfviPopsInnerNfviPopAttributesMemoryResourceAttributes.md)
 - [NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint](docs/NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint.md)
 - [NfviPopsInnerNfviPopAttributesResourceZoneAttributes](docs/NfviPopsInnerNfviPopAttributesResourceZoneAttributes.md)
 - [NfviPopsInnerNfviPopAttributesStorageResourceAttributes](docs/NfviPopsInnerNfviPopAttributesStorageResourceAttributes.md)
 - [QueryComputeCapacityRequest](docs/QueryComputeCapacityRequest.md)
 - [QueryComputeCapacityRequestTimePeriod](docs/QueryComputeCapacityRequestTimePeriod.md)
 - [QueryNetworkCapacityRequest](docs/QueryNetworkCapacityRequest.md)
 - [ReservedVirtualCompute](docs/ReservedVirtualCompute.md)
 - [ReservedVirtualComputeComputePoolReserved](docs/ReservedVirtualComputeComputePoolReserved.md)
 - [ReservedVirtualComputeComputePoolReservedComputeAttributes](docs/ReservedVirtualComputeComputePoolReservedComputeAttributes.md)
 - [ReservedVirtualComputeVirtualisationContainerReserved](docs/ReservedVirtualComputeVirtualisationContainerReserved.md)
 - [ReservedVirtualComputeVirtualisationContainerReservedFlavourId](docs/ReservedVirtualComputeVirtualisationContainerReservedFlavourId.md)
 - [ReservedVirtualComputeVirtualisationContainerReservedVirtualCpu](docs/ReservedVirtualComputeVirtualisationContainerReservedVirtualCpu.md)
 - [ReservedVirtualComputeVirtualisationContainerReservedVirtualMemory](docs/ReservedVirtualComputeVirtualisationContainerReservedVirtualMemory.md)
 - [ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface](docs/ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface.md)
 - [ReservedVirtualNetwork](docs/ReservedVirtualNetwork.md)
 - [ReservedVirtualNetworkNetworkAttributes](docs/ReservedVirtualNetworkNetworkAttributes.md)
 - [ReservedVirtualNetworkNetworkPorts](docs/ReservedVirtualNetworkNetworkPorts.md)
 - [ResourceGroupIds](docs/ResourceGroupIds.md)
 - [ResourceZone](docs/ResourceZone.md)
 - [SoftwareImageAddQuery](docs/SoftwareImageAddQuery.md)
 - [SoftwareImageInformation](docs/SoftwareImageInformation.md)
 - [VirtualCompute](docs/VirtualCompute.md)
 - [VirtualComputeFlavour](docs/VirtualComputeFlavour.md)
 - [VirtualComputeQuota](docs/VirtualComputeQuota.md)
 - [VirtualComputeResourceInformation](docs/VirtualComputeResourceInformation.md)
 - [VirtualComputeResourceInformationVirtualCPU](docs/VirtualComputeResourceInformationVirtualCPU.md)
 - [VirtualComputeResourceInformationVirtualMemory](docs/VirtualComputeResourceInformationVirtualMemory.md)
 - [VirtualComputeVirtualCpu](docs/VirtualComputeVirtualCpu.md)
 - [VirtualComputeVirtualMemory](docs/VirtualComputeVirtualMemory.md)
 - [VirtualLinks](docs/VirtualLinks.md)
 - [VirtualLinksInner](docs/VirtualLinksInner.md)
 - [VirtualLinksInnerVirtualLink](docs/VirtualLinksInnerVirtualLink.md)
 - [VirtualLinksInnerVirtualLinkNetworkQoS](docs/VirtualLinksInnerVirtualLinkNetworkQoS.md)
 - [VirtualNetwork](docs/VirtualNetwork.md)
 - [VirtualNetworkQuota](docs/VirtualNetworkQuota.md)
 - [VirtualNetworkResourceInformation](docs/VirtualNetworkResourceInformation.md)


## Documentation for Authorization

All endpoints do not require authorization.
Authentication schemes defined for the API:

## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author



