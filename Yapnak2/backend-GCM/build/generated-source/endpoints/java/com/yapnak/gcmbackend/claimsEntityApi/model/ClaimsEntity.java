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
 * (build: 2015-03-26 20:30:19 UTC)
 * on 2015-05-21 at 10:18:56 UTC 
 * Modify at your own risk.
 */

package com.yapnak.gcmbackend.claimsEntityApi.model;

/**
 * Model definition for ClaimsEntity.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the claimsEntityApi. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class ClaimsEntity extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String clientID;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer offerID;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime timestamp;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String userID;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getClientID() {
    return clientID;
  }

  /**
   * @param clientID clientID or {@code null} for none
   */
  public ClaimsEntity setClientID(java.lang.String clientID) {
    this.clientID = clientID;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getOfferID() {
    return offerID;
  }

  /**
   * @param offerID offerID or {@code null} for none
   */
  public ClaimsEntity setOfferID(java.lang.Integer offerID) {
    this.offerID = offerID;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getTimestamp() {
    return timestamp;
  }

  /**
   * @param timestamp timestamp or {@code null} for none
   */
  public ClaimsEntity setTimestamp(com.google.api.client.util.DateTime timestamp) {
    this.timestamp = timestamp;
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
  public ClaimsEntity setUserID(java.lang.String userID) {
    this.userID = userID;
    return this;
  }

  @Override
  public ClaimsEntity set(String fieldName, Object value) {
    return (ClaimsEntity) super.set(fieldName, value);
  }

  @Override
  public ClaimsEntity clone() {
    return (ClaimsEntity) super.clone();
  }

}
