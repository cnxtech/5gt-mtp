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
import com.mtp.extinterface.nbi.swagger.model.ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface;
import com.mtp.extinterface.nbi.swagger.model.VirtualComputeVirtualCpu;
import com.mtp.extinterface.nbi.swagger.model.VirtualComputeVirtualMemory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * VIMVirtualCompute
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-05-28T11:44:14.596Z")
public class VIMVirtualCompute {
  @SerializedName("accelerationCapability")
  private List<String> accelerationCapability = new ArrayList<String>();

  @SerializedName("computeId")
  private String computeId = null;

  @SerializedName("computeName")
  private String computeName = null;

  @SerializedName("flavourId")
  private String flavourId = null;

  @SerializedName("hostId")
  private String hostId = null;

  @SerializedName("operationalState")
  private String operationalState = null;

  @SerializedName("vcImageId")
  private String vcImageId = null;

  @SerializedName("virtualCpu")
  private VirtualComputeVirtualCpu virtualCpu = null;

  @SerializedName("virtualDisks")
  private String virtualDisks = null;

  @SerializedName("virtualMemory")
  private VirtualComputeVirtualMemory virtualMemory = null;

  @SerializedName("virtualNetworkInterface")
  private List<ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface> virtualNetworkInterface = new ArrayList<ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface>();

  @SerializedName("zoneId")
  private String zoneId = null;

  public VIMVirtualCompute accelerationCapability(List<String> accelerationCapability) {
    this.accelerationCapability = accelerationCapability;
    return this;
  }

  public VIMVirtualCompute addAccelerationCapabilityItem(String accelerationCapabilityItem) {
    this.accelerationCapability.add(accelerationCapabilityItem);
    return this;
  }

   /**
   * Selected acceleration capabilities (e.g. crypto, GPU) from the set of capabilities offered by the compute node acceleration resources. The cardinality can be 0, if no particular acceleration capability is provided.
   * @return accelerationCapability
  **/
  @ApiModelProperty(required = true, value = "Selected acceleration capabilities (e.g. crypto, GPU) from the set of capabilities offered by the compute node acceleration resources. The cardinality can be 0, if no particular acceleration capability is provided.")
  public List<String> getAccelerationCapability() {
    return accelerationCapability;
  }

  public void setAccelerationCapability(List<String> accelerationCapability) {
    this.accelerationCapability = accelerationCapability;
  }

  public VIMVirtualCompute computeId(String computeId) {
    this.computeId = computeId;
    return this;
  }

   /**
   * Identifier of the virtualised compute resource.
   * @return computeId
  **/
  @ApiModelProperty(required = true, value = "Identifier of the virtualised compute resource.")
  public String getComputeId() {
    return computeId;
  }

  public void setComputeId(String computeId) {
    this.computeId = computeId;
  }

  public VIMVirtualCompute computeName(String computeName) {
    this.computeName = computeName;
    return this;
  }

   /**
   * Name of the virtualised compute resource.
   * @return computeName
  **/
  @ApiModelProperty(required = true, value = "Name of the virtualised compute resource.")
  public String getComputeName() {
    return computeName;
  }

  public void setComputeName(String computeName) {
    this.computeName = computeName;
  }

  public VIMVirtualCompute flavourId(String flavourId) {
    this.flavourId = flavourId;
    return this;
  }

   /**
   * Identifier of the given compute flavour used to instantiate this virtual compute.
   * @return flavourId
  **/
  @ApiModelProperty(required = true, value = "Identifier of the given compute flavour used to instantiate this virtual compute.")
  public String getFlavourId() {
    return flavourId;
  }

  public void setFlavourId(String flavourId) {
    this.flavourId = flavourId;
  }

  public VIMVirtualCompute hostId(String hostId) {
    this.hostId = hostId;
    return this;
  }

   /**
   * Identifier of the host the virtualised compute resource is allocated on.
   * @return hostId
  **/
  @ApiModelProperty(required = true, value = "Identifier of the host the virtualised compute resource is allocated on.")
  public String getHostId() {
    return hostId;
  }

  public void setHostId(String hostId) {
    this.hostId = hostId;
  }

  public VIMVirtualCompute operationalState(String operationalState) {
    this.operationalState = operationalState;
    return this;
  }

   /**
   * Operational state of the compute resource.
   * @return operationalState
  **/
  @ApiModelProperty(required = true, value = "Operational state of the compute resource.")
  public String getOperationalState() {
    return operationalState;
  }

  public void setOperationalState(String operationalState) {
    this.operationalState = operationalState;
  }

  public VIMVirtualCompute vcImageId(String vcImageId) {
    this.vcImageId = vcImageId;
    return this;
  }

   /**
   * Identifier of the virtualisation container software image (e.g. virtual machine image). Cardinality can be 0 if an \&quot;empty\&quot; virtualisation container is allocated.
   * @return vcImageId
  **/
  @ApiModelProperty(required = true, value = "Identifier of the virtualisation container software image (e.g. virtual machine image). Cardinality can be 0 if an \"empty\" virtualisation container is allocated.")
  public String getVcImageId() {
    return vcImageId;
  }

  public void setVcImageId(String vcImageId) {
    this.vcImageId = vcImageId;
  }

