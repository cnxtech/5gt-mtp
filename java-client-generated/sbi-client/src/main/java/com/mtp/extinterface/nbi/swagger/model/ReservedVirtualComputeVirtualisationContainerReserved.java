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
import com.mtp.extinterface.nbi.swagger.model.CreateComputeResourceReservationRequestContainerFlavourStorageAttributes;
import com.mtp.extinterface.nbi.swagger.model.ReservedVirtualComputeVirtualisationContainerReservedFlavourId;
import com.mtp.extinterface.nbi.swagger.model.ReservedVirtualComputeVirtualisationContainerReservedVirtualCpu;
import com.mtp.extinterface.nbi.swagger.model.ReservedVirtualComputeVirtualisationContainerReservedVirtualMemory;
import com.mtp.extinterface.nbi.swagger.model.ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Information about the virtualisation container(s) that have been reserved.
 */
@ApiModel(description = "Information about the virtualisation container(s) that have been reserved.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-05-28T11:44:14.596Z")
public class ReservedVirtualComputeVirtualisationContainerReserved {
  @SerializedName("accelerationCapability")
  private String accelerationCapability = null;

  @SerializedName("containerId")
  private String containerId = null;

  @SerializedName("flavourId")
  private List<ReservedVirtualComputeVirtualisationContainerReservedFlavourId> flavourId = new ArrayList<ReservedVirtualComputeVirtualisationContainerReservedFlavourId>();

  @SerializedName("virtualCpu")
  private ReservedVirtualComputeVirtualisationContainerReservedVirtualCpu virtualCpu = null;

  @SerializedName("virtualDisks")
  private List<CreateComputeResourceReservationRequestContainerFlavourStorageAttributes> virtualDisks = new ArrayList<CreateComputeResourceReservationRequestContainerFlavourStorageAttributes>();

  @SerializedName("virtualMemory")
  private ReservedVirtualComputeVirtualisationContainerReservedVirtualMemory virtualMemory = null;

  @SerializedName("virtualNetworkInterface")
  private List<ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface> virtualNetworkInterface = new ArrayList<ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface>();

  @SerializedName("zoneId")
  private String zoneId = null;

  public ReservedVirtualComputeVirtualisationContainerReserved accelerationCapability(String accelerationCapability) {
    this.accelerationCapability = accelerationCapability;
    return this;
  }

   /**
   * Selected acceleration capabilities (e.g. crypto, GPU) from the set of capabilities offered by the compute node acceleration resources. The cardinality can be 0, if no particular acceleration capability is provided.
   * @return accelerationCapability
  **/
  @ApiModelProperty(required = true, value = "Selected acceleration capabilities (e.g. crypto, GPU) from the set of capabilities offered by the compute node acceleration resources. The cardinality can be 0, if no particular acceleration capability is provided.")
  public String getAccelerationCapability() {
    return accelerationCapability;
  }

  public void setAccelerationCapability(String accelerationCapability) {
    this.accelerationCapability = accelerationCapability;
  }

  public ReservedVirtualComputeVirtualisationContainerReserved containerId(String containerId) {
    this.containerId = containerId;
    return this;
  }

   /**
   * The identifier of the virtualisation container that has been reserved.
   * @return containerId
  **/
  @ApiModelProperty(required = true, value = "The identifier of the virtualisation container that has been reserved.")
  public String getContainerId() {
    return containerId;
  }

  public void setContainerId(String containerId) {
    this.containerId = containerId;
  }

  public ReservedVirtualComputeVirtualisationContainerReserved flavourId(List<ReservedVirtualComputeVirtualisationContainerReservedFlavourId> flavourId) {
    this.flavourId = flavourId;
    return this;
  }

  public ReservedVirtualComputeVirtualisationContainerReserved addFlavourIdItem(ReservedVirtualComputeVirtualisationContainerReservedFlavourId flavourIdItem) {
    this.flavourId.add(flavourIdItem);
    return this;
  }

   /**
   * Identifier of the given compute flavour used to reserve the virtualisation container.
   * @return flavourId
  **/
  @ApiModelProperty(required = true, value = "Identifier of the given compute flavour used to reserve the virtualisation container.")
  public List<ReservedVirtualComputeVirtualisationContainerReservedFlavourId> getFlavourId() {
    return flavourId;
  }

  public void setFlavourId(List<ReservedVirtualComputeVirtualisationContainerReservedFlavourId> flavourId) {
    this.flavourId = flavourId;
  }

  public ReservedVirtualComputeVirtualisationContainerReserved virtualCpu(ReservedVirtualComputeVirtualisationContainerReservedVirtualCpu virtualCpu) {
    this.virtualCpu = virtualCpu;
    return this;
  }

   /**
   * Get virtualCpu
   * @return virtualCpu
  **/
  @ApiModelProperty(required = true, value = "")
  public ReservedVirtualComputeVirtualisationContainerReservedVirtualCpu getVirtualCpu() {
    return virtualCpu;
  }

  public void setVirtualCpu(ReservedVirtualComputeVirtualisationContainerReservedVirtualCpu virtualCpu) {
    this.virtualCpu = virtualCpu;
  }

