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
 * on 2015-10-07 at 22:09:58 UTC 
 * Modify at your own risk.
 */

package com.yapnak.gcmbackend.sQLEntityApi.model;

/**
 * Model definition for UserEntity.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the sQLEntityApi. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class UserEntity extends com.google.api.client.json.GenericJson {

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
  @com.google.api.client.util.Key
  private java.lang.String mobNo;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String userID;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getEmail() {
    return email;
  }

  /**
   * @param email email or {@code null} for none
   */
  public UserEntity setEmail(java.lang.String email) {
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
  public UserEntity setFirstName(java.lang.String firstName) {
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
  public UserEntity setLastName(java.lang.String lastName) {
    this.lastName = lastName;
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
  public UserEntity setMobNo(java.lang.String mobNo) {
    this.mobNo = mobNo;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getUserID() {
    return userID;
  }

  /**
   * @param userID userID or {@code null} for none
   */
  public UserEntity setUserID(java.lang.String userID) {
    this.userID = userID;
    return this;
  }

  @Override
  public UserEntity set(String fieldName, Object value) {
    return (UserEntity) super.set(fieldName, value);
  }

  @Override
  public UserEntity clone() {
    return (UserEntity) super.clone();
  }

}
