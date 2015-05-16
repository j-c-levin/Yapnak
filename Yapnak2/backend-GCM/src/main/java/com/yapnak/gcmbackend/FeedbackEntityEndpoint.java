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
        name = "feedbackEntityApi",
        version = "v1",
        resource = "feedbackEntity",
        namespace = @ApiNamespace(
                ownerDomain = "gcmbackend.yapnak.com",
                ownerName = "gcmbackend.yapnak.com",
                packagePath = ""
        )
)
public class FeedbackEntityEndpoint {

    private static final Logger logger = Logger.getLogger(FeedbackEntityEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(FeedbackEntity.class);
    }

    /**
     * Returns the {@link FeedbackEntity} with the corresponding ID.
     *
     * @param userID the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code FeedbackEntity} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "feedbackEntity/{userID}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public FeedbackEntity get(@Named("userID") String userID) throws NotFoundException {
        logger.info("Getting FeedbackEntity with ID: " + userID);
        FeedbackEntity feedbackEntity = ofy().load().type(FeedbackEntity.class).id(userID).now();
        if (feedbackEntity == null) {
            throw new NotFoundException("Could not find FeedbackEntity with ID: " + userID);
        }
        return feedbackEntity;
    }

    /**
     * Inserts a new {@code FeedbackEntity}.
     */
    @ApiMethod(
            name = "insert",
            path = "feedbackEntity",
            httpMethod = ApiMethod.HttpMethod.POST)
    public FeedbackEntity insert(FeedbackEntity feedbackEntity) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that feedbackEntity.userID has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(feedbackEntity).now();
        logger.info("Created FeedbackEntity.");

        return ofy().load().entity(feedbackEntity).now();
    }

    /**
     * Updates an existing {@code FeedbackEntity}.
     *
     * @param userID         the ID of the entity to be updated
     * @param feedbackEntity the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code userID} does not correspond to an existing
     *                           {@code FeedbackEntity}
     */
    @ApiMethod(
            name = "update",
            path = "feedbackEntity/{userID}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public FeedbackEntity update(@Named("userID") String userID, FeedbackEntity feedbackEntity) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(userID);
        ofy().save().entity(feedbackEntity).now();
        logger.info("Updated FeedbackEntity: " + feedbackEntity);
        return ofy().load().entity(feedbackEntity).now();
    }

    /**
     * Deletes the specified {@code FeedbackEntity}.
     *
     * @param userID the ID of the entity to delete
     * @throws NotFoundException if the {@code userID} does not correspond to an existing
     *                           {@code FeedbackEntity}
     */
    @ApiMethod(
            name = "remove",
            path = "feedbackEntity/{userID}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("userID") String userID) throws NotFoundException {
        checkExists(userID);
        ofy().delete().type(FeedbackEntity.class).id(userID).now();
        logger.info("Deleted FeedbackEntity with ID: " + userID);
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
            path = "feedbackEntity",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<FeedbackEntity> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<FeedbackEntity> query = ofy().load().type(FeedbackEntity.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<FeedbackEntity> queryIterator = query.iterator();
        List<FeedbackEntity> feedbackEntityList = new ArrayList<FeedbackEntity>(limit);
        while (queryIterator.hasNext()) {
            feedbackEntityList.add(queryIterator.next());
        }
        return CollectionResponse.<FeedbackEntity>builder().setItems(feedbackEntityList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String userID) throws NotFoundException {
        try {
            ofy().load().type(FeedbackEntity.class).id(userID).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find FeedbackEntity with ID: " + userID);
        }
    }
}