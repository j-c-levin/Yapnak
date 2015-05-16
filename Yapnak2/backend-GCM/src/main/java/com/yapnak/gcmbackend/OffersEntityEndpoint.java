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
        name = "offersEntityApi",
        version = "v1",
        resource = "offersEntity",
        namespace = @ApiNamespace(
                ownerDomain = "gcmbackend.yapnak.com",
                ownerName = "gcmbackend.yapnak.com",
                packagePath = ""
        )
)
public class OffersEntityEndpoint {

    private static final Logger logger = Logger.getLogger(OffersEntityEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(OffersEntity.class);
    }

    /**
     * Returns the {@link OffersEntity} with the corresponding ID.
     *
     * @param offerID the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code OffersEntity} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "offersEntity/{offerID}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public OffersEntity get(@Named("offerID") int offerID) throws NotFoundException {
        logger.info("Getting OffersEntity with ID: " + offerID);
        OffersEntity offersEntity = ofy().load().type(OffersEntity.class).id(offerID).now();
        if (offersEntity == null) {
            throw new NotFoundException("Could not find OffersEntity with ID: " + offerID);
        }
        return offersEntity;
    }

    /**
     * Inserts a new {@code OffersEntity}.
     */
    @ApiMethod(
            name = "insert",
            path = "offersEntity",
            httpMethod = ApiMethod.HttpMethod.POST)
    public OffersEntity insert(OffersEntity offersEntity) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that offersEntity.offerID has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(offersEntity).now();
        logger.info("Created OffersEntity with ID: " + offersEntity.getOfferID());

        return ofy().load().entity(offersEntity).now();
    }

    /**
     * Updates an existing {@code OffersEntity}.
     *
     * @param offerID      the ID of the entity to be updated
     * @param offersEntity the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code offerID} does not correspond to an existing
     *                           {@code OffersEntity}
     */
    @ApiMethod(
            name = "update",
            path = "offersEntity/{offerID}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public OffersEntity update(@Named("offerID") int offerID, OffersEntity offersEntity) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(offerID);
        ofy().save().entity(offersEntity).now();
        logger.info("Updated OffersEntity: " + offersEntity);
        return ofy().load().entity(offersEntity).now();
    }

    /**
     * Deletes the specified {@code OffersEntity}.
     *
     * @param offerID the ID of the entity to delete
     * @throws NotFoundException if the {@code offerID} does not correspond to an existing
     *                           {@code OffersEntity}
     */
    @ApiMethod(
            name = "remove",
            path = "offersEntity/{offerID}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("offerID") int offerID) throws NotFoundException {
        checkExists(offerID);
        ofy().delete().type(OffersEntity.class).id(offerID).now();
        logger.info("Deleted OffersEntity with ID: " + offerID);
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
            path = "offersEntity",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<OffersEntity> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<OffersEntity> query = ofy().load().type(OffersEntity.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<OffersEntity> queryIterator = query.iterator();
        List<OffersEntity> offersEntityList = new ArrayList<OffersEntity>(limit);
        while (queryIterator.hasNext()) {
            offersEntityList.add(queryIterator.next());
        }
        return CollectionResponse.<OffersEntity>builder().setItems(offersEntityList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(int offerID) throws NotFoundException {
        try {
            ofy().load().type(OffersEntity.class).id(offerID).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find OffersEntity with ID: " + offerID);
        }
    }
}