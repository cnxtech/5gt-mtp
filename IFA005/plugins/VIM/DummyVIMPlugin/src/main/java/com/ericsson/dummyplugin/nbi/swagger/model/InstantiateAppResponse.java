package com.ericsson.dummyplugin.nbi.swagger.model;

import javax.validation.constraints.*;
import javax.validation.Valid;


import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;


public class InstantiateAppResponse   {
  
  private @Valid String lifecycleOperationOccurenceId = null;

  /**
   * The identifier of the application lifecycle operation occurrence.
   **/
  public InstantiateAppResponse lifecycleOperationOccurenceId(String lifecycleOperationOccurenceId) {
    this.lifecycleOperationOccurenceId = lifecycleOperationOccurenceId;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "The identifier of the application lifecycle operation occurrence.")
  @JsonProperty("lifecycleOperationOccurenceId")
  @NotNull
  public String getLifecycleOperationOccurenceId() {
    return lifecycleOperationOccurenceId;
  }
  public void setLifecycleOperationOccurenceId(String lifecycleOperationOccurenceId) {
    this.lifecycleOperationOccurenceId = lifecycleOperationOccurenceId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InstantiateAppResponse instantiateAppResponse = (InstantiateAppResponse) o;
    return Objects.equals(lifecycleOperationOccurenceId, instantiateAppResponse.lifecycleOperationOccurenceId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lifecycleOperationOccurenceId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InstantiateAppResponse {\n");
    
    sb.append("    lifecycleOperationOccurenceId: ").append(toIndentedString(lifecycleOperationOccurenceId)).append("\n");
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

