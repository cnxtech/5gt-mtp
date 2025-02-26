/*
 * MTP Manager API
 * MTP Manager API
 *
 * OpenAPI spec version: 2.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.mtp.extinterface.nbi.swagger.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.mtp.extinterface.nbi.swagger.model.CategoryRef;
import com.mtp.extinterface.nbi.swagger.model.TransportDependency;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ServiceDependency
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-05-28T11:44:14.596Z")
public class ServiceDependency {
  @SerializedName("serName")
  private String serName = null;

  @SerializedName("serCategory")
  private CategoryRef serCategory = null;

  @SerializedName("version")
  private String version = null;

  @SerializedName("serTransportDependencies")
  private List<TransportDependency> serTransportDependencies = null;

  @SerializedName("requestedPermissions")
  private Object requestedPermissions = null;

  public ServiceDependency serName(String serName) {
    this.serName = serName;
    return this;
  }

   /**
   * The name of the service, for example, RNIS, LocationService, etc.
   * @return serName
  **/
  @ApiModelProperty(required = true, value = "The name of the service, for example, RNIS, LocationService, etc.")
  public String getSerName() {
    return serName;
  }

  public void setSerName(String serName) {
    this.serName = serName;
  }

  public ServiceDependency serCategory(CategoryRef serCategory) {
    this.serCategory = serCategory;
    return this;
  }

   /**
   * Get serCategory
   * @return serCategory
  **/
  @ApiModelProperty(value = "")
  public CategoryRef getSerCategory() {
    return serCategory;
  }

  public void setSerCategory(CategoryRef serCategory) {
    this.serCategory = serCategory;
  }

  public ServiceDependency version(String version) {
    this.version = version;
    return this;
  }

   /**
   * The version of the service.
   * @return version
  **/
  @ApiModelProperty(required = true, value = "The version of the service.")
  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public ServiceDependency serTransportDependencies(List<TransportDependency> serTransportDependencies) {
    this.serTransportDependencies = serTransportDependencies;
    return this;
  }

  public ServiceDependency addSerTransportDependenciesItem(TransportDependency serTransportDependenciesItem) {
    if (this.serTransportDependencies == null) {
      this.serTransportDependencies = new ArrayList<TransportDependency>();
    }
    this.serTransportDependencies.add(serTransportDependenciesItem);
    return this;
  }

   /**
   * Indicates transport and serialization format dependencies of consuming the service. Defaults to REST + JSON if absent. This attribute indicates groups of transport bindings that a service-consuming ME application supports for the consumption of the ME service defined by this ServiceDependency structure. If at leastone of the indicated groups is supported by the service it may be consumed by the application.
   * @return serTransportDependencies
  **/
  @ApiModelProperty(value = "Indicates transport and serialization format dependencies of consuming the service. Defaults to REST + JSON if absent. This attribute indicates groups of transport bindings that a service-consuming ME application supports for the consumption of the ME service defined by this ServiceDependency structure. If at leastone of the indicated groups is supported by the service it may be consumed by the application.")
  public List<TransportDependency> getSerTransportDependencies() {
    return serTransportDependencies;
  }

  public void setSerTransportDependencies(List<TransportDependency> serTransportDependencies) {
    this.serTransportDependencies = serTransportDependencies;
  }

  public ServiceDependency requestedPermissions(Object requestedPermissions) {
    this.requestedPermissions = requestedPermissions;
    return this;
  }

   /**
   * Requested permissions regarding the access of the application to the service. See clause 8.2 of ETSI GS MEC 009. The format of this attribute is left for the data model design stage.
   * @return requestedPermissions
  **/
  @ApiModelProperty(value = "Requested permissions regarding the access of the application to the service. See clause 8.2 of ETSI GS MEC 009. The format of this attribute is left for the data model design stage.")
  public Object getRequestedPermissions() {
    return requestedPermissions;
  }

  public void setRequestedPermissions(Object requestedPermissions) {
    this.requestedPermissions = requestedPermissions;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServiceDependency serviceDependency = (ServiceDependency) o;
    return Objects.equals(this.serName, serviceDependency.serName) &&
        Objects.equals(this.serCategory, serviceDependency.serCategory) &&
        Objects.equals(this.version, serviceDependency.version) &&
        Objects.equals(this.serTransportDependencies, serviceDependency.serTransportDependencies) &&
        Objects.equals(this.requestedPermissions, serviceDependency.requestedPermissions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serName, serCategory, version, serTransportDependencies, requestedPermissions);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServiceDependency {\n");
    
    sb.append("    serName: ").append(toIndentedString(serName)).append("\n");
    sb.append("    serCategory: ").append(toIndentedString(serCategory)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    serTransportDependencies: ").append(toIndentedString(serTransportDependencies)).append("\n");
    sb.append("    requestedPermissions: ").append(toIndentedString(requestedPermissions)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

