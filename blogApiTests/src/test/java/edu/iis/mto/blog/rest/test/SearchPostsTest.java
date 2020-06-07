package edu.iis.mto.blog.rest.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

public class SearchPostsTest extends FunctionalTests {

    private static final String USER_API = "/blog/user";
    private static final long NEW_USER_ID = 2L;
    private static final long REMOVED_USER_ID = 3L;


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

