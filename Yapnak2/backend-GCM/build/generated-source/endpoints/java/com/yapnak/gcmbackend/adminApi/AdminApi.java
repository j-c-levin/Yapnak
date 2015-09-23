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
 * on 2015-09-20 at 23:02:06 UTC 
 * Modify at your own risk.
 */

package com.yapnak.gcmbackend.adminApi;

/**
 * Service definition for AdminApi (v1).
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
 * This service uses {@link AdminApiRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class AdminApi extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.20.0 of the adminApi library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
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
  public static final String DEFAULT_SERVICE_PATH = "adminApi/v1/";

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
  public AdminApi(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  AdminApi(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "adminLogin".
   *
   * This request holds the parameters needed by the adminApi server.  After setting any optional
   * parameters, call the {@link AdminLogin#execute()} method to invoke the remote operation.
   *
   * @param email
   * @param password
   * @return the request
   */
  public AdminLogin adminLogin(java.lang.String email, java.lang.String password) throws java.io.IOException {
    AdminLogin result = new AdminLogin(email, password);
    initialize(result);
    return result;
  }

  public class AdminLogin extends AdminApiRequest<com.yapnak.gcmbackend.adminApi.model.AdminAuthEntity> {

    private static final String REST_PATH = "adminLogin";

    /**
     * Create a request for the method "adminLogin".
     *
     * This request holds the parameters needed by the the adminApi server.  After setting any
     * optional parameters, call the {@link AdminLogin#execute()} method to invoke the remote
     * operation. <p> {@link
     * AdminLogin#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param email
     * @param password
     * @since 1.13
     */
    protected AdminLogin(java.lang.String email, java.lang.String password) {
      super(AdminApi.this, "POST", REST_PATH, null, com.yapnak.gcmbackend.adminApi.model.AdminAuthEntity.class);
      this.email = com.google.api.client.util.Preconditions.checkNotNull(email, "Required parameter email must be specified.");
      this.password = com.google.api.client.util.Preconditions.checkNotNull(password, "Required parameter password must be specified.");
    }

    @Override
    public AdminLogin setAlt(java.lang.String alt) {
      return (AdminLogin) super.setAlt(alt);
    }

    @Override
    public AdminLogin setFields(java.lang.String fields) {
      return (AdminLogin) super.setFields(fields);
    }

    @Override
    public AdminLogin setKey(java.lang.String key) {
      return (AdminLogin) super.setKey(key);
    }

    @Override
    public AdminLogin setOauthToken(java.lang.String oauthToken) {
      return (AdminLogin) super.setOauthToken(oauthToken);
    }

    @Override
    public AdminLogin setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (AdminLogin) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public AdminLogin setQuotaUser(java.lang.String quotaUser) {
      return (AdminLogin) super.setQuotaUser(quotaUser);
    }

    @Override
    public AdminLogin setUserIp(java.lang.String userIp) {
      return (AdminLogin) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String email;

    /**

     */
    public java.lang.String getEmail() {
      return email;
    }

    public AdminLogin setEmail(java.lang.String email) {
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

    public AdminLogin setPassword(java.lang.String password) {
      this.password = password;
      return this;
    }

    @Override
    public AdminLogin set(String parameterName, Object value) {
      return (AdminLogin) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getAllClients".
   *
   * This request holds the parameters needed by the adminApi server.  After setting any optional
   * parameters, call the {@link GetAllClients#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public GetAllClients getAllClients() throws java.io.IOException {
    GetAllClients result = new GetAllClients();
    initialize(result);
    return result;
  }

  public class GetAllClients extends AdminApiRequest<com.yapnak.gcmbackend.adminApi.model.ClientListEntity> {

    private static final String REST_PATH = "getAllClients";

    /**
     * Create a request for the method "getAllClients".
     *
     * This request holds the parameters needed by the the adminApi server.  After setting any
     * optional parameters, call the {@link GetAllClients#execute()} method to invoke the remote
     * operation. <p> {@link GetAllClients#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @since 1.13
     */
    protected GetAllClients() {
      super(AdminApi.this, "GET", REST_PATH, null, com.yapnak.gcmbackend.adminApi.model.ClientListEntity.class);
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
    public GetAllClients setAlt(java.lang.String alt) {
      return (GetAllClients) super.setAlt(alt);
    }

    @Override
    public GetAllClients setFields(java.lang.String fields) {
      return (GetAllClients) super.setFields(fields);
    }

    @Override
    public GetAllClients setKey(java.lang.String key) {
      return (GetAllClients) super.setKey(key);
    }

    @Override
    public GetAllClients setOauthToken(java.lang.String oauthToken) {
      return (GetAllClients) super.setOauthToken(oauthToken);
    }

    @Override
    public GetAllClients setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetAllClients) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetAllClients setQuotaUser(java.lang.String quotaUser) {
      return (GetAllClients) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetAllClients setUserIp(java.lang.String userIp) {
      return (GetAllClients) super.setUserIp(userIp);
    }

    @Override
    public GetAllClients set(String parameterName, Object value) {
      return (GetAllClients) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getAllOffers".
   *
   * This request holds the parameters needed by the adminApi server.  After setting any optional
   * parameters, call the {@link GetAllOffers#execute()} method to invoke the remote operation.
   *
   * @param clientId
   * @return the request
   */
  public GetAllOffers getAllOffers(java.lang.Integer clientId) throws java.io.IOException {
    GetAllOffers result = new GetAllOffers(clientId);
    initialize(result);
    return result;
  }

  public class GetAllOffers extends AdminApiRequest<com.yapnak.gcmbackend.adminApi.model.ClientOfferListEntity> {

    private static final String REST_PATH = "getAllOffers";

    /**
     * Create a request for the method "getAllOffers".
     *
     * This request holds the parameters needed by the the adminApi server.  After setting any
     * optional parameters, call the {@link GetAllOffers#execute()} method to invoke the remote
     * operation. <p> {@link
     * GetAllOffers#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param clientId
     * @since 1.13
     */
    protected GetAllOffers(java.lang.Integer clientId) {
      super(AdminApi.this, "GET", REST_PATH, null, com.yapnak.gcmbackend.adminApi.model.ClientOfferListEntity.class);
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
   * This request holds the parameters needed by the adminApi server.  After setting any optional
   * parameters, call the {@link GetClientInfo#execute()} method to invoke the remote operation.
   *
   * @param clientId
   * @return the request
   */
  public GetClientInfo getClientInfo(java.lang.String clientId) throws java.io.IOException {
    GetClientInfo result = new GetClientInfo(clientId);
    initialize(result);
    return result;
  }

  public class GetClientInfo extends AdminApiRequest<com.yapnak.gcmbackend.adminApi.model.ClientEntity> {

    private static final String REST_PATH = "getClientInfo";

    /**
     * Create a request for the method "getClientInfo".
     *
     * This request holds the parameters needed by the the adminApi server.  After setting any
     * optional parameters, call the {@link GetClientInfo#execute()} method to invoke the remote
     * operation. <p> {@link GetClientInfo#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param clientId
     * @since 1.13
     */
    protected GetClientInfo(java.lang.String clientId) {
      super(AdminApi.this, "GET", REST_PATH, null, com.yapnak.gcmbackend.adminApi.model.ClientEntity.class);
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
    private java.lang.String clientId;

    /**

     */
    public java.lang.String getClientId() {
      return clientId;
    }

    public GetClientInfo setClientId(java.lang.String clientId) {
      this.clientId = clientId;
      return this;
    }

    @Override
    public GetClientInfo set(String parameterName, Object value) {
      return (GetClientInfo) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "hashGenerator".
   *
   * This request holds the parameters needed by the adminApi server.  After setting any optional
   * parameters, call the {@link HashGenerator#execute()} method to invoke the remote operation.
   *
   * @param password
   * @return the request
   */
  public HashGenerator hashGenerator(java.lang.String password) throws java.io.IOException {
    HashGenerator result = new HashGenerator(password);
    initialize(result);
    return result;
  }

  public class HashGenerator extends AdminApiRequest<Void> {

    private static final String REST_PATH = "hashGenerator";

    /**
     * Create a request for the method "hashGenerator".
     *
     * This request holds the parameters needed by the the adminApi server.  After setting any
     * optional parameters, call the {@link HashGenerator#execute()} method to invoke the remote
     * operation. <p> {@link HashGenerator#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param password
     * @since 1.13
     */
    protected HashGenerator(java.lang.String password) {
      super(AdminApi.this, "POST", REST_PATH, null, Void.class);
      this.password = com.google.api.client.util.Preconditions.checkNotNull(password, "Required parameter password must be specified.");
    }

    @Override
    public HashGenerator setAlt(java.lang.String alt) {
      return (HashGenerator) super.setAlt(alt);
    }

    @Override
    public HashGenerator setFields(java.lang.String fields) {
      return (HashGenerator) super.setFields(fields);
    }

    @Override
    public HashGenerator setKey(java.lang.String key) {
      return (HashGenerator) super.setKey(key);
    }

    @Override
    public HashGenerator setOauthToken(java.lang.String oauthToken) {
      return (HashGenerator) super.setOauthToken(oauthToken);
    }

    @Override
    public HashGenerator setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (HashGenerator) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public HashGenerator setQuotaUser(java.lang.String quotaUser) {
      return (HashGenerator) super.setQuotaUser(quotaUser);
    }

    @Override
    public HashGenerator setUserIp(java.lang.String userIp) {
      return (HashGenerator) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String password;

    /**

     */
    public java.lang.String getPassword() {
      return password;
    }

    public HashGenerator setPassword(java.lang.String password) {
      this.password = password;
      return this;
    }

    @Override
    public HashGenerator set(String parameterName, Object value) {
      return (HashGenerator) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertPromo".
   *
   * This request holds the parameters needed by the adminApi server.  After setting any optional
   * parameters, call the {@link InsertPromo#execute()} method to invoke the remote operation.
   *
   * @param promoCode
   * @return the request
   */
  public InsertPromo insertPromo(java.lang.String promoCode) throws java.io.IOException {
    InsertPromo result = new InsertPromo(promoCode);
    initialize(result);
    return result;
  }

  public class InsertPromo extends AdminApiRequest<Void> {

    private static final String REST_PATH = "insertPromo";

    /**
     * Create a request for the method "insertPromo".
     *
     * This request holds the parameters needed by the the adminApi server.  After setting any
     * optional parameters, call the {@link InsertPromo#execute()} method to invoke the remote
     * operation. <p> {@link
     * InsertPromo#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param promoCode
     * @since 1.13
     */
    protected InsertPromo(java.lang.String promoCode) {
      super(AdminApi.this, "POST", REST_PATH, null, Void.class);
      this.promoCode = com.google.api.client.util.Preconditions.checkNotNull(promoCode, "Required parameter promoCode must be specified.");
    }

    @Override
    public InsertPromo setAlt(java.lang.String alt) {
      return (InsertPromo) super.setAlt(alt);
    }

    @Override
    public InsertPromo setFields(java.lang.String fields) {
      return (InsertPromo) super.setFields(fields);
    }

    @Override
    public InsertPromo setKey(java.lang.String key) {
      return (InsertPromo) super.setKey(key);
    }

    @Override
    public InsertPromo setOauthToken(java.lang.String oauthToken) {
      return (InsertPromo) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertPromo setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertPromo) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertPromo setQuotaUser(java.lang.String quotaUser) {
      return (InsertPromo) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertPromo setUserIp(java.lang.String userIp) {
      return (InsertPromo) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String promoCode;

    /**

     */
    public java.lang.String getPromoCode() {
      return promoCode;
    }

    public InsertPromo setPromoCode(java.lang.String promoCode) {
      this.promoCode = promoCode;
      return this;
    }

    @Override
    public InsertPromo set(String parameterName, Object value) {
      return (InsertPromo) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "toggleClient".
   *
   * This request holds the parameters needed by the adminApi server.  After setting any optional
   * parameters, call the {@link ToggleClient#execute()} method to invoke the remote operation.
   *
   * @param clientId
   * @param session
   * @param value
   * @return the request
   */
  public ToggleClient toggleClient(java.lang.Integer clientId, java.lang.String session, java.lang.Integer value) throws java.io.IOException {
    ToggleClient result = new ToggleClient(clientId, session, value);
    initialize(result);
    return result;
  }

  public class ToggleClient extends AdminApiRequest<com.yapnak.gcmbackend.adminApi.model.SimpleEntity> {

    private static final String REST_PATH = "toggleClient";

    /**
     * Create a request for the method "toggleClient".
     *
     * This request holds the parameters needed by the the adminApi server.  After setting any
     * optional parameters, call the {@link ToggleClient#execute()} method to invoke the remote
     * operation. <p> {@link
     * ToggleClient#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param clientId
     * @param session
     * @param value
     * @since 1.13
     */
    protected ToggleClient(java.lang.Integer clientId, java.lang.String session, java.lang.Integer value) {
      super(AdminApi.this, "POST", REST_PATH, null, com.yapnak.gcmbackend.adminApi.model.SimpleEntity.class);
      this.clientId = com.google.api.client.util.Preconditions.checkNotNull(clientId, "Required parameter clientId must be specified.");
      this.session = com.google.api.client.util.Preconditions.checkNotNull(session, "Required parameter session must be specified.");
      this.value = com.google.api.client.util.Preconditions.checkNotNull(value, "Required parameter value must be specified.");
    }

    @Override
    public ToggleClient setAlt(java.lang.String alt) {
      return (ToggleClient) super.setAlt(alt);
    }

    @Override
    public ToggleClient setFields(java.lang.String fields) {
      return (ToggleClient) super.setFields(fields);
    }

    @Override
    public ToggleClient setKey(java.lang.String key) {
      return (ToggleClient) super.setKey(key);
    }

    @Override
    public ToggleClient setOauthToken(java.lang.String oauthToken) {
      return (ToggleClient) super.setOauthToken(oauthToken);
    }

    @Override
    public ToggleClient setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ToggleClient) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ToggleClient setQuotaUser(java.lang.String quotaUser) {
      return (ToggleClient) super.setQuotaUser(quotaUser);
    }

    @Override
    public ToggleClient setUserIp(java.lang.String userIp) {
      return (ToggleClient) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Integer clientId;

    /**

     */
    public java.lang.Integer getClientId() {
      return clientId;
    }

    public ToggleClient setClientId(java.lang.Integer clientId) {
      this.clientId = clientId;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String session;

    /**

     */
    public java.lang.String getSession() {
      return session;
    }

    public ToggleClient setSession(java.lang.String session) {
      this.session = session;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Integer value;

    /**

     */
    public java.lang.Integer getValue() {
      return value;
    }

    public ToggleClient setValue(java.lang.Integer value) {
      this.value = value;
      return this;
    }

    @Override
    public ToggleClient set(String parameterName, Object value) {
      return (ToggleClient) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link AdminApi}.
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

    /** Builds a new instance of {@link AdminApi}. */
    @Override
    public AdminApi build() {
      return new AdminApi(this);
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
     * Set the {@link AdminApiRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setAdminApiRequestInitializer(
        AdminApiRequestInitializer adminapiRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(adminapiRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
