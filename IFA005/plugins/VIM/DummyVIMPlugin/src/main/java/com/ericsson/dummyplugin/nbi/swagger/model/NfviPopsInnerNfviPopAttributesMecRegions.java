package com.ericsson.dummyplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import com.ericsson.dummyplugin.nbi.swagger.model.NfviPopsInnerNfviPopAttributesLocationInfo;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Information about Mec Specific parameters.  
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Information about Mec Specific parameters.  ")

public class NfviPopsInnerNfviPopAttributesMecRegions   {
  
  private @Valid String regionId = null;
  private @Valid NfviPopsInnerNfviPopAttributesLocationInfo locationInfo = null;

  /**
   * Region Identifier supported by MEC PoP
   **/
  public NfviPopsInnerNfviPopAttributesMecRegions regionId(String regionId) {
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
  public NfviPopsInnerNfviPopAttributesMecRegions locationInfo(NfviPopsInnerNfviPopAttributesLocationInfo locationInfo) {
    this.locationInfo = locationInfo;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("LocationInfo")
  @NotNull
  public NfviPopsInnerNfviPopAttributesLocationInfo getLocationInfo() {
    return locationInfo;
  }
  public void setLocationInfo(NfviPopsInnerNfviPopAttributesLocationInfo locationInfo) {
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
    NfviPopsInnerNfviPopAttributesMecRegions nfviPopsInnerNfviPopAttributesMecRegions = (NfviPopsInnerNfviPopAttributesMecRegions) o;
    return Objects.equals(regionId, nfviPopsInnerNfviPopAttributesMecRegions.regionId) &&
        Objects.equals(locationInfo, nfviPopsInnerNfviPopAttributesMecRegions.locationInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(regionId, locationInfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NfviPopsInnerNfviPopAttributesMecRegions {\n");
    
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

