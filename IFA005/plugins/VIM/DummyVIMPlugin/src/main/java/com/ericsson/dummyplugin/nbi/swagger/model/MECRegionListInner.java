package com.ericsson.dummyplugin.nbi.swagger.model;

import com.ericsson.dummyplugin.nbi.swagger.model.MECRegionListInnerMECRegionInfo;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class MECRegionListInner   {
  
  private @Valid MECRegionListInnerMECRegionInfo meCRegionInfo = null;

  /**
   **/
  public MECRegionListInner meCRegionInfo(MECRegionListInnerMECRegionInfo meCRegionInfo) {
    this.meCRegionInfo = meCRegionInfo;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("MECRegionInfo")
  @NotNull
  public MECRegionListInnerMECRegionInfo getMeCRegionInfo() {
    return meCRegionInfo;
  }
  public void setMeCRegionInfo(MECRegionListInnerMECRegionInfo meCRegionInfo) {
    this.meCRegionInfo = meCRegionInfo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MECRegionListInner meCRegionListInner = (MECRegionListInner) o;
    return Objects.equals(meCRegionInfo, meCRegionListInner.meCRegionInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(meCRegionInfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MECRegionListInner {\n");
    
    sb.append("    meCRegionInfo: ").append(toIndentedString(meCRegionInfo)).append("\n");
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

