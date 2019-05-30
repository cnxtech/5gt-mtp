package com.ericsson.dummyplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Onboarding operation output parameters.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Onboarding operation output parameters.")

public class OnboardAppPkgResponse   {
  
  private @Valid String onboardedAppPkgId = null;
  private @Valid String appDId = null;

  /**
   * Identifier of the onboarded application package.
   **/
  public OnboardAppPkgResponse onboardedAppPkgId(String onboardedAppPkgId) {
    this.onboardedAppPkgId = onboardedAppPkgId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier of the onboarded application package.")
  @JsonProperty("onboardedAppPkgId")
  @NotNull
  public String getOnboardedAppPkgId() {
    return onboardedAppPkgId;
  }
  public void setOnboardedAppPkgId(String onboardedAppPkgId) {
    this.onboardedAppPkgId = onboardedAppPkgId;
  }

  /**
   * Identifier that identifies the application package in a globally unique way. This is managed by the application provider.
   **/
  public OnboardAppPkgResponse appDId(String appDId) {
    this.appDId = appDId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier that identifies the application package in a globally unique way. This is managed by the application provider.")
  @JsonProperty("appDId")
  @NotNull
  public String getAppDId() {
    return appDId;
  }
  public void setAppDId(String appDId) {
    this.appDId = appDId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OnboardAppPkgResponse onboardAppPkgResponse = (OnboardAppPkgResponse) o;
    return Objects.equals(onboardedAppPkgId, onboardAppPkgResponse.onboardedAppPkgId) &&
        Objects.equals(appDId, onboardAppPkgResponse.appDId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(onboardedAppPkgId, appDId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OnboardAppPkgResponse {\n");
    
    sb.append("    onboardedAppPkgId: ").append(toIndentedString(onboardedAppPkgId)).append("\n");
    sb.append("    appDId: ").append(toIndentedString(appDId)).append("\n");
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

