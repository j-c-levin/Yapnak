package com.yapnak.joshua.qrscanner.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ImagesServiceFailureException;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.utils.SystemProperty;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "userEndpointApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "gcmbackend.yapnak.com",
                ownerName = "gcmbackend.yapnak.com",
                packagePath = ""
        )
)
public class UserEndpoint {
    private static final Logger logger = Logger.getLogger(UserEndpoint.class.getName());

    //*******Handles hashing********
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    private static String SALT = "Y3aQcfpTiUUdpSAY";

    static String hashPassword(String in) {
        try {
            MessageDigest md = MessageDigest
                    .getInstance("SHA-256");
            md.update(SALT.getBytes());        // <-- Prepend SALT.
            md.update(in.getBytes());

            byte[] out = md.digest();
            return bytesToHex(out);            // <-- Return the Hex Hash.
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    //********************************

    //Generates some random numbers
    public static int randInt() {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int max = 9998;
        int min = 1000;
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    @ApiMethod(
            name = "updatePhotoUrl",
            path = "updatePhotoUrl",
            httpMethod = ApiMethod.HttpMethod.POST)
    public OfferEntity updatePhotoUrl() {
        OfferEntity response = new OfferEntity();
        Connection connection;
        try {
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
                connection = DriverManager.getConnection("jdbc:google:mysql://yapnak-app:yapnak-main/yapnak_main?user=root");
            } else {
                // Local MySQL instance to use during development.
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://173.194.230.210/yapnak_main", "client", "g7lFVLRzYdJoWXc3");
            }
            queryBlock:
            try {
                String query = "SELECT clientPhoto, clientID from client";
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                query = "UPDATE client SET clientPhotoUrl = ? WHERE clientID = ?";
                statement = connection.prepareStatement(query);
                while (rs.next()) {
                    logger.info("found " + rs.getString("clientID"));
                    ImagesService services = ImagesServiceFactory.getImagesService();
                    ServingUrlOptions serve = ServingUrlOptions.Builder.withBlobKey(new BlobKey(rs.getString("clientPhoto")));
                    String url;
                    try {
                        url = services.getServingUrl(serve);
                        url = url + "=s100";
                    } catch (IllegalArgumentException e) {
                        url = "http://yapnak.com/images/yapnakmonster.png";
                        logger.warning("IllegalArgumentException " + e);
                        e.printStackTrace();
                    } catch (ImagesServiceFailureException e1) {
                        url = "http://yapnak.com/images/yapnakmonster.png";
                        logger.warning("ImagesServiceFailureException " + e1);
                        e1.printStackTrace();
                    }
                    statement.setString(1, url);
                    statement.setInt(2, rs.getInt("clientID"));
                    int success = statement.executeUpdate();
                    if (success == -1) {
                        logger.warning("Failed to update logo for " + rs.getInt("clientID"));
                    } else {
                        logger.info(rs.getInt("clientID") + " logo updated");
                    }
                }
            } finally {
                connection.close();
                return response;
            }
        } finally {
            return response;
        }
    }

    @ApiMethod(
            name = "registerUser",
            path = "registerUser",
            httpMethod = ApiMethod.HttpMethod.POST)
    public RegisterUserEntity registerUser(@Named("email") @Nullable String email, @Named("mobNo") @Nullable String mobNo, @Named("password") String password, @Named("firstName") @Nullable String firstName, @Named("lastName") @Nullable String lastName) {
        RegisterUserEntity response = new RegisterUserEntity();
        Connection connection;
        try {
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
                connection = DriverManager.getConnection("jdbc:google:mysql://yapnak-app:yapnak-main/yapnak_main?user=root");
            } else {
                // Local MySQL instance to use during development.
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://173.194.230.210/yapnak_main", "client", "g7lFVLRzYdJoWXc3");
            }
            queryBlock:
            try {
                String query;
                PreparedStatement statement;
                String userId;
                if (email != null) {
                    query = "SELECT COUNT(*) FROM user WHERE email = ?";
                    statement = connection.prepareStatement(query);
                    statement.setString(1, email);
                    userId = email;
                    logger.info("searching for registered email: " + email);
                } else if (mobNo != null) {
                    query = "SELECT COUNT(*) FROM user WHERE mobNo = ?";
                    statement = connection.prepareStatement(query);
                    statement.setString(1, mobNo);
                    userId = mobNo;
                    logger.info("searching for registered number: " + mobNo);
                } else {
                    //user details are missing
                    response.setStatus("False");
                    response.setMessage("User email or mobile number missing");
                    logger.info("User email or mobile number missing");
                    break queryBlock;
                }
                ResultSet rs = statement.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {
                    //User already exists in the system
                    response.setStatus("False");
                    response.setMessage("User already registered");
                    logger.warning("User already registered");
                    break queryBlock;
                }
                //User needs to be registered
                query = "INSERT INTO user (userID, firstName, lastName, mobNo, email, password) VALUES (?,?,?,?,?,?)";
                statement = connection.prepareStatement(query);
                statement.setString(1, userId.substring(0, 4) + randInt());
                statement.setString(2, (firstName == null) ? "" : firstName);
                statement.setString(3, (lastName == null) ? "" : lastName);
                statement.setString(4, (mobNo == null) ? "" : mobNo);
                statement.setString(5, (email == null) ? "" : email);
                statement.setString(6, hashPassword(password));
                int success = statement.executeUpdate();
                if (success == -1) {
                    //Insert failed
                    logger.warning("Registration insert FAILED");
                    response.setStatus("False");
                    response.setMessage("Registration insert failed");
                } else {
                    //Insert succeeded
                    logger.info("Registration insert success");
                    response.setStatus("True");
                }
            } finally {
                connection.close();
                return response;
            }
        } finally {
            return response;
        }
    }

    @ApiMethod(
            name = "authenticateUser",
            path = "authenticateUser",
            httpMethod = ApiMethod.HttpMethod.POST)
    public AuthenticateEntity authenticateUser(@Named("email") @Nullable String email, @Named("mobNo") @Nullable String mobNo, @Named("password") String password) {
        AuthenticateEntity response = new AuthenticateEntity();
        Connection connection;
        try {
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
                connection = DriverManager.getConnection("jdbc:google:mysql://yapnak-app:yapnak-main/yapnak_main?user=root");
            } else {
                // Local MySQL instance to use during development.
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://173.194.230.210/yapnak_main", "client", "g7lFVLRzYdJoWXc3");
            }
            try {
                PreparedStatement statement;
                if (email != null) {
                    String query = "SELECT userID FROM user WHERE email = ? AND password = ?";
                    statement = connection.prepareStatement(query);
                    statement.setString(1, email);
                    logger.info("beginning user authentication for: " + email);
                } else if (mobNo != null) {
                    String query = "SELECT userID FROM user WHERE mobNo = ? AND password = ?";
                    statement = connection.prepareStatement(query);
                    statement.setString(1, mobNo);
                    logger.info("beginning user authentication for: " + mobNo);
                } else {
                    //user details are missing
                    response.setStatus("False");
                    response.setMessage("User email or mobile number missing");
                    logger.warning("User email or mobile number missing");
                    connection.close();
                    return response;
                }
                statement.setString(2, hashPassword(password));
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    //user valid
                    //do some authentication here
                    logger.info("successful authentication");
                    response.setStatus("True");
                    response.setUserId(rs.getString("userID"));
                } else {
                    //details incorrect
                    response.setStatus("False");
                    response.setMessage("User details incorrect");
                    logger.warning("User details incorrect");
                }
            } finally {
                connection.close();
                return response;
            }
        } finally {
            return response;
        }
    }

    @ApiMethod(
            name = "deauthenticateUser",
            path = "deauthenticateUser",
            httpMethod = ApiMethod.HttpMethod.GET)
    public DeauthenticateEntity deauthenticateUser(@Named("userId") String userId) {
        DeauthenticateEntity response = new DeauthenticateEntity();
        Connection connection;
        try {
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
                connection = DriverManager.getConnection("jdbc:google:mysql://yapnak-app:yapnak-main/yapnak_main?user=root");
            } else {
                // Local MySQL instance to use during development.
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://173.194.230.210/yapnak_main", "client", "g7lFVLRzYdJoWXc3");
            }
            try {
                //deauthenticate the user here
                response.setStatus("True");
            } finally {
                connection.close();
                return response;
            }
        } finally {
            return response;
        }
    }

    @ApiMethod(
            name = "getUserDetails",
            path = "getUserDetails",
            httpMethod = ApiMethod.HttpMethod.GET)
    public UserDetailsEntity getUserDetails(@Named("userId") @Nullable String userId, @Named("email") @Nullable String email, @Named("mobNo") @Nullable String mobNo) {
        UserDetailsEntity response = new UserDetailsEntity();
        Connection connection;
        try {
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
                connection = DriverManager.getConnection("jdbc:google:mysql://yapnak-app:yapnak-main/yapnak_main?user=root");
            } else {
                // Local MySQL instance to use during development.
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://173.194.230.210/yapnak_main", "client", "g7lFVLRzYdJoWXc3");
            }
            queryBlock:
            try {
                String query = "SELECT userID, email, mobNo, dateOfBirth, firstName, lastName, loyaltyPoints, userImage FROM user WHERE userID = ? OR email = ? OR mobNo = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                String details;
                if (userId != null) {
                    details = userId;
                } else if (email != null) {
                    details = email;
                } else if (mobNo != null) {
                    details = mobNo;
                } else {
                    //no details sent
                    response.setStatus("False");
                    response.setMessage("No details provided");
                    logger.warning("No details provided");
                    break queryBlock;
                }
                statement.setString(1, details);
                statement.setString(2, details);
                statement.setString(3, details);
                logger.info("Searching for user details of: " + details);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    //User details found
                    logger.info("found user");
                    response.setDateOfBirth(rs.getDate("dateOfBirth"));
                    response.setEmail(rs.getString("email"));
                    response.setFirstName(rs.getString("firstName"));
                    response.setLastName(rs.getString("lastName"));
                    response.setLoyaltyPoints(rs.getInt("loyaltyPoints"));
                    response.setUserId(rs.getString("userID"));
                    response.setUserImage(rs.getString("userImage"));
                    response.setStatus("True");
                } else {
                    //No user found
                    response.setStatus("False");
                    response.setMessage("User not found");
                    logger.warning("User not found");
                    break queryBlock;
                }
            } finally {
                connection.close();
                return response;
            }
        } finally {
            return response;
        }
    }

    @ApiMethod(
            name = "setUserDetails",
            path = "setUserDetails",
            httpMethod = ApiMethod.HttpMethod.POST)
    public SetUserDetailsEntity setUserDetails(@Named("userId") String userId, @Named("email") @Nullable String email, @Named("mobNo") @Nullable String mobNo, @Named("password") @Nullable String password, @Named("dateOfBirth") @Nullable String dateOfBirth, @Named("firstName") @Nullable String firstName, @Named("lastName") @Nullable String lastName,  @Nullable UserImageEntity userImage) {
        SetUserDetailsEntity response = new SetUserDetailsEntity();
        Connection connection;
        try {
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
                connection = DriverManager.getConnection("jdbc:google:mysql://yapnak-app:yapnak-main/yapnak_main?user=root");
            } else {
                // Local MySQL instance to use during development.
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://173.194.230.210/yapnak_main", "client", "g7lFVLRzYdJoWXc3");
            }
            queryBlock:
            try {
                String query = "SELECT COUNT(*) FROM user WHERE userID = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, userId);
                logger.info("Search for user: " + userId);
                ResultSet rs = statement.executeQuery();
                rs.next();
                if (rs.getInt(1) == 0) {
                    //userID not found
                    response.setStatus("False");
                    response.setMessage("userId not found");
                    logger.warning("userId not found");
                    break queryBlock;
                }
                //user exists, update details
                logger.info("user exists, updating details");
                //need users authenticated?
                if (email != null) {
                    //Update email
                    logger.info("updating email to: " + email);
                    query = "UPDATE user SET email = ? where userID = ?";
                    statement = connection.prepareStatement(query);
                    statement.setString(1, email);
                    statement.setString(2, userId);
                    int success = statement.executeUpdate();
                    if (success == -1) {
                        response.setStatus("False");
                        response.setMessage("Failed to update email");
                        logger.warning("Failed to update email");
                        break queryBlock;
                    }
                }
                if (mobNo != null) {
                    //Update mobile number
                    logger.info("updating mobile number to: " + mobNo);
                    query = "UPDATE user SET mobNo = ? where userID = ?";
                    statement = connection.prepareStatement(query);
                    statement.setString(1, mobNo);
                    statement.setString(2, userId);
                    int success = statement.executeUpdate();
                    if (success == -1) {
                        response.setStatus("False");
                        response.setMessage("Failed to update mobile number");
                        logger.warning("Failed to update mobile number");
                        break queryBlock;
                    }
                    logger.info("updated mobile number");
                }
                if (password != null) {
                    //Update password
                    logger.info("updating password to: " + hashPassword(password));
                    query = "UPDATE user SET password = ? where userID = ?";
                    statement = connection.prepareStatement(query);
                    statement.setString(1, hashPassword(password));
                    statement.setString(2, userId);
                    int success = statement.executeUpdate();
                    if (success == -1) {
                        response.setStatus("False");
                        response.setMessage("Failed to update password");
                        logger.warning("Failed to update password");
                        break queryBlock;
                    }
                    logger.info("updated password");
                }
                if (dateOfBirth != null) {
                    try {
                        //Update date of birth
                        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                        Date parsed = format.parse(dateOfBirth);
                        java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());
                        logger.info("updating date of birth to: " + sqlDate);
                        query = "UPDATE user SET dateOfBirth = ? where userID = ?";
                        statement = connection.prepareStatement(query);
                        statement.setDate(1, sqlDate);
                        statement.setString(2, userId);
                        int success = statement.executeUpdate();
                        if (success == -1) {
                            response.setStatus("False");
                            response.setMessage("Failed to update date of birth");
                            logger.warning("Failed to update date of birth");
                            break queryBlock;
                        }
                        logger.info("updated date of birth");
                    } catch (ParseException e) {
                        e.printStackTrace();
                        logger.warning("ParseException:" + e);
                        response.setStatus("False");
                        response.setMessage("Date of birth parsing exception");
                        break queryBlock;
                    }
                }
                if (firstName != null) {
                    //Update first name
                    logger.info("updating first name to: " + firstName);
                    query = "UPDATE user SET firstName = ? where userID = ?";
                    statement = connection.prepareStatement(query);
                    statement.setString(1, firstName);
                    statement.setString(2, userId);
                    int success = statement.executeUpdate();
                    if (success == -1) {
                        response.setStatus("False");
                        response.setMessage("Failed to update first name");
                        logger.warning("Failed to update first name");
                        break queryBlock;
                    }
                    logger.info("updated first name");
                }
                if (lastName != null) {
                    //Update last name
                    logger.info("updating last name to: " + lastName);
                    query = "UPDATE user SET lastName = ? where userID = ?";
                    statement = connection.prepareStatement(query);
                    statement.setString(1, lastName);
                    statement.setString(2, userId);
                    int success = statement.executeUpdate();
                    if (success == -1) {
                        response.setStatus("False");
                        response.setMessage("Failed to update last name");
                        logger.warning("Failed to update last name");
                        break queryBlock;
                    }
                    logger.info("updated last name");
                }
                if (userImage.getImageString() != null || userImage.getImageUrl() != null) {
                    //Update user image
                    //TODO: implement cloud storage saving and recording the blobstore URL to the database
                    if (true) {
                        response.setStatus("Failed");
                        response.setMessage("Functionality to upload pictures is not implemented");
                        logger.warning("Failed to update user image");
                        break queryBlock;
                    }
                    logger.info("updated user image");
                }
                logger.info("Updates successful");
                response.setStatus("True");
            } finally {
                connection.close();
                return response;
            }
        } finally {
            return response;
        }
    }

    @ApiMethod(
            name = "getClients",
            path = "getClients",
            httpMethod = ApiMethod.HttpMethod.GET)
    public OfferListEntity getClients(@Named("longitude") double longitude, @Named("latitude") double latitude) {
        OfferListEntity response = new OfferListEntity();
        OfferEntity offer;
        List<OfferEntity> list = new ArrayList<OfferEntity>();
        Connection connection;
        double distance = 0.1;
        try {
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
                connection = DriverManager.getConnection("jdbc:google:mysql://yapnak-app:yapnak-main/yapnak_main?user=root");
            } else {
                // Local MySQL instance to use during development.
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://173.194.230.210/yapnak_main", "client", "g7lFVLRzYdJoWXc3");
            }
            queryBlock:
            try {
                String statement = "SELECT clientName,clientX,clientY,clientFoodStyle,clientPhotoUrl,client.clientID,offers.offerText offer,offers.offerID FROM client JOIN offers ON client.clientID=offers.clientID AND offers.isActive = 1 AND offers.showOffer = 1 WHERE clientX BETWEEN ? AND ? AND clientY BETWEEN ? AND ?";
                PreparedStatement stmt = connection.prepareStatement(statement);
                double t = longitude - distance;
                stmt.setDouble(1, t);
                t = longitude + distance;
                stmt.setDouble(2, t);
                t = latitude - distance;
                stmt.setDouble(3, t);
                t = latitude + distance;
                stmt.setDouble(4, t);
                logger.info("search for clients at (lng/lat): " + longitude + " : " + latitude);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    rs.beforeFirst();
                    while (rs.next()) {
                        logger.info("Retrieving client: " + rs.getString("clientName"));
                        offer = new OfferEntity();
                        offer.setOfferId(rs.getInt("offerID"));
                        offer.setClientId(rs.getInt("clientID"));
                        offer.setClientName(rs.getString("clientName"));
                        offer.setOfferText(rs.getString("offer"));
                        offer.setLongitude(rs.getDouble("clientX"));
                        offer.setLatitude(rs.getDouble("clientY"));
                        offer.setFoodStyle(rs.getString("clientFoodStyle"));
                        offer.setClientPhoto(rs.getString("clientPhotoUrl"));
                        list.add(offer);
                    }
                    //sort list here by top three priority and then distance
                    response.setOfferList(list);
                    response.setStatus("True");
                    response.setFoundOffers(true);
                } else {
                    //No clients found
                    response.setStatus("True");
                    logger.info("No clients found");
                    response.setFoundOffers(false);
                    break queryBlock;
                }
            } finally {
                connection.close();
                return response;
            }
        } finally {
            return response;
        }
    }

    @ApiMethod(
            name = "searchClients",
            path = "searchClients",
            httpMethod = ApiMethod.HttpMethod.GET)
    public OfferListEntity searchClients(@Named("searchString") String searchString) {
        OfferListEntity response = new OfferListEntity();
        OfferEntity offer;
        List<OfferEntity> list = new ArrayList<OfferEntity>();
        Connection connection;
        try {
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
                connection = DriverManager.getConnection("jdbc:google:mysql://yapnak-app:yapnak-main/yapnak_main?user=root");
            } else {
                // Local MySQL instance to use during development.
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://173.194.230.210/yapnak_main", "client", "g7lFVLRzYdJoWXc3");
            }
            queryBlock:
            try {
                String query = "SELECT clientName,clientX,clientY,clientFoodStyle,clientPhotoUrl,client.clientID,offers.offerText offer,offers.offerID FROM client JOIN offers ON client.clientID=offers.clientID AND offers.isActive = 1 AND offers.showOffer = 1 WHERE clientName LIKE ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, "%" + searchString + "%");
                logger.info("Searching for clients similar to: " + searchString);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    rs.beforeFirst();
                    while (rs.next()) {
                        logger.info("Retrieving client: " + rs.getString("clientName"));
                        offer = new OfferEntity();
                        offer.setOfferId(rs.getInt("offerID"));
                        offer.setClientId(rs.getInt("clientID"));
                        offer.setClientName(rs.getString("clientName"));
                        offer.setOfferText(rs.getString("offer"));
                        offer.setLongitude(rs.getDouble("clientX"));
                        offer.setLatitude(rs.getDouble("clientY"));
                        offer.setFoodStyle(rs.getString("clientFoodStyle"));
                        offer.setClientPhoto(rs.getString("clientPhotoUrl"));
                        list.add(offer);
                    }
                    //sort list here by top three priority and then distance
                    response.setOfferList(list);
                    response.setStatus("True");
                    response.setFoundOffers(true);
                } else {
                    //No clients found
                    response.setStatus("True");
                    logger.info("No clients found");
                    response.setFoundOffers(false);
                    break queryBlock;
                }
            } finally {
                connection.close();
                return response;
            }
        } finally {
            return response;
        }
    }

    @ApiMethod(
            name = "favouriteOffer",
            path = "favouriteOffer",
            httpMethod = ApiMethod.HttpMethod.POST)
    public FavouriteOfferEntity favouriteOffer(@Named("userId") String userId, @Named("offerId") int offerId) {
        FavouriteOfferEntity response = new FavouriteOfferEntity();
        Connection connection;
        try {
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
                connection = DriverManager.getConnection("jdbc:google:mysql://yapnak-app:yapnak-main/yapnak_main?user=root");
            } else {
                // Local MySQL instance to use during development.
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://173.194.230.210/yapnak_main", "client", "g7lFVLRzYdJoWXc3");
            }
            queryBlock:
            try {
                String query = "REPLACE favourites (userID,offerID) VALUES (?,?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, userId);
                statement.setInt(2, offerId);
                logger.info("Adding offer: " + offerId + " to favourites of: " + userId);
                int success = statement.executeUpdate();
                if (success == -1) {
                    response.setStatus("False");
                    response.setMessage("Favourites insertion failed");
                    logger.warning("Favourites insertion failed");
                    break queryBlock;
                }
                logger.info("Favourites insertion success");
                response.setStatus("True");
            } finally {
                connection.close();
                return response;
            }
        } finally {
            return response;
        }

    }

    @ApiMethod(
            name = "removeFavouriteOffer",
            path = "removeFavouriteOffer",
            httpMethod = ApiMethod.HttpMethod.POST)
    public FavouriteOfferEntity removeFavouriteOffer(@Named("userId") String userId, @Named("offerId") int offerId) {
        FavouriteOfferEntity response = new FavouriteOfferEntity();
        Connection connection;
        try {
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
                connection = DriverManager.getConnection("jdbc:google:mysql://yapnak-app:yapnak-main/yapnak_main?user=root");
            } else {
                // Local MySQL instance to use during development.
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://173.194.230.210/yapnak_main", "client", "g7lFVLRzYdJoWXc3");
            }
            queryBlock:
            try {
                String query = "DELETE FROM favourites WHERE userID = ? AND offerID = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, userId);
                statement.setInt(2, offerId);
                logger.info("removing offer: " + offerId + " from the favourites of: " + userId);
                int success = statement.executeUpdate();
                if (success == -1) {
                    response.setStatus("False");
                    response.setMessage("Favourites removal failed");
                    logger.warning("Favourites removal failed");
                    break queryBlock;
                }
                logger.info("Favourites removal success");
                response.setStatus("True");
            } finally {
                connection.close();
                return response;
            }
        } finally {
            return response;
        }

    }

    @ApiMethod(
            name = "feedback",
            path = "feedback",
            httpMethod = ApiMethod.HttpMethod.POST)
    public FeedbackEntity feedback(@Named("userId") String userId, @Named("clientId") int clientId, @Named("offerId") int offerId, @Named("rating") int rating, @Named("comment") @Nullable String comment, @Named("isAccepted") boolean isAccepted) {
        FeedbackEntity response = new FeedbackEntity();
        Connection connection;
        try {
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
                connection = DriverManager.getConnection("jdbc:google:mysql://yapnak-app:yapnak-main/yapnak_main?user=root");
            } else {
                // Local MySQL instance to use during development.
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://173.194.230.210/yapnak_main", "client", "g7lFVLRzYdJoWXc3");
            }
            queryBlock:
            try {
                logger.info("feedback from: " + userId);
                if (isAccepted == false) {
                    //Offer not accepted, notify us
                    logger.info("Offer was not accepted!!!");
                    Properties props = new Properties();
                    Session session = Session.getDefaultInstance(props, null);
                    javax.mail.Message msg = new MimeMessage(session);
                    String query = "SELECT email, clientName FROM client where clientID = ? ";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, clientId);
                    ResultSet rs = statement.executeQuery();
                    if (rs.next()) {
                        //clientId found, send an email to us about the problem
                        logger.info("Retrieved client: " + rs.getString("clientName"));
                        msg.setFrom(new InternetAddress("yapnak.uq@gmail.com", "Yapnak"));
                        msg.addRecipient(javax.mail.Message.RecipientType.TO,
                                new InternetAddress("yapnak.uq@gmail.com", "Yapnak"));
                        msg.setSubject("Negative feedback - client didn't accept code");
                        msg.setText("From: " + userId + " regarding client: " + rs.getString("clientName") + ", " + rs.getString("email") + " on offerId: " + offerId + " - " + comment);
                        Transport.send(msg);
                    } else {
                        //clientId not found
                        logger.info("client not found");
                        response.setStatus("False");
                        response.setMessage("ClientId not found");
                        break queryBlock;
                    }
                }
                if (rating > 5) {
                    response.setStatus("False");
                    response.setMessage("Rating value out of range");
                    break queryBlock;
                }
                String query = "REPLACE feedback (userID, clientID, offerID, feedback, rating) VALUES (?,?,?,?,?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, userId);
                statement.setInt(2, clientId);
                statement.setInt(3, offerId);
                statement.setString(4, (comment != null) ? comment : "");
                statement.setInt(5, rating);
                logger.info("inserting feedback " + statement.toString());
                int success = statement.executeUpdate();
                if (success == -1) {
                    //insert failed
                    response.setStatus("False");
                    response.setMessage("Feedback insert failed");
                    logger.warning("Feedback insert FAILED");
                    break queryBlock;
                }
                logger.info("Feedback inserted success");
                response.setStatus("True");
            } finally {
                connection.close();
                return response;
            }
        } finally {
            return response;
        }
    }

    @ApiMethod(
            name = "recommend",
            path = "recommend",
            httpMethod = ApiMethod.HttpMethod.POST)
    public RecommendEntity recommend(@Named("userId") String userId, @Named("otherUserId") @Nullable String otherUserId, @Named("otherMobNo") @Nullable String otherMobNo, @Named("clientId") int clientId) {
        RecommendEntity response = new RecommendEntity();
        response.setStatus("True");
        Connection connection;
        try {
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
                connection = DriverManager.getConnection("jdbc:google:mysql://yapnak-app:yapnak-main/yapnak_main?user=root");
            } else {
                // Local MySQL instance to use during development.
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://173.194.230.210/yapnak_main", "client", "g7lFVLRzYdJoWXc3");
            }
            queryBlock:
            try {
                String details;
                String detailsAttribute;
                String query;
                boolean isMob = false;
                if (otherUserId != null) {
                    details = otherUserId;
                    query = "SELECT COUNT(*) FROM user WHERE userID = ?";
                } else if (otherMobNo != null) {
                    details = otherMobNo;
                    isMob = true;
                    query = "SELECT COUNT(*) FROM user WHERE mobNo = ?";
                } else {
                    //Details not provided
                    logger.info("Other user details have not been provided");
                    response.setStatus("False");
                    response.setMessage("Other user details have not been provided");
                    break queryBlock;
                }
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, details);
                ResultSet rs = statement.executeQuery();
                rs.next();
                if (rs.getInt(1) < 1) {
                    //User is not registered
                    if (isMob) {
                        //send a recommendation sms
                        response.setStatus("True");
                        response.setResult(0);
                        logger.info("User not found, sending recommendation SMS");
                        break queryBlock;
                    }
                    //User is not in the system.
                    response.setStatus("False");
                    response.setResult(-1);
                    response.setMessage("User not found in the system");
                    logger.info("User not found in the system");
                    break queryBlock;
                }
                //User found
                logger.info("User " + userId + " is recommending " + otherUserId + " to eat at " + clientId);
                query = "SELECT COUNT(*) FROM recommend WHERE userID = ? AND clientID = ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, userId);
                statement.setInt(2, clientId);
                rs = statement.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {
                    //User has already been recommended to that client
                    response.setStatus("True");
                    response.setResult(2);
                    logger.info("Other user has already been recommended to that client");
                    break queryBlock;
                }
                //Insert recommendation details
                query = "REPLACE recommend (userID, clientID, referrerID) VALUES (?,?,?)";
                statement = connection.prepareStatement(query);
                statement.setString(1, userId);
                statement.setInt(2, clientId);
                statement.setString(3, details);
                int success = statement.executeUpdate();
                if (success == -1) {
                    //Insert update failed
                    response.setStatus("False");
                    response.setMessage("Inserting recommendation failed");
                    response.setResult(-1);
                    logger.warning("Inserting recommendation failed");
                    break queryBlock;
                }
                response.setStatus("True");
                response.setResult(1);
            } finally {
                connection.close();
                return response;
            }
        } finally {
            return response;
        }
    }

    @ApiMethod(
            name = "searchUsers",
            path = "searchUsers",
            httpMethod = ApiMethod.HttpMethod.POST)
    public SearchUserEntity searchUsers(@Named("details") String[] details) {
        SearchUserEntity user = new SearchUserEntity();
        Connection connection;
        try {
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
                connection = DriverManager.getConnection("jdbc:google:mysql://yapnak-app:yapnak-main/yapnak_main?user=root");
            } else {
                // Local MySQL instance to use during development.
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://173.194.230.210/yapnak_main", "client", "g7lFVLRzYdJoWXc3");
            }
            try {
                String statement = "SELECT COUNT(*) FROM user WHERE email = ? OR mobNo = ?";
                PreparedStatement stmt = connection.prepareStatement(statement);
                ResultSet rs;
                List<Integer> isUser = new ArrayList<Integer>();
                for (int i = 0; i < details.length; i++) {
                    stmt.setString(1, details[i]);
                    stmt.setString(2, details[i]);
                    rs = stmt.executeQuery();
                    rs.next();
                    isUser.add(rs.getInt(1));
                }
                user.setStatus("True");
                user.setIsUser(isUser);
                logger.info("Successfully searched for users");
            } finally {
                connection.close();
                return user;
            }
        } catch (ClassNotFoundException e) {
            user.setStatus("False");
            user.setMessage("ClassNotFoundException");
            e.printStackTrace();
        } catch (SQLException e) {
            user.setStatus("False");
            user.setMessage("SQLException");
            e.printStackTrace();
        } finally {
            return user;
        }
    }
}