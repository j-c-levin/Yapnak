package com.yapnak.gcmbackend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

/**
 * Created by Joshua on 06/09/2015.
 */
@Api(
        name = "userEndpointApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "gcmbackend.yapnak.com",
                ownerName = "gcmbackend.yapnak.com",
                packagePath = ""
        ),
        scopes = {Constants.EMAIL_SCOPE},
        clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE})
public class UserEndpoint {
    private static final Logger logger = Logger.getLogger(UserEndpoint.class.getName());

    @ApiMethod(
            name = "authenticateUser",
            path = "authenticateUser",
            httpMethod = ApiMethod.HttpMethod.POST)
    public AuthenticateEntity authenticateUser(@Named("email") @Nullable String email, @Named("mobileNo") @Nullable String mobNo, @Named("password") String password) {
        AuthenticateEntity response = new AuthenticateEntity();
        response.setStatus("True");
        return response;
    }

    @ApiMethod(
            name = "deauthenticateUser",
            path = "deauthenticateUser",
            httpMethod = ApiMethod.HttpMethod.GET)
    public DeauthenticateEntity deauthenticateUser(@Named("userId") String userId) {
        DeauthenticateEntity response = new DeauthenticateEntity();
        response.setStatus("True");
        return response;
    }

    @ApiMethod(
            name = "registerUser",
            path = "registerUser",
            httpMethod = ApiMethod.HttpMethod.POST)
    public RegisterUserEntity registerUser(@Named("email") @Nullable String email, @Named("mobNo") @Nullable String mobNo, @Named("password") String password, @Named("dateOfBirth")@Nullable String dateOfBirth, @Named("firstName") @Nullable String firstName, @Named("lastName") @Nullable String lastName) {
        RegisterUserEntity response = new RegisterUserEntity();
        response.setStatus("True");
        return response;
    }

    @ApiMethod(
            name = "getUserDetails",
            path = "getUserDetails",
            httpMethod = ApiMethod.HttpMethod.GET)
    public UserDetailsEntity getUserDetails(@Named("userId")@Nullable String userId,@Named("email")@Nullable String email,@Named("mobNo")@Nullable String mobNo) {
        UserDetailsEntity response = new UserDetailsEntity();
        response.setStatus("True");
        return response;
    }

    @ApiMethod(
            name = "setUserDetails",
            path = "setUserDetails",
            httpMethod = ApiMethod.HttpMethod.POST)
         public SetUserDetailsEntity setUserDetails(@Named("email") @Nullable String email, @Named("mobNo") @Nullable String mobNo, @Named("password") String password, @Named("dateOfBirth")@Nullable Date dateOfBirth, @Named("firstName") @Nullable String firstName, @Named("lastName") @Nullable String lastName) {
        SetUserDetailsEntity response = new SetUserDetailsEntity();
        response.setStatus("True");
        return response;
    }

    @ApiMethod(
            name = "getClients",
            path = "getClients",
            httpMethod = ApiMethod.HttpMethod.GET)
         public OfferListEntity getClients(@Named("longitude") double longitude, @Named("latitude") double latitude, @Named("userId") String userId) {
        OfferListEntity response = new OfferListEntity();
        OfferEntity offer;
        response.setStatus("True");
        return response;
    }

    @ApiMethod(
            name = "feedback",
            path = "feedback",
            httpMethod = ApiMethod.HttpMethod.POST)
    public FeedbackEntity feedback(@Named("userId") String userId, @Named("clientId") int clientId, @Named("rating") int rating, @Named("comment") @Nullable String comment) {
        FeedbackEntity response = new FeedbackEntity();
        OfferEntity offer;
        response.setStatus("True");
        return response;
    }

    @ApiMethod(
            name = "recommend",
            path = "recommend",
            httpMethod = ApiMethod.HttpMethod.POST)
    public RecommendEntity recommend(@Named("userId") String userId, @Named("clientId") int clientId, @Named("rating") int rating, @Named("comment") @Nullable String comment) {
        RecommendEntity response = new RecommendEntity();
        response.setStatus("True");
        response.setResult(1);
        return response;
    }


}
