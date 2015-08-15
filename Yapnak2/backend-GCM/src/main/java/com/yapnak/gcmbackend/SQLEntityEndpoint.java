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

import java.io.UnsupportedEncodingException;
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
import java.util.Properties;
import java.util.Random;
import java.util.logging.Logger;

import javax.inject.Named;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
            path = "getUser",
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
                    String message = "You've gained points, nice one.";
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

    @ApiMethod(
            name = "forgotLogin",
            path = "forgotLogin",
            httpMethod = ApiMethod.HttpMethod.POST)
    public VoidEntity forgotLogin(@Named("email") String email) throws ClassNotFoundException, SQLException {
        VoidEntity voidEntity = new VoidEntity();
        Connection connection;
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
            String statement = "SELECT COUNT(email) AS count from client where email = ?";
            PreparedStatement stmt = connection.prepareStatement(statement);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                voidEntity.setStatus("True");
                //Gather hashes and send email
                String reset = hashPassword(String.valueOf(randInt()));
                String cancel = hashPassword(String.valueOf(randInt()));
                statement = "REPLACE forgot (email,reset,cancel,time) VALUES (?,?,?,CURDATE())";
                stmt = connection.prepareStatement(statement);
                stmt.setString(1, email);
                stmt.setString(2, reset);
                stmt.setString(3, cancel);
                stmt.executeUpdate();
                String subject = "Yapnak password reset";
                String message = "Hi,\n\nWe have received a request to reset the password on your Yapnak account.\n\nTo reset, click: www.yapnak.com/resetPassword?response=" + reset + "\n\nThis link will be active for one day.\n\nIf you didn't request this email, click here: www.yapnak.com/cancelReset?response=" + cancel + "\n\nKind regards,\nthe Yapnak team.";
                sendEmail(email,subject,message);
            } else {
                voidEntity.setStatus("False");
                voidEntity.setMessage("Email not found");
            }
        } finally {
            connection.close();
            return voidEntity;
        }
    }

    @ApiMethod(
            name = "resetPassword",
            path = "resetPassword",
            httpMethod = ApiMethod.HttpMethod.POST)
    public VoidEntity resetPassword(@Named("password") String password, @Named("hash") String hash) throws ClassNotFoundException, SQLException {
        VoidEntity voidEntity = new VoidEntity();
        Connection connection;

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

            String statement = "SELECT email FROM forgot WHERE reset = ?";
            PreparedStatement stmt = connection.prepareStatement(statement);
            stmt.setString(1, hash);
            ResultSet rs = stmt.executeQuery();
            rs.next();

            statement = "UPDATE client SET password = ? WHERE email = ?";
            stmt = connection.prepareStatement(statement);
            stmt.setString(1, hashPassword(password));
            stmt.setString(2, rs.getString("email"));
            stmt.executeUpdate();

            statement = "DELETE FROM forgot WHERE reset = ?";
            stmt = connection.prepareStatement(statement);
            stmt.setString(1, hash);
            stmt.executeUpdate();

            voidEntity.setStatus("True");
            voidEntity.setMessage("");

            connection.close();
            return voidEntity;


    }

    static void sendEmail(String email, String subject, String message) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        try {
            javax.mail.Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("yapnak.uq@gmail.com", "Yapnak"));
            msg.addRecipient(javax.mail.Message.RecipientType.TO,
                    new InternetAddress(email));
            msg.setSubject(subject);
            msg.setText(message);
            Transport.send(msg);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
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
    public SQLList getClients(@Named("longitude") double x, @Named("latitude") double y, @Named("userID") String userID) throws NotFoundException, OAuthRequestException {

        Connection connection;
        double distance = 0.01;
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
            String statement = "SELECT clientName,clientX,clientY,clientOffer,clientFoodStyle,clientPhoto,rating,clientID,showOffer FROM client WHERE clientX BETWEEN ? AND ? AND clientY BETWEEN ? AND ?";
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
            ResultSet rt;
            if (rs.next()) {
                rs.beforeFirst();
                while (rs.next()) {
                    sql = new SQLEntity();
                    sql.setId(rs.getInt("clientID"));
                    sql.setName(rs.getString("clientName"));
                    sql.setOffer(rs.getString("clientOffer"));
                    sql.setX(rs.getDouble("clientX"));
                    sql.setY(rs.getDouble("clientY"));
                    sql.setRating((rs.getDouble("rating")));
                    sql.setFoodStyle(rs.getString("clientFoodStyle"));
                    sql.setShowOffer(rs.getInt("showOffer"));
                    //get photo from blobstore
                    String url;
                    if (!rs.getString("clientPhoto").equals("")) {
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
                        url = "http://pcsclite.alioth.debian.org/ccid/img/no_image.png";
                    }
                    sql.setPhoto(url);
                    statement = "SELECT points FROM points WHERE clientID = ? and userID = ?";
                    stmt = connection.prepareStatement(statement);
                    stmt.setInt(1, rs.getInt("clientID"));
                    //TODO:put in user name here
                    stmt.setString(2, userID);
                    rt = stmt.executeQuery();
                    if (rt.next()) {
                        sql.setPoints(rt.getInt("points"));
                    } else {
                        sql.setPoints(0);
                    }
                    list.add(sql);
                }
            } else {
                sql = new SQLEntity();
                sql.setOffer("No clients found nearby :(");
                sql.setName("Know any decent places?  Tell them to sign up!");
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

    /**
     * Returns the {@link SQLEntity} with the corresponding ID.
     *
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code SQLEntity} with the provided ID.
     */
    @ApiMethod(
            name = "getAllClients",
            path = "getAllClients",
            httpMethod = ApiMethod.HttpMethod.GET)
    public allList getAllClients() throws NotFoundException, OAuthRequestException {
/*        if (user == null) {
            throw new OAuthRequestException("User is not valid " + user);
        }*/
        Connection connection;
        List<all> list = new ArrayList<all>();
        ResultSet rs = null;
        all all = null;
        allList alllist = new allList();
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
            String statement = "SELECT * FROM client WHERE clientX BETWEEN -0.408929 AND -0.208929 AND clientY BETWEEN 51.58543 AND 51.78543";
            PreparedStatement stmt = connection.prepareStatement(statement);
            rs = stmt.executeQuery();
            while (rs.next()) {
                all = new all();
                all.setClientID(rs.getInt("clientID"));
                all.setEmail(rs.getString("email"));
                all.setPassword(rs.getString("password"));
                all.setAdmin(rs.getInt("admin"));
                all.setClientName(rs.getString("clientName"));
                all.setClientX(rs.getDouble("clientX"));
                all.setClientY(rs.getDouble("clientY"));
                all.setClientFoodStyle(rs.getString("clientFoodStyle"));
                all.setClientOffer(rs.getString("clientOffer"));
                all.setClientPhoto(rs.getString("clientPhoto"));
                all.setSalt(rs.getString("salt"));
                all.setRating(rs.getDouble("rating"));
                list.add(all);
            }
            connection.close();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            alllist.setList(list);
            return alllist;
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
        return new BigInteger(130, random).toString(32).substring(5, 9);
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
    public UserEntity insert(@Named("email") String email, @Named("password") String password) {

        Connection connection;
        UserEntity user = new UserEntity();
        try {
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
                connection = DriverManager.getConnection("jdbc:google:mysql://yapnak-app:yapnak-main/yapnak_main?user=root");
            } else {
                // Local MySQL instance kto use during development.
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://173.194.230.210/yapnak_main", "client", "g7lFVLRzYdJoWXc3");

            }
            int success = -1;
            try {
                String statement = "SELECT userID FROM user WHERE email = ?";
                PreparedStatement stmt = connection.prepareStatement(statement);
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    //user already exists with a google sign in
                    user.setUserID(rs.getString("userID"));
                } else {
                    statement = "INSERT INTO user (userID, email, password) VALUES(?,?,?)";
                    stmt = connection.prepareStatement(statement);

                    //Generate userID
                    String userID = "";
                    userID = email.substring(0, 4) + randInt();
                    user.setUserID(userID);
                    //Generate password
                    String newPassword = hashPassword(email);
                    stmt.setString(1, userID);
                    stmt.setString(2, email);
                    stmt.setString(3, newPassword);
                    success = stmt.executeUpdate();
                    if (success == -1) {
                        logger.warning("Inserting user failed");
                        user.setUserID("Failed");
                    } else {
                        logger.info("Successfully inserted the user");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.close();
                return user;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return user;
        }
    }

    @ApiMethod(
            name = "insertExternalUser",
            path = "insertExternalUser",
            httpMethod = ApiMethod.HttpMethod.POST)
    public UserEntity insertExternal(@Named("email") String email) throws ClassNotFoundException, SQLException {
        Connection connection;
        UserEntity user = new UserEntity();
        try {
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
                connection = DriverManager.getConnection("jdbc:google:mysql://yapnak-app:yapnak-main/yapnak_main?user=root");
            } else {
                // Local MySQL instance kto use during development.
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://173.194.230.210/yapnak_main", "client", "g7lFVLRzYdJoWXc3");
            }
            int success = -1;
            try {
                String statement = "SELECT userID FROM user WHERE email = ?";
                PreparedStatement stmt = connection.prepareStatement(statement);
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    //user already exists with a google sign in
                    logger.info("found user " + rs.getString("userID"));
                    user.setUserID(rs.getString("userID"));
                } else {
                    statement = "INSERT INTO user (userID, email) VALUES(?,?)";
                    stmt = connection.prepareStatement(statement);
                    //Generate userID
                    String userID = "";
                    userID = email.substring(0, 4) + randInt();
                    user.setUserID(userID);
                    stmt.setString(1, userID);
                    stmt.setString(2, email);
                    success = stmt.executeUpdate();
                    if (success == -1) {
                        logger.warning("Inserting user failed");
                        user.setUserID("Failed");
                    } else {
                        logger.info("Successfully inserted the user " + user.getUserID());
                    }
                }
            } finally {
                connection.close();
                return user;
            }
        } finally {
            return user;
        }
    }

    @ApiMethod(
            name = "feedback",
            path = "feedback",
            httpMethod = ApiMethod.HttpMethod.POST)
    public void feedback(@Named("type") int type, @Named("Message") String message, @Named("userID") String userID) throws UnsupportedEncodingException, MessagingException {

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        javax.mail.Message msg = new MimeMessage(session);
        switch (type) {
            //1 = positive
            case 1:
                props = new Properties();
                session = Session.getDefaultInstance(props, null);
                msg = new MimeMessage(session);
                try {
                    msg.setFrom(new InternetAddress("yapnak.uq@gmail.com", "Yapnak"));
                    msg.addRecipient(javax.mail.Message.RecipientType.TO,
                            new InternetAddress("yapnak.uq@gmail.com", "Yapnak"));
                    msg.setSubject("Positive feedback");
                    msg.setText("From: " + userID + " - " + message);
                    Transport.send(msg);
                } finally {
                }
                break;
            //1 = negative, general
            case 2:
                props = new Properties();
                session = Session.getDefaultInstance(props, null);
                msg = new MimeMessage(session);
                try {
                    msg.setFrom(new InternetAddress("yapnak.uq@gmail.com", "Yapnak"));
                    msg.addRecipient(javax.mail.Message.RecipientType.TO,
                            new InternetAddress("yapnak.uq@gmail.com", "Yapnak"));
                    msg.setSubject("Negative feedback");
                    msg.setText("From: " + userID + " - " + message);
                    Transport.send(msg);
                } finally {

                }
                break;
            //3 = negative, client didn't accept user code
            case 3:
                props = new Properties();
                session = Session.getDefaultInstance(props, null);
                msg = new MimeMessage(session);
                try {
                    msg.setFrom(new InternetAddress("yapnak.uq@gmail.com", "Yapnak"));
                    msg.addRecipient(javax.mail.Message.RecipientType.TO,
                            new InternetAddress("yapnak.uq@gmail.com", "Yapnak"));
                    msg.setSubject("Negative feedback - client didn't accept code");
                    msg.setText("From: " + userID + " - " + message);
                    Transport.send(msg);
                } finally {

                }
                break;
        }
        return;
    }

    @ApiMethod(
            name = "setUserDetails",
            path = "setUserDetails",
            httpMethod = ApiMethod.HttpMethod.POST)
    public void setUserDetails(@Named("number") String mobNo, @Named("fName") String fName, @Named("lName") String lName, @Named("userID") String userID) throws ClassNotFoundException, SQLException {
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
            int success = -1;
            try {
                String statement = "UPDATE user SET mobNo = ?, firstName = ?, lastName = ? WHERE userID = ?";
                PreparedStatement stmt = connection.prepareStatement(statement);
                stmt.setString(1, mobNo);
                stmt.setString(2, fName);
                stmt.setString(3, lName);
                stmt.setString(4, userID);
                success = stmt.executeUpdate();
            } finally {
                connection.close();
                return;
            }
        } finally {
            return;
        }
    }

    @ApiMethod(
            name = "getUserDetails",
            path = "getUserDetails",
            httpMethod = ApiMethod.HttpMethod.POST)
    public UserEntity getUserDetails(@Named("userID") String userID) throws ClassNotFoundException, SQLException {
        Connection connection;
        UserEntity user = new UserEntity();
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
                String statement = "SELECT firstName, lastName, mobNo, email FROM user WHERE userID = ?";
                PreparedStatement stmt = connection.prepareStatement(statement);
                stmt.setString(1, userID);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    if (rs.getString("email") != null) {
                        user.setEmail(rs.getString("email"));
                    } else {
                        user.setEmail("_null");
                    }
                    if (rs.getString("firstName") != null) {
                        user.setFirstName(rs.getString("firstName"));
                    } else {
                        user.setFirstName("_null");
                    }
                    if (rs.getString("lastName") != null) {
                        user.setLastName(rs.getString("lastName"));
                    } else {
                        user.setLastName("_null");
                    }
                    if (rs.getString("mobNo") != null) {
                        user.setMobNo(rs.getString("mobNo"));
                    } else {
                        user.setMobNo("_null");
                    }
                } else {
                    logger.info("nothing found");
                }
            } finally {
                connection.close();
                return user;
            }
        } finally {
            return user;
        }
    }

    @ApiMethod(
            name = "recommend",
            path = "recommend",
            httpMethod = ApiMethod.HttpMethod.POST)
    private RecommendEntity recommend(@Named("user") String userID, @Named("clientID") int clientID, @Named("this user") String r_userID) throws ClassNotFoundException, SQLException {
        Connection connection;
        RecommendEntity recommendation = new RecommendEntity();
        String pushKey;
        String statement;
        PreparedStatement stmt;
        ResultSet rs;
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
                //check if userID is in system
                statement = "SELECT pushKey FROM user WHERE userID = ?";
                stmt = connection.prepareStatement(statement);
                stmt.setString(1, userID);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    //UserID exists in system, check if referred
                    pushKey = rs.getString("pushKey");
                    statement = "SELECT referrerID FROM points WHERE clientID = ? AND userID = ?";
                    stmt = connection.prepareStatement(statement);
                    stmt.setInt(1, clientID);
                    stmt.setString(2, userID);
                    rs = stmt.executeQuery();
                    if (rs.next()) {
                        //user is also ready referred
                        recommendation.setSuccess(0);
                    } else {
                        //user hasn't been recommended, check the row exists
                        statement = "SELECT points FROM points where clientID = ? and userID = ?";
                        stmt = connection.prepareStatement(statement);
                        stmt.setInt(1, clientID);
                        stmt.setString(2, userID);
                        rs = stmt.executeQuery();
                        if (rs.next()) {
                            //update the referrerID
                            statement = "UPDATE points SET referrerID = ? where clientID = ? and userID = ?";
                            stmt = connection.prepareStatement(statement);
                            stmt.setString(1, r_userID);
                            stmt.setInt(2, clientID);
                            stmt.setString(3, userID);
                            stmt.executeUpdate();
                            recommendation.setSuccess(1);
                            //post update
                            statement = "SELECT clientName from client where clientID = ?";
                            stmt = connection.prepareStatement(statement);
                            stmt.setInt(1, clientID);
                            rs = stmt.executeQuery();
                            rs.next();
                            //push notification
                            String message = "You have been recommended to eat at " + rs.getString("clientName");
                            Sender sender = new Sender(API_KEY);
                            Message msg = new Message.Builder().addData("message", message).build();
                            sender.send(msg, pushKey, 5);
                        } else {
                            //add a new row to the points table with referrerID
                            statement = "INSET INTO points (userID, clientID, referrerID) VALUES (?,?,?)";
                            stmt = connection.prepareStatement(statement);
                            stmt.setString(1, userID);
                            stmt.setInt(2, clientID);
                            stmt.setString(3, r_userID);
                            stmt.executeUpdate();
                            recommendation.setSuccess(1);
                            //post update
                            statement = "SELECT clientName from client where clientID = ?";
                            stmt = connection.prepareStatement(statement);
                            stmt.setInt(1, clientID);
                            rs = stmt.executeQuery();
                            rs.next();
                            //push notification
                            String message = "You have been recommended to eat at " + rs.getString("clientName");
                            Sender sender = new Sender(API_KEY);
                            Message msg = new Message.Builder().addData("message", message).build();
                            sender.send(msg, pushKey, 5);
                        }
                    }
                } else {
                    //check if it's a mobile number
                    statement = "SELECT pushKey FROM user WHERE mobNo = ?";
                    stmt = connection.prepareStatement(statement);
                    stmt.setString(1, userID);
                    rs = stmt.executeQuery();
                    if (rs.next()) {
                        //mobNo is in system
                        pushKey = rs.getString("pushKey");
                        statement = "SELECT referrerID FROM points WHERE clientID = ? AND userID = ?";
                        stmt = connection.prepareStatement(statement);
                        stmt.setInt(1, clientID);
                        stmt.setString(2, userID);
                        rs = stmt.executeQuery();
                        if (rs.next()) {
                            //user is also ready referred
                            recommendation.setSuccess(0);
                        } else {
                            //user hasn't been recommended, check the row exists
                            statement = "SELECT points FROM points where clientID = ? and userID = ?";
                            stmt = connection.prepareStatement(statement);
                            stmt.setInt(1, clientID);
                            stmt.setString(2, userID);
                            rs = stmt.executeQuery();
                            if (rs.next()) {
                                //update the referrerID
                                statement = "UPDATE points SET referrerID = ? where clientID = ? and userID = ?";
                                stmt = connection.prepareStatement(statement);
                                stmt.setString(1, r_userID);
                                stmt.setInt(2, clientID);
                                stmt.setString(3, userID);
                                stmt.executeUpdate();
                                recommendation.setSuccess(1);
                                //post update
                                statement = "SELECT clientName from client where clientID = ?";
                                stmt = connection.prepareStatement(statement);
                                stmt.setInt(1, clientID);
                                rs = stmt.executeQuery();
                                rs.next();
                                //push notification
                                String message = "You have been recommended to eat at " + rs.getString("clientName");
                                Sender sender = new Sender(API_KEY);
                                Message msg = new Message.Builder().addData("message", message).build();
                                sender.send(msg, pushKey, 5);
                            } else {
                                //add a new row to the points table with referrerID
                                statement = "INSET INTO points (userID, clientID, referrerID) VALUES (?,?,?)";
                                stmt = connection.prepareStatement(statement);
                                stmt.setString(1, userID);
                                stmt.setInt(2, clientID);
                                stmt.setString(3, r_userID);
                                stmt.executeUpdate();
                                recommendation.setSuccess(1);
                                //post update
                                statement = "SELECT clientName from client where clientID = ?";
                                stmt = connection.prepareStatement(statement);
                                stmt.setInt(1, clientID);
                                rs = stmt.executeQuery();
                                rs.next();
                                //push notification
                                String message = "You have been recommended to eat at " + rs.getString("clientName");
                                Sender sender = new Sender(API_KEY);
                                Message msg = new Message.Builder().addData("message", message).build();
                                sender.send(msg, pushKey, 5);
                            }
                        }
                    } else {
                        //user isn't in system, send a text
                        recommendation.setSuccess(2);
                    }
                }
            } finally {
                connection.close();
                return recommendation;
            }
        } finally {
            return recommendation;
        }
    }
}