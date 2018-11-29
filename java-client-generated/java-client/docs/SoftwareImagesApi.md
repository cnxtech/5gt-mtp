# SoftwareImagesApi

All URIs are relative to *https://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addSoftwareImage**](SoftwareImagesApi.md#addSoftwareImage) | **POST** /software_images | 
[**deleteSoftwareImage**](SoftwareImagesApi.md#deleteSoftwareImage) | **DELETE** /software_images/{id} | 
[**querySoftwareImage**](SoftwareImagesApi.md#querySoftwareImage) | **GET** /software_images/{id} | 
[**querySoftwareImages**](SoftwareImagesApi.md#querySoftwareImages) | **GET** /software_images | 


<a name="addSoftwareImage"></a>
# **addSoftwareImage**
> SoftwareImageInformation addSoftwareImage(body)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.SoftwareImagesApi;


SoftwareImagesApi apiInstance = new SoftwareImagesApi();
SoftwareImageAddQuery body = new SoftwareImageAddQuery(); // SoftwareImageAddQuery | 
try {
    SoftwareImageInformation result = apiInstance.addSoftwareImage(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SoftwareImagesApi#addSoftwareImage");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SoftwareImageAddQuery**](SoftwareImageAddQuery.md)|  |

### Return type

[**SoftwareImageInformation**](SoftwareImageInformation.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteSoftwareImage"></a>
# **deleteSoftwareImage**
> String deleteSoftwareImage(id)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.SoftwareImagesApi;


SoftwareImagesApi apiInstance = new SoftwareImagesApi();
String id = "id_example"; // String | The identifier of the software image to be deleted.
try {
    String result = apiInstance.deleteSoftwareImage(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SoftwareImagesApi#deleteSoftwareImage");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The identifier of the software image to be deleted. |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="querySoftwareImage"></a>
# **querySoftwareImage**
> SoftwareImageInformation querySoftwareImage(id)



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.SoftwareImagesApi;


SoftwareImagesApi apiInstance = new SoftwareImagesApi();
String id = "id_example"; // String | The identifier of the software image to be queried.
try {
    SoftwareImageInformation result = apiInstance.querySoftwareImage(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SoftwareImagesApi#querySoftwareImage");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| The identifier of the software image to be queried. |

### Return type

[**SoftwareImageInformation**](SoftwareImageInformation.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="querySoftwareImages"></a>
# **querySoftwareImages**
> List&lt;SoftwareImageInformation&gt; querySoftwareImages()



### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.SoftwareImagesApi;


SoftwareImagesApi apiInstance = new SoftwareImagesApi();
try {
    List<SoftwareImageInformation> result = apiInstance.querySoftwareImages();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SoftwareImagesApi#querySoftwareImages");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;SoftwareImageInformation&gt;**](SoftwareImageInformation.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

