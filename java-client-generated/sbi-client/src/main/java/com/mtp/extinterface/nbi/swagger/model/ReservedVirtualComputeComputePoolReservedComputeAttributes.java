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
import java.io.IOException;

/**
 * Information specifying additional attributes of the virtual compute resource that have been reserved.
 */
@ApiModel(description = "Information specifying additional attributes of the virtual compute resource that have been reserved.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-05-28T11:44:14.596Z")
public class ReservedVirtualComputeComputePoolReservedComputeAttributes {
  @SerializedName("accelerationCapability")
  private String accelerationCapability = null;

  @SerializedName("cpuArchitecture")
  private String cpuArchitecture = null;

  @SerializedName("virtualCpuOversubscriptionPolicy")
  private String virtualCpuOversubscriptionPolicy = null;

  public ReservedVirtualComputeComputePoolReservedComputeAttributes accelerationCapability(String accelerationCapability) {
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

  public ReservedVirtualComputeComputePoolReservedComputeAttributes cpuArchitecture(String cpuArchitecture) {
    this.cpuArchitecture = cpuArchitecture;
    return this;
  }

   /**
   * CPU architecture type. Examples are \&quot;x86\&quot;, \&quot;ARM\&quot;. The cardinality can be 0, if no particular CPU architecture type is provided.
   * @return cpuArchitecture
  **/
  @ApiModelProperty(required = true, value = "CPU architecture type. Examples are \"x86\", \"ARM\". The cardinality can be 0, if no particular CPU architecture type is provided.")
  public String getCpuArchitecture() {
    return cpuArchitecture;
  }

  public void setCpuArchitecture(String cpuArchitecture) {
    this.cpuArchitecture = cpuArchitecture;
  }

  public ReservedVirtualComputeComputePoolReservedComputeAttributes virtualCpuOversubscriptionPolicy(String virtualCpuOversubscriptionPolicy) {
    this.virtualCpuOversubscriptionPolicy = virtualCpuOversubscriptionPolicy;
    return this;
  }

   /**
   * The CPU core oversubscription policy in terms of virtual CPU cores to physical CPU cores/threads on the platform. The cardinality can be 0, if no particular value is provided.
   * @return virtualCpuOversubscriptionPolicy
  **/
  @ApiModelProperty(required = true, value = "The CPU core oversubscription policy in terms of virtual CPU cores to physical CPU cores/threads on the platform. The cardinality can be 0, if no particular value is provided.")
  public String getVirtualCpuOversubscriptionPolicy() {
    return virtualCpuOversubscriptionPolicy;
  }

  public void setVirtualCpuOversubscriptionPolicy(String virtualCpuOversubscriptionPolicy) {
    this.virtualCpuOversubscriptionPolicy = virtualCpuOversubscriptionPolicy;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReservedVirtualComputeComputePoolReservedComputeAttributes reservedVirtualComputeComputePoolReservedComputeAttributes = (ReservedVirtualComputeComputePoolReservedComputeAttributes) o;
    return Objects.equals(this.accelerationCapability, reservedVirtualComputeComputePoolReservedComputeAttributes.accelerationCapability) &&
        Objects.equals(this.cpuArchitecture, reservedVirtualComputeComputePoolReservedComputeAttributes.cpuArchitecture) &&
        Objects.equals(this.virtualCpuOversubscriptionPolicy, reservedVirtualComputeComputePoolReservedComputeAttributes.virtualCpuOversubscriptionPolicy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accelerationCapability, cpuArchitecture, virtualCpuOversubscriptionPolicy);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReservedVirtualComputeComputePoolReservedComputeAttributes {\n");
    
    sb.append("    accelerationCapability: ").append(toIndentedString(accelerationCapability)).append("\n");
    sb.append("    cpuArchitecture: ").append(toIndentedString(cpuArchitecture)).append("\n");
    sb.append("    virtualCpuOversubscriptionPolicy: ").append(toIndentedString(virtualCpuOversubscriptionPolicy)).append("\n");
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

