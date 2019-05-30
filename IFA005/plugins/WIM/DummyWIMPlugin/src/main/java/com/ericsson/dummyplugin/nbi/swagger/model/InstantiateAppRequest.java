package com.ericsson.dummyplugin.nbi.swagger.model;

import com.ericsson.dummyplugin.nbi.swagger.model.VirtualComputeDescription;
import com.ericsson.dummyplugin.nbi.swagger.model.VirtualStorageDescriptor;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class InstantiateAppRequest   {
  
  private @Valid String appInstanceId = null;
  private @Valid VirtualComputeDescription virtualComputeDescriptor = null;
  private @Valid List<VirtualStorageDescriptor> virtualStorageDescriptor = new ArrayList<VirtualStorageDescriptor>();
  private @Valid Object selectedMEHostInfo = null;

  /**
   * dentifier of the application instance created by Create application instance identifier operation.
   **/
  public InstantiateAppRequest appInstanceId(String appInstanceId) {
    this.appInstanceId = appInstanceId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "dentifier of the application instance created by Create application instance identifier operation.")
  @JsonProperty("appInstanceId")
  @NotNull
  public String getAppInstanceId() {
    return appInstanceId;
  }
  public void setAppInstanceId(String appInstanceId) {
    this.appInstanceId = appInstanceId;
  }

  /**
   **/
  public InstantiateAppRequest virtualComputeDescriptor(VirtualComputeDescription virtualComputeDescriptor) {
    this.virtualComputeDescriptor = virtualComputeDescriptor;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("virtualComputeDescriptor")
  public VirtualComputeDescription getVirtualComputeDescriptor() {
    return virtualComputeDescriptor;
  }
  public void setVirtualComputeDescriptor(VirtualComputeDescription virtualComputeDescriptor) {
    this.virtualComputeDescriptor = virtualComputeDescriptor;
  }

  /**
   **/
  public InstantiateAppRequest virtualStorageDescriptor(List<VirtualStorageDescriptor> virtualStorageDescriptor) {
    this.virtualStorageDescriptor = virtualStorageDescriptor;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("virtualStorageDescriptor")
  public List<VirtualStorageDescriptor> getVirtualStorageDescriptor() {
    return virtualStorageDescriptor;
  }
  public void setVirtualStorageDescriptor(List<VirtualStorageDescriptor> virtualStorageDescriptor) {
    this.virtualStorageDescriptor = virtualStorageDescriptor;
  }

  /**
   * Describes the information of selected mobile edge host for the application instance. The data type is not specified in ETSI MEC 010.
   **/
  public InstantiateAppRequest selectedMEHostInfo(Object selectedMEHostInfo) {
    this.selectedMEHostInfo = selectedMEHostInfo;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Describes the information of selected mobile edge host for the application instance. The data type is not specified in ETSI MEC 010.")
  @JsonProperty("selectedMEHostInfo")
  @NotNull
  public Object getSelectedMEHostInfo() {
    return selectedMEHostInfo;
  }
  public void setSelectedMEHostInfo(Object selectedMEHostInfo) {
    this.selectedMEHostInfo = selectedMEHostInfo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InstantiateAppRequest instantiateAppRequest = (InstantiateAppRequest) o;
    return Objects.equals(appInstanceId, instantiateAppRequest.appInstanceId) &&
        Objects.equals(virtualComputeDescriptor, instantiateAppRequest.virtualComputeDescriptor) &&
        Objects.equals(virtualStorageDescriptor, instantiateAppRequest.virtualStorageDescriptor) &&
        Objects.equals(selectedMEHostInfo, instantiateAppRequest.selectedMEHostInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appInstanceId, virtualComputeDescriptor, virtualStorageDescriptor, selectedMEHostInfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InstantiateAppRequest {\n");
    
    sb.append("    appInstanceId: ").append(toIndentedString(appInstanceId)).append("\n");
    sb.append("    virtualComputeDescriptor: ").append(toIndentedString(virtualComputeDescriptor)).append("\n");
    sb.append("    virtualStorageDescriptor: ").append(toIndentedString(virtualStorageDescriptor)).append("\n");
    sb.append("    selectedMEHostInfo: ").append(toIndentedString(selectedMEHostInfo)).append("\n");
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

