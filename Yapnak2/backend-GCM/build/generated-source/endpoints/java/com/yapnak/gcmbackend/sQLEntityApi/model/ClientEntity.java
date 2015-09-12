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
 * on 2015-08-27 at 09:52:47 UTC 
 * Modify at your own risk.
 */

package com.yapnak.gcmbackend.sQLEntityApi.model;

/**
 * Model definition for ClientEntity.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the sQLEntityApi. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class ClientEntity extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String foodStyle;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String message;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String name;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String offer1;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String offer2;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String offer3;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String photo;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double rating;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer showOffer1;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer showOffer2;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer showOffer3;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String status;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double x;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double y;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getFoodStyle() {
    return foodStyle;
  }

  /**
   * @param foodStyle foodStyle or {@code null} for none
   */
  public ClientEntity setFoodStyle(java.lang.String foodStyle) {
    this.foodStyle = foodStyle;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public ClientEntity setId(java.lang.Long id) {
    this.id = id;
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
  public ClientEntity setMessage(java.lang.String message) {
    this.message = message;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getName() {
    return name;
  }

  /**
   * @param name name or {@code null} for none
   */
  public ClientEntity setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getOffer1() {
    return offer1;
  }

  /**
   * @param offer1 offer1 or {@code null} for none
   */
  public ClientEntity setOffer1(java.lang.String offer1) {
    this.offer1 = offer1;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getOffer2() {
    return offer2;
  }

  /**
   * @param offer2 offer2 or {@code null} for none
   */
  public ClientEntity setOffer2(java.lang.String offer2) {
    this.offer2 = offer2;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getOffer3() {
    return offer3;
  }

  /**
   * @param offer3 offer3 or {@code null} for none
   */
  public ClientEntity setOffer3(java.lang.String offer3) {
    this.offer3 = offer3;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPhoto() {
    return photo;
  }

  /**
   * @param photo photo or {@code null} for none
   */
  public ClientEntity setPhoto(java.lang.String photo) {
    this.photo = photo;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getRating() {
    return rating;
  }

  /**
   * @param rating rating or {@code null} for none
   */
  public ClientEntity setRating(java.lang.Double rating) {
    this.rating = rating;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getShowOffer1() {
    return showOffer1;
  }

  /**
   * @param showOffer1 showOffer1 or {@code null} for none
   */
  public ClientEntity setShowOffer1(java.lang.Integer showOffer1) {
    this.showOffer1 = showOffer1;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getShowOffer2() {
    return showOffer2;
  }

  /**
   * @param showOffer2 showOffer2 or {@code null} for none
   */
  public ClientEntity setShowOffer2(java.lang.Integer showOffer2) {
    this.showOffer2 = showOffer2;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getShowOffer3() {
    return showOffer3;
  }

  /**
   * @param showOffer3 showOffer3 or {@code null} for none
   */
  public ClientEntity setShowOffer3(java.lang.Integer showOffer3) {
    this.showOffer3 = showOffer3;
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
  public ClientEntity setStatus(java.lang.String status) {
    this.status = status;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getX() {
    return x;
  }

  /**
   * @param x x or {@code null} for none
   */
  public ClientEntity setX(java.lang.Double x) {
    this.x = x;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getY() {
    return y;
  }

  /**
   * @param y y or {@code null} for none
   */
  public ClientEntity setY(java.lang.Double y) {
    this.y = y;
    return this;
  }

  @Override
  public ClientEntity set(String fieldName, Object value) {
    return (ClientEntity) super.set(fieldName, value);
  }

  @Override
  public ClientEntity clone() {
    return (ClientEntity) super.clone();
  }

}
