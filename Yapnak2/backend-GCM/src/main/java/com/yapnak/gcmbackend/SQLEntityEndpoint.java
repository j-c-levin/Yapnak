package com.yapnak.gcmbackend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.utils.SystemProperty;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.ObjectifyService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(SQLEntity.class);
    }

    @ApiMethod(
            name = "getUser",
            httpMethod = ApiMethod.HttpMethod.GET)
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
                String statement = "SELECT userID FROM user where userID = ?";
                PreparedStatement stmt = connection.prepareStatement(statement);
                stmt.setString(1, userID);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    logger.info("found user");
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
            path = "sQLEntity_clients",
            httpMethod = ApiMethod.HttpMethod.GET)
    public SQLEntity get(@Named("x") double x, @Named("y") double y) throws NotFoundException, OAuthRequestException {
/*        if (user == null) {
            throw new OAuthRequestException("User is not valid " + user);
        }*/
        Connection connection;
        double distance = 0.02;
        ArrayList<SQLEntity> list2 = new ArrayList<>();
        SQLEntity s = new SQLEntity();
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
                String statement = "SELECT clientName,clientX,clientY,clientOffer,clientFoodStyle,clientPhoto,rating FROM client WHERE clientX BETWEEN ? AND ? AND clientY BETWEEN ? AND ?";
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
                for (int i = 0; i < p; i++) {
                    rs.next();
                    SQLEntity sql = new SQLEntity();
                    sql.setName(rs.getString("clientName"));
                    sql.setOffer(rs.getString("clientOffer"));
                    sql.setX(rs.getDouble("clientX"));
                    sql.setY(rs.getDouble("clientY"));
                    sql.setFoodStyle(rs.getString("clientFoodStyle"));
                    sql.setPhoto(rs.getString("clientPhoto"));
                    sql.setRating((rs.getDouble("rating")));
                    list2.add(sql);
                    logger.info("found client: " + rs.getString("clientName"));
                }
                s.setList(list2);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.close();
                return s;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Inserts a new client {@code SQLEntity}.
     */
    @ApiMethod(
            name = "insertClient",
            path = "sQLEntity_client",
            httpMethod = ApiMethod.HttpMethod.POST)
    public void insertClient(@Named("name") String name, @Named("x") double x, @Named("y") double y, @Named("offer") String offer) {
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
            int success = 0;
            try {
                String statement = "INSERT INTO client (clientName,clientLocation, clientX, clientY, clientOffer) VALUES(?,Point(?,?),?,?,?)";
                PreparedStatement stmt = connection.prepareStatement(statement);
                stmt.setString(1, name);
                stmt.setDouble(2, x);
                stmt.setDouble(3, y);
                stmt.setDouble(4, x);
                stmt.setDouble(5, y);
                stmt.setString(6, offer);
                success = stmt.executeUpdate();
                logger.info("returned: " + success);
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

    /**
     * Inserts a new {@code SQLEntity}.
     */
    @ApiMethod(
            name = "insertUser",
            path = "sQLEntity_user",
            httpMethod = ApiMethod.HttpMethod.POST)
    public void insert(@Named("name") String name) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that sQLEntity.name has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
//        ofy().save().entity(sQLEntity).now();
//        logger.info("Created SQLEntity with ID: " + sQLEntity.getName());

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

            try {
                String statement = "INSERT INTO user (userID) VALUES(?)";
                PreparedStatement stmt = connection.prepareStatement(statement);
                stmt.setString(1, name);
                int success = stmt.executeUpdate();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}