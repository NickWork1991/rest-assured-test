import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

public  class APIServices {
    AuthRequest authRequest = new AuthRequest("admin","password123");
    private static final String TOKEN_URL="https://restful-booker.herokuapp.com/auth";
    private static final String BASE_URL="https://restful-booker.herokuapp.com";

    private static final Gson gson= new Gson();

    @Step("Method to get token")
    public Response getToken (){
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(gson.toJson(authRequest))
                .log().body()
                .when()
                .post(TOKEN_URL)
                .then()
                .statusCode(200)
                .extract()
                .response();
    }
    @Step("Method to create new booking")
     public  Response createBooking (BookingInfo bookingInfo){
           return RestAssured.given()
                   .contentType(ContentType.JSON)
                   .body(gson.toJson(bookingInfo))
                   .when()
                   .log().body()
                   .post(BASE_URL+"/booking")
                   .then()
                   .extract()
                   .response();
    }

    @Step("Method to create booking without or with invalid value in any required fields")
    public Response createInvalidBooking(Map<String, Object> payload){
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload)
                .log().body()
                .when()
                .post(BASE_URL+"/booking")
                .then().extract().response();
    }
}
