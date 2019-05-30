package com.ericsson.dummyplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import com.ericsson.dummyplugin.nbi.swagger.model.MECRegionListInnerMECRegionInfoLocationInfo;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * 5GT - Element providing information for MEC Region supported by PoP.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "5GT - Element providing information for MEC Region supported by PoP.")

public class MECRegionListInnerMECRegionInfo   {
  
  private @Valid String regionId = null;
  private @Valid MECRegionListInnerMECRegionInfoLocationInfo locationInfo = null;

  /**
   * Region Identifier supported by MEC PoP
   **/
  public MECRegionListInnerMECRegionInfo regionId(String regionId) {
    this.regionId = regionId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Region Identifier supported by MEC PoP")
  @JsonProperty("RegionId")
  @NotNull
  public String getRegionId() {
    return regionId;
  }
  public void setRegionId(String regionId) {
    this.regionId = regionId;
  }

  /**
   **/
  public MECRegionListInnerMECRegionInfo locationInfo(MECRegionListInnerMECRegionInfoLocationInfo locationInfo) {
    this.locationInfo = locationInfo;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("LocationInfo")
  @NotNull
  public MECRegionListInnerMECRegionInfoLocationInfo getLocationInfo() {
    return locationInfo;
  }
  public void setLocationInfo(MECRegionListInnerMECRegionInfoLocationInfo locationInfo) {
    this.locationInfo = locationInfo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MECRegionListInnerMECRegionInfo meCRegionListInnerMECRegionInfo = (MECRegionListInnerMECRegionInfo) o;
    return Objects.equals(regionId, meCRegionListInnerMECRegionInfo.regionId) &&
        Objects.equals(locationInfo, meCRegionListInnerMECRegionInfo.locationInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(regionId, locationInfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MECRegionListInnerMECRegionInfo {\n");
    
    sb.append("    regionId: ").append(toIndentedString(regionId)).append("\n");
    sb.append("    locationInfo: ").append(toIndentedString(locationInfo)).append("\n");
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

