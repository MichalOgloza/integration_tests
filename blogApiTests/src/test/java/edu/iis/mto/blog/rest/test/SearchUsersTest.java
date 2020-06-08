package edu.iis.mto.blog.rest.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

public class SearchUsersTest extends FunctionalTests {

    private static final String FIND_USER_API = "/blog/user/find?searchString=";
    private static final int REMOVED_USER_ID = 3;

    @Test
    public void FindAllUsersReturnsOkStatusAndListNotContainingDeletedUser() {
        given().accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("id", not(hasItem(REMOVED_USER_ID)))
                .when()
                .get(FIND_USER_API);
    }
}

