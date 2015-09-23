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
 * on 2015-09-21 at 15:42:25 UTC 
 * Modify at your own risk.
 */

package com.yapnak.gcmbackend.clientEndpointApi.model;

/**
 * Model definition for RedemptionEntity.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the clientEndpointApi. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class RedemptionEntity extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long loyaltyPoints;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String loyaltyRedeemedLevel;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String message;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String offerText;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long recommended;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String status;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getLoyaltyPoints() {
    return loyaltyPoints;
  }

  /**
   * @param loyaltyPoints loyaltyPoints or {@code null} for none
   */
  public RedemptionEntity setLoyaltyPoints(java.lang.Long loyaltyPoints) {
    this.loyaltyPoints = loyaltyPoints;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getLoyaltyRedeemedLevel() {
    return loyaltyRedeemedLevel;
  }

  /**
   * @param loyaltyRedeemedLevel loyaltyRedeemedLevel or {@code null} for none
   */
  public RedemptionEntity setLoyaltyRedeemedLevel(java.lang.String loyaltyRedeemedLevel) {
    this.loyaltyRedeemedLevel = loyaltyRedeemedLevel;
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
  public RedemptionEntity setMessage(java.lang.String message) {
    this.message = message;
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
  public RedemptionEntity setOfferText(java.lang.String offerText) {
    this.offerText = offerText;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getRecommended() {
    return recommended;
  }

  /**
   * @param recommended recommended or {@code null} for none
   */
  public RedemptionEntity setRecommended(java.lang.Long recommended) {
    this.recommended = recommended;
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
  public RedemptionEntity setStatus(java.lang.String status) {
    this.status = status;
    return this;
  }

  @Override
  public RedemptionEntity set(String fieldName, Object value) {
    return (RedemptionEntity) super.set(fieldName, value);
  }

  @Override
  public RedemptionEntity clone() {
    return (RedemptionEntity) super.clone();
  }

}
