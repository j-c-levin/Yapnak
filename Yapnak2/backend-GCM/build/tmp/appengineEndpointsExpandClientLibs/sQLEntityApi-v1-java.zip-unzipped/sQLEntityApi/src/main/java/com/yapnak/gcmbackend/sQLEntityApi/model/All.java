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
 * on 2015-09-15 at 00:53:09 UTC 
 * Modify at your own risk.
 */

package com.yapnak.gcmbackend.sQLEntityApi.model;

/**
 * Model definition for All.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the sQLEntityApi. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class All extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer admin;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String clientFoodStyle;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer clientID;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String clientName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String clientOffer;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String clientPhoto;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double clientX;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double clientY;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String email;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String password;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double rating;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String salt;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getAdmin() {
    return admin;
  }

  /**
   * @param admin admin or {@code null} for none
   */
  public All setAdmin(java.lang.Integer admin) {
    this.admin = admin;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getClientFoodStyle() {
    return clientFoodStyle;
  }

  /**
   * @param clientFoodStyle clientFoodStyle or {@code null} for none
   */
  public All setClientFoodStyle(java.lang.String clientFoodStyle) {
    this.clientFoodStyle = clientFoodStyle;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getClientID() {
    return clientID;
  }

  /**
   * @param clientID clientID or {@code null} for none
   */
  public All setClientID(java.lang.Integer clientID) {
    this.clientID = clientID;
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
  public All setClientName(java.lang.String clientName) {
    this.clientName = clientName;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getClientOffer() {
    return clientOffer;
  }

  /**
   * @param clientOffer clientOffer or {@code null} for none
   */
  public All setClientOffer(java.lang.String clientOffer) {
    this.clientOffer = clientOffer;
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
  public All setClientPhoto(java.lang.String clientPhoto) {
    this.clientPhoto = clientPhoto;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getClientX() {
    return clientX;
  }

  /**
   * @param clientX clientX or {@code null} for none
   */
  public All setClientX(java.lang.Double clientX) {
    this.clientX = clientX;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getClientY() {
    return clientY;
  }

  /**
   * @param clientY clientY or {@code null} for none
   */
  public All setClientY(java.lang.Double clientY) {
    this.clientY = clientY;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getEmail() {
    return email;
  }

  /**
   * @param email email or {@code null} for none
   */
  public All setEmail(java.lang.String email) {
    this.email = email;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPassword() {
    return password;
  }

  /**
   * @param password password or {@code null} for none
   */
  public All setPassword(java.lang.String password) {
    this.password = password;
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
  public All setRating(java.lang.Double rating) {
    this.rating = rating;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getSalt() {
    return salt;
  }

  /**
   * @param salt salt or {@code null} for none
   */
  public All setSalt(java.lang.String salt) {
    this.salt = salt;
    return this;
  }

  @Override
  public All set(String fieldName, Object value) {
    return (All) super.set(fieldName, value);
  }

  @Override
  public All clone() {
    return (All) super.clone();
  }

}
