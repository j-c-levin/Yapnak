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
 * on 2015-05-22 at 12:22:28 UTC 
 * Modify at your own risk.
 */

package com.yapnak.gcmbackend.pointsEntityApi.model;

/**
 * Model definition for PointsEntity.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the pointsEntityApi. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class PointsEntity extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String clientID;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer points;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String referrerID;

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
  public PointsEntity setClientID(java.lang.String clientID) {
    this.clientID = clientID;
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
  public PointsEntity setPoints(java.lang.Integer points) {
    this.points = points;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getReferrerID() {
    return referrerID;
  }

  /**
   * @param referrerID referrerID or {@code null} for none
   */
  public PointsEntity setReferrerID(java.lang.String referrerID) {
    this.referrerID = referrerID;
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
  public PointsEntity setUserID(java.lang.String userID) {
    this.userID = userID;
    return this;
  }

  @Override
  public PointsEntity set(String fieldName, Object value) {
    return (PointsEntity) super.set(fieldName, value);
  }

  @Override
  public PointsEntity clone() {
    return (PointsEntity) super.clone();
  }

}
