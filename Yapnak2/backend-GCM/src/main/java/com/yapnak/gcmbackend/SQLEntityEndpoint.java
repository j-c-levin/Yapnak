package com.yapnak.gcmbackend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.api.utils.SystemProperty;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

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
        )
)
public class SQLEntityEndpoint {

    private static final Logger logger = Logger.getLogger(SQLEntityEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(SQLEntity.class);
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
    public ArrayList get(@Named("x") double x, @Named("y") double y) throws NotFoundException {
        Connection connection;
        double distance = 0.02;
        ArrayList<SQLEntity> list2 = new ArrayList();
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
                String statement = "SELECT clientName,clientX,clientY,clientOffer,clientFoodStyle,clientPhoto FROM client WHERE clientX BETWEEN ? AND ? AND clientY BETWEEN ? AND ?";
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
                    list2.add(sql);
                    logger.info("found client: " + rs.getString("clientName"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.close();
                return list2;
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

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "sQLEntity",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<SQLEntity> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<SQLEntity> query = ofy().load().type(SQLEntity.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<SQLEntity> queryIterator = query.iterator();
        List<SQLEntity> sQLEntityList = new ArrayList<SQLEntity>(limit);
        while (queryIterator.hasNext()) {
            sQLEntityList.add(queryIterator.next());
        }
        return CollectionResponse.<SQLEntity>builder().setItems(sQLEntityList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String name) throws NotFoundException {
        try {
            ofy().load().type(SQLEntity.class).id(name).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find SQLEntity with ID: " + name);
        }
    }
}