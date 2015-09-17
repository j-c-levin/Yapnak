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
 * on 2015-09-16 at 00:10:30 UTC 
 * Modify at your own risk.
 */

package com.yapnak.gcmbackend.userEndpointApi.model;

/**
 * Model definition for UserDetailsEntity.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the userEndpointApi. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class UserDetailsEntity extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime dateOfBirth;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String email;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String firstName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String lastName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long loyaltyPoints;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String message;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String mobNo;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String status;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String userId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String userImage;

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getDateOfBirth() {
    return dateOfBirth;
  }

  /**
   * @param dateOfBirth dateOfBirth or {@code null} for none
   */
  public UserDetailsEntity setDateOfBirth(com.google.api.client.util.DateTime dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
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
  public UserDetailsEntity setEmail(java.lang.String email) {
    this.email = email;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName firstName or {@code null} for none
   */
  public UserDetailsEntity setFirstName(java.lang.String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getLastName() {
    return lastName;
  }

  /**
   * @param lastName lastName or {@code null} for none
   */
  public UserDetailsEntity setLastName(java.lang.String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getLoyaltyPoints() {
    return loyaltyPoints;
  }

  /**
   * @param loyaltyPoints loyaltyPoints or {@code null} for none
   */
  public UserDetailsEntity setLoyaltyPoints(java.lang.Long loyaltyPoints) {
    this.loyaltyPoints = loyaltyPoints;
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
  public UserDetailsEntity setMessage(java.lang.String message) {
    this.message = message;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getMobNo() {
    return mobNo;
  }

  /**
   * @param mobNo mobNo or {@code null} for none
   */
  public UserDetailsEntity setMobNo(java.lang.String mobNo) {
    this.mobNo = mobNo;
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
  public UserDetailsEntity setStatus(java.lang.String status) {
    this.status = status;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getUserId() {
    return userId;
  }

  /**
   * @param userId userId or {@code null} for none
   */
  public UserDetailsEntity setUserId(java.lang.String userId) {
    this.userId = userId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getUserImage() {
    return userImage;
  }

  /**
   * @param userImage userImage or {@code null} for none
   */
  public UserDetailsEntity setUserImage(java.lang.String userImage) {
    this.userImage = userImage;
    return this;
  }

  @Override
  public UserDetailsEntity set(String fieldName, Object value) {
    return (UserDetailsEntity) super.set(fieldName, value);
  }

  @Override
  public UserDetailsEntity clone() {
    return (UserDetailsEntity) super.clone();
  }

}