  public VIMVirtualCompute virtualCpu(VirtualComputeVirtualCpu virtualCpu) {
    this.virtualCpu = virtualCpu;
    return this;
  }

   /**
   * Get virtualCpu
   * @return virtualCpu
  **/
  @ApiModelProperty(required = true, value = "")
  public VirtualComputeVirtualCpu getVirtualCpu() {
    return virtualCpu;
  }

  public void setVirtualCpu(VirtualComputeVirtualCpu virtualCpu) {
    this.virtualCpu = virtualCpu;
  }

  public VIMVirtualCompute virtualDisks(String virtualDisks) {
    this.virtualDisks = virtualDisks;
    return this;
  }

   /**
   * Element with information of the virtualised storage resources (volumes, ephemeral that are attached to the compute resource.)
   * @return virtualDisks
  **/
  @ApiModelProperty(required = true, value = "Element with information of the virtualised storage resources (volumes, ephemeral that are attached to the compute resource.)")
  public String getVirtualDisks() {
    return virtualDisks;
  }

  public void setVirtualDisks(String virtualDisks) {
    this.virtualDisks = virtualDisks;
  }

  public VIMVirtualCompute virtualMemory(VirtualComputeVirtualMemory virtualMemory) {
    this.virtualMemory = virtualMemory;
    return this;
  }

   /**
   * Get virtualMemory
   * @return virtualMemory
  **/
  @ApiModelProperty(required = true, value = "")
  public VirtualComputeVirtualMemory getVirtualMemory() {
    return virtualMemory;
  }

  public void setVirtualMemory(VirtualComputeVirtualMemory virtualMemory) {
    this.virtualMemory = virtualMemory;
  }

  public VIMVirtualCompute virtualNetworkInterface(List<ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface> virtualNetworkInterface) {
    this.virtualNetworkInterface = virtualNetworkInterface;
    return this;
  }

  public VIMVirtualCompute addVirtualNetworkInterfaceItem(ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface virtualNetworkInterfaceItem) {
    this.virtualNetworkInterface.add(virtualNetworkInterfaceItem);
    return this;
  }

   /**
   * Element with information of the instantiated virtual network interfaces of the compute resource.
   * @return virtualNetworkInterface
  **/
  @ApiModelProperty(required = true, value = "Element with information of the instantiated virtual network interfaces of the compute resource.")
  public List<ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface> getVirtualNetworkInterface() {
    return virtualNetworkInterface;
  }

  public void setVirtualNetworkInterface(List<ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface> virtualNetworkInterface) {
    this.virtualNetworkInterface = virtualNetworkInterface;
  }

  public VIMVirtualCompute zoneId(String zoneId) {
    this.zoneId = zoneId;
    return this;
  }

   /**
   * If present, it identifies the Resource Zone where the virtual compute resources have been allocated.
   * @return zoneId
  **/
  @ApiModelProperty(required = true, value = "If present, it identifies the Resource Zone where the virtual compute resources have been allocated.")
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
    VIMVirtualCompute viMVirtualCompute = (VIMVirtualCompute) o;
    return Objects.equals(this.accelerationCapability, viMVirtualCompute.accelerationCapability) &&
        Objects.equals(this.computeId, viMVirtualCompute.computeId) &&
        Objects.equals(this.computeName, viMVirtualCompute.computeName) &&
        Objects.equals(this.flavourId, viMVirtualCompute.flavourId) &&
        Objects.equals(this.hostId, viMVirtualCompute.hostId) &&
        Objects.equals(this.operationalState, viMVirtualCompute.operationalState) &&
        Objects.equals(this.vcImageId, viMVirtualCompute.vcImageId) &&
        Objects.equals(this.virtualCpu, viMVirtualCompute.virtualCpu) &&
        Objects.equals(this.virtualDisks, viMVirtualCompute.virtualDisks) &&
        Objects.equals(this.virtualMemory, viMVirtualCompute.virtualMemory) &&
        Objects.equals(this.virtualNetworkInterface, viMVirtualCompute.virtualNetworkInterface) &&
        Objects.equals(this.zoneId, viMVirtualCompute.zoneId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accelerationCapability, computeId, computeName, flavourId, hostId, operationalState, vcImageId, virtualCpu, virtualDisks, virtualMemory, virtualNetworkInterface, zoneId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VIMVirtualCompute {\n");
    
    sb.append("    accelerationCapability: ").append(toIndentedString(accelerationCapability)).append("\n");
    sb.append("    computeId: ").append(toIndentedString(computeId)).append("\n");
    sb.append("    computeName: ").append(toIndentedString(computeName)).append("\n");
    sb.append("    flavourId: ").append(toIndentedString(flavourId)).append("\n");
    sb.append("    hostId: ").append(toIndentedString(hostId)).append("\n");
    sb.append("    operationalState: ").append(toIndentedString(operationalState)).append("\n");
    sb.append("    vcImageId: ").append(toIndentedString(vcImageId)).append("\n");
    sb.append("    virtualCpu: ").append(toIndentedString(virtualCpu)).append("\n");
    sb.append("    virtualDisks: ").append(toIndentedString(virtualDisks)).append("\n");
    sb.append("    virtualMemory: ").append(toIndentedString(virtualMemory)).append("\n");
    sb.append("    virtualNetworkInterface: ").append(toIndentedString(virtualNetworkInterface)).append("\n");
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

