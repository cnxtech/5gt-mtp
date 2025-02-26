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
import com.mtp.extinterface.nbi.swagger.model.AllocateNetworkResultNetworkDataNetworkPort;
import com.mtp.extinterface.nbi.swagger.model.AllocateNetworkResultNetworkDataNetworkQoS;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * VirtualNetwork
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-05-28T11:44:14.596Z")
public class VirtualNetwork {
  @SerializedName("bandwidth")
  private BigDecimal bandwidth = null;

  @SerializedName("isShared")
  private Boolean isShared = null;

  @SerializedName("networkPort")
  private List<AllocateNetworkResultNetworkDataNetworkPort> networkPort = new ArrayList<AllocateNetworkResultNetworkDataNetworkPort>();

  @SerializedName("networkQoS")
  private List<AllocateNetworkResultNetworkDataNetworkQoS> networkQoS = new ArrayList<AllocateNetworkResultNetworkDataNetworkQoS>();

  @SerializedName("networkResourceId")
  private String networkResourceId = null;

  @SerializedName("networkResourceName")
  private String networkResourceName = null;

  @SerializedName("networkType")
  private String networkType = null;

  @SerializedName("operationalState")
  private String operationalState = null;

  @SerializedName("segmentType")
  private String segmentType = null;

  @SerializedName("sharingCriteria")
  private String sharingCriteria = null;

  @SerializedName("subnet")
  private String subnet = null;

  @SerializedName("zoneId")
  private String zoneId = null;

  public VirtualNetwork bandwidth(BigDecimal bandwidth) {
    this.bandwidth = bandwidth;
    return this;
  }

   /**
   * Minimum network bandwidth (in Mbps).
   * @return bandwidth
  **/
  @ApiModelProperty(required = true, value = "Minimum network bandwidth (in Mbps).")
  public BigDecimal getBandwidth() {
    return bandwidth;
  }

  public void setBandwidth(BigDecimal bandwidth) {
    this.bandwidth = bandwidth;
  }

  public VirtualNetwork isShared(Boolean isShared) {
    this.isShared = isShared;
    return this;
  }

   /**
   * It defines whether the virtualised network is shared among consumers.
   * @return isShared
  **/
  @ApiModelProperty(required = true, value = "It defines whether the virtualised network is shared among consumers.")
  public Boolean isIsShared() {
    return isShared;
  }

  public void setIsShared(Boolean isShared) {
    this.isShared = isShared;
  }

  public VirtualNetwork networkPort(List<AllocateNetworkResultNetworkDataNetworkPort> networkPort) {
    this.networkPort = networkPort;
    return this;
  }

  public VirtualNetwork addNetworkPortItem(AllocateNetworkResultNetworkDataNetworkPort networkPortItem) {
    this.networkPort.add(networkPortItem);
    return this;
  }

   /**
   * Element providing information of an instantiated virtual network port.
   * @return networkPort
  **/
  @ApiModelProperty(required = true, value = "Element providing information of an instantiated virtual network port.")
  public List<AllocateNetworkResultNetworkDataNetworkPort> getNetworkPort() {
    return networkPort;
  }

  public void setNetworkPort(List<AllocateNetworkResultNetworkDataNetworkPort> networkPort) {
    this.networkPort = networkPort;
  }

  public VirtualNetwork networkQoS(List<AllocateNetworkResultNetworkDataNetworkQoS> networkQoS) {
    this.networkQoS = networkQoS;
    return this;
  }

  public VirtualNetwork addNetworkQoSItem(AllocateNetworkResultNetworkDataNetworkQoS networkQoSItem) {
    this.networkQoS.add(networkQoSItem);
    return this;
  }

   /**
   * Element providing information about Quality of Service attributes that the network shall support. The cardinality can be \&quot;0\&quot; to allow for networks without any specified QoS requirements.
   * @return networkQoS
  **/
  @ApiModelProperty(required = true, value = "Element providing information about Quality of Service attributes that the network shall support. The cardinality can be \"0\" to allow for networks without any specified QoS requirements.")
  public List<AllocateNetworkResultNetworkDataNetworkQoS> getNetworkQoS() {
    return networkQoS;
  }

  public void setNetworkQoS(List<AllocateNetworkResultNetworkDataNetworkQoS> networkQoS) {
    this.networkQoS = networkQoS;
  }

  public VirtualNetwork networkResourceId(String networkResourceId) {
    this.networkResourceId = networkResourceId;
    return this;
  }

   /**
   * Identifier of the virtualised network resource.
   * @return networkResourceId
  **/
  @ApiModelProperty(required = true, value = "Identifier of the virtualised network resource.")
  public String getNetworkResourceId() {
    return networkResourceId;
  }

  public void setNetworkResourceId(String networkResourceId) {
    this.networkResourceId = networkResourceId;
  }

  public VirtualNetwork networkResourceName(String networkResourceName) {
    this.networkResourceName = networkResourceName;
    return this;
  }

   /**
   * Name of the virtualised network resource.
   * @return networkResourceName
  **/
  @ApiModelProperty(required = true, value = "Name of the virtualised network resource.")
  public String getNetworkResourceName() {
    return networkResourceName;
  }

  public void setNetworkResourceName(String networkResourceName) {
    this.networkResourceName = networkResourceName;
  }

  public VirtualNetwork networkType(String networkType) {
    this.networkType = networkType;
    return this;
  }

