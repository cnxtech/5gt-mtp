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
import com.mtp.extinterface.nbi.swagger.model.MetaDataInner;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Information specifying additional attributes of the network resource that has been reserved.
 */
@ApiModel(description = "Information specifying additional attributes of the network resource that has been reserved.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-05-28T11:44:14.596Z")
public class ReservedVirtualNetworkNetworkAttributes {
  @SerializedName("bandwidth")
  private BigDecimal bandwidth = null;

  @SerializedName("isShared")
  private Boolean isShared = null;

  @SerializedName("metadata")
  private List<MetaDataInner> metadata = null;

  @SerializedName("networkType")
  private String networkType = null;

  @SerializedName("segmentType")
  private String segmentType = null;

  public ReservedVirtualNetworkNetworkAttributes bandwidth(BigDecimal bandwidth) {
    this.bandwidth = bandwidth;
    return this;
  }

   /**
   * Minimum network bitrate (in Mbps).
   * @return bandwidth
  **/
  @ApiModelProperty(required = true, value = "Minimum network bitrate (in Mbps).")
  public BigDecimal getBandwidth() {
    return bandwidth;
  }

  public void setBandwidth(BigDecimal bandwidth) {
    this.bandwidth = bandwidth;
  }

  public ReservedVirtualNetworkNetworkAttributes isShared(Boolean isShared) {
    this.isShared = isShared;
    return this;
  }

   /**
   * It defines whether the virtualised network that has been reserved is shared among consumers.
   * @return isShared
  **/
  @ApiModelProperty(required = true, value = "It defines whether the virtualised network that has been reserved is shared among consumers.")
  public Boolean isIsShared() {
    return isShared;
  }

  public void setIsShared(Boolean isShared) {
    this.isShared = isShared;
  }

  public ReservedVirtualNetworkNetworkAttributes metadata(List<MetaDataInner> metadata) {
    this.metadata = metadata;
    return this;
  }

  public ReservedVirtualNetworkNetworkAttributes addMetadataItem(MetaDataInner metadataItem) {
    if (this.metadata == null) {
      this.metadata = new ArrayList<MetaDataInner>();
    }
    this.metadata.add(metadataItem);
    return this;
  }

   /**
   * List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.
   * @return metadata
  **/
  @ApiModelProperty(value = "List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.")
  public List<MetaDataInner> getMetadata() {
    return metadata;
  }

  public void setMetadata(List<MetaDataInner> metadata) {
    this.metadata = metadata;
  }

  public ReservedVirtualNetworkNetworkAttributes networkType(String networkType) {
    this.networkType = networkType;
    return this;
  }

   /**
   * The type of network that maps to the virtualised network that has been reserved. Examples are: \&quot;local\&quot;, \&quot;vlan\&quot;, \&quot;vxlan\&quot;, \&quot;gre\&quot;, etc.
   * @return networkType
  **/
  @ApiModelProperty(required = true, value = "The type of network that maps to the virtualised network that has been reserved. Examples are: \"local\", \"vlan\", \"vxlan\", \"gre\", etc.")
  public String getNetworkType() {
    return networkType;
  }

  public void setNetworkType(String networkType) {
    this.networkType = networkType;
  }

  public ReservedVirtualNetworkNetworkAttributes segmentType(String segmentType) {
    this.segmentType = segmentType;
    return this;
  }

   /**
   * The isolated segment for the virtualised network that has been reserved. For instance, for a \&quot;vlan\&quot; networkType, it corresponds to the vlan identifier; and for a \&quot;gre\&quot; networkType, this corresponds to a gre key.
   * @return segmentType
  **/
  @ApiModelProperty(required = true, value = "The isolated segment for the virtualised network that has been reserved. For instance, for a \"vlan\" networkType, it corresponds to the vlan identifier; and for a \"gre\" networkType, this corresponds to a gre key.")
  public String getSegmentType() {
    return segmentType;
  }

  public void setSegmentType(String segmentType) {
    this.segmentType = segmentType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReservedVirtualNetworkNetworkAttributes reservedVirtualNetworkNetworkAttributes = (ReservedVirtualNetworkNetworkAttributes) o;
    return Objects.equals(this.bandwidth, reservedVirtualNetworkNetworkAttributes.bandwidth) &&
        Objects.equals(this.isShared, reservedVirtualNetworkNetworkAttributes.isShared) &&
        Objects.equals(this.metadata, reservedVirtualNetworkNetworkAttributes.metadata) &&
        Objects.equals(this.networkType, reservedVirtualNetworkNetworkAttributes.networkType) &&
        Objects.equals(this.segmentType, reservedVirtualNetworkNetworkAttributes.segmentType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bandwidth, isShared, metadata, networkType, segmentType);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReservedVirtualNetworkNetworkAttributes {\n");
    
    sb.append("    bandwidth: ").append(toIndentedString(bandwidth)).append("\n");
    sb.append("    isShared: ").append(toIndentedString(isShared)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    networkType: ").append(toIndentedString(networkType)).append("\n");
    sb.append("    segmentType: ").append(toIndentedString(segmentType)).append("\n");
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

