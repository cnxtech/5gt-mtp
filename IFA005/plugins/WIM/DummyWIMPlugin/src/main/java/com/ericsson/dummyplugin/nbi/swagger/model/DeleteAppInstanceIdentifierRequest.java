package com.ericsson.dummyplugin.nbi.swagger.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class DeleteAppInstanceIdentifierRequest   {
  
  private @Valid String appInstanceId = null;

  /**
   * Application instance identifier to be deleted.
   **/
  public DeleteAppInstanceIdentifierRequest appInstanceId(String appInstanceId) {
    this.appInstanceId = appInstanceId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Application instance identifier to be deleted.")
  @JsonProperty("appInstanceId")
  @NotNull
  public String getAppInstanceId() {
    return appInstanceId;
  }
  public void setAppInstanceId(String appInstanceId) {
    this.appInstanceId = appInstanceId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeleteAppInstanceIdentifierRequest deleteAppInstanceIdentifierRequest = (DeleteAppInstanceIdentifierRequest) o;
    return Objects.equals(appInstanceId, deleteAppInstanceIdentifierRequest.appInstanceId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appInstanceId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeleteAppInstanceIdentifierRequest {\n");
    
    sb.append("    appInstanceId: ").append(toIndentedString(appInstanceId)).append("\n");
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

