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
 * on 2015-09-18 at 12:07:01 UTC 
 * Modify at your own risk.
 */

package com.yapnak.gcmbackend.userEndpointApi.model;

/**
 * Model definition for OfferListEntity.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the userEndpointApi. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class OfferListEntity extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean foundOffers;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String message;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<OfferEntity> offerList;

  static {
    // hack to force ProGuard to consider OfferEntity used, since otherwise it would be stripped out
    // see http://code.google.com/p/google-api-java-client/issues/detail?id=528
    com.google.api.client.util.Data.nullOf(OfferEntity.class);
  }

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String status;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getFoundOffers() {
    return foundOffers;
  }

  /**
   * @param foundOffers foundOffers or {@code null} for none
   */
  public OfferListEntity setFoundOffers(java.lang.Boolean foundOffers) {
    this.foundOffers = foundOffers;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getMessage() {
    return message;
  }

  /**
   * @param message message or {@code null} for none
   */
  public OfferListEntity setMessage(java.lang.String message) {
    this.message = message;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<OfferEntity> getOfferList() {
    return offerList;
  }

  /**
   * @param offerList offerList or {@code null} for none
   */
  public OfferListEntity setOfferList(java.util.List<OfferEntity> offerList) {
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
  public OfferListEntity setStatus(java.lang.String status) {
    this.status = status;
    return this;
  }

  @Override
  public OfferListEntity set(String fieldName, Object value) {
    return (OfferListEntity) super.set(fieldName, value);
  }

  @Override
  public OfferListEntity clone() {
    return (OfferListEntity) super.clone();
  }

}