   /**
   * The type of network that maps to the virtualised network. This list is extensible. Examples are: \&quot;local\&quot;, \&quot;vlan\&quot;, \&quot;vxlan\&quot;, \&quot;gre\&quot;, \&quot;l3-vpn\&quot;, etc. The cardinality can be \&quot;0\&quot; to cover the case where this attribute is not required to create the virtualised network.
   * @return networkType
  **/
  @ApiModelProperty(required = true, value = "The type of network that maps to the virtualised network. This list is extensible. Examples are: \"local\", \"vlan\", \"vxlan\", \"gre\", \"l3-vpn\", etc. The cardinality can be \"0\" to cover the case where this attribute is not required to create the virtualised network.")
  public String getNetworkType() {
    return networkType;
  }

  public void setNetworkType(String networkType) {
    this.networkType = networkType;
  }

  public VirtualNetwork operationalState(String operationalState) {
    this.operationalState = operationalState;
    return this;
  }

   /**
   * The operational state of the virtualised network.
   * @return operationalState
  **/
  @ApiModelProperty(required = true, value = "The operational state of the virtualised network.")
  public String getOperationalState() {
    return operationalState;
  }

  public void setOperationalState(String operationalState) {
    this.operationalState = operationalState;
  }

  public VirtualNetwork segmentType(String segmentType) {
    this.segmentType = segmentType;
    return this;
  }

   /**
   * The isolated segment for the virtualised network. For instance, for a \&quot;vlan\&quot; networkType, it corresponds to the vlan identifier; and for a \&quot;gre\&quot; networkType, this corresponds to a gre key. The cardinality can be \&quot;0\&quot; to allow for flat networks without any specific segmentation.
   * @return segmentType
  **/
  @ApiModelProperty(required = true, value = "The isolated segment for the virtualised network. For instance, for a \"vlan\" networkType, it corresponds to the vlan identifier; and for a \"gre\" networkType, this corresponds to a gre key. The cardinality can be \"0\" to allow for flat networks without any specific segmentation.")
  public String getSegmentType() {
    return segmentType;
  }

  public void setSegmentType(String segmentType) {
    this.segmentType = segmentType;
  }

  public VirtualNetwork sharingCriteria(String sharingCriteria) {
    this.sharingCriteria = sharingCriteria;
    return this;
  }

   /**
   * Only present for shared networks. Indicate the sharing criteria for this network. This criteria might be a list of authorized consumers.
   * @return sharingCriteria
  **/
  @ApiModelProperty(required = true, value = "Only present for shared networks. Indicate the sharing criteria for this network. This criteria might be a list of authorized consumers.")
  public String getSharingCriteria() {
    return sharingCriteria;
  }

  public void setSharingCriteria(String sharingCriteria) {
    this.sharingCriteria = sharingCriteria;
  }

  public VirtualNetwork subnet(String subnet) {
    this.subnet = subnet;
    return this;
  }

   /**
   * Only present if the network provides layer 3 connectivity.
   * @return subnet
  **/
  @ApiModelProperty(required = true, value = "Only present if the network provides layer 3 connectivity.")
  public String getSubnet() {
    return subnet;
  }

  public void setSubnet(String subnet) {
    this.subnet = subnet;
  }

  public VirtualNetwork zoneId(String zoneId) {
    this.zoneId = zoneId;
    return this;
  }

   /**
   * If present, it identifies the Resource Zone where the virtual network resources have been allocated.
   * @return zoneId
  **/
  @ApiModelProperty(required = true, value = "If present, it identifies the Resource Zone where the virtual network resources have been allocated.")
  public String getZoneId() {
    return zoneId;
  }

  public void setZoneId(String zoneId) {
    this.zoneId = zoneId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtualNetwork virtualNetwork = (VirtualNetwork) o;
    return Objects.equals(this.bandwidth, virtualNetwork.bandwidth) &&
        Objects.equals(this.isShared, virtualNetwork.isShared) &&
        Objects.equals(this.networkPort, virtualNetwork.networkPort) &&
        Objects.equals(this.networkQoS, virtualNetwork.networkQoS) &&
        Objects.equals(this.networkResourceId, virtualNetwork.networkResourceId) &&
        Objects.equals(this.networkResourceName, virtualNetwork.networkResourceName) &&
        Objects.equals(this.networkType, virtualNetwork.networkType) &&
        Objects.equals(this.operationalState, virtualNetwork.operationalState) &&
        Objects.equals(this.segmentType, virtualNetwork.segmentType) &&
        Objects.equals(this.sharingCriteria, virtualNetwork.sharingCriteria) &&
        Objects.equals(this.subnet, virtualNetwork.subnet) &&
        Objects.equals(this.zoneId, virtualNetwork.zoneId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bandwidth, isShared, networkPort, networkQoS, networkResourceId, networkResourceName, networkType, operationalState, segmentType, sharingCriteria, subnet, zoneId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualNetwork {\n");
    
    sb.append("    bandwidth: ").append(toIndentedString(bandwidth)).append("\n");
    sb.append("    isShared: ").append(toIndentedString(isShared)).append("\n");
    sb.append("    networkPort: ").append(toIndentedString(networkPort)).append("\n");
    sb.append("    networkQoS: ").append(toIndentedString(networkQoS)).append("\n");
    sb.append("    networkResourceId: ").append(toIndentedString(networkResourceId)).append("\n");
    sb.append("    networkResourceName: ").append(toIndentedString(networkResourceName)).append("\n");
    sb.append("    networkType: ").append(toIndentedString(networkType)).append("\n");
    sb.append("    operationalState: ").append(toIndentedString(operationalState)).append("\n");
    sb.append("    segmentType: ").append(toIndentedString(segmentType)).append("\n");
    sb.append("    sharingCriteria: ").append(toIndentedString(sharingCriteria)).append("\n");
    sb.append("    subnet: ").append(toIndentedString(subnet)).append("\n");
    sb.append("    zoneId: ").append(toIndentedString(zoneId)).append("\n");
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

