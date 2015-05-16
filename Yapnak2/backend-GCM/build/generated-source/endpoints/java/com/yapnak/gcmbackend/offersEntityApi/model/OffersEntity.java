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
 * on 2015-05-16 at 12:06:26 UTC 
 * Modify at your own risk.
 */

package com.yapnak.gcmbackend.offersEntityApi.model;

/**
 * Model definition for OffersEntity.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the offersEntityApi. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class OffersEntity extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String clientID;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime offerEnd;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String offerID;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime offerStart;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String offerText;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getClientID() {
    return clientID;
  }

  /**
   * @param clientID clientID or {@code null} for none
   */
  public OffersEntity setClientID(java.lang.String clientID) {
    this.clientID = clientID;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getOfferEnd() {
    return offerEnd;
  }

  /**
   * @param offerEnd offerEnd or {@code null} for none
   */
  public OffersEntity setOfferEnd(com.google.api.client.util.DateTime offerEnd) {
    this.offerEnd = offerEnd;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getOfferID() {
    return offerID;
  }

  /**
   * @param offerID offerID or {@code null} for none
   */
  public OffersEntity setOfferID(java.lang.String offerID) {
    this.offerID = offerID;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getOfferStart() {
    return offerStart;
  }

  /**
   * @param offerStart offerStart or {@code null} for none
   */
  public OffersEntity setOfferStart(com.google.api.client.util.DateTime offerStart) {
    this.offerStart = offerStart;
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
  public OffersEntity setOfferText(java.lang.String offerText) {
    this.offerText = offerText;
    return this;
  }

  @Override
  public OffersEntity set(String fieldName, Object value) {
    return (OffersEntity) super.set(fieldName, value);
  }

  @Override
  public OffersEntity clone() {
    return (OffersEntity) super.clone();
  }

}
