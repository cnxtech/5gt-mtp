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
 * ReservedVirtualNetworkNetworkPorts
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-05-28T11:44:14.596Z")
public class ReservedVirtualNetworkNetworkPorts {
  @SerializedName("bandwidth")
  private BigDecimal bandwidth = null;

  @SerializedName("metadata")
  private List<MetaDataInner> metadata = null;

  @SerializedName("portId")
  private String portId = null;

  @SerializedName("portType")
  private String portType = null;

  @SerializedName("segmentId")
  private String segmentId = null;

  public ReservedVirtualNetworkNetworkPorts bandwidth(BigDecimal bandwidth) {
    this.bandwidth = bandwidth;
    return this;
  }

   /**
   * The bitrate of the virtual network port (in Mbps)
   * @return bandwidth
  **/
  @ApiModelProperty(required = true, value = "The bitrate of the virtual network port (in Mbps)")
  public BigDecimal getBandwidth() {
    return bandwidth;
  }

  public void setBandwidth(BigDecimal bandwidth) {
    this.bandwidth = bandwidth;
  }

  public ReservedVirtualNetworkNetworkPorts metadata(List<MetaDataInner> metadata) {
    this.metadata = metadata;
    return this;
  }

  public ReservedVirtualNetworkNetworkPorts addMetadataItem(MetaDataInner metadataItem) {
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

  public ReservedVirtualNetworkNetworkPorts portId(String portId) {
    this.portId = portId;
    return this;
  }

   /**
   * Identifier of the network port that has been reserved.
   * @return portId
  **/
  @ApiModelProperty(required = true, value = "Identifier of the network port that has been reserved.")
  public String getPortId() {
    return portId;
  }

  public void setPortId(String portId) {
    this.portId = portId;
  }

  public ReservedVirtualNetworkNetworkPorts portType(String portType) {
    this.portType = portType;
    return this;
  }

   /**
   * Type of network port. Examples of types are access ports, or trunk ports (layer 1) that become transport for multiple layer 2 or layer 3 networks.
   * @return portType
  **/
  @ApiModelProperty(required = true, value = "Type of network port. Examples of types are access ports, or trunk ports (layer 1) that become transport for multiple layer 2 or layer 3 networks.")
  public String getPortType() {
    return portType;
  }

  public void setPortType(String portType) {
    this.portType = portType;
  }

  public ReservedVirtualNetworkNetworkPorts segmentId(String segmentId) {
    this.segmentId = segmentId;
    return this;
  }

   /**
   * The isolated segment the network port belongs to. For instance, for a \&quot;vlan\&quot;, it corresponds to the vlan identifier; and for a \&quot;gre\&quot;, this corresponds to a gre key. The cardinality can be 0 to allow for flat networks without any specific segmentation.
   * @return segmentId
  **/
  @ApiModelProperty(required = true, value = "The isolated segment the network port belongs to. For instance, for a \"vlan\", it corresponds to the vlan identifier; and for a \"gre\", this corresponds to a gre key. The cardinality can be 0 to allow for flat networks without any specific segmentation.")
  public String getSegmentId() {
    return segmentId;
  }

  public void setSegmentId(String segmentId) {
    this.segmentId = segmentId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReservedVirtualNetworkNetworkPorts reservedVirtualNetworkNetworkPorts = (ReservedVirtualNetworkNetworkPorts) o;
    return Objects.equals(this.bandwidth, reservedVirtualNetworkNetworkPorts.bandwidth) &&
        Objects.equals(this.metadata, reservedVirtualNetworkNetworkPorts.metadata) &&
        Objects.equals(this.portId, reservedVirtualNetworkNetworkPorts.portId) &&
        Objects.equals(this.portType, reservedVirtualNetworkNetworkPorts.portType) &&
        Objects.equals(this.segmentId, reservedVirtualNetworkNetworkPorts.segmentId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bandwidth, metadata, portId, portType, segmentId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReservedVirtualNetworkNetworkPorts {\n");
    
    sb.append("    bandwidth: ").append(toIndentedString(bandwidth)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    portId: ").append(toIndentedString(portId)).append("\n");
    sb.append("    portType: ").append(toIndentedString(portType)).append("\n");
    sb.append("    segmentId: ").append(toIndentedString(segmentId)).append("\n");
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

