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
import com.mtp.extinterface.nbi.swagger.model.ServiceDescriptorTransportsSupported;
import java.io.IOException;

/**
 * ServiceDescriptor
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-05-28T11:44:14.596Z")
public class ServiceDescriptor {
  @SerializedName("serName")
  private String serName = null;

  @SerializedName("serCategory")
  private CategoryRef serCategory = null;

  @SerializedName("version")
  private String version = null;

  @SerializedName("transportsSupported")
  private ServiceDescriptorTransportsSupported transportsSupported = null;

  public ServiceDescriptor serName(String serName) {
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

  public ServiceDescriptor serCategory(CategoryRef serCategory) {
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

  public ServiceDescriptor version(String version) {
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

  public ServiceDescriptor transportsSupported(ServiceDescriptorTransportsSupported transportsSupported) {
    this.transportsSupported = transportsSupported;
    return this;
  }

   /**
   * Get transportsSupported
   * @return transportsSupported
  **/
  @ApiModelProperty(value = "")
  public ServiceDescriptorTransportsSupported getTransportsSupported() {
    return transportsSupported;
  }

  public void setTransportsSupported(ServiceDescriptorTransportsSupported transportsSupported) {
    this.transportsSupported = transportsSupported;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServiceDescriptor serviceDescriptor = (ServiceDescriptor) o;
    return Objects.equals(this.serName, serviceDescriptor.serName) &&
        Objects.equals(this.serCategory, serviceDescriptor.serCategory) &&
        Objects.equals(this.version, serviceDescriptor.version) &&
        Objects.equals(this.transportsSupported, serviceDescriptor.transportsSupported);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serName, serCategory, version, transportsSupported);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServiceDescriptor {\n");
    
    sb.append("    serName: ").append(toIndentedString(serName)).append("\n");
    sb.append("    serCategory: ").append(toIndentedString(serCategory)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    transportsSupported: ").append(toIndentedString(transportsSupported)).append("\n");
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

