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
 * on 2015-09-15 at 23:00:09 UTC 
 * Modify at your own risk.
 */

package com.yapnak.gcmbackend.clientEndpointApi.model;

/**
 * Model definition for ClientOfferListEntity.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the clientEndpointApi. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class ClientOfferListEntity extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String message;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<ClientOfferEntity> offerList;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String status;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getMessage() {
    return message;
  }

  /**
   * @param message message or {@code null} for none
   */
  public ClientOfferListEntity setMessage(java.lang.String message) {
    this.message = message;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<ClientOfferEntity> getOfferList() {
    return offerList;
  }

  /**
   * @param offerList offerList or {@code null} for none
   */
  public ClientOfferListEntity setOfferList(java.util.List<ClientOfferEntity> offerList) {
    this.offerList = offerList;
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
  public ClientOfferListEntity setStatus(java.lang.String status) {
    this.status = status;
    return this;
  }

  @Override
  public ClientOfferListEntity set(String fieldName, Object value) {
    return (ClientOfferListEntity) super.set(fieldName, value);
  }

  @Override
  public ClientOfferListEntity clone() {
    return (ClientOfferListEntity) super.clone();
  }

}
