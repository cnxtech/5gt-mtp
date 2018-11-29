# AbstractResourcesApi

All URIs are relative to *https://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**allocateAbstractCompute**](AbstractResourcesApi.md#allocateAbstractCompute) | **POST** /abstract-compute-resources | Allocate abstract compute resources
[**collectMtpCloudNetworkResourceInformation**](AbstractResourcesApi.md#collectMtpCloudNetworkResourceInformation) | **GET** /abstract-resources | Retrieve aggregated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity
[**createInterNfviPoPConnectivity**](AbstractResourcesApi.md#createInterNfviPoPConnectivity) | **POST** /abstract-network-resources | Create inter-NFVI-PoP connectivity
[**deleteInterNfviPoPConnectivity**](AbstractResourcesApi.md#deleteInterNfviPoPConnectivity) | **DELETE** /abstract-network-resources | Delete inter-NFVI-PoP connectivity
[**terminateResources**](AbstractResourcesApi.md#terminateResources) | **DELETE** /abstract-compute-resources | Terminate abstract compute resources


<a name="allocateAbstractCompute"></a>
# **allocateAbstractCompute**
> VirtualCompute allocateAbstractCompute(body)

Allocate abstract compute resources

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AbstractResourcesApi;


AbstractResourcesApi apiInstance = new AbstractResourcesApi();
AllocateComputeRequest body = new AllocateComputeRequest(); // AllocateComputeRequest | 
try {
    VirtualCompute result = apiInstance.allocateAbstractCompute(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AbstractResourcesApi#allocateAbstractCompute");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**AllocateComputeRequest**](AllocateComputeRequest.md)|  |

### Return type

[**VirtualCompute**](VirtualCompute.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="collectMtpCloudNetworkResourceInformation"></a>
# **collectMtpCloudNetworkResourceInformation**
> InlineResponse2001 collectMtpCloudNetworkResourceInformation()

Retrieve aggregated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity

Retrieve aggregated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AbstractResourcesApi;


AbstractResourcesApi apiInstance = new AbstractResourcesApi();
try {
    InlineResponse2001 result = apiInstance.collectMtpCloudNetworkResourceInformation();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AbstractResourcesApi#collectMtpCloudNetworkResourceInformation");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**InlineResponse2001**](InlineResponse2001.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="createInterNfviPoPConnectivity"></a>
# **createInterNfviPoPConnectivity**
> InlineResponse201 createInterNfviPoPConnectivity(body)

Create inter-NFVI-PoP connectivity



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AbstractResourcesApi;


AbstractResourcesApi apiInstance = new AbstractResourcesApi();
InterNfviPopConnectivityRequest body = new InterNfviPopConnectivityRequest(); // InterNfviPopConnectivityRequest | Create inter-NfviPop Connectivity
try {
    InlineResponse201 result = apiInstance.createInterNfviPoPConnectivity(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AbstractResourcesApi#createInterNfviPoPConnectivity");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**InterNfviPopConnectivityRequest**](InterNfviPopConnectivityRequest.md)| Create inter-NfviPop Connectivity |

### Return type

[**InlineResponse201**](InlineResponse201.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteInterNfviPoPConnectivity"></a>
# **deleteInterNfviPoPConnectivity**
> deleteInterNfviPoPConnectivity(body)

Delete inter-NFVI-PoP connectivity



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AbstractResourcesApi;


AbstractResourcesApi apiInstance = new AbstractResourcesApi();
DeleteInterNfviPopConnectivityRequest body = new DeleteInterNfviPopConnectivityRequest(); // DeleteInterNfviPopConnectivityRequest | Delete inter-NfviPop Connectivity
try {
    apiInstance.deleteInterNfviPoPConnectivity(body);
} catch (ApiException e) {
    System.err.println("Exception when calling AbstractResourcesApi#deleteInterNfviPoPConnectivity");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**DeleteInterNfviPopConnectivityRequest**](DeleteInterNfviPopConnectivityRequest.md)| Delete inter-NfviPop Connectivity |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a name="terminateResources"></a>
# **terminateResources**
> List&lt;String&gt; terminateResources(computeId)

Terminate abstract compute resources

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AbstractResourcesApi;


AbstractResourcesApi apiInstance = new AbstractResourcesApi();
List<String> computeId = Arrays.asList("computeId_example"); // List<String> | Identifier(s) of the virtualised compute resource(s) to be terminated.
try {
    List<String> result = apiInstance.terminateResources(computeId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AbstractResourcesApi#terminateResources");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **computeId** | [**List&lt;String&gt;**](String.md)| Identifier(s) of the virtualised compute resource(s) to be terminated. |

### Return type

**List&lt;String&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

