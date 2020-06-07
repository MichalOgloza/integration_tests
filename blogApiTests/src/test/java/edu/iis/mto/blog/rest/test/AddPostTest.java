package edu.iis.mto.blog.rest.test;

import static io.restassured.RestAssured.given;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

public class AddPostTest extends FunctionalTests {

    private static final String USER_API = "/blog/user";
    private static final long CONFIRMED_USER_ID = 1L;
    private static final long NEW_USER_ID = 2L;

    @Test
    public void createPostByConfirmedUserReturnsCreatedStatus() {
        JSONObject jsonObj = new JSONObject().put("entry", "example");
        given().accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_CREATED)
                .when()
                .post(USER_API + "/" + CONFIRMED_USER_ID + "/post");
    }
}

