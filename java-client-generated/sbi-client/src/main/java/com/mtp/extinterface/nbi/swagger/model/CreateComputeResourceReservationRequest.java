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
import com.mtp.extinterface.nbi.swagger.model.AllocateComputeRequestAffinityOrAntiAffinityConstraints;
import com.mtp.extinterface.nbi.swagger.model.CreateComputeResourceReservationRequestComputePoolReservation;
import com.mtp.extinterface.nbi.swagger.model.CreateComputeResourceReservationRequestVirtualisationContainerReservation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.threeten.bp.OffsetDateTime;

/**
 * CreateComputeResourceReservationRequest
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-05-28T11:44:14.596Z")
public class CreateComputeResourceReservationRequest {
  @SerializedName("affinityConstraint")
  private List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> affinityConstraint = new ArrayList<AllocateComputeRequestAffinityOrAntiAffinityConstraints>();

  @SerializedName("antiAffinityConstraint")
  private List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> antiAffinityConstraint = new ArrayList<AllocateComputeRequestAffinityOrAntiAffinityConstraints>();

  @SerializedName("computePoolReservation")
  private CreateComputeResourceReservationRequestComputePoolReservation computePoolReservation = null;

  @SerializedName("endTime")
  private OffsetDateTime endTime = null;

  @SerializedName("expiryTime")
  private OffsetDateTime expiryTime = null;

  @SerializedName("locationConstraints")
  private String locationConstraints = null;

  @SerializedName("resourceGroupId")
  private String resourceGroupId = null;

  @SerializedName("startTime")
  private OffsetDateTime startTime = null;

  @SerializedName("virtualisationContainerReservation")
  private List<CreateComputeResourceReservationRequestVirtualisationContainerReservation> virtualisationContainerReservation = new ArrayList<CreateComputeResourceReservationRequestVirtualisationContainerReservation>();

  public CreateComputeResourceReservationRequest affinityConstraint(List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> affinityConstraint) {
    this.affinityConstraint = affinityConstraint;
    return this;
  }

  public CreateComputeResourceReservationRequest addAffinityConstraintItem(AllocateComputeRequestAffinityOrAntiAffinityConstraints affinityConstraintItem) {
    this.affinityConstraint.add(affinityConstraintItem);
    return this;
  }

   /**
   * Element with affinity information of the virtualised compute resources to reserve. For the resource reservation at resource pool granularity level, it defines the affinity information of the virtual compute pool resources to reserve. For the resource reservation at virtual container granularity level, it defines the affinity information of the virtualisation container(s) to reserve.
   * @return affinityConstraint
  **/
  @ApiModelProperty(required = true, value = "Element with affinity information of the virtualised compute resources to reserve. For the resource reservation at resource pool granularity level, it defines the affinity information of the virtual compute pool resources to reserve. For the resource reservation at virtual container granularity level, it defines the affinity information of the virtualisation container(s) to reserve.")
  public List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> getAffinityConstraint() {
    return affinityConstraint;
  }

  public void setAffinityConstraint(List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> affinityConstraint) {
    this.affinityConstraint = affinityConstraint;
  }

  public CreateComputeResourceReservationRequest antiAffinityConstraint(List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> antiAffinityConstraint) {
    this.antiAffinityConstraint = antiAffinityConstraint;
    return this;
  }

  public CreateComputeResourceReservationRequest addAntiAffinityConstraintItem(AllocateComputeRequestAffinityOrAntiAffinityConstraints antiAffinityConstraintItem) {
    this.antiAffinityConstraint.add(antiAffinityConstraintItem);
    return this;
  }

   /**
   * Element with anti-affinity information of the virtualised compute resources to reserve. For the resource reservation at resource pool granularity level, it defines the anti-affinity information of the virtual compute pool resources to reserve. For the resource reservation at virtual container granularity level, it defines the anti-affinity information of the virtualisation container(s) to reserve.
   * @return antiAffinityConstraint
  **/
  @ApiModelProperty(required = true, value = "Element with anti-affinity information of the virtualised compute resources to reserve. For the resource reservation at resource pool granularity level, it defines the anti-affinity information of the virtual compute pool resources to reserve. For the resource reservation at virtual container granularity level, it defines the anti-affinity information of the virtualisation container(s) to reserve.")
  public List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> getAntiAffinityConstraint() {
    return antiAffinityConstraint;
  }

  public void setAntiAffinityConstraint(List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> antiAffinityConstraint) {
    this.antiAffinityConstraint = antiAffinityConstraint;
  }

  public CreateComputeResourceReservationRequest computePoolReservation(CreateComputeResourceReservationRequestComputePoolReservation computePoolReservation) {
    this.computePoolReservation = computePoolReservation;
    return this;
  }

   /**
   * Get computePoolReservation
   * @return computePoolReservation
  **/
  @ApiModelProperty(required = true, value = "")
  public CreateComputeResourceReservationRequestComputePoolReservation getComputePoolReservation() {
    return computePoolReservation;
  }

  public void setComputePoolReservation(CreateComputeResourceReservationRequestComputePoolReservation computePoolReservation) {
    this.computePoolReservation = computePoolReservation;
  }

  public CreateComputeResourceReservationRequest endTime(OffsetDateTime endTime) {
    this.endTime = endTime;
    return this;
  }

   /**
   * Indication when the reservation ends (when the issuer of the request expects that the resources will no longer be needed) and used by the VIM to schedule the reservation. If not present, resources are reserved for unlimited usage time.
   * @return endTime
  **/
  @ApiModelProperty(required = true, value = "Indication when the reservation ends (when the issuer of the request expects that the resources will no longer be needed) and used by the VIM to schedule the reservation. If not present, resources are reserved for unlimited usage time.")
  public OffsetDateTime getEndTime() {
    return endTime;
  }

  public void setEndTime(OffsetDateTime endTime) {
    this.endTime = endTime;
  }

  public CreateComputeResourceReservationRequest expiryTime(OffsetDateTime expiryTime) {
    this.expiryTime = expiryTime;
    return this;
  }

   /**
   * Indication when the VIM can release the reservation in case no allocation request against this reservation was made.
   * @return expiryTime
  **/
  @ApiModelProperty(required = true, value = "Indication when the VIM can release the reservation in case no allocation request against this reservation was made.")
  public OffsetDateTime getExpiryTime() {
    return expiryTime;
  }

  public void setExpiryTime(OffsetDateTime expiryTime) {
    this.expiryTime = expiryTime;
  }

  public CreateComputeResourceReservationRequest locationConstraints(String locationConstraints) {
    this.locationConstraints = locationConstraints;
    return this;
  }

   /**
   * If present, it defines location constraints for the resource(s) is (are) requested to be reserved, e.g. in what particular Resource Zone.
   * @return locationConstraints
  **/
  @ApiModelProperty(required = true, value = "If present, it defines location constraints for the resource(s) is (are) requested to be reserved, e.g. in what particular Resource Zone.")
  public String getLocationConstraints() {
    return locationConstraints;
  }

  public void setLocationConstraints(String locationConstraints) {
    this.locationConstraints = locationConstraints;
  }

  public CreateComputeResourceReservationRequest resourceGroupId(String resourceGroupId) {
    this.resourceGroupId = resourceGroupId;
    return this;
  }

   /**
   * Unique identifier of the \&quot;infrastructure resource group\&quot;, logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.
   * @return resourceGroupId
  **/
  @ApiModelProperty(required = true, value = "Unique identifier of the \"infrastructure resource group\", logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.")
  public String getResourceGroupId() {
    return resourceGroupId;
  }

  public void setResourceGroupId(String resourceGroupId) {
    this.resourceGroupId = resourceGroupId;
  }

  public CreateComputeResourceReservationRequest startTime(OffsetDateTime startTime) {
    this.startTime = startTime;
    return this;
  }

   /**
   * Indication when the consumption of the resources starts. If the value is 0, resources are reserved for immediate use.
   * @return startTime
  **/
  @ApiModelProperty(required = true, value = "Indication when the consumption of the resources starts. If the value is 0, resources are reserved for immediate use.")
  public OffsetDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(OffsetDateTime startTime) {
    this.startTime = startTime;
  }

  public CreateComputeResourceReservationRequest virtualisationContainerReservation(List<CreateComputeResourceReservationRequestVirtualisationContainerReservation> virtualisationContainerReservation) {
    this.virtualisationContainerReservation = virtualisationContainerReservation;
    return this;
  }

  public CreateComputeResourceReservationRequest addVirtualisationContainerReservationItem(CreateComputeResourceReservationRequestVirtualisationContainerReservation virtualisationContainerReservationItem) {
    this.virtualisationContainerReservation.add(virtualisationContainerReservationItem);
    return this;
  }

   /**
   * Virtualisation containers that need to be reserved (e.g. following a specific compute \&quot;flavour\&quot;)
   * @return virtualisationContainerReservation
  **/
  @ApiModelProperty(required = true, value = "Virtualisation containers that need to be reserved (e.g. following a specific compute \"flavour\")")
  public List<CreateComputeResourceReservationRequestVirtualisationContainerReservation> getVirtualisationContainerReservation() {
    return virtualisationContainerReservation;
  }

  public void setVirtualisationContainerReservation(List<CreateComputeResourceReservationRequestVirtualisationContainerReservation> virtualisationContainerReservation) {
    this.virtualisationContainerReservation = virtualisationContainerReservation;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateComputeResourceReservationRequest createComputeResourceReservationRequest = (CreateComputeResourceReservationRequest) o;
    return Objects.equals(this.affinityConstraint, createComputeResourceReservationRequest.affinityConstraint) &&
        Objects.equals(this.antiAffinityConstraint, createComputeResourceReservationRequest.antiAffinityConstraint) &&
        Objects.equals(this.computePoolReservation, createComputeResourceReservationRequest.computePoolReservation) &&
        Objects.equals(this.endTime, createComputeResourceReservationRequest.endTime) &&
        Objects.equals(this.expiryTime, createComputeResourceReservationRequest.expiryTime) &&
        Objects.equals(this.locationConstraints, createComputeResourceReservationRequest.locationConstraints) &&
        Objects.equals(this.resourceGroupId, createComputeResourceReservationRequest.resourceGroupId) &&
        Objects.equals(this.startTime, createComputeResourceReservationRequest.startTime) &&
        Objects.equals(this.virtualisationContainerReservation, createComputeResourceReservationRequest.virtualisationContainerReservation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(affinityConstraint, antiAffinityConstraint, computePoolReservation, endTime, expiryTime, locationConstraints, resourceGroupId, startTime, virtualisationContainerReservation);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateComputeResourceReservationRequest {\n");
    
    sb.append("    affinityConstraint: ").append(toIndentedString(affinityConstraint)).append("\n");
    sb.append("    antiAffinityConstraint: ").append(toIndentedString(antiAffinityConstraint)).append("\n");
    sb.append("    computePoolReservation: ").append(toIndentedString(computePoolReservation)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    expiryTime: ").append(toIndentedString(expiryTime)).append("\n");
    sb.append("    locationConstraints: ").append(toIndentedString(locationConstraints)).append("\n");
    sb.append("    resourceGroupId: ").append(toIndentedString(resourceGroupId)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    virtualisationContainerReservation: ").append(toIndentedString(virtualisationContainerReservation)).append("\n");
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

