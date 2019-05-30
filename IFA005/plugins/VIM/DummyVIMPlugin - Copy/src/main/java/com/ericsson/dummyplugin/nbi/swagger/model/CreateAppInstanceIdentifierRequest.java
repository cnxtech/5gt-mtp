package com.ericsson.dummyplugin.nbi.swagger.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class CreateAppInstanceIdentifierRequest   {
  
  private @Valid String appDId = null;
  private @Valid String appInstanceName = null;
  private @Valid String appInstanceDescription = null;

  /**
   * Identifier that identifies the application package that will be instantiated. The application package is identified by the appDId of the application descriptor included in the package.
   **/
  public CreateAppInstanceIdentifierRequest appDId(String appDId) {
    this.appDId = appDId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier that identifies the application package that will be instantiated. The application package is identified by the appDId of the application descriptor included in the package.")
  @JsonProperty("appDId")
  @NotNull
  public String getAppDId() {
    return appDId;
  }
  public void setAppDId(String appDId) {
    this.appDId = appDId;
  }

  /**
   * Human-readable name of the application instance to be created.
   **/
  public CreateAppInstanceIdentifierRequest appInstanceName(String appInstanceName) {
    this.appInstanceName = appInstanceName;
    return this;
  }

  
  @ApiModelProperty(value = "Human-readable name of the application instance to be created.")
  @JsonProperty("appInstanceName")
  public String getAppInstanceName() {
    return appInstanceName;
  }
  public void setAppInstanceName(String appInstanceName) {
    this.appInstanceName = appInstanceName;
  }

  /**
   * Human-readable description of the application instance to be created.
   **/
  public CreateAppInstanceIdentifierRequest appInstanceDescription(String appInstanceDescription) {
    this.appInstanceDescription = appInstanceDescription;
    return this;
  }

  
  @ApiModelProperty(value = "Human-readable description of the application instance to be created.")
  @JsonProperty("appInstanceDescription")
  public String getAppInstanceDescription() {
    return appInstanceDescription;
  }
  public void setAppInstanceDescription(String appInstanceDescription) {
    this.appInstanceDescription = appInstanceDescription;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateAppInstanceIdentifierRequest createAppInstanceIdentifierRequest = (CreateAppInstanceIdentifierRequest) o;
    return Objects.equals(appDId, createAppInstanceIdentifierRequest.appDId) &&
        Objects.equals(appInstanceName, createAppInstanceIdentifierRequest.appInstanceName) &&
        Objects.equals(appInstanceDescription, createAppInstanceIdentifierRequest.appInstanceDescription);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appDId, appInstanceName, appInstanceDescription);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateAppInstanceIdentifierRequest {\n");
    
    sb.append("    appDId: ").append(toIndentedString(appDId)).append("\n");
    sb.append("    appInstanceName: ").append(toIndentedString(appInstanceName)).append("\n");
    sb.append("    appInstanceDescription: ").append(toIndentedString(appInstanceDescription)).append("\n");
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

