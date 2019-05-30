package com.ericsson.dummyplugin.nbi.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;


public class TerminateAppInsRequest   {
  
  private @Valid List<String> appInstanceId = new ArrayList<String>();

public enum TerminationTypeEnum {

    FORCEFUL(String.valueOf("FORCEFUL")), GRACEFUL(String.valueOf("GRACEFUL"));


    private String value;

    TerminationTypeEnum (String v) {
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
    public static TerminationTypeEnum fromValue(String v) {
        for (TerminationTypeEnum b : TerminationTypeEnum.values()) {
            if (String.valueOf(b.value).equals(v)) {
                return b;
            }
        }
        return null;
    }
}

  private @Valid List<TerminationTypeEnum> terminationType = new ArrayList<TerminationTypeEnum>();
  private @Valid List<BigDecimal> gracefulTerminationTimeout = new ArrayList<BigDecimal>();

  /**
   * Identifier(s) of the mobile edge application instance to be terminated.
   **/
  public TerminateAppInsRequest appInstanceId(List<String> appInstanceId) {
    this.appInstanceId = appInstanceId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Identifier(s) of the mobile edge application instance to be terminated.")
  @JsonProperty("appInstanceId")
  @NotNull
  public List<String> getAppInstanceId() {
    return appInstanceId;
  }
  public void setAppInstanceId(List<String> appInstanceId) {
    this.appInstanceId = appInstanceId;
  }

  /**
   * Signals whether FORCEFUL or GRACEFUL termination is requested. In case of FORCEFUL termination, the mobile edge application is shut down immediately, and resources are released. Note that if the mobile edge application is still in service, this may adversely impact user experience. In case of GRACEFUL termination, the mobile edge system gives time to the mobile edge application for application level termination (e.g. via Mp1 interaction). Once this was successful, or after a timeout, the mobile edge system shuts down the mobile edge application and releases the resources. If the mobile edge application does not support Mp1, the terminationType shall be set to FORCEFUL termination.
   **/
  public TerminateAppInsRequest terminationType(List<TerminationTypeEnum> terminationType) {
    this.terminationType = terminationType;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "Signals whether FORCEFUL or GRACEFUL termination is requested. In case of FORCEFUL termination, the mobile edge application is shut down immediately, and resources are released. Note that if the mobile edge application is still in service, this may adversely impact user experience. In case of GRACEFUL termination, the mobile edge system gives time to the mobile edge application for application level termination (e.g. via Mp1 interaction). Once this was successful, or after a timeout, the mobile edge system shuts down the mobile edge application and releases the resources. If the mobile edge application does not support Mp1, the terminationType shall be set to FORCEFUL termination.")
  @JsonProperty("terminationType")
  @NotNull
  public List<TerminationTypeEnum> getTerminationType() {
    return terminationType;
  }
  public void setTerminationType(List<TerminationTypeEnum> terminationType) {
    this.terminationType = terminationType;
  }

  /**
   * The time interval given to the mobile edge application for application level termination during graceful termination, before shutting down the mobile edge application and releasing the resources. If not given, it is expected that the mobile edge system waits for the successful application level termination, no matter how long it takes, before shutting down the mobile edge application and releasing the resources. Minimum timeout or timeout range are specified by the application vendor defined in the AppD. (Not relevant in case of forceful termination.)
   **/
  public TerminateAppInsRequest gracefulTerminationTimeout(List<BigDecimal> gracefulTerminationTimeout) {
    this.gracefulTerminationTimeout = gracefulTerminationTimeout;
    return this;
  }

  
  @ApiModelProperty(value = "The time interval given to the mobile edge application for application level termination during graceful termination, before shutting down the mobile edge application and releasing the resources. If not given, it is expected that the mobile edge system waits for the successful application level termination, no matter how long it takes, before shutting down the mobile edge application and releasing the resources. Minimum timeout or timeout range are specified by the application vendor defined in the AppD. (Not relevant in case of forceful termination.)")
  @JsonProperty("gracefulTerminationTimeout")
  public List<BigDecimal> getGracefulTerminationTimeout() {
    return gracefulTerminationTimeout;
  }
  public void setGracefulTerminationTimeout(List<BigDecimal> gracefulTerminationTimeout) {
    this.gracefulTerminationTimeout = gracefulTerminationTimeout;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TerminateAppInsRequest terminateAppInsRequest = (TerminateAppInsRequest) o;
    return Objects.equals(appInstanceId, terminateAppInsRequest.appInstanceId) &&
        Objects.equals(terminationType, terminateAppInsRequest.terminationType) &&
        Objects.equals(gracefulTerminationTimeout, terminateAppInsRequest.gracefulTerminationTimeout);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appInstanceId, terminationType, gracefulTerminationTimeout);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TerminateAppInsRequest {\n");
    
    sb.append("    appInstanceId: ").append(toIndentedString(appInstanceId)).append("\n");
    sb.append("    terminationType: ").append(toIndentedString(terminationType)).append("\n");
    sb.append("    gracefulTerminationTimeout: ").append(toIndentedString(gracefulTerminationTimeout)).append("\n");
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

