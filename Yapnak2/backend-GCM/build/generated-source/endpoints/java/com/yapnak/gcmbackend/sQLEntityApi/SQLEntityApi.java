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
 * on 2015-08-11 at 15:23:19 UTC 
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
   * Create a request for the method "feedback".
   *
   * This request holds the parameters needed by the sQLEntityApi server.  After setting any optional
   * parameters, call the {@link Feedback#execute()} method to invoke the remote operation.
   *
   * @param message
   * @param type
   * @param userID
   * @return the request
   */
  public Feedback feedback(java.lang.String message, java.lang.Integer type, java.lang.String userID) throws java.io.IOException {
    Feedback result = new Feedback(message, type, userID);
    initialize(result);
    return result;
  }

  public class Feedback extends SQLEntityApiRequest<Void> {

    private static final String REST_PATH = "feedback";

    /**
     * Create a request for the method "feedback".
     *
     * This request holds the parameters needed by the the sQLEntityApi server.  After setting any
     * optional parameters, call the {@link Feedback#execute()} method to invoke the remote operation.
     * <p> {@link
     * Feedback#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param message
     * @param type
     * @param userID
     * @since 1.13
     */
    protected Feedback(java.lang.String message, java.lang.Integer type, java.lang.String userID) {
      super(SQLEntityApi.this, "POST", REST_PATH, null, Void.class);
      this.message = com.google.api.client.util.Preconditions.checkNotNull(message, "Required parameter message must be specified.");
      this.type = com.google.api.client.util.Preconditions.checkNotNull(type, "Required parameter type must be specified.");
      this.userID = com.google.api.client.util.Preconditions.checkNotNull(userID, "Required parameter userID must be specified.");
    }

    @Override
    public Feedback setAlt(java.lang.String alt) {
      return (Feedback) super.setAlt(alt);
    }

    @Override
    public Feedback setFields(java.lang.String fields) {
      return (Feedback) super.setFields(fields);
    }

    @Override
    public Feedback setKey(java.lang.String key) {
      return (Feedback) super.setKey(key);
    }

    @Override
    public Feedback setOauthToken(java.lang.String oauthToken) {
      return (Feedback) super.setOauthToken(oauthToken);
    }

    @Override
    public Feedback setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Feedback) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Feedback setQuotaUser(java.lang.String quotaUser) {
      return (Feedback) super.setQuotaUser(quotaUser);
    }

    @Override
    public Feedback setUserIp(java.lang.String userIp) {
      return (Feedback) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key("Message")
    private java.lang.String message;

    /**

     */
    public java.lang.String getMessage() {
      return message;
    }

    public Feedback setMessage(java.lang.String message) {
      this.message = message;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Integer type;

    /**

     */
    public java.lang.Integer getType() {
      return type;
    }

    public Feedback setType(java.lang.Integer type) {
      this.type = type;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String userID;

    /**

     */
    public java.lang.String getUserID() {
      return userID;
    }

    public Feedback setUserID(java.lang.String userID) {
      this.userID = userID;
      return this;
    }

    @Override
    public Feedback set(String parameterName, Object value) {
      return (Feedback) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getAllClients".
   *
   * This request holds the parameters needed by the sQLEntityApi server.  After setting any optional
   * parameters, call the {@link GetAllClients#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public GetAllClients getAllClients() throws java.io.IOException {
    GetAllClients result = new GetAllClients();
    initialize(result);
    return result;
  }

  public class GetAllClients extends SQLEntityApiRequest<com.yapnak.gcmbackend.sQLEntityApi.model.AllList> {

    private static final String REST_PATH = "getAllClients";

    /**
     * Create a request for the method "getAllClients".
     *
     * This request holds the parameters needed by the the sQLEntityApi server.  After setting any
     * optional parameters, call the {@link GetAllClients#execute()} method to invoke the remote
     * operation. <p> {@link GetAllClients#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @since 1.13
     */
    protected GetAllClients() {
      super(SQLEntityApi.this, "GET", REST_PATH, null, com.yapnak.gcmbackend.sQLEntityApi.model.AllList.class);
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
   * Create a request for the method "getClients".
   *
   * This request holds the parameters needed by the sQLEntityApi server.  After setting any optional
   * parameters, call the {@link GetClients#execute()} method to invoke the remote operation.
   *
   * @param latitude
   * @param longitude
   * @return the request
   */
  public GetClients getClients(java.lang.Double latitude, java.lang.Double longitude) throws java.io.IOException {
    GetClients result = new GetClients(latitude, longitude);
    initialize(result);
    return result;
  }

  public class GetClients extends SQLEntityApiRequest<com.yapnak.gcmbackend.sQLEntityApi.model.SQLList> {

    private static final String REST_PATH = "getClients";

    /**
     * Create a request for the method "getClients".
     *
     * This request holds the parameters needed by the the sQLEntityApi server.  After setting any
     * optional parameters, call the {@link GetClients#execute()} method to invoke the remote
     * operation. <p> {@link
     * GetClients#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param latitude
     * @param longitude
     * @since 1.13
     */
    protected GetClients(java.lang.Double latitude, java.lang.Double longitude) {
      super(SQLEntityApi.this, "GET", REST_PATH, null, com.yapnak.gcmbackend.sQLEntityApi.model.SQLList.class);
      this.latitude = com.google.api.client.util.Preconditions.checkNotNull(latitude, "Required parameter latitude must be specified.");
      this.longitude = com.google.api.client.util.Preconditions.checkNotNull(longitude, "Required parameter longitude must be specified.");
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
    private java.lang.Double latitude;

    /**

     */
    public java.lang.Double getLatitude() {
      return latitude;
    }

    public GetClients setLatitude(java.lang.Double latitude) {
      this.latitude = latitude;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Double longitude;

    /**

     */
    public java.lang.Double getLongitude() {
      return longitude;
    }

    public GetClients setLongitude(java.lang.Double longitude) {
      this.longitude = longitude;
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
   * @param clientEmail
   * @param userID
   * @return the request
   */
  public GetUser getUser(java.lang.String clientEmail, java.lang.String userID) throws java.io.IOException {
    GetUser result = new GetUser(clientEmail, userID);
    initialize(result);
    return result;
  }

  public class GetUser extends SQLEntityApiRequest<com.yapnak.gcmbackend.sQLEntityApi.model.PointsEntity> {

    private static final String REST_PATH = "getUser";

    /**
     * Create a request for the method "getUser".
     *
     * This request holds the parameters needed by the the sQLEntityApi server.  After setting any
     * optional parameters, call the {@link GetUser#execute()} method to invoke the remote operation.
     * <p> {@link
     * GetUser#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
     * be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param clientEmail
     * @param userID
     * @since 1.13
     */
    protected GetUser(java.lang.String clientEmail, java.lang.String userID) {
      super(SQLEntityApi.this, "POST", REST_PATH, null, com.yapnak.gcmbackend.sQLEntityApi.model.PointsEntity.class);
      this.clientEmail = com.google.api.client.util.Preconditions.checkNotNull(clientEmail, "Required parameter clientEmail must be specified.");
      this.userID = com.google.api.client.util.Preconditions.checkNotNull(userID, "Required parameter userID must be specified.");
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

    @Override
    public GetUser set(String parameterName, Object value) {
      return (GetUser) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getUserDetails".
   *
   * This request holds the parameters needed by the sQLEntityApi server.  After setting any optional
   * parameters, call the {@link GetUserDetails#execute()} method to invoke the remote operation.
   *
   * @param userID
   * @return the request
   */
  public GetUserDetails getUserDetails(java.lang.String userID) throws java.io.IOException {
    GetUserDetails result = new GetUserDetails(userID);
    initialize(result);
    return result;
  }

  public class GetUserDetails extends SQLEntityApiRequest<com.yapnak.gcmbackend.sQLEntityApi.model.UserEntity> {

    private static final String REST_PATH = "getUserDetails";

    /**
     * Create a request for the method "getUserDetails".
     *
     * This request holds the parameters needed by the the sQLEntityApi server.  After setting any
     * optional parameters, call the {@link GetUserDetails#execute()} method to invoke the remote
     * operation. <p> {@link GetUserDetails#initialize(com.google.api.client.googleapis.services.Abstr
     * actGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param userID
     * @since 1.13
     */
    protected GetUserDetails(java.lang.String userID) {
      super(SQLEntityApi.this, "POST", REST_PATH, null, com.yapnak.gcmbackend.sQLEntityApi.model.UserEntity.class);
      this.userID = com.google.api.client.util.Preconditions.checkNotNull(userID, "Required parameter userID must be specified.");
    }

    @Override
    public GetUserDetails setAlt(java.lang.String alt) {
      return (GetUserDetails) super.setAlt(alt);
    }

    @Override
    public GetUserDetails setFields(java.lang.String fields) {
      return (GetUserDetails) super.setFields(fields);
    }

    @Override
    public GetUserDetails setKey(java.lang.String key) {
      return (GetUserDetails) super.setKey(key);
    }

    @Override
    public GetUserDetails setOauthToken(java.lang.String oauthToken) {
      return (GetUserDetails) super.setOauthToken(oauthToken);
    }

    @Override
    public GetUserDetails setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetUserDetails) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetUserDetails setQuotaUser(java.lang.String quotaUser) {
      return (GetUserDetails) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetUserDetails setUserIp(java.lang.String userIp) {
      return (GetUserDetails) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String userID;

    /**

     */
    public java.lang.String getUserID() {
      return userID;
    }

    public GetUserDetails setUserID(java.lang.String userID) {
      this.userID = userID;
      return this;
    }

    @Override
    public GetUserDetails set(String parameterName, Object value) {
      return (GetUserDetails) super.set(parameterName, value);
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

    private static final String REST_PATH = "insertUser";

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
   * Create a request for the method "setUserDetails".
   *
   * This request holds the parameters needed by the sQLEntityApi server.  After setting any optional
   * parameters, call the {@link SetUserDetails#execute()} method to invoke the remote operation.
   *
   * @param fName
   * @param lName
   * @param number
   * @param userID
   * @return the request
   */
  public SetUserDetails setUserDetails(java.lang.String fName, java.lang.String lName, java.lang.String number, java.lang.String userID) throws java.io.IOException {
    SetUserDetails result = new SetUserDetails(fName, lName, number, userID);
    initialize(result);
    return result;
  }

  public class SetUserDetails extends SQLEntityApiRequest<Void> {

    private static final String REST_PATH = "setUserDetails";

    /**
     * Create a request for the method "setUserDetails".
     *
     * This request holds the parameters needed by the the sQLEntityApi server.  After setting any
     * optional parameters, call the {@link SetUserDetails#execute()} method to invoke the remote
     * operation. <p> {@link SetUserDetails#initialize(com.google.api.client.googleapis.services.Abstr
     * actGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param fName
     * @param lName
     * @param number
     * @param userID
     * @since 1.13
     */
    protected SetUserDetails(java.lang.String fName, java.lang.String lName, java.lang.String number, java.lang.String userID) {
      super(SQLEntityApi.this, "POST", REST_PATH, null, Void.class);
      this.fName = com.google.api.client.util.Preconditions.checkNotNull(fName, "Required parameter fName must be specified.");
      this.lName = com.google.api.client.util.Preconditions.checkNotNull(lName, "Required parameter lName must be specified.");
      this.number = com.google.api.client.util.Preconditions.checkNotNull(number, "Required parameter number must be specified.");
      this.userID = com.google.api.client.util.Preconditions.checkNotNull(userID, "Required parameter userID must be specified.");
    }

    @Override
    public SetUserDetails setAlt(java.lang.String alt) {
      return (SetUserDetails) super.setAlt(alt);
    }

    @Override
    public SetUserDetails setFields(java.lang.String fields) {
      return (SetUserDetails) super.setFields(fields);
    }

    @Override
    public SetUserDetails setKey(java.lang.String key) {
      return (SetUserDetails) super.setKey(key);
    }

    @Override
    public SetUserDetails setOauthToken(java.lang.String oauthToken) {
      return (SetUserDetails) super.setOauthToken(oauthToken);
    }

    @Override
    public SetUserDetails setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (SetUserDetails) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public SetUserDetails setQuotaUser(java.lang.String quotaUser) {
      return (SetUserDetails) super.setQuotaUser(quotaUser);
    }

    @Override
    public SetUserDetails setUserIp(java.lang.String userIp) {
      return (SetUserDetails) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String fName;

    /**

     */
    public java.lang.String getFName() {
      return fName;
    }

    public SetUserDetails setFName(java.lang.String fName) {
      this.fName = fName;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String lName;

    /**

     */
    public java.lang.String getLName() {
      return lName;
    }

    public SetUserDetails setLName(java.lang.String lName) {
      this.lName = lName;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String number;

    /**

     */
    public java.lang.String getNumber() {
      return number;
    }

    public SetUserDetails setNumber(java.lang.String number) {
      this.number = number;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String userID;

    /**

     */
    public java.lang.String getUserID() {
      return userID;
    }

    public SetUserDetails setUserID(java.lang.String userID) {
      this.userID = userID;
      return this;
    }

    @Override
    public SetUserDetails set(String parameterName, Object value) {
      return (SetUserDetails) super.set(parameterName, value);
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
