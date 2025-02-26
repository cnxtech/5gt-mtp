package com.mtp.extinterface.nbi.swagger.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class AllocateComputeRequestInterfaceData   {
  
  private @Valid String ipAddress = null;
  private @Valid String macAddress = null;

  /**
   * The virtual network interface can be configured with specific IP address(es) associated to the network to be attached to. The cardinality can be 0 in the case that a network interface is created without being attached to any specific network, or when an IP address can be automatically configured, e.g. by DHCP.
   **/
  public AllocateComputeRequestInterfaceData ipAddress(String ipAddress) {
    this.ipAddress = ipAddress;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The virtual network interface can be configured with specific IP address(es) associated to the network to be attached to. The cardinality can be 0 in the case that a network interface is created without being attached to any specific network, or when an IP address can be automatically configured, e.g. by DHCP.")
  @JsonProperty("ipAddress")
  @NotNull
  public String getIpAddress() {
    return ipAddress;
  }
  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  /**
   * The MAC address desired for the virtual network interface. The cardinality can be 0 to allow for network interface without specific MAC address configuration.
   **/
  public AllocateComputeRequestInterfaceData macAddress(String macAddress) {
    this.macAddress = macAddress;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The MAC address desired for the virtual network interface. The cardinality can be 0 to allow for network interface without specific MAC address configuration.")
  @JsonProperty("macAddress")
  @NotNull
  public String getMacAddress() {
    return macAddress;
  }
  public void setMacAddress(String macAddress) {
    this.macAddress = macAddress;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AllocateComputeRequestInterfaceData allocateComputeRequestInterfaceData = (AllocateComputeRequestInterfaceData) o;
    return Objects.equals(ipAddress, allocateComputeRequestInterfaceData.ipAddress) &&
        Objects.equals(macAddress, allocateComputeRequestInterfaceData.macAddress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ipAddress, macAddress);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AllocateComputeRequestInterfaceData {\n");
    
    sb.append("    ipAddress: ").append(toIndentedString(ipAddress)).append("\n");
    sb.append("    macAddress: ").append(toIndentedString(macAddress)).append("\n");
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

