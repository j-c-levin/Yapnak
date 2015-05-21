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
import java.sql.Statement;
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
     * @param name the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code SQLEntity} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "sQLEntity/{name}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public SQLEntity get(@Named("name") String name) throws NotFoundException {
//        logger.info("Getting SQLEntity with ID: " + name);
//        SQLEntity sQLEntity = ofy().load().type(SQLEntity.class).id(name).now();
        Connection connection;
        SQLEntity x = new SQLEntity();
        try {
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
                connection = DriverManager.getConnection("jdbc:mysql://yapnak-app:yapnak-main/yapnak_main", "client", "g7lFVLRzYdJoWXc3");
            } else {
                // Local MySQL instance to use during development.
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://127.0.0.1:3306/yapnak_main?user=client";
                connection = DriverManager.getConnection(url);
                // Alternatively, connect to a Google Cloud SQL instance using:
                // jdbc:mysql://ip-address-of-google-cloud-sql-instance:3306/guestbook?user=root
            }
            String query = "Select * from user";
            try {
                Class.forName("com.mysql.jdbc.Driver");

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                x.setName(resultSet.toString());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return x;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return x;
    }

    /**
     * Inserts a new {@code SQLEntity}.
     */
    @ApiMethod(
            name = "insert",
            path = "sQLEntity",
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
        SQLEntity x = new SQLEntity();
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
     * Updates an existing {@code SQLEntity}.
     *
     * @param name      the ID of the entity to be updated
     * @param sQLEntity the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code name} does not correspond to an existing
     *                           {@code SQLEntity}
     */
    @ApiMethod(
            name = "update",
            path = "sQLEntity/{name}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public SQLEntity update(@Named("name") String name, SQLEntity sQLEntity) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(name);
        ofy().save().entity(sQLEntity).now();
        logger.info("Updated SQLEntity: " + sQLEntity);
        return ofy().load().entity(sQLEntity).now();
    }

    /**
     * Deletes the specified {@code SQLEntity}.
     *
     * @param name the ID of the entity to delete
     * @throws NotFoundException if the {@code name} does not correspond to an existing
     *                           {@code SQLEntity}
     */
    @ApiMethod(
            name = "remove",
            path = "sQLEntity/{name}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("name") String name) throws NotFoundException {
        checkExists(name);
        ofy().delete().type(SQLEntity.class).id(name).now();
        logger.info("Deleted SQLEntity with ID: " + name);
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