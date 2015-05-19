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
 * (build: 2015-05-05 20:00:12 UTC)
 * on 2015-05-19 at 16:41:53 UTC 
 * Modify at your own risk.
 */

package com.yapnak.gcmbackend.clientEntityApi.model;

/**
 * Model definition for ClientEntity.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the clientEntityApi. For a detailed explanation see:
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
  private java.lang.String clientLocation;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String clientName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Float clientX;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Float clientY;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String foodStyle;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String imageLink;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String offer;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getClientLocation() {
    return clientLocation;
  }

  /**
   * @param clientLocation clientLocation or {@code null} for none
   */
  public ClientEntity setClientLocation(java.lang.String clientLocation) {
    this.clientLocation = clientLocation;
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
  public ClientEntity setClientName(java.lang.String clientName) {
    this.clientName = clientName;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Float getClientX() {
    return clientX;
  }

  /**
   * @param clientX clientX or {@code null} for none
   */
  public ClientEntity setClientX(java.lang.Float clientX) {
    this.clientX = clientX;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Float getClientY() {
    return clientY;
  }

  /**
   * @param clientY clientY or {@code null} for none
   */
  public ClientEntity setClientY(java.lang.Float clientY) {
    this.clientY = clientY;
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
  public ClientEntity setFoodStyle(java.lang.String foodStyle) {
    this.foodStyle = foodStyle;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public ClientEntity setId(java.lang.String id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getImageLink() {
    return imageLink;
  }

  /**
   * @param imageLink imageLink or {@code null} for none
   */
  public ClientEntity setImageLink(java.lang.String imageLink) {
    this.imageLink = imageLink;
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
  public ClientEntity setOffer(java.lang.String offer) {
    this.offer = offer;
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
