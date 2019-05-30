package com.ericsson.dummyplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import com.ericsson.dummyplugin.nbi.swagger.model.AppD;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Input parameters for the application package onboarding operation. MEC 010 does not include the appD in the request. Rather, it includes a URL where the MEO can download the full application package. Here, we drop the package URL and include the appD directly in the body of the request.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
@ApiModel(description = "Input parameters for the application package onboarding operation. MEC 010 does not include the appD in the request. Rather, it includes a URL where the MEO can download the full application package. Here, we drop the package URL and include the appD directly in the body of the request.")

public class OnboardAppPkgRequest   {
  
  private @Valid String name = null;
  private @Valid String version = null;
  private @Valid String provider = null;
  private @Valid String checksum = null;
  private @Valid Object userDefinedData = null;
  private @Valid AppD appD = null;

  /**
   * Name of the application package to be onboarded.
   **/
  public OnboardAppPkgRequest name(String name) {
    this.name = name;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Name of the application package to be onboarded.")
  @JsonProperty("name")
  @NotNull
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Version of the application package to be onboarded.
   **/
  public OnboardAppPkgRequest version(String version) {
    this.version = version;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Version of the application package to be onboarded.")
  @JsonProperty("version")
  @NotNull
  public String getVersion() {
    return version;
  }
  public void setVersion(String version) {
    this.version = version;
  }

  /**
   * Provider of the application package to be onboarded.
   **/
  public OnboardAppPkgRequest provider(String provider) {
    this.provider = provider;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Provider of the application package to be onboarded.")
  @JsonProperty("provider")
  @NotNull
  public String getProvider() {
    return provider;
  }
  public void setProvider(String provider) {
    this.provider = provider;
  }

  /**
   * Checksum of the onboarded application package.
   **/
  public OnboardAppPkgRequest checksum(String checksum) {
    this.checksum = checksum;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Checksum of the onboarded application package.")
  @JsonProperty("checksum")
  @NotNull
  public String getChecksum() {
    return checksum;
  }
  public void setChecksum(String checksum) {
    this.checksum = checksum;
  }

  /**
   * User defined data for the application package. Not specified in ETSI MEC 010.
   **/
  public OnboardAppPkgRequest userDefinedData(Object userDefinedData) {
    this.userDefinedData = userDefinedData;
    return this;
  }

  
  @ApiModelProperty(value = "User defined data for the application package. Not specified in ETSI MEC 010.")
  @JsonProperty("userDefinedData")
  public Object getUserDefinedData() {
    return userDefinedData;
  }
  public void setUserDefinedData(Object userDefinedData) {
    this.userDefinedData = userDefinedData;
  }

  /**
   **/
  public OnboardAppPkgRequest appD(AppD appD) {
    this.appD = appD;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("appD")
  @NotNull
  public AppD getAppD() {
    return appD;
  }
  public void setAppD(AppD appD) {
    this.appD = appD;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OnboardAppPkgRequest onboardAppPkgRequest = (OnboardAppPkgRequest) o;
    return Objects.equals(name, onboardAppPkgRequest.name) &&
        Objects.equals(version, onboardAppPkgRequest.version) &&
        Objects.equals(provider, onboardAppPkgRequest.provider) &&
        Objects.equals(checksum, onboardAppPkgRequest.checksum) &&
        Objects.equals(userDefinedData, onboardAppPkgRequest.userDefinedData) &&
        Objects.equals(appD, onboardAppPkgRequest.appD);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, version, provider, checksum, userDefinedData, appD);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OnboardAppPkgRequest {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    provider: ").append(toIndentedString(provider)).append("\n");
    sb.append("    checksum: ").append(toIndentedString(checksum)).append("\n");
    sb.append("    userDefinedData: ").append(toIndentedString(userDefinedData)).append("\n");
    sb.append("    appD: ").append(toIndentedString(appD)).append("\n");
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

