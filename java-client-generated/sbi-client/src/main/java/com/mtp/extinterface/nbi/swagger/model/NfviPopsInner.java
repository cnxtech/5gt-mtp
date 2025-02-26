/*
 * MTP Manager API
 * MTP Manager API
 *
 * OpenAPI spec version: 2.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.mtp.extinterface.nbi.swagger.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.mtp.extinterface.nbi.swagger.model.NfviPopsInnerNfviPopAttributes;
import java.io.IOException;

/**
 * NfviPopsInner
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-05-28T11:44:14.596Z")
public class NfviPopsInner {
  @SerializedName("nfviPopAttributes")
  private NfviPopsInnerNfviPopAttributes nfviPopAttributes = null;

  public NfviPopsInner nfviPopAttributes(NfviPopsInnerNfviPopAttributes nfviPopAttributes) {
    this.nfviPopAttributes = nfviPopAttributes;
    return this;
  }

   /**
   * Get nfviPopAttributes
   * @return nfviPopAttributes
  **/
  @ApiModelProperty(required = true, value = "")
  public NfviPopsInnerNfviPopAttributes getNfviPopAttributes() {
    return nfviPopAttributes;
  }

  public void setNfviPopAttributes(NfviPopsInnerNfviPopAttributes nfviPopAttributes) {
    this.nfviPopAttributes = nfviPopAttributes;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NfviPopsInner nfviPopsInner = (NfviPopsInner) o;
    return Objects.equals(this.nfviPopAttributes, nfviPopsInner.nfviPopAttributes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nfviPopAttributes);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NfviPopsInner {\n");
    
    sb.append("    nfviPopAttributes: ").append(toIndentedString(nfviPopAttributes)).append("\n");
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

