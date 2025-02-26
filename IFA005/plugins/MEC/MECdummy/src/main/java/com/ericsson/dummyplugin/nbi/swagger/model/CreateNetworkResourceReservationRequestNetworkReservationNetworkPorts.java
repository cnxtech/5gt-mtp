package com.ericsson.dummyplugin.nbi.swagger.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class CreateNetworkResourceReservationRequestNetworkReservationNetworkPorts   {
  
  private @Valid BigDecimal bandwidth = null;
  private @Valid List<MetaDataInner> metadata = new ArrayList<MetaDataInner>();
  private @Valid Integer portId = null;
  private @Valid String portType = null;
  private @Valid String segmentId = null;

  /**
   * The bitrate of the virtual network port (in Mbps).
   **/
  public CreateNetworkResourceReservationRequestNetworkReservationNetworkPorts bandwidth(BigDecimal bandwidth) {
    this.bandwidth = bandwidth;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The bitrate of the virtual network port (in Mbps).")
  @JsonProperty("bandwidth")
  @NotNull
  public BigDecimal getBandwidth() {
    return bandwidth;
  }
  public void setBandwidth(BigDecimal bandwidth) {
    this.bandwidth = bandwidth;
  }

  /**
   * List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.
   **/
  public CreateNetworkResourceReservationRequestNetworkReservationNetworkPorts metadata(List<MetaDataInner> metadata) {
    this.metadata = metadata;
    return this;
  }

  
  @ApiModelProperty(value = "List of metadata key-value pairs used by the consumer to associate meaningful metadata to the related virtualised resource.")
  @JsonProperty("metadata")
  public List<MetaDataInner> getMetadata() {
    return metadata;
  }
  public void setMetadata(List<MetaDataInner> metadata) {
    this.metadata = metadata;
  }

  /**
   * Identifier of the network port to reserve.
   **/
  public CreateNetworkResourceReservationRequestNetworkReservationNetworkPorts portId(Integer portId) {
    this.portId = portId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the network port to reserve.")
  @JsonProperty("portId")
  @NotNull
  public Integer getPortId() {
    return portId;
  }
  public void setPortId(Integer portId) {
    this.portId = portId;
  }

  /**
   * Type of network port. Examples of types are access ports, or trunk ports (layer 1) that become transport for multiple layer 2 or layer 3 networks.
   **/
  public CreateNetworkResourceReservationRequestNetworkReservationNetworkPorts portType(String portType) {
    this.portType = portType;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Type of network port. Examples of types are access ports, or trunk ports (layer 1) that become transport for multiple layer 2 or layer 3 networks.")
  @JsonProperty("portType")
  @NotNull
  public String getPortType() {
    return portType;
  }
  public void setPortType(String portType) {
    this.portType = portType;
  }

  /**
   * The isolated segment the network port belongs to. For instance, for a \&quot;vlan\&quot;, it corresponds to the vlan identifier; and for a \&quot;gre\&quot;, this corresponds to a gre key. The cardinality can be 0 to allow for flat networks without any specific segmentation.
   **/
  public CreateNetworkResourceReservationRequestNetworkReservationNetworkPorts segmentId(String segmentId) {
    this.segmentId = segmentId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The isolated segment the network port belongs to. For instance, for a \"vlan\", it corresponds to the vlan identifier; and for a \"gre\", this corresponds to a gre key. The cardinality can be 0 to allow for flat networks without any specific segmentation.")
  @JsonProperty("segmentId")
  @NotNull
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
    CreateNetworkResourceReservationRequestNetworkReservationNetworkPorts createNetworkResourceReservationRequestNetworkReservationNetworkPorts = (CreateNetworkResourceReservationRequestNetworkReservationNetworkPorts) o;
    return Objects.equals(bandwidth, createNetworkResourceReservationRequestNetworkReservationNetworkPorts.bandwidth) &&
        Objects.equals(metadata, createNetworkResourceReservationRequestNetworkReservationNetworkPorts.metadata) &&
        Objects.equals(portId, createNetworkResourceReservationRequestNetworkReservationNetworkPorts.portId) &&
        Objects.equals(portType, createNetworkResourceReservationRequestNetworkReservationNetworkPorts.portType) &&
        Objects.equals(segmentId, createNetworkResourceReservationRequestNetworkReservationNetworkPorts.segmentId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bandwidth, metadata, portId, portType, segmentId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateNetworkResourceReservationRequestNetworkReservationNetworkPorts {\n");
    
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

