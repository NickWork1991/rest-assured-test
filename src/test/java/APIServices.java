import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public  class APIServices {
    AuthRequest authRequest = new AuthRequest("admin","password123");
    private static final String TOKEN_URL="https://restful-booker.herokuapp.com/auth";
    private static final Gson gson= new Gson();

    @Step("Method to get token")
    public Response getToken (){
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(gson.toJson(authRequest))
                .when()
                .post(TOKEN_URL)
                .then()
                .statusCode(200)
                .extract()
                .response();
    }
}
