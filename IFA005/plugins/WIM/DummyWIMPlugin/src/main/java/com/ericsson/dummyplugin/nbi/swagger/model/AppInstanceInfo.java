package com.ericsson.dummyplugin.nbi.swagger.model;

import io.swagger.annotations.ApiModel;
import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Information about an application instance. This is not specified in ETSI MEC 010. It can be included in the response of an instance query operation.
 **/
import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
@ApiModel(description = "Information about an application instance. This is not specified in ETSI MEC 010. It can be included in the response of an instance query operation.")

public class AppInstanceInfo   {
  
  private @Valid String appInstanceId = null;
  private @Valid AppD appD = null;

public enum AppInstanceStatusEnum {

    NOT_INSTANTITATED(String.valueOf("NOT_INSTANTITATED")), STARTED(String.valueOf("STARTED")), STOPPED(String.valueOf("STOPPED"));


    private String value;

    AppInstanceStatusEnum (String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static AppInstanceStatusEnum fromValue(String v) {
        for (AppInstanceStatusEnum b : AppInstanceStatusEnum.values()) {
            if (String.valueOf(b.value).equals(v)) {
                return b;
            }
        }
        return null;
    }
}

  private @Valid AppInstanceStatusEnum appInstanceStatus = null;

  /**
   * Application instance id.
   **/
  public AppInstanceInfo appInstanceId(String appInstanceId) {
    this.appInstanceId = appInstanceId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Application instance id.")
  @JsonProperty("appInstanceId")
  @NotNull
  public String getAppInstanceId() {
    return appInstanceId;
  }
  public void setAppInstanceId(String appInstanceId) {
    this.appInstanceId = appInstanceId;
  }

  /**
   **/
  public AppInstanceInfo appD(AppD appD) {
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

  /**
   * Status of the instance.
   **/
  public AppInstanceInfo appInstanceStatus(AppInstanceStatusEnum appInstanceStatus) {
    this.appInstanceStatus = appInstanceStatus;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Status of the instance.")
  @JsonProperty("appInstanceStatus")
  @NotNull
  public AppInstanceStatusEnum getAppInstanceStatus() {
    return appInstanceStatus;
  }
  public void setAppInstanceStatus(AppInstanceStatusEnum appInstanceStatus) {
    this.appInstanceStatus = appInstanceStatus;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AppInstanceInfo appInstanceInfo = (AppInstanceInfo) o;
    return Objects.equals(appInstanceId, appInstanceInfo.appInstanceId) &&
        Objects.equals(appD, appInstanceInfo.appD) &&
        Objects.equals(appInstanceStatus, appInstanceInfo.appInstanceStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appInstanceId, appD, appInstanceStatus);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AppInstanceInfo {\n");
    
    sb.append("    appInstanceId: ").append(toIndentedString(appInstanceId)).append("\n");
    sb.append("    appD: ").append(toIndentedString(appD)).append("\n");
    sb.append("    appInstanceStatus: ").append(toIndentedString(appInstanceStatus)).append("\n");
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

