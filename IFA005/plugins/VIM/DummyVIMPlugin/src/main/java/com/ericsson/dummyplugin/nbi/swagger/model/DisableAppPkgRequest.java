package com.ericsson.dummyplugin.nbi.swagger.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class DisableAppPkgRequest   {
  
  private @Valid String onboardedAppPkgId = null;

  /**
   * Application package identifier to be disabled.
   **/
  public DisableAppPkgRequest onboardedAppPkgId(String onboardedAppPkgId) {
    this.onboardedAppPkgId = onboardedAppPkgId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Application package identifier to be disabled.")
  @JsonProperty("onboardedAppPkgId")
  @NotNull
  public String getOnboardedAppPkgId() {
    return onboardedAppPkgId;
  }
  public void setOnboardedAppPkgId(String onboardedAppPkgId) {
    this.onboardedAppPkgId = onboardedAppPkgId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DisableAppPkgRequest disableAppPkgRequest = (DisableAppPkgRequest) o;
    return Objects.equals(onboardedAppPkgId, disableAppPkgRequest.onboardedAppPkgId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(onboardedAppPkgId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DisableAppPkgRequest {\n");
    
    sb.append("    onboardedAppPkgId: ").append(toIndentedString(onboardedAppPkgId)).append("\n");
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

