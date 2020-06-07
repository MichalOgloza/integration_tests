package edu.iis.mto.blog.rest.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.apache.http.HttpStatus;
import org.hamcrest.collection.IsCollectionWithSize;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

public class SearchPostsTest extends FunctionalTests {

    private static final String USER_API = "/blog/user";
    private static final long CONFIRMED_USER_ID = 1L;
    private static final long NEW_USER_ID = 2L;
    private static final long REMOVED_USER_ID = 3L;
    private static final long FIRST_USER_POST_ID = 1L;
    private static final long SECOND_USER_POST_ID = 2L;
    private static final long LIKED_POST_ID = 3L;


    @Test
    public void FindUserPostReturnsCorrectNumberOfPosts() {
        given().accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .get(USER_API + "/" + NEW_USER_ID + "/post")
                .then()
                .body("entry", hasSize(2));
    }

    @Test
    public void FindPostOfRemovedUserReturnsBadRequestStatus() {
        given().accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .when()
                .get(USER_API + "/" + REMOVED_USER_ID + "/post");
    }
}

