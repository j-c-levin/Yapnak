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
 * on 2015-09-18 at 17:36:20 UTC 
 * Modify at your own risk.
 */

package com.yapnak.gcmbackend.clientEndpointApi;

/**
 * Service definition for ClientEndpointApi (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link ClientEndpointApiRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class ClientEndpointApi extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.20.0 of the clientEndpointApi library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://yapnak-app.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "clientEndpointApi/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public ClientEndpointApi(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  ClientEndpointApi(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "authenticateClient".
   *
   * This request holds the parameters needed by the clientEndpointApi server.  After setting any
   * optional parameters, call the {@link AuthenticateClient#execute()} method to invoke the remote
   * operation.
   *
   * @param email
   * @param password
   * @return the request
   */
  public AuthenticateClient authenticateClient(java.lang.String email, java.lang.String password) throws java.io.IOException {
    AuthenticateClient result = new AuthenticateClient(email, password);
    initialize(result);
    return result;
  }

  public class AuthenticateClient extends ClientEndpointApiRequest<com.yapnak.gcmbackend.clientEndpointApi.model.ClientAuthEntity> {

    private static final String REST_PATH = "authenticateClient";

    /**
     * Create a request for the method "authenticateClient".
     *
     * This request holds the parameters needed by the the clientEndpointApi server.  After setting
     * any optional parameters, call the {@link AuthenticateClient#execute()} method to invoke the
     * remote operation. <p> {@link AuthenticateClient#initialize(com.google.api.client.googleapis.ser
     * vices.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param email
     * @param password
     * @since 1.13
     */
    protected AuthenticateClient(java.lang.String email, java.lang.String password) {
      super(ClientEndpointApi.this, "POST", REST_PATH, null, com.yapnak.gcmbackend.clientEndpointApi.model.ClientAuthEntity.class);
      this.email = com.google.api.client.util.Preconditions.checkNotNull(email, "Required parameter email must be specified.");
      this.password = com.google.api.client.util.Preconditions.checkNotNull(password, "Required parameter password must be specified.");
    }

    @Override
    public AuthenticateClient setAlt(java.lang.String alt) {
      return (AuthenticateClient) super.setAlt(alt);
    }

    @Override
    public AuthenticateClient setFields(java.lang.String fields) {
      return (AuthenticateClient) super.setFields(fields);
    }

    @Override
    public AuthenticateClient setKey(java.lang.String key) {
      return (AuthenticateClient) super.setKey(key);
    }

    @Override
    public AuthenticateClient setOauthToken(java.lang.String oauthToken) {
      return (AuthenticateClient) super.setOauthToken(oauthToken);
    }

    @Override
    public AuthenticateClient setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (AuthenticateClient) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public AuthenticateClient setQuotaUser(java.lang.String quotaUser) {
      return (AuthenticateClient) super.setQuotaUser(quotaUser);
    }

    @Override
    public AuthenticateClient setUserIp(java.lang.String userIp) {
      return (AuthenticateClient) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String email;

    /**

     */
    public java.lang.String getEmail() {
      return email;
    }

    public AuthenticateClient setEmail(java.lang.String email) {
      this.email = email;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String password;

    /**

     */
    public java.lang.String getPassword() {
      return password;
    }

    public AuthenticateClient setPassword(java.lang.String password) {
      this.password = password;
      return this;
    }

    @Override
    public AuthenticateClient set(String parameterName, Object value) {
      return (AuthenticateClient) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getAllOffers".
   *
   * This request holds the parameters needed by the clientEndpointApi server.  After setting any
   * optional parameters, call the {@link GetAllOffers#execute()} method to invoke the remote
   * operation.
   *
   * @param clientId
   * @return the request
   */
  public GetAllOffers getAllOffers(java.lang.Integer clientId) throws java.io.IOException {
    GetAllOffers result = new GetAllOffers(clientId);
    initialize(result);
    return result;
  }

  public class GetAllOffers extends ClientEndpointApiRequest<com.yapnak.gcmbackend.clientEndpointApi.model.ClientOfferListEntity> {

    private static final String REST_PATH = "getAllOffers";

    /**
     * Create a request for the method "getAllOffers".
     *
     * This request holds the parameters needed by the the clientEndpointApi server.  After setting
     * any optional parameters, call the {@link GetAllOffers#execute()} method to invoke the remote
     * operation. <p> {@link
     * GetAllOffers#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param clientId
     * @since 1.13
     */
    protected GetAllOffers(java.lang.Integer clientId) {
      super(ClientEndpointApi.this, "GET", REST_PATH, null, com.yapnak.gcmbackend.clientEndpointApi.model.ClientOfferListEntity.class);
      this.clientId = com.google.api.client.util.Preconditions.checkNotNull(clientId, "Required parameter clientId must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetAllOffers setAlt(java.lang.String alt) {
      return (GetAllOffers) super.setAlt(alt);
    }

    @Override
    public GetAllOffers setFields(java.lang.String fields) {
      return (GetAllOffers) super.setFields(fields);
    }

    @Override
    public GetAllOffers setKey(java.lang.String key) {
      return (GetAllOffers) super.setKey(key);
    }

    @Override
    public GetAllOffers setOauthToken(java.lang.String oauthToken) {
      return (GetAllOffers) super.setOauthToken(oauthToken);
    }

    @Override
    public GetAllOffers setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetAllOffers) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetAllOffers setQuotaUser(java.lang.String quotaUser) {
      return (GetAllOffers) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetAllOffers setUserIp(java.lang.String userIp) {
      return (GetAllOffers) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Integer clientId;

    /**

     */
    public java.lang.Integer getClientId() {
      return clientId;
    }

    public GetAllOffers setClientId(java.lang.Integer clientId) {
      this.clientId = clientId;
      return this;
    }

    @Override
    public GetAllOffers set(String parameterName, Object value) {
      return (GetAllOffers) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getClientInfo".
   *
   * This request holds the parameters needed by the clientEndpointApi server.  After setting any
   * optional parameters, call the {@link GetClientInfo#execute()} method to invoke the remote
   * operation.
   *
   * @param email
   * @return the request
   */
  public GetClientInfo getClientInfo(java.lang.String email) throws java.io.IOException {
    GetClientInfo result = new GetClientInfo(email);
    initialize(result);
    return result;
  }

  public class GetClientInfo extends ClientEndpointApiRequest<com.yapnak.gcmbackend.clientEndpointApi.model.ClientEntity> {

    private static final String REST_PATH = "getClientInfo";

    /**
     * Create a request for the method "getClientInfo".
     *
     * This request holds the parameters needed by the the clientEndpointApi server.  After setting
     * any optional parameters, call the {@link GetClientInfo#execute()} method to invoke the remote
     * operation. <p> {@link GetClientInfo#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param email
     * @since 1.13
     */
    protected GetClientInfo(java.lang.String email) {
      super(ClientEndpointApi.this, "GET", REST_PATH, null, com.yapnak.gcmbackend.clientEndpointApi.model.ClientEntity.class);
      this.email = com.google.api.client.util.Preconditions.checkNotNull(email, "Required parameter email must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetClientInfo setAlt(java.lang.String alt) {
      return (GetClientInfo) super.setAlt(alt);
    }

    @Override
    public GetClientInfo setFields(java.lang.String fields) {
      return (GetClientInfo) super.setFields(fields);
    }

    @Override
    public GetClientInfo setKey(java.lang.String key) {
      return (GetClientInfo) super.setKey(key);
    }

    @Override
    public GetClientInfo setOauthToken(java.lang.String oauthToken) {
      return (GetClientInfo) super.setOauthToken(oauthToken);
    }

    @Override
    public GetClientInfo setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetClientInfo) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetClientInfo setQuotaUser(java.lang.String quotaUser) {
      return (GetClientInfo) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetClientInfo setUserIp(java.lang.String userIp) {
      return (GetClientInfo) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String email;

    /**

     */
    public java.lang.String getEmail() {
      return email;
    }

    public GetClientInfo setEmail(java.lang.String email) {
      this.email = email;
      return this;
    }

    @Override
    public GetClientInfo set(String parameterName, Object value) {
      return (GetClientInfo) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getRedemptionForUser".
   *
   * This request holds the parameters needed by the clientEndpointApi server.  After setting any
   * optional parameters, call the {@link GetRedemptionForUser#execute()} method to invoke the remote
   * operation.
   *
   * @param userId
   * @return the request
   */
  public GetRedemptionForUser getRedemptionForUser(java.lang.String userId) throws java.io.IOException {
    GetRedemptionForUser result = new GetRedemptionForUser(userId);
    initialize(result);
    return result;
  }

  public class GetRedemptionForUser extends ClientEndpointApiRequest<com.yapnak.gcmbackend.clientEndpointApi.model.LoyaltyRedeemEntity> {

    private static final String REST_PATH = "getRedemptionForUser";

    /**
     * Create a request for the method "getRedemptionForUser".
     *
     * This request holds the parameters needed by the the clientEndpointApi server.  After setting
     * any optional parameters, call the {@link GetRedemptionForUser#execute()} method to invoke the
     * remote operation. <p> {@link GetRedemptionForUser#initialize(com.google.api.client.googleapis.s
     * ervices.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param userId
     * @since 1.13
     */
    protected GetRedemptionForUser(java.lang.String userId) {
      super(ClientEndpointApi.this, "POST", REST_PATH, null, com.yapnak.gcmbackend.clientEndpointApi.model.LoyaltyRedeemEntity.class);
      this.userId = com.google.api.client.util.Preconditions.checkNotNull(userId, "Required parameter userId must be specified.");
    }

    @Override
    public GetRedemptionForUser setAlt(java.lang.String alt) {
      return (GetRedemptionForUser) super.setAlt(alt);
    }

    @Override
    public GetRedemptionForUser setFields(java.lang.String fields) {
      return (GetRedemptionForUser) super.setFields(fields);
    }

    @Override
    public GetRedemptionForUser setKey(java.lang.String key) {
      return (GetRedemptionForUser) super.setKey(key);
    }

    @Override
    public GetRedemptionForUser setOauthToken(java.lang.String oauthToken) {
      return (GetRedemptionForUser) super.setOauthToken(oauthToken);
    }

    @Override
    public GetRedemptionForUser setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetRedemptionForUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetRedemptionForUser setQuotaUser(java.lang.String quotaUser) {
      return (GetRedemptionForUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetRedemptionForUser setUserIp(java.lang.String userIp) {
      return (GetRedemptionForUser) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String userId;

    /**

     */
    public java.lang.String getUserId() {
      return userId;
    }

    public GetRedemptionForUser setUserId(java.lang.String userId) {
      this.userId = userId;
      return this;
    }

    @Override
    public GetRedemptionForUser set(String parameterName, Object value) {
      return (GetRedemptionForUser) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "qrRedeem".
   *
   * This request holds the parameters needed by the clientEndpointApi server.  After setting any
   * optional parameters, call the {@link QrRedeem#execute()} method to invoke the remote operation.
   *
   * @param clientId
   * @param offerId
   * @param userId
   * @return the request
   */
  public QrRedeem qrRedeem(java.lang.Integer clientId, java.lang.Integer offerId, java.lang.String userId) throws java.io.IOException {
    QrRedeem result = new QrRedeem(clientId, offerId, userId);
    initialize(result);
    return result;
  }

  public class QrRedeem extends ClientEndpointApiRequest<com.yapnak.gcmbackend.clientEndpointApi.model.RedemptionEntity> {

    private static final String REST_PATH = "qrRedeem";

    /**
     * Create a request for the method "qrRedeem".
     *
     * This request holds the parameters needed by the the clientEndpointApi server.  After setting
     * any optional parameters, call the {@link QrRedeem#execute()} method to invoke the remote
     * operation. <p> {@link
     * QrRedeem#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param clientId
     * @param offerId
     * @param userId
     * @since 1.13
     */
    protected QrRedeem(java.lang.Integer clientId, java.lang.Integer offerId, java.lang.String userId) {
      super(ClientEndpointApi.this, "POST", REST_PATH, null, com.yapnak.gcmbackend.clientEndpointApi.model.RedemptionEntity.class);
      this.clientId = com.google.api.client.util.Preconditions.checkNotNull(clientId, "Required parameter clientId must be specified.");
      this.offerId = com.google.api.client.util.Preconditions.checkNotNull(offerId, "Required parameter offerId must be specified.");
      this.userId = com.google.api.client.util.Preconditions.checkNotNull(userId, "Required parameter userId must be specified.");
    }

    @Override
    public QrRedeem setAlt(java.lang.String alt) {
      return (QrRedeem) super.setAlt(alt);
    }

    @Override
    public QrRedeem setFields(java.lang.String fields) {
      return (QrRedeem) super.setFields(fields);
    }

    @Override
    public QrRedeem setKey(java.lang.String key) {
      return (QrRedeem) super.setKey(key);
    }

    @Override
    public QrRedeem setOauthToken(java.lang.String oauthToken) {
      return (QrRedeem) super.setOauthToken(oauthToken);
    }

    @Override
    public QrRedeem setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (QrRedeem) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public QrRedeem setQuotaUser(java.lang.String quotaUser) {
      return (QrRedeem) super.setQuotaUser(quotaUser);
    }

    @Override
    public QrRedeem setUserIp(java.lang.String userIp) {
      return (QrRedeem) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Integer clientId;

    /**

     */
    public java.lang.Integer getClientId() {
      return clientId;
    }

    public QrRedeem setClientId(java.lang.Integer clientId) {
      this.clientId = clientId;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Integer offerId;

    /**

     */
    public java.lang.Integer getOfferId() {
      return offerId;
    }

    public QrRedeem setOfferId(java.lang.Integer offerId) {
      this.offerId = offerId;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String userId;

    /**

     */
    public java.lang.String getUserId() {
      return userId;
    }

    public QrRedeem setUserId(java.lang.String userId) {
      this.userId = userId;
      return this;
    }

    @Override
    public QrRedeem set(String parameterName, Object value) {
      return (QrRedeem) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "redeemUser".
   *
   * This request holds the parameters needed by the clientEndpointApi server.  After setting any
   * optional parameters, call the {@link RedeemUser#execute()} method to invoke the remote operation.
   *
   * @param clientId
   * @param userId
   * @return the request
   */
  public RedeemUser redeemUser(java.lang.Integer clientId, java.lang.String userId) throws java.io.IOException {
    RedeemUser result = new RedeemUser(clientId, userId);
    initialize(result);
    return result;
  }

  public class RedeemUser extends ClientEndpointApiRequest<com.yapnak.gcmbackend.clientEndpointApi.model.RedemptionEntity> {

    private static final String REST_PATH = "redeemUser";

    /**
     * Create a request for the method "redeemUser".
     *
     * This request holds the parameters needed by the the clientEndpointApi server.  After setting
     * any optional parameters, call the {@link RedeemUser#execute()} method to invoke the remote
     * operation. <p> {@link
     * RedeemUser#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param clientId
     * @param userId
     * @since 1.13
     */
    protected RedeemUser(java.lang.Integer clientId, java.lang.String userId) {
      super(ClientEndpointApi.this, "POST", REST_PATH, null, com.yapnak.gcmbackend.clientEndpointApi.model.RedemptionEntity.class);
      this.clientId = com.google.api.client.util.Preconditions.checkNotNull(clientId, "Required parameter clientId must be specified.");
      this.userId = com.google.api.client.util.Preconditions.checkNotNull(userId, "Required parameter userId must be specified.");
    }

    @Override
    public RedeemUser setAlt(java.lang.String alt) {
      return (RedeemUser) super.setAlt(alt);
    }

    @Override
    public RedeemUser setFields(java.lang.String fields) {
      return (RedeemUser) super.setFields(fields);
    }

    @Override
    public RedeemUser setKey(java.lang.String key) {
      return (RedeemUser) super.setKey(key);
    }

    @Override
    public RedeemUser setOauthToken(java.lang.String oauthToken) {
      return (RedeemUser) super.setOauthToken(oauthToken);
    }

    @Override
    public RedeemUser setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RedeemUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RedeemUser setQuotaUser(java.lang.String quotaUser) {
      return (RedeemUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public RedeemUser setUserIp(java.lang.String userIp) {
      return (RedeemUser) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Integer clientId;

    /**

     */
    public java.lang.Integer getClientId() {
      return clientId;
    }

    public RedeemUser setClientId(java.lang.Integer clientId) {
      this.clientId = clientId;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String userId;

    /**

     */
    public java.lang.String getUserId() {
      return userId;
    }

    public RedeemUser setUserId(java.lang.String userId) {
      this.userId = userId;
      return this;
    }

    @Override
    public RedeemUser set(String parameterName, Object value) {
      return (RedeemUser) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "replaceActiveOffer".
   *
   * This request holds the parameters needed by the clientEndpointApi server.  After setting any
   * optional parameters, call the {@link ReplaceActiveOffer#execute()} method to invoke the remote
   * operation.
   *
   * @param currentOfferId
   * @param email
   * @param newOfferId
   * @param offerPosition
   * @return the request
   */
  public ReplaceActiveOffer replaceActiveOffer(java.lang.Integer currentOfferId, java.lang.String email, java.lang.Integer newOfferId, java.lang.Integer offerPosition) throws java.io.IOException {
    ReplaceActiveOffer result = new ReplaceActiveOffer(currentOfferId, email, newOfferId, offerPosition);
    initialize(result);
    return result;
  }

  public class ReplaceActiveOffer extends ClientEndpointApiRequest<com.yapnak.gcmbackend.clientEndpointApi.model.SimpleEntity> {

    private static final String REST_PATH = "replaceActiveOffer";

    /**
     * Create a request for the method "replaceActiveOffer".
     *
     * This request holds the parameters needed by the the clientEndpointApi server.  After setting
     * any optional parameters, call the {@link ReplaceActiveOffer#execute()} method to invoke the
     * remote operation. <p> {@link ReplaceActiveOffer#initialize(com.google.api.client.googleapis.ser
     * vices.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param currentOfferId
     * @param email
     * @param newOfferId
     * @param offerPosition
     * @since 1.13
     */
    protected ReplaceActiveOffer(java.lang.Integer currentOfferId, java.lang.String email, java.lang.Integer newOfferId, java.lang.Integer offerPosition) {
      super(ClientEndpointApi.this, "POST", REST_PATH, null, com.yapnak.gcmbackend.clientEndpointApi.model.SimpleEntity.class);
      this.currentOfferId = com.google.api.client.util.Preconditions.checkNotNull(currentOfferId, "Required parameter currentOfferId must be specified.");
      this.email = com.google.api.client.util.Preconditions.checkNotNull(email, "Required parameter email must be specified.");
      this.newOfferId = com.google.api.client.util.Preconditions.checkNotNull(newOfferId, "Required parameter newOfferId must be specified.");
      this.offerPosition = com.google.api.client.util.Preconditions.checkNotNull(offerPosition, "Required parameter offerPosition must be specified.");
    }

    @Override
    public ReplaceActiveOffer setAlt(java.lang.String alt) {
      return (ReplaceActiveOffer) super.setAlt(alt);
    }

    @Override
    public ReplaceActiveOffer setFields(java.lang.String fields) {
      return (ReplaceActiveOffer) super.setFields(fields);
    }

    @Override
    public ReplaceActiveOffer setKey(java.lang.String key) {
      return (ReplaceActiveOffer) super.setKey(key);
    }

    @Override
    public ReplaceActiveOffer setOauthToken(java.lang.String oauthToken) {
      return (ReplaceActiveOffer) super.setOauthToken(oauthToken);
    }

    @Override
    public ReplaceActiveOffer setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ReplaceActiveOffer) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ReplaceActiveOffer setQuotaUser(java.lang.String quotaUser) {
      return (ReplaceActiveOffer) super.setQuotaUser(quotaUser);
    }

    @Override
    public ReplaceActiveOffer setUserIp(java.lang.String userIp) {
      return (ReplaceActiveOffer) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Integer currentOfferId;

    /**

     */
    public java.lang.Integer getCurrentOfferId() {
      return currentOfferId;
    }

    public ReplaceActiveOffer setCurrentOfferId(java.lang.Integer currentOfferId) {
      this.currentOfferId = currentOfferId;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String email;

    /**

     */
    public java.lang.String getEmail() {
      return email;
    }

    public ReplaceActiveOffer setEmail(java.lang.String email) {
      this.email = email;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Integer newOfferId;

    /**

     */
    public java.lang.Integer getNewOfferId() {
      return newOfferId;
    }

    public ReplaceActiveOffer setNewOfferId(java.lang.Integer newOfferId) {
      this.newOfferId = newOfferId;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Integer offerPosition;

    /**

     */
    public java.lang.Integer getOfferPosition() {
      return offerPosition;
    }

    public ReplaceActiveOffer setOfferPosition(java.lang.Integer offerPosition) {
      this.offerPosition = offerPosition;
      return this;
    }

    @Override
    public ReplaceActiveOffer set(String parameterName, Object value) {
      return (ReplaceActiveOffer) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link ClientEndpointApi}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link ClientEndpointApi}. */
    @Override
    public ClientEndpointApi build() {
      return new ClientEndpointApi(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link ClientEndpointApiRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setClientEndpointApiRequestInitializer(
        ClientEndpointApiRequestInitializer clientendpointapiRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(clientendpointapiRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
