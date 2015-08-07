package com.yapnak.gcmbackend;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.utils.SystemProperty;
import com.googlecode.objectify.ObjectifyService;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.inject.Named;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "sQLEntityApi",
        version = "v1",
        resource = "sQLEntity",
        namespace = @ApiNamespace(
                ownerDomain = "gcmbackend.yapnak.com",
                ownerName = "gcmbackend.yapnak.com",
                packagePath = ""
        ),
        scopes = {Constants.EMAIL_SCOPE},
        clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE})

public class SQLEntityEndpoint {

    private static final Logger logger = Logger.getLogger(SQLEntityEndpoint.class.getName());

    private static final String API_KEY = System.getProperty("gcm.api.key");

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(SQLEntity.class);
    }

    @ApiMethod(
            name = "getUser",
            httpMethod = ApiMethod.HttpMethod.POST)
    public PointsEntity getUser(@Named("userID") String userID, @Named("clientEmail") String clientEmail) {
        Connection connection;
        PointsEntity points = new PointsEntity();
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
                String statement = "SELECT userID, pushKey FROM user where userID = ?";
                PreparedStatement stmt = connection.prepareStatement(statement);
                stmt.setString(1, userID);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    logger.info("found user");

                    //push notification
                    String message = "you have gained points!";
                    Sender sender = new Sender(API_KEY);
                    Message msg = new Message.Builder().addData("message", message).build();
                    Result result = sender.send(msg, rs.getString("pushKey"), 5);

                    statement = "SELECT clientID from client where email = ?";
                    stmt = connection.prepareStatement(statement);
                    stmt.setString(1, clientEmail);
                    rs = stmt.executeQuery();
                    rs.next();
                    points.setClientID(rs.getInt("clientID"));
                    points.setUserID(userID);
                    statement = "SELECT points FROM points where userID = ? AND clientID = ?";
                    stmt = connection.prepareStatement(statement);
                    stmt.setString(1, userID);
                    stmt.setInt(2, rs.getInt("clientID"));
                    rs = stmt.executeQuery();
                    if (rs.next()) {
                        points.setPoints(rs.getInt("points") + 5);
                        //change the number here to adjust points given)
                        logger.info("points: " + points.getPoints());
                        statement = "UPDATE points SET points = ? where userID = ? AND clientID = ?";
                        stmt = connection.prepareStatement(statement);
                        stmt.setInt(1, points.getPoints());
                        stmt.setString(2, points.getUserID());
                        stmt.setInt(3, points.getClientID());
                        stmt.executeUpdate();
                        connection.close();
                        return points;
                    } else {
                        logger.info("creating " + points.getPoints() + " " + points.getUserID() + " " + points.getClientID());
                        statement = "INSERT INTO points (points,userID,clientID) VALUES (?,?,?)";
                        stmt = connection.prepareStatement(statement);
                        //number of points per visit
                        stmt.setInt(1, 5);
                        stmt.setString(2, points.getUserID());
                        stmt.setInt(3, points.getClientID());
                        stmt.executeUpdate();
                        points.setPoints(5);
                        connection.close();
                        return points;
                    }

                } else {
                    logger.info("couldn't find user");
                    points = null;
                }
            } finally {
                connection.close();
                return points;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return points;
        }
    }


    /**
     * Returns the {@link SQLEntity} with the corresponding ID.
     *
     * @param x is the x coordinate of the user
     * @param y is the y coordinate of the user
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code SQLEntity} with the provided ID.
     */
    @ApiMethod(
            name = "getClients",
            path = "getClients",
            httpMethod = ApiMethod.HttpMethod.GET)
    public SQLList getClients(@Named("longitude") double x, @Named("latitude") double y) throws NotFoundException, OAuthRequestException {
/*        if (user == null) {
            throw new OAuthRequestException("User is not valid " + user);
        }*/
        Connection connection;
        double distance = 0.04;
        List<SQLEntity> list = new ArrayList<SQLEntity>();
        SQLEntity sql = new SQLEntity();
        SQLList sqlList = new SQLList();
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
            String statement = "SELECT clientName,clientX,clientY,clientOffer,clientFoodStyle,clientPhoto,rating,clientID FROM client WHERE clientX BETWEEN ? AND ? AND clientY BETWEEN ? AND ?";
            PreparedStatement stmt = connection.prepareStatement(statement);
            double t = x - distance;
            stmt.setDouble(1, t);
            t = x + distance;
            stmt.setDouble(2, t);
            t = y - distance;
            stmt.setDouble(3, t);
            t = y + distance;
            stmt.setDouble(4, t);
            ResultSet rs = stmt.executeQuery();
            rs.last();
            logger.info("number of results: " + rs.getRow());
            int p = rs.getRow();
            rs.beforeFirst();
            ResultSet rt = null;
            for (int i = 0; i < p; i++) {
                rs.next();
                sql = new SQLEntity();
                sql.setName(rs.getString("clientName"));
                sql.setOffer(rs.getString("clientOffer"));
                sql.setX(rs.getDouble("clientX"));
                sql.setY(rs.getDouble("clientY"));
                sql.setFoodStyle(rs.getString("clientFoodStyle"));
                //get photo from blobstore
                String url = null;
                if (rs.getString("clientPhoto") != null) {
                    logger.info("photo: " + rs.getString("clientPhoto"));
                    if (SystemProperty.environment.value() ==
                            SystemProperty.Environment.Value.Production) {
                        ImagesService services = ImagesServiceFactory.getImagesService();
                        ServingUrlOptions serve = ServingUrlOptions.Builder.withBlobKey(new BlobKey(rs.getString("clientPhoto")));    // Blobkey of the image uploaded to BlobStore.
                        url = services.getServingUrl(serve);
                        url = url + "=s100";
                    } else {
                        url = rs.getString("clientPhoto");
                    }
                } else {
                    logger.info("no photo found");
                    url = "http://pcsclite.alioth.debian.org/ccid/img/no_image.png";
                }
                sql.setPhoto(url);
                sql.setRating((rs.getDouble("rating")));
                statement = "SELECT points FROM points WHERE clientID = ? and userID = ?";
                stmt = connection.prepareStatement(statement);
                stmt.setInt(1,rs.getInt("clientID"));
                //TODO:put in user name here
                stmt.setString(2,"3333");
                rt = stmt.executeQuery();
                if (rt.next()) {
                    sql.setPoints(rt.getInt("points"));
                }
                else {
                    sql.setPoints(0);
                }
                list.add(sql);
            }

            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sqlList.setList(list);
            return sqlList;
        }
    }

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

    // A password hashing method.
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

    static String secureInt() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32).substring(5,9);
    }

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

    /**
     * Inserts a new {@code SQLEntity}.
     */
    @ApiMethod(
            name = "insertUser",
            path = "insertUser",
            httpMethod = ApiMethod.HttpMethod.POST)
    public void insert(@Named("email") String email, @Named("password") String password) {

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
//                String url = "jdbc:mysql://localhost:3306/yapnak_main?user=client&password=g7lFVLRzYdJoWXc3";
//                connection = DriverManager.getConnection(url);
                connection = DriverManager.getConnection("jdbc:mysql://173.194.230.210/yapnak_main", "client", "g7lFVLRzYdJoWXc3");
                // Alternatively, connect to a Google Cloud SQL instance using:
                // jdbc:mysql://ip-address-of-google-cloud-sql-instance:3306/guestbook?user=root
            }
            int success = -1;
            try {
                String statement = "INSERT INTO user (userID, email, password) VALUES(?,?,?)";
                PreparedStatement stmt = connection.prepareStatement(statement);

                //Generate userID
                String userID = "";
                userID = email.substring(0,4) + randInt();

                //Generate password
                String newPassword = hashPassword(email);

                stmt.setString(1, userID);
                stmt.setString(2, email);
                stmt.setString(3, newPassword);
                success = stmt.executeUpdate();
                if (success == -1) {
                    logger.warning("Inserting user failed");
                }
                else {
                    logger.info("Successfully inserted the user");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}