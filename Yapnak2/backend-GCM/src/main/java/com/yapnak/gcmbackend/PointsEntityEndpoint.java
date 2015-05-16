package com.yapnak.gcmbackend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

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
        name = "pointsEntityApi",
        version = "v1",
        resource = "pointsEntity",
        namespace = @ApiNamespace(
                ownerDomain = "gcmbackend.yapnak.com",
                ownerName = "gcmbackend.yapnak.com",
                packagePath = ""
        )
)
public class PointsEntityEndpoint {

    private static final Logger logger = Logger.getLogger(PointsEntityEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(PointsEntity.class);
    }

    /**
     * Returns the {@link PointsEntity} with the corresponding ID.
     *
     * @param userID the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code PointsEntity} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "pointsEntity/{userID}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public PointsEntity get(@Named("userID") String userID) throws NotFoundException {
        logger.info("Getting PointsEntity with ID: " + userID);
        PointsEntity pointsEntity = ofy().load().type(PointsEntity.class).id(userID).now();
        if (pointsEntity == null) {
            throw new NotFoundException("Could not find PointsEntity with ID: " + userID);
        }
        return pointsEntity;
    }

    /**
     * Inserts a new {@code PointsEntity}.
     */
    @ApiMethod(
            name = "insert",
            path = "pointsEntity",
            httpMethod = ApiMethod.HttpMethod.POST)
    public PointsEntity insert(PointsEntity pointsEntity) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that pointsEntity.userID has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(pointsEntity).now();
        logger.info("Created PointsEntity with ID: " + pointsEntity.getUserID());

        return ofy().load().entity(pointsEntity).now();
    }

    /**
     * Updates an existing {@code PointsEntity}.
     *
     * @param userID       the ID of the entity to be updated
     * @param pointsEntity the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code userID} does not correspond to an existing
     *                           {@code PointsEntity}
     */
    @ApiMethod(
            name = "update",
            path = "pointsEntity/{userID}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public PointsEntity update(@Named("userID") String userID, PointsEntity pointsEntity) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(userID);
        ofy().save().entity(pointsEntity).now();
        logger.info("Updated PointsEntity: " + pointsEntity);
        return ofy().load().entity(pointsEntity).now();
    }

    /**
     * Deletes the specified {@code PointsEntity}.
     *
     * @param userID the ID of the entity to delete
     * @throws NotFoundException if the {@code userID} does not correspond to an existing
     *                           {@code PointsEntity}
     */
    @ApiMethod(
            name = "remove",
            path = "pointsEntity/{userID}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("userID") String userID) throws NotFoundException {
        checkExists(userID);
        ofy().delete().type(PointsEntity.class).id(userID).now();
        logger.info("Deleted PointsEntity with ID: " + userID);
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
            path = "pointsEntity",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<PointsEntity> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<PointsEntity> query = ofy().load().type(PointsEntity.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<PointsEntity> queryIterator = query.iterator();
        List<PointsEntity> pointsEntityList = new ArrayList<PointsEntity>(limit);
        while (queryIterator.hasNext()) {
            pointsEntityList.add(queryIterator.next());
        }
        return CollectionResponse.<PointsEntity>builder().setItems(pointsEntityList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String userID) throws NotFoundException {
        try {
            ofy().load().type(PointsEntity.class).id(userID).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find PointsEntity with ID: " + userID);
        }
    }
}