/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2015-08-03 17:34:38 UTC)
 * on 2015-09-24 at 09:26:24 UTC 
 * Modify at your own risk.
 */

package com.yapnak.gcmbackend.clientEndpointApi.model;

/**
 * Model definition for PhotoEntity.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the clientEndpointApi. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class PhotoEntity extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String message;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String status;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String uploadUrl;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getMessage() {
    return message;
  }

  /**
   * @param message message or {@code null} for none
   */
  public PhotoEntity setMessage(java.lang.String message) {
    this.message = message;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getStatus() {
    return status;
  }

  /**
   * @param status status or {@code null} for none
   */
  public PhotoEntity setStatus(java.lang.String status) {
    this.status = status;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getUploadUrl() {
    return uploadUrl;
  }

  /**
   * @param uploadUrl uploadUrl or {@code null} for none
   */
  public PhotoEntity setUploadUrl(java.lang.String uploadUrl) {
    this.uploadUrl = uploadUrl;
    return this;
  }

  @Override
  public PhotoEntity set(String fieldName, Object value) {
    return (PhotoEntity) super.set(fieldName, value);
  }

  @Override
  public PhotoEntity clone() {
    return (PhotoEntity) super.clone();
  }

}
