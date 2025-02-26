package com.mtp.extinterface.nbi.swagger.model;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class VirtualCompute   {
  
  private @Valid List<String> accelerationCapability = new ArrayList<String>();
  private @Valid String computeId = null;
  private @Valid String computeName = null;
  private @Valid String flavourId = null;
  private @Valid String hostId = null;
  private @Valid String operationalState = null;
  private @Valid String vcImageId = null;
  private @Valid VirtualComputeVirtualCpu virtualCpu = null;
  private @Valid String virtualDisks = null;
  private @Valid VirtualComputeVirtualMemory virtualMemory = null;
  private @Valid List<ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface> virtualNetworkInterface = new ArrayList<ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface>();
  private @Valid String zoneId = null;
  private @Valid String meCappID = null;

  /**
   * Selected acceleration capabilities (e.g. crypto, GPU) from the set of capabilities offered by the compute node acceleration resources. The cardinality can be 0, if no particular acceleration capability is provided.
   **/
  public VirtualCompute accelerationCapability(List<String> accelerationCapability) {
    this.accelerationCapability = accelerationCapability;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Selected acceleration capabilities (e.g. crypto, GPU) from the set of capabilities offered by the compute node acceleration resources. The cardinality can be 0, if no particular acceleration capability is provided.")
  @JsonProperty("accelerationCapability")
  @NotNull
  public List<String> getAccelerationCapability() {
    return accelerationCapability;
  }
  public void setAccelerationCapability(List<String> accelerationCapability) {
    this.accelerationCapability = accelerationCapability;
  }

  /**
   * Identifier of the virtualised compute resource.
   **/
  public VirtualCompute computeId(String computeId) {
    this.computeId = computeId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the virtualised compute resource.")
  @JsonProperty("computeId")
  @NotNull
  public String getComputeId() {
    return computeId;
  }
  public void setComputeId(String computeId) {
    this.computeId = computeId;
  }

  /**
   * Name of the virtualised compute resource.
   **/
  public VirtualCompute computeName(String computeName) {
    this.computeName = computeName;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Name of the virtualised compute resource.")
  @JsonProperty("computeName")
  @NotNull
  public String getComputeName() {
    return computeName;
  }
  public void setComputeName(String computeName) {
    this.computeName = computeName;
  }

  /**
   * Identifier of the given compute flavour used to instantiate this virtual compute.
   **/
  public VirtualCompute flavourId(String flavourId) {
    this.flavourId = flavourId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the given compute flavour used to instantiate this virtual compute.")
  @JsonProperty("flavourId")
  @NotNull
  public String getFlavourId() {
    return flavourId;
  }
  public void setFlavourId(String flavourId) {
    this.flavourId = flavourId;
  }

  /**
   * Identifier of the host the virtualised compute resource is allocated on.
   **/
  public VirtualCompute hostId(String hostId) {
    this.hostId = hostId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the host the virtualised compute resource is allocated on.")
  @JsonProperty("hostId")
  @NotNull
  public String getHostId() {
    return hostId;
  }
  public void setHostId(String hostId) {
    this.hostId = hostId;
  }

  /**
   * Operational state of the compute resource.
   **/
  public VirtualCompute operationalState(String operationalState) {
    this.operationalState = operationalState;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Operational state of the compute resource.")
  @JsonProperty("operationalState")
  @NotNull
  public String getOperationalState() {
    return operationalState;
  }
  public void setOperationalState(String operationalState) {
    this.operationalState = operationalState;
  }

  /**
   * Identifier of the virtualisation container software image (e.g. virtual machine image). Cardinality can be 0 if an \&quot;empty\&quot; virtualisation container is allocated.
   **/
  public VirtualCompute vcImageId(String vcImageId) {
    this.vcImageId = vcImageId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the virtualisation container software image (e.g. virtual machine image). Cardinality can be 0 if an \"empty\" virtualisation container is allocated.")
  @JsonProperty("vcImageId")
  @NotNull
  public String getVcImageId() {
    return vcImageId;
  }
  public void setVcImageId(String vcImageId) {
    this.vcImageId = vcImageId;
  }

  /**
   **/
  public VirtualCompute virtualCpu(VirtualComputeVirtualCpu virtualCpu) {
    this.virtualCpu = virtualCpu;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("virtualCpu")
  @NotNull
  public VirtualComputeVirtualCpu getVirtualCpu() {
    return virtualCpu;
  }
  public void setVirtualCpu(VirtualComputeVirtualCpu virtualCpu) {
    this.virtualCpu = virtualCpu;
  }

  /**
   * Element with information of the virtualised storage resources (volumes, ephemeral that are attached to the compute resource.)
   **/
  public VirtualCompute virtualDisks(String virtualDisks) {
    this.virtualDisks = virtualDisks;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Element with information of the virtualised storage resources (volumes, ephemeral that are attached to the compute resource.)")
  @JsonProperty("virtualDisks")
  @NotNull
  public String getVirtualDisks() {
    return virtualDisks;
  }
  public void setVirtualDisks(String virtualDisks) {
    this.virtualDisks = virtualDisks;
  }

  /**
   **/
  public VirtualCompute virtualMemory(VirtualComputeVirtualMemory virtualMemory) {
    this.virtualMemory = virtualMemory;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("virtualMemory")
  @NotNull
  public VirtualComputeVirtualMemory getVirtualMemory() {
    return virtualMemory;
  }
  public void setVirtualMemory(VirtualComputeVirtualMemory virtualMemory) {
    this.virtualMemory = virtualMemory;
  }

  /**
   * Element with information of the instantiated virtual network interfaces of the compute resource.
   **/
  public VirtualCompute virtualNetworkInterface(List<ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface> virtualNetworkInterface) {
    this.virtualNetworkInterface = virtualNetworkInterface;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Element with information of the instantiated virtual network interfaces of the compute resource.")
  @JsonProperty("virtualNetworkInterface")
  @NotNull
  public List<ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface> getVirtualNetworkInterface() {
    return virtualNetworkInterface;
  }
  public void setVirtualNetworkInterface(List<ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface> virtualNetworkInterface) {
    this.virtualNetworkInterface = virtualNetworkInterface;
  }

  /**
   * If present, it identifies the Resource Zone where the virtual compute resources have been allocated.
   **/
  public VirtualCompute zoneId(String zoneId) {
    this.zoneId = zoneId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "If present, it identifies the Resource Zone where the virtual compute resources have been allocated.")
  @JsonProperty("zoneId")
  @NotNull
  public String getZoneId() {
    return zoneId;
  }
  public void setZoneId(String zoneId) {
    this.zoneId = zoneId;
  }

  /**
   * If present, it identifies the reference MEC AppD reference Descritptor to apply for the allocated compute resources
   **/
  public VirtualCompute meCappID(String meCappID) {
    this.meCappID = meCappID;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "If present, it identifies the reference MEC AppD reference Descritptor to apply for the allocated compute resources")
  @JsonProperty("MECappID")
  @NotNull
  public String getMeCappID() {
    return meCappID;
  }
  public void setMeCappID(String meCappID) {
    this.meCappID = meCappID;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VirtualCompute virtualCompute = (VirtualCompute) o;
    return Objects.equals(accelerationCapability, virtualCompute.accelerationCapability) &&
        Objects.equals(computeId, virtualCompute.computeId) &&
        Objects.equals(computeName, virtualCompute.computeName) &&
        Objects.equals(flavourId, virtualCompute.flavourId) &&
        Objects.equals(hostId, virtualCompute.hostId) &&
        Objects.equals(operationalState, virtualCompute.operationalState) &&
        Objects.equals(vcImageId, virtualCompute.vcImageId) &&
        Objects.equals(virtualCpu, virtualCompute.virtualCpu) &&
        Objects.equals(virtualDisks, virtualCompute.virtualDisks) &&
        Objects.equals(virtualMemory, virtualCompute.virtualMemory) &&
        Objects.equals(virtualNetworkInterface, virtualCompute.virtualNetworkInterface) &&
        Objects.equals(zoneId, virtualCompute.zoneId) &&
        Objects.equals(meCappID, virtualCompute.meCappID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accelerationCapability, computeId, computeName, flavourId, hostId, operationalState, vcImageId, virtualCpu, virtualDisks, virtualMemory, virtualNetworkInterface, zoneId, meCappID);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VirtualCompute {\n");
    
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
    sb.append("    meCappID: ").append(toIndentedString(meCappID)).append("\n");
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

