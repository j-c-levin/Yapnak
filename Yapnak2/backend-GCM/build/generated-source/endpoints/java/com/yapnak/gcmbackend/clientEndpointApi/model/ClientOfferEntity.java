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
 * on 2015-10-16 at 10:18:43 UTC 
 * Modify at your own risk.
 */

package com.yapnak.gcmbackend.clientEndpointApi.model;

/**
 * Model definition for ClientOfferEntity.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the clientEndpointApi. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class ClientOfferEntity extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String message;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private JSONArray offerDays;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer offerEnd;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long offerId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer offerStart;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String offerText;

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
  public ClientOfferEntity setMessage(java.lang.String message) {
    this.message = message;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public JSONArray getOfferDays() {
    return offerDays;
  }

  /**
   * @param offerDays offerDays or {@code null} for none
   */
  public ClientOfferEntity setOfferDays(JSONArray offerDays) {
    this.offerDays = offerDays;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getOfferEnd() {
    return offerEnd;
  }

  /**
   * @param offerEnd offerEnd or {@code null} for none
   */
  public ClientOfferEntity setOfferEnd(java.lang.Integer offerEnd) {
    this.offerEnd = offerEnd;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getOfferId() {
    return offerId;
  }

  /**
   * @param offerId offerId or {@code null} for none
   */
  public ClientOfferEntity setOfferId(java.lang.Long offerId) {
    this.offerId = offerId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getOfferStart() {
    return offerStart;
  }

  /**
   * @param offerStart offerStart or {@code null} for none
   */
  public ClientOfferEntity setOfferStart(java.lang.Integer offerStart) {
    this.offerStart = offerStart;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getOfferText() {
    return offerText;
  }

  /**
   * @param offerText offerText or {@code null} for none
   */
  public ClientOfferEntity setOfferText(java.lang.String offerText) {
    this.offerText = offerText;
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
  public ClientOfferEntity setStatus(java.lang.String status) {
    this.status = status;
    return this;
  }

  @Override
  public ClientOfferEntity set(String fieldName, Object value) {
    return (ClientOfferEntity) super.set(fieldName, value);
  }

  @Override
  public ClientOfferEntity clone() {
    return (ClientOfferEntity) super.clone();
  }

}
