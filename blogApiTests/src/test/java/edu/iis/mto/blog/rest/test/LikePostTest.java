package edu.iis.mto.blog.rest.test;

import static io.restassured.RestAssured.given;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

public class LikePostTest extends FunctionalTests {

    private static final String USER_API = "/blog/user";
    private static final long CONFIRMED_USER_ID = 1L;
    private static final long NEW_USER_ID = 2L;
    private static final long FIRST_USER_POST_ID = 1L;
    private static final long SECOND_USER_POST_ID = 2L;


    @Test
    public void likePostByConfirmedUserReturnsOkStatus() {
        given().accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .post(USER_API + "/" + CONFIRMED_USER_ID + "/like/" + SECOND_USER_POST_ID);
    }

    @Test
    public void likePostByNewUserReturnsBadRequestStatus() {
        given().accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .when()
                .post(USER_API + "/" + NEW_USER_ID + "/like/" + FIRST_USER_POST_ID);
    }
}

