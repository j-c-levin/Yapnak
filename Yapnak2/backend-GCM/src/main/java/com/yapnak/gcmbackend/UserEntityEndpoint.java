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
        name = "userEntityApi",
        version = "v1",
        resource = "userEntity",
        namespace = @ApiNamespace(
                ownerDomain = "gcmbackend.yapnak.com",
                ownerName = "gcmbackend.yapnak.com",
                packagePath = ""
        )
)
public class UserEntityEndpoint {

    private static final Logger logger = Logger.getLogger(UserEntityEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(UserEntity.class);
    }

    /**
     * Returns the {@link UserEntity} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code UserEntity} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "userEntity/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public UserEntity get(@Named("id") String id) throws NotFoundException {
        logger.info("Getting UserEntity with ID: " + id);
        UserEntity userEntity = ofy().load().type(UserEntity.class).id(id).now();
        if (userEntity == null) {
            throw new NotFoundException("Could not find UserEntity with ID: " + id);
        }
        return userEntity;
    }

    /**
     * Inserts a new {@code UserEntity}.
     */
    @ApiMethod(
            name = "insert",
            path = "userEntity",
            httpMethod = ApiMethod.HttpMethod.POST)
    public UserEntity insert(UserEntity userEntity) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that userEntity.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(userEntity).now();
        logger.info("Created UserEntity with ID: " + userEntity.getId());

        return ofy().load().entity(userEntity).now();
    }

    /**
     * Updates an existing {@code UserEntity}.
     *
     * @param id         the ID of the entity to be updated
     * @param userEntity the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code UserEntity}
     */
    @ApiMethod(
            name = "update",
            path = "userEntity/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public UserEntity update(@Named("id") String id, UserEntity userEntity) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(userEntity).now();
        logger.info("Updated UserEntity: " + userEntity);
        return ofy().load().entity(userEntity).now();
    }

    /**
     * Deletes the specified {@code UserEntity}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code UserEntity}
     */
    @ApiMethod(
            name = "remove",
            path = "userEntity/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") String id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(UserEntity.class).id(id).now();
        logger.info("Deleted UserEntity with ID: " + id);
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
            path = "userEntity",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<UserEntity> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<UserEntity> query = ofy().load().type(UserEntity.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<UserEntity> queryIterator = query.iterator();
        List<UserEntity> userEntityList = new ArrayList<UserEntity>(limit);
        while (queryIterator.hasNext()) {
            userEntityList.add(queryIterator.next());
        }
        return CollectionResponse.<UserEntity>builder().setItems(userEntityList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String id) throws NotFoundException {
        try {
            ofy().load().type(UserEntity.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find UserEntity with ID: " + id);
        }
    }

}