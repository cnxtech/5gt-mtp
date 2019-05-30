package com.ericsson.radioplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import com.ericsson.radioplugin.nbi.swagger.model.MECRegionInfoMECRegionInfo;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Information about a region.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Information about a region.")

public class MECRegionInfo   {
  
  private @Valid MECRegionInfoMECRegionInfo meCRegionInfo = null;

  /**
   **/
  public MECRegionInfo meCRegionInfo(MECRegionInfoMECRegionInfo meCRegionInfo) {
    this.meCRegionInfo = meCRegionInfo;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("MECRegionInfo")
  public MECRegionInfoMECRegionInfo getMeCRegionInfo() {
    return meCRegionInfo;
  }
  public void setMeCRegionInfo(MECRegionInfoMECRegionInfo meCRegionInfo) {
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
    MECRegionInfo meCRegionInfo = (MECRegionInfo) o;
    return Objects.equals(meCRegionInfo, meCRegionInfo.meCRegionInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(meCRegionInfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MECRegionInfo {\n");
    
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

