package com.yapnak.gcmbackend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.utils.SystemProperty;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "clientEndpointApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "gcmbackend.yapnak.com",
                ownerName = "gcmbackend.yapnak.com",
                packagePath = ""
        )
)
public class ClientEndpoint {

    private static final Logger logger = Logger.getLogger(ClientEndpoint.class.getName());

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

    @ApiMethod(
            name = "getAllOffers",
            path = "getAllOffers",
            httpMethod = ApiMethod.HttpMethod.GET)
    public ClientOfferListEntity getAllOffers(@Named("clientId") int clientId) {
        ClientOfferListEntity response = new ClientOfferListEntity();
        ClientOfferEntity offer;
        List<ClientOfferEntity> list = new ArrayList<ClientOfferEntity>();
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
                String query = "SELECT offerID, offerText FROM offers WHERE clientID = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, clientId);
                logger.info("Searching for offers from " + clientId);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    logger.info("Found offer " + rs.getInt("offerID"));
                    offer = new ClientOfferEntity();
                    offer.setOfferId(rs.getInt("offerID"));
                    offer.setOfferText(rs.getString("offerText"));
                    list.add(offer);
                }
                response.setOfferList(list);
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
            name = "replaceActiveOffer",
            path = "replaceActiveOffer",
            httpMethod = ApiMethod.HttpMethod.POST)
    public SimpleEntity replaceActiveOffer(@Named("email") String email, @Named("currentOfferId") int currentOfferId, @Named("newOfferId") int newOfferId, @Named("offerPosition") int offerPosition) {
        SimpleEntity response = new SimpleEntity();
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
                String query = "UPDATE client SET offer" + offerPosition + " = ? WHERE email = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, newOfferId);
                statement.setString(2, email);
                logger.info("Replacing offer " + currentOfferId + " with " + newOfferId + " at position " + offerPosition);
                int success = statement.executeUpdate();
                if (success == -1) {
                    logger.warning("Replacing active offer failed");
                    response.setStatus("False");
                    response.setMessage("Replacing active offer failed");
                    break queryBlock;
                }
                logger.info("Replacing offer successful");
                query = "UPDATE offers SET isActive = ?, showOffer = ? WHERE offerID = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, 0);
                statement.setInt(2, 0);
                statement.setInt(3, currentOfferId);
                success = statement.executeUpdate();
                if (success == -1) {
                    logger.warning("Updating offers table failed on setting inactive");
                    response.setStatus("False");
                    response.setMessage("Replacing active offer failed on turning current offer inactive");
                    break queryBlock;
                }
                statement.setInt(1, 1);
                statement.setInt(2, 1);
                statement.setInt(3, newOfferId);
                success = statement.executeUpdate();
                if (success == -1) {
                    logger.warning("Updating offers table failed on setting active");
                    response.setStatus("False");
                    response.setMessage("Replacing active offer failed on turning new offer active");
                    break queryBlock;
                }
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
            name = "getClientInfo",
            path = "getClientInfo",
            httpMethod = ApiMethod.HttpMethod.GET)
    public ClientEntity getClientInfo(@Named("email") String email) {
        ClientEntity client = new ClientEntity();
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
                String statement = "SELECT client.clientID, clientName, clientX, clientY, clientFoodStyle, clientPhotoUrl, client.offer1,client.offer2,client.offer3, client.isActive, offers.offerID, offers.offerText offer, offers.showOffer showOffer FROM client JOIN offers ON client.clientID=offers.clientID WHERE client.email = ? AND offers.isActive = 1";
                PreparedStatement stmt = connection.prepareStatement(statement);
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    //client found
                    logger.info("retrieving client data: " + rs.getString("clientName"));
                    client.setStatus("True");
                    client.setId(rs.getInt("clientID"));
                    client.setName(rs.getString("clientName"));
                    client.setX(rs.getDouble("clientX"));
                    client.setY(rs.getDouble("clientY"));
                    client.setFoodStyle(rs.getString("clientFoodStyle"));
                    client.setPhoto(rs.getString("clientPhotoUrl"));
                    client.setIsActive(rs.getInt("isActive"));
                    do {
                        if (rs.getInt("offerID") == rs.getInt("offer1")) {
                            logger.info("found offer 1: " + rs.getString("offer"));
                            client.setShowOffer1(rs.getInt("showOffer"));
                            client.setOffer1(rs.getString("offer"));
                            client.setOffer1Id(rs.getInt("offerID"));

                        } else if (rs.getInt("offerID") == rs.getInt("offer2")) {
                            logger.info("found offer 2: " + rs.getString("offer"));
                            client.setShowOffer2(rs.getInt("showOffer"));
                            client.setOffer2(rs.getString("offer"));
                            client.setOffer2Id(rs.getInt("offerID"));

                        } else {
                            logger.info("found offer 3: " + rs.getString("offer"));
                            client.setShowOffer3(rs.getInt("showOffer"));
                            client.setOffer3(rs.getString("offer"));
                            client.setOffer3Id(rs.getInt("offerID"));
                        }
                    } while (rs.next());

                } else {
                    //client not found
                    client.setStatus("False");
                    client.setMessage("No client found with that email");
                }
            } finally {
                connection.close();
                return client;
            }
        } finally {
            return client;
        }
    }

    @ApiMethod(
            name = "authenticateClient",
            path = "authenticateClient",
            httpMethod = ApiMethod.HttpMethod.POST)
    public ClientAuthEntity authenticateClient(@Named("email") String email, @Named("password") String password) {
        ClientAuthEntity response = new ClientAuthEntity();
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
                logger.info("Beginning authentication for client " + email);
                String query = "SELECT clientID FROM client WHERE email = ? AND password = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, email);
                statement.setString(2, hashPassword(password));
                ResultSet rs = statement.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {
                    logger.info("Authenticated client");
                    response.setStatus("True");
                    logger.info("ID " + rs.getInt("clientID"));
                    response.setClientId(rs.getInt("clientID"));
                } else {
                    logger.info("Incorrect client details");
                    response.setStatus("False");
                    response.setMessage("Incorrect client details");
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
            name = "getRemeptionForUser",
            path = "getRemeptionForUser",
            httpMethod = ApiMethod.HttpMethod.POST)
    public LoyaltyRedeemEntity getRemeptionForUser(@Named("userId") String userId) {
        LoyaltyRedeemEntity response = new LoyaltyRedeemEntity();
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
                String query = "SELECT loyaltyPoints FROM user WHERE userID = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, userId);
                ResultSet rs = statement.executeQuery();
                if (!rs.next()) {
                    //User doesn't exist
                    logger.warning("UserId " + userId + " not found");
                    response.setStatus("False");
                    response.setMessage("UserId not found");
                    break queryBlock;
                }
                int points = rs.getInt("loyaltyPoints");
                if (points >= 200) {
                    //Got gold
                    response.setStatus("True");
                    response.setLoyaltyRedeemedLevel(3);
                    points -= 200;
                } else if (points >= 120) {
                    //Got silver
                    response.setStatus("True");
                    response.setLoyaltyRedeemedLevel(2);
                    points -= 120;
                } else if (points >= 60) {
                    //Got bronze
                    response.setStatus("True");
                    response.setLoyaltyRedeemedLevel(1);
                    points -= 60;
                } else {
                    //Got red
                    response.setStatus("True");
                    response.setLoyaltyRedeemedLevel(0);
                    break queryBlock;
                }
                query = "UPDATE user SET loyaltyPoints = ? where userID = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, points);
                statement.setString(2, userId);
                int success = statement.executeUpdate();
                if (success == -1) {
                    //update failed
                    logger.warning("Updating points failed");
                    response.setStatus("False");
                    response.setMessage("Updating points failed");
                    break queryBlock;
                }
                response.setLoyaltyPoints(points);
            } finally {
                connection.close();
                return response;
            }
        } finally {
            return response;
        }
    }

}