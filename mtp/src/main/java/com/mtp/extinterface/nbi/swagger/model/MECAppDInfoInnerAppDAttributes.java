package com.mtp.extinterface.nbi.swagger.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class MECAppDInfoInnerAppDAttributes   {
  
  private @Valid String apPDInfo = null;
  private @Valid String meCAppDId = null;

  /**
   * It provides information about MEC APPD (TBD)
   **/
  public MECAppDInfoInnerAppDAttributes apPDInfo(String apPDInfo) {
    this.apPDInfo = apPDInfo;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "It provides information about MEC APPD (TBD)")
  @JsonProperty("APPDInfo")
  @NotNull
  public String getApPDInfo() {
    return apPDInfo;
  }
  public void setApPDInfo(String apPDInfo) {
    this.apPDInfo = apPDInfo;
  }

  /**
   * Identification of the MEC APPdId
   **/
  public MECAppDInfoInnerAppDAttributes meCAppDId(String meCAppDId) {
    this.meCAppDId = meCAppDId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identification of the MEC APPdId")
  @JsonProperty("MECAppDId")
  @NotNull
  public String getMeCAppDId() {
    return meCAppDId;
  }
  public void setMeCAppDId(String meCAppDId) {
    this.meCAppDId = meCAppDId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MECAppDInfoInnerAppDAttributes meCAppDInfoInnerAppDAttributes = (MECAppDInfoInnerAppDAttributes) o;
    return Objects.equals(apPDInfo, meCAppDInfoInnerAppDAttributes.apPDInfo) &&
        Objects.equals(meCAppDId, meCAppDInfoInnerAppDAttributes.meCAppDId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(apPDInfo, meCAppDId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MECAppDInfoInnerAppDAttributes {\n");
    
    sb.append("    apPDInfo: ").append(toIndentedString(apPDInfo)).append("\n");
    sb.append("    meCAppDId: ").append(toIndentedString(meCAppDId)).append("\n");
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

