package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;

public class APITests {
  @Before
  public void setup() {
    RestAssured.baseURI = "http://localhost:3000";
  }

  @Test
  public void whenRequestGetItems_thenOK() {
    when().get("/todos").then().statusCode(200);
    //Cleanup
    when().delete("/todos").then().extract().response();
  }

  @Test
  public void whenRequestPostItem_thenCreated() {
    with().contentType(ContentType.JSON).body("{\"title\": \"buy milk\", \"completed\": false, \"id\": 1}")
        .when().post("/todos").then().statusCode(201);
    when().get("/todos").then().body("title[0]", equalTo("buy milk"));
    //Cleanup
    when().delete("/todos").then().extract().response();
  }

  @Test
  public void whenRequestPostItems_thenCreated() {
    with().contentType(ContentType.JSON).body("[{\"title\": \"buy milk\", \"completed\": false, \"id\": 1}, "
        + "{\"title\": \"buy cookies\", \"completed\": false, \"id\": 2}]")
        .when().post("/todos/seed").then().statusCode(201);
    when().get("/todos").then().body("title[1]", equalTo("buy cookies"));
    //Cleanup
    when().delete("/todos").then().extract().response();
  }

  @Test
  public void whenRequestPatchItem_thenOK() {
    with().contentType(ContentType.JSON).body("{\"title\": \"buy milk\", \"completed\": false, \"id\": 1}")
        .when().post("/todos").then().statusCode(201);
    with().contentType(ContentType.JSON).body("{\"title\": \"buy juice\", \"completed\": false}")
        .when().patch("/todos/1").then().statusCode(200);
    when().get("/todos").then().body("title[0]", equalTo("buy juice"));
    //Cleanup
    when().delete("/todos").then().extract().response();
  }

  @Test
  public void whenRequestPatchItem_thenNotFound() {
    with().contentType(ContentType.JSON).body("{\"title\": \"buy juice\", \"completed\": false}")
        .when().patch("/todos/1").then().statusCode(404);
    //Cleanup
    when().delete("/todos").then().extract().response();
  }

  @Test
  public void whenRequestDeleteItem_thenOK() {
    with().contentType(ContentType.JSON).body("{\"title\": \"buy milk\", \"completed\": false, \"id\": 1}")
        .when().post("/todos").then().statusCode(201);
    when().delete("/todos/1").then().statusCode(200);
    when().get("/todos").then().body("A", empty());
  }

  @Test
  public void whenRequestDeleteItems_thenDeleted() {
    with().contentType(ContentType.JSON).body("[{\"title\": \"buy milk\", \"completed\": false, \"id\": 1}, "
        + "{\"title\": \"buy cookies\", \"completed\": false, \"id\": 2}]")
        .when().post("/todos/seed").then().statusCode(201);
    when().delete("/todos").then().statusCode(204);
    when().get("/todos").then().body("A", empty());
  }

  @Test
  public void whenRequestPostSignup_thenCreated() {
    with().contentType(ContentType.JSON).body("{\"email\": \"mymail\", \"password\": \"mypass\"}")
        .header("sendwelcomeemail", true)
        .when().post("/signup").then().statusCode(201).header("Set-Cookie", "auth=true;");
    //Cleanup
    when().delete("/accounts").then().extract().response();
  }

  @Test
  public void whenRequestPostSignup_thenConflict() {
    with().contentType(ContentType.JSON).body("{\"email\": \"mymail\", \"password\": \"mypass\"}")
        .header("sendwelcomeemail", true)
        .when().post("/signup").then().statusCode(201).header("Set-Cookie", "auth=true;");
    with().contentType(ContentType.JSON).body("{\"email\": \"mymail\", \"password\": \"mypass\"}")
        .when().post("/signup").then().statusCode(409);
    //Cleanup
    when().delete("/accounts").then().extract().response();
  }

  @Test
  public void whenRequestPostSignup_thenUnauthorized() {
    with().contentType(ContentType.JSON).body("{\"email\": \"\", \"password\": \"mypass\"}")
        .header("sendwelcomeemail", true)
        .when().post("/signup").then().statusCode(401);
  }

  @Test
  public void whenRequestPostLogin_thenCreated() {
    with().contentType(ContentType.JSON).body("{\"email\": \"mymail\", \"password\": \"mypass\"}")
        .when().post("/signup").then().statusCode(201).header("Set-Cookie", "auth=true;");
    with().contentType(ContentType.JSON).body("{\"email\": \"mymail\", \"password\": \"mypass\"}")
        .when().post("/login").then().statusCode(200).header("Set-Cookie", "auth=true;");
    //Cleanup
    when().delete("/accounts").then().extract().response();
  }

  @Test
  public void whenRequestPostLogin_thenUnauthorized() {
    with().contentType(ContentType.JSON).body("{\"email\": \"mymail\", \"password\": \"mypass\"}")
        .when().post("/login").then().statusCode(401);
  }

  @Test
  public void whenRequestDeleteAccounts_thenDeleted() {
    with().contentType(ContentType.JSON).body("{\"email\": \"mymail\", \"password\": \"mypass\"}")
        .header("sendwelcomeemail", true)
        .when().post("/signup").then().statusCode(201).header("Set-Cookie", "auth=true;");
    when().delete("/accounts").then().statusCode(204);
  }

  @Test
  public void whenRequestPostAccounts_thenCreated() {
    with().contentType(ContentType.JSON).body("[{\"email\": \"mymail\", \"password\": \"mypass\"}, "
        + "{\"email\": \"hismail\", \"password\": \"hispass\"}]")
        .when().post("/accounts/seed").then().statusCode(201);
    //Cleanup
    when().delete("/accounts").then().extract().response();
  }

  @Test
  public void whenRequestPostReset_thenDeleted() {
    with().contentType(ContentType.JSON).body("[{\"email\": \"mymail\", \"password\": \"mypass\"}, "
        + "{\"email\": \"hismail\", \"password\": \"hispass\"}]")
        .when().post("/accounts/seed").then().statusCode(201);
    with().contentType(ContentType.JSON).body("[{\"title\": \"buy milk\", \"completed\": false, \"id\": 1}, "
        + "{\"title\": \"buy cookies\", \"completed\": false, \"id\": 2}]")
        .when().post("/todos/seed").then().statusCode(201);
    with().contentType(ContentType.JSON).body("{}").when().post("/reset").then().statusCode(204);
    when().get("/todos").then().body("A", empty());
  }

}
