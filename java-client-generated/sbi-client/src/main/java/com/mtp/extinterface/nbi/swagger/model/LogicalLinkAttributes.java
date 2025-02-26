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
 * 5GT - inter-Nfvi-Pop connectivity link.
 */
@ApiModel(description = "5GT - inter-Nfvi-Pop connectivity link.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-05-28T11:44:14.596Z")
public class LogicalLinkAttributes {
  @SerializedName("logicalLinkId")
  private String logicalLinkId = null;

  @SerializedName("srcGwIpAddress")
  private String srcGwIpAddress = null;

  @SerializedName("localLinkId")
  private Integer localLinkId = null;

  @SerializedName("dstGwIpAddress")
  private String dstGwIpAddress = null;

  @SerializedName("remoteLinkId")
  private Integer remoteLinkId = null;

  public LogicalLinkAttributes logicalLinkId(String logicalLinkId) {
    this.logicalLinkId = logicalLinkId;
    return this;
  }

   /**
   * (numbered) Identifier of the logical link
   * @return logicalLinkId
  **/
  @ApiModelProperty(required = true, value = "(numbered) Identifier of the logical link")
  public String getLogicalLinkId() {
    return logicalLinkId;
  }

  public void setLogicalLinkId(String logicalLinkId) {
    this.logicalLinkId = logicalLinkId;
  }

  public LogicalLinkAttributes srcGwIpAddress(String srcGwIpAddress) {
    this.srcGwIpAddress = srcGwIpAddress;
    return this;
  }

   /**
   * 5GT - Source NFVI-PoP Gw IPv4 Address in terms of A.B.C.D (/32).
   * @return srcGwIpAddress
  **/
  @ApiModelProperty(required = true, value = "5GT - Source NFVI-PoP Gw IPv4 Address in terms of A.B.C.D (/32).")
  public String getSrcGwIpAddress() {
    return srcGwIpAddress;
  }

  public void setSrcGwIpAddress(String srcGwIpAddress) {
    this.srcGwIpAddress = srcGwIpAddress;
  }

  public LogicalLinkAttributes localLinkId(Integer localLinkId) {
    this.localLinkId = localLinkId;
    return this;
  }

   /**
   * Local Logical Link Id.
   * @return localLinkId
  **/
  @ApiModelProperty(required = true, value = "Local Logical Link Id.")
  public Integer getLocalLinkId() {
    return localLinkId;
  }

  public void setLocalLinkId(Integer localLinkId) {
    this.localLinkId = localLinkId;
  }

  public LogicalLinkAttributes dstGwIpAddress(String dstGwIpAddress) {
    this.dstGwIpAddress = dstGwIpAddress;
    return this;
  }

   /**
   * 5GT - Destination NFVI-PoP Gw IPv4 Address in terms of A.B.C.D (/32).
   * @return dstGwIpAddress
  **/
  @ApiModelProperty(required = true, value = "5GT - Destination NFVI-PoP Gw IPv4 Address in terms of A.B.C.D (/32).")
  public String getDstGwIpAddress() {
    return dstGwIpAddress;
  }

  public void setDstGwIpAddress(String dstGwIpAddress) {
    this.dstGwIpAddress = dstGwIpAddress;
  }

  public LogicalLinkAttributes remoteLinkId(Integer remoteLinkId) {
    this.remoteLinkId = remoteLinkId;
    return this;
  }

   /**
   * Remote Logical Link Id.
   * @return remoteLinkId
  **/
  @ApiModelProperty(required = true, value = "Remote Logical Link Id.")
  public Integer getRemoteLinkId() {
    return remoteLinkId;
  }

  public void setRemoteLinkId(Integer remoteLinkId) {
    this.remoteLinkId = remoteLinkId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LogicalLinkAttributes logicalLinkAttributes = (LogicalLinkAttributes) o;
    return Objects.equals(this.logicalLinkId, logicalLinkAttributes.logicalLinkId) &&
        Objects.equals(this.srcGwIpAddress, logicalLinkAttributes.srcGwIpAddress) &&
        Objects.equals(this.localLinkId, logicalLinkAttributes.localLinkId) &&
        Objects.equals(this.dstGwIpAddress, logicalLinkAttributes.dstGwIpAddress) &&
        Objects.equals(this.remoteLinkId, logicalLinkAttributes.remoteLinkId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(logicalLinkId, srcGwIpAddress, localLinkId, dstGwIpAddress, remoteLinkId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LogicalLinkAttributes {\n");
    
    sb.append("    logicalLinkId: ").append(toIndentedString(logicalLinkId)).append("\n");
    sb.append("    srcGwIpAddress: ").append(toIndentedString(srcGwIpAddress)).append("\n");
    sb.append("    localLinkId: ").append(toIndentedString(localLinkId)).append("\n");
    sb.append("    dstGwIpAddress: ").append(toIndentedString(dstGwIpAddress)).append("\n");
    sb.append("    remoteLinkId: ").append(toIndentedString(remoteLinkId)).append("\n");
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

