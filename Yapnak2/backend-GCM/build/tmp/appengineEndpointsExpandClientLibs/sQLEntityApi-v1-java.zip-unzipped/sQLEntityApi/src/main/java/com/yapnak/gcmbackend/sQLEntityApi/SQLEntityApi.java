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
 * (build: 2015-07-16 18:28:29 UTC)
 * on 2015-07-23 at 21:22:51 UTC 
 * Modify at your own risk.
 */

package com.yapnak.gcmbackend.sQLEntityApi;

/**
 * Service definition for SQLEntityApi (v1).
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
 * This service uses {@link SQLEntityApiRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class SQLEntityApi extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.20.0 of the sQLEntityApi library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
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
  public static final String DEFAULT_SERVICE_PATH = "sQLEntityApi/v1/";

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
  public SQLEntityApi(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  SQLEntityApi(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getClients".
   *
   * This request holds the parameters needed by the sQLEntityApi server.  After setting any optional
   * parameters, call the {@link GetClients#execute()} method to invoke the remote operation.
   *
   * @param x
   * @param y
   * @return the request
   */
  public GetClients getClients(java.lang.Double x, java.lang.Double y) throws java.io.IOException {
    GetClients result = new GetClients(x, y);
    initialize(result);
    return result;
  }

  public class GetClients extends SQLEntityApiRequest<com.yapnak.gcmbackend.sQLEntityApi.model.SQLList> {

    private static final String REST_PATH = "sQLEntity_client";

    /**
     * Create a request for the method "getClients".
     *
     * This request holds the parameters needed by the the sQLEntityApi server.  After setting any
     * optional parameters, call the {@link GetClients#execute()} method to invoke the remote
     * operation. <p> {@link
     * GetClients#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param x
     * @param y
     * @since 1.13
     */
    protected GetClients(java.lang.Double x, java.lang.Double y) {
      super(SQLEntityApi.this, "GET", REST_PATH, null, com.yapnak.gcmbackend.sQLEntityApi.model.SQLList.class);
      this.x = com.google.api.client.util.Preconditions.checkNotNull(x, "Required parameter x must be specified.");
      this.y = com.google.api.client.util.Preconditions.checkNotNull(y, "Required parameter y must be specified.");
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
    public GetClients setAlt(java.lang.String alt) {
      return (GetClients) super.setAlt(alt);
    }

    @Override
    public GetClients setFields(java.lang.String fields) {
      return (GetClients) super.setFields(fields);
    }

    @Override
    public GetClients setKey(java.lang.String key) {
      return (GetClients) super.setKey(key);
    }

    @Override
    public GetClients setOauthToken(java.lang.String oauthToken) {
      return (GetClients) super.setOauthToken(oauthToken);
    }

    @Override
    public GetClients setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetClients) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetClients setQuotaUser(java.lang.String quotaUser) {
      return (GetClients) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetClients setUserIp(java.lang.String userIp) {
      return (GetClients) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Double x;

    /**

     */
    public java.lang.Double getX() {
      return x;
    }

    public GetClients setX(java.lang.Double x) {
      this.x = x;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Double y;

    /**

     */
    public java.lang.Double getY() {
      return y;
    }

    public GetClients setY(java.lang.Double y) {
      this.y = y;
      return this;
    }

    @Override
    public GetClients set(String parameterName, Object value) {
      return (GetClients) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getUser".
   *
   * This request holds the parameters needed by the sQLEntityApi server.  After setting any optional
   * parameters, call the {@link GetUser#execute()} method to invoke the remote operation.
   *
   * @param userID
   * @param clientEmail
   * @return the request
   */
  public GetUser getUser(java.lang.String userID, java.lang.String clientEmail) throws java.io.IOException {
    GetUser result = new GetUser(userID, clientEmail);
    initialize(result);
    return result;
  }

  public class GetUser extends SQLEntityApiRequest<com.yapnak.gcmbackend.sQLEntityApi.model.PointsEntity> {

    private static final String REST_PATH = "sqlentity/{userID}/{clientEmail}";

    /**
     * Create a request for the method "getUser".
     *
     * This request holds the parameters needed by the the sQLEntityApi server.  After setting any
     * optional parameters, call the {@link GetUser#execute()} method to invoke the remote operation.
     * <p> {@link
     * GetUser#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
     * be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param userID
     * @param clientEmail
     * @since 1.13
     */
    protected GetUser(java.lang.String userID, java.lang.String clientEmail) {
      super(SQLEntityApi.this, "GET", REST_PATH, null, com.yapnak.gcmbackend.sQLEntityApi.model.PointsEntity.class);
      this.userID = com.google.api.client.util.Preconditions.checkNotNull(userID, "Required parameter userID must be specified.");
      this.clientEmail = com.google.api.client.util.Preconditions.checkNotNull(clientEmail, "Required parameter clientEmail must be specified.");
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
    public GetUser setAlt(java.lang.String alt) {
      return (GetUser) super.setAlt(alt);
    }

    @Override
    public GetUser setFields(java.lang.String fields) {
      return (GetUser) super.setFields(fields);
    }

    @Override
    public GetUser setKey(java.lang.String key) {
      return (GetUser) super.setKey(key);
    }

    @Override
    public GetUser setOauthToken(java.lang.String oauthToken) {
      return (GetUser) super.setOauthToken(oauthToken);
    }

    @Override
    public GetUser setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetUser setQuotaUser(java.lang.String quotaUser) {
      return (GetUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetUser setUserIp(java.lang.String userIp) {
      return (GetUser) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String userID;

    /**

     */
    public java.lang.String getUserID() {
      return userID;
    }

    public GetUser setUserID(java.lang.String userID) {
      this.userID = userID;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String clientEmail;

    /**

     */
    public java.lang.String getClientEmail() {
      return clientEmail;
    }

    public GetUser setClientEmail(java.lang.String clientEmail) {
      this.clientEmail = clientEmail;
      return this;
    }

    @Override
    public GetUser set(String parameterName, Object value) {
      return (GetUser) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertClient".
   *
   * This request holds the parameters needed by the sQLEntityApi server.  After setting any optional
   * parameters, call the {@link InsertClient#execute()} method to invoke the remote operation.
   *
   * @param name
   * @param offer
   * @param x
   * @param y
   * @return the request
   */
  public InsertClient insertClient(java.lang.String name, java.lang.String offer, java.lang.Double x, java.lang.Double y) throws java.io.IOException {
    InsertClient result = new InsertClient(name, offer, x, y);
    initialize(result);
    return result;
  }

  public class InsertClient extends SQLEntityApiRequest<Void> {

    private static final String REST_PATH = "sQLEntity_client";

    /**
     * Create a request for the method "insertClient".
     *
     * This request holds the parameters needed by the the sQLEntityApi server.  After setting any
     * optional parameters, call the {@link InsertClient#execute()} method to invoke the remote
     * operation. <p> {@link
     * InsertClient#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param name
     * @param offer
     * @param x
     * @param y
     * @since 1.13
     */
    protected InsertClient(java.lang.String name, java.lang.String offer, java.lang.Double x, java.lang.Double y) {
      super(SQLEntityApi.this, "POST", REST_PATH, null, Void.class);
      this.name = com.google.api.client.util.Preconditions.checkNotNull(name, "Required parameter name must be specified.");
      this.offer = com.google.api.client.util.Preconditions.checkNotNull(offer, "Required parameter offer must be specified.");
      this.x = com.google.api.client.util.Preconditions.checkNotNull(x, "Required parameter x must be specified.");
      this.y = com.google.api.client.util.Preconditions.checkNotNull(y, "Required parameter y must be specified.");
    }

    @Override
    public InsertClient setAlt(java.lang.String alt) {
      return (InsertClient) super.setAlt(alt);
    }

    @Override
    public InsertClient setFields(java.lang.String fields) {
      return (InsertClient) super.setFields(fields);
    }

    @Override
    public InsertClient setKey(java.lang.String key) {
      return (InsertClient) super.setKey(key);
    }

    @Override
    public InsertClient setOauthToken(java.lang.String oauthToken) {
      return (InsertClient) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertClient setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertClient) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertClient setQuotaUser(java.lang.String quotaUser) {
      return (InsertClient) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertClient setUserIp(java.lang.String userIp) {
      return (InsertClient) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String name;

    /**

     */
    public java.lang.String getName() {
      return name;
    }

    public InsertClient setName(java.lang.String name) {
      this.name = name;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String offer;

    /**

     */
    public java.lang.String getOffer() {
      return offer;
    }

    public InsertClient setOffer(java.lang.String offer) {
      this.offer = offer;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Double x;

    /**

     */
    public java.lang.Double getX() {
      return x;
    }

    public InsertClient setX(java.lang.Double x) {
      this.x = x;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Double y;

    /**

     */
    public java.lang.Double getY() {
      return y;
    }

    public InsertClient setY(java.lang.Double y) {
      this.y = y;
      return this;
    }

    @Override
    public InsertClient set(String parameterName, Object value) {
      return (InsertClient) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertUser".
   *
   * This request holds the parameters needed by the sQLEntityApi server.  After setting any optional
   * parameters, call the {@link InsertUser#execute()} method to invoke the remote operation.
   *
   * @param email
   * @param password
   * @return the request
   */
  public InsertUser insertUser(java.lang.String email, java.lang.String password) throws java.io.IOException {
    InsertUser result = new InsertUser(email, password);
    initialize(result);
    return result;
  }

  public class InsertUser extends SQLEntityApiRequest<Void> {

    private static final String REST_PATH = "sQLEntity_user";

    /**
     * Create a request for the method "insertUser".
     *
     * This request holds the parameters needed by the the sQLEntityApi server.  After setting any
     * optional parameters, call the {@link InsertUser#execute()} method to invoke the remote
     * operation. <p> {@link
     * InsertUser#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param email
     * @param password
     * @since 1.13
     */
    protected InsertUser(java.lang.String email, java.lang.String password) {
      super(SQLEntityApi.this, "POST", REST_PATH, null, Void.class);
      this.email = com.google.api.client.util.Preconditions.checkNotNull(email, "Required parameter email must be specified.");
      this.password = com.google.api.client.util.Preconditions.checkNotNull(password, "Required parameter password must be specified.");
    }

    @Override
    public InsertUser setAlt(java.lang.String alt) {
      return (InsertUser) super.setAlt(alt);
    }

    @Override
    public InsertUser setFields(java.lang.String fields) {
      return (InsertUser) super.setFields(fields);
    }

    @Override
    public InsertUser setKey(java.lang.String key) {
      return (InsertUser) super.setKey(key);
    }

    @Override
    public InsertUser setOauthToken(java.lang.String oauthToken) {
      return (InsertUser) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertUser setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertUser setQuotaUser(java.lang.String quotaUser) {
      return (InsertUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertUser setUserIp(java.lang.String userIp) {
      return (InsertUser) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String email;

    /**

     */
    public java.lang.String getEmail() {
      return email;
    }

    public InsertUser setEmail(java.lang.String email) {
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

    public InsertUser setPassword(java.lang.String password) {
      this.password = password;
      return this;
    }

    @Override
    public InsertUser set(String parameterName, Object value) {
      return (InsertUser) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link SQLEntityApi}.
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

    /** Builds a new instance of {@link SQLEntityApi}. */
    @Override
    public SQLEntityApi build() {
      return new SQLEntityApi(this);
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
     * Set the {@link SQLEntityApiRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setSQLEntityApiRequestInitializer(
        SQLEntityApiRequestInitializer sqlentityapiRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(sqlentityapiRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
