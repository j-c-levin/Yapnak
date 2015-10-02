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
 * on 2015-10-01 at 21:31:15 UTC 
 * Modify at your own risk.
 */

package com.yapnak.gcmbackend.sQLEntityApi.model;

/**
 * Model definition for SQLEntity.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the sQLEntityApi. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class SQLEntity extends com.google.api.client.json.GenericJson {

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
  private java.lang.String name;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String offer;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String photo;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer points;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double rating;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer showOffer;

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
  public SQLEntity setFoodStyle(java.lang.String foodStyle) {
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
  public SQLEntity setId(java.lang.Long id) {
    this.id = id;
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
  public SQLEntity setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getOffer() {
    return offer;
  }

  /**
   * @param offer offer or {@code null} for none
   */
  public SQLEntity setOffer(java.lang.String offer) {
    this.offer = offer;
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
  public SQLEntity setPhoto(java.lang.String photo) {
    this.photo = photo;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getPoints() {
    return points;
  }

  /**
   * @param points points or {@code null} for none
   */
  public SQLEntity setPoints(java.lang.Integer points) {
    this.points = points;
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
  public SQLEntity setRating(java.lang.Double rating) {
    this.rating = rating;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getShowOffer() {
    return showOffer;
  }

  /**
   * @param showOffer showOffer or {@code null} for none
   */
  public SQLEntity setShowOffer(java.lang.Integer showOffer) {
    this.showOffer = showOffer;
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
  public SQLEntity setX(java.lang.Double x) {
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
  public SQLEntity setY(java.lang.Double y) {
    this.y = y;
    return this;
  }

  @Override
  public SQLEntity set(String fieldName, Object value) {
    return (SQLEntity) super.set(fieldName, value);
  }

  @Override
  public SQLEntity clone() {
    return (SQLEntity) super.clone();
  }

}
