package com.mtp.extinterface.nbi.swagger.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class MECRegionInfoMECRegionInfo   {
  
  private @Valid String regionId = null;
  private @Valid LocationInfo locationInfo = null;

  /**
   * Identifier of the region.
   **/
  public MECRegionInfoMECRegionInfo regionId(String regionId) {
    this.regionId = regionId;
    return this;
  }

  
  @ApiModelProperty(value = "Identifier of the region.")
  @JsonProperty("RegionId")
  public String getRegionId() {
    return regionId;
  }
  public void setRegionId(String regionId) {
    this.regionId = regionId;
  }

  /**
   **/
  public MECRegionInfoMECRegionInfo locationInfo(LocationInfo locationInfo) {
    this.locationInfo = locationInfo;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("LocationInfo")
  public LocationInfo getLocationInfo() {
    return locationInfo;
  }
  public void setLocationInfo(LocationInfo locationInfo) {
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
    MECRegionInfoMECRegionInfo meCRegionInfoMECRegionInfo = (MECRegionInfoMECRegionInfo) o;
    return Objects.equals(regionId, meCRegionInfoMECRegionInfo.regionId) &&
        Objects.equals(locationInfo, meCRegionInfoMECRegionInfo.locationInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(regionId, locationInfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MECRegionInfoMECRegionInfo {\n");
    
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

