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
 * on 2015-09-15 at 13:10:51 UTC 
 * Modify at your own risk.
 */

package com.yapnak.gcmbackend.userEndpointApi.model;

/**
 * Model definition for OfferEntity.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the userEndpointApi. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class OfferEntity extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long clientId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String clientName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String clientPhoto;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String foodStyle;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double latitude;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double longitude;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String message;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long offerId;

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
  public java.lang.Long getClientId() {
    return clientId;
  }

  /**
   * @param clientId clientId or {@code null} for none
   */
  public OfferEntity setClientId(java.lang.Long clientId) {
    this.clientId = clientId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getClientName() {
    return clientName;
  }

  /**
   * @param clientName clientName or {@code null} for none
   */
  public OfferEntity setClientName(java.lang.String clientName) {
    this.clientName = clientName;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getClientPhoto() {
    return clientPhoto;
  }

  /**
   * @param clientPhoto clientPhoto or {@code null} for none
   */
  public OfferEntity setClientPhoto(java.lang.String clientPhoto) {
    this.clientPhoto = clientPhoto;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getFoodStyle() {
    return foodStyle;
  }

  /**
   * @param foodStyle foodStyle or {@code null} for none
   */
  public OfferEntity setFoodStyle(java.lang.String foodStyle) {
    this.foodStyle = foodStyle;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getLatitude() {
    return latitude;
  }

  /**
   * @param latitude latitude or {@code null} for none
   */
  public OfferEntity setLatitude(java.lang.Double latitude) {
    this.latitude = latitude;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getLongitude() {
    return longitude;
  }

  /**
   * @param longitude longitude or {@code null} for none
   */
  public OfferEntity setLongitude(java.lang.Double longitude) {
    this.longitude = longitude;
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
  public OfferEntity setMessage(java.lang.String message) {
    this.message = message;
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
  public OfferEntity setOfferId(java.lang.Long offerId) {
    this.offerId = offerId;
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
  public OfferEntity setOfferText(java.lang.String offerText) {
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
  public OfferEntity setStatus(java.lang.String status) {
    this.status = status;
    return this;
  }

  @Override
  public OfferEntity set(String fieldName, Object value) {
    return (OfferEntity) super.set(fieldName, value);
  }

  @Override
  public OfferEntity clone() {
    return (OfferEntity) super.clone();
  }

}