  public ReservedVirtualComputeVirtualisationContainerReserved virtualDisks(List<CreateComputeResourceReservationRequestContainerFlavourStorageAttributes> virtualDisks) {
    this.virtualDisks = virtualDisks;
    return this;
  }

  public ReservedVirtualComputeVirtualisationContainerReserved addVirtualDisksItem(CreateComputeResourceReservationRequestContainerFlavourStorageAttributes virtualDisksItem) {
    this.virtualDisks.add(virtualDisksItem);
    return this;
  }

   /**
   * Element with information of the virtualised storage resources attached to the reserved virtualisation container.
   * @return virtualDisks
  **/
  @ApiModelProperty(required = true, value = "Element with information of the virtualised storage resources attached to the reserved virtualisation container.")
  public List<CreateComputeResourceReservationRequestContainerFlavourStorageAttributes> getVirtualDisks() {
    return virtualDisks;
  }

  public void setVirtualDisks(List<CreateComputeResourceReservationRequestContainerFlavourStorageAttributes> virtualDisks) {
    this.virtualDisks = virtualDisks;
  }

  public ReservedVirtualComputeVirtualisationContainerReserved virtualMemory(ReservedVirtualComputeVirtualisationContainerReservedVirtualMemory virtualMemory) {
    this.virtualMemory = virtualMemory;
    return this;
  }

   /**
   * Get virtualMemory
   * @return virtualMemory
  **/
  @ApiModelProperty(required = true, value = "")
  public ReservedVirtualComputeVirtualisationContainerReservedVirtualMemory getVirtualMemory() {
    return virtualMemory;
  }

  public void setVirtualMemory(ReservedVirtualComputeVirtualisationContainerReservedVirtualMemory virtualMemory) {
    this.virtualMemory = virtualMemory;
  }

  public ReservedVirtualComputeVirtualisationContainerReserved virtualNetworkInterface(List<ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface> virtualNetworkInterface) {
    this.virtualNetworkInterface = virtualNetworkInterface;
    return this;
  }

  public ReservedVirtualComputeVirtualisationContainerReserved addVirtualNetworkInterfaceItem(ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface virtualNetworkInterfaceItem) {
    this.virtualNetworkInterface.add(virtualNetworkInterfaceItem);
    return this;
  }

   /**
   * Element with information of the virtual network interfaces of the reserved virtualisation container.
   * @return virtualNetworkInterface
  **/
  @ApiModelProperty(required = true, value = "Element with information of the virtual network interfaces of the reserved virtualisation container.")
  public List<ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface> getVirtualNetworkInterface() {
    return virtualNetworkInterface;
  }

  public void setVirtualNetworkInterface(List<ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface> virtualNetworkInterface) {
    this.virtualNetworkInterface = virtualNetworkInterface;
  }

  public ReservedVirtualComputeVirtualisationContainerReserved zoneId(String zoneId) {
    this.zoneId = zoneId;
    return this;
  }

   /**
   * References the resource zone where the virtualisation container has been reserved. Cardinality can be 0 to cover the case where reserved network resources are not bound to a specific resource zone.
   * @return zoneId
  **/
  @ApiModelProperty(required = true, value = "References the resource zone where the virtualisation container has been reserved. Cardinality can be 0 to cover the case where reserved network resources are not bound to a specific resource zone.")
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
    ReservedVirtualComputeVirtualisationContainerReserved reservedVirtualComputeVirtualisationContainerReserved = (ReservedVirtualComputeVirtualisationContainerReserved) o;
    return Objects.equals(this.accelerationCapability, reservedVirtualComputeVirtualisationContainerReserved.accelerationCapability) &&
        Objects.equals(this.containerId, reservedVirtualComputeVirtualisationContainerReserved.containerId) &&
        Objects.equals(this.flavourId, reservedVirtualComputeVirtualisationContainerReserved.flavourId) &&
        Objects.equals(this.virtualCpu, reservedVirtualComputeVirtualisationContainerReserved.virtualCpu) &&
        Objects.equals(this.virtualDisks, reservedVirtualComputeVirtualisationContainerReserved.virtualDisks) &&
        Objects.equals(this.virtualMemory, reservedVirtualComputeVirtualisationContainerReserved.virtualMemory) &&
        Objects.equals(this.virtualNetworkInterface, reservedVirtualComputeVirtualisationContainerReserved.virtualNetworkInterface) &&
        Objects.equals(this.zoneId, reservedVirtualComputeVirtualisationContainerReserved.zoneId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accelerationCapability, containerId, flavourId, virtualCpu, virtualDisks, virtualMemory, virtualNetworkInterface, zoneId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReservedVirtualComputeVirtualisationContainerReserved {\n");
    
    sb.append("    accelerationCapability: ").append(toIndentedString(accelerationCapability)).append("\n");
    sb.append("    containerId: ").append(toIndentedString(containerId)).append("\n");
    sb.append("    flavourId: ").append(toIndentedString(flavourId)).append("\n");
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

