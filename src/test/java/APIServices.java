import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

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
                .log().body()
                .statusCode(200)
                .extract()
                .response();
    }
    @Step("Method to create new booking")
     public  Response createBooking (BookingInfo bookingInfo){
           return RestAssured.given()
                   .contentType(ContentType.JSON)
                   .body(gson.toJson(bookingInfo))
                   .log().body()
                   .when()
                   .post(BASE_URL+"/booking")
                   .then()
                   .log().body()
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

    @Step("Method to get all booking")
    public Response getAllBooking(){
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get(BASE_URL+"/booking")
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Method to get booking using specific params")
    public Response getBookingWithParams(String firstname, String lastname, String checkin, String checkout){
        RequestSpecification request = RestAssured.given()
                .log().uri();
        if (firstname != null && !firstname.isEmpty()) {
            request.queryParam("firstname", firstname);
        }
        if (lastname!=null && !lastname.isEmpty()){
            request.queryParam("lastname",lastname);
        }
        if (checkin!=null && !checkin.isEmpty()){
            request.queryParam("checkin", checkin);
        }
        if (checkout!=null && !checkout.isEmpty()){
            request.queryParam("checkout", checkout);
        }
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get(BASE_URL+"/booking")
                .then()
                .log().body()
                .extract().response();
    }

    @Step("Method to GET booking by Id")
    Response getBookingById(String id){
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("https://restful-booker.herokuapp.com/booking/"+id)
                .then()
                .log().body()
                .extract().response();
    }

    @Step("Method to update created booking")
    Response updateBooking(String idToUpdate, BookingInfo bookingInfo, String token){
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .accept("application/json")
                .header("Cookie", "token=" + token)
                .body(gson.toJson(bookingInfo))
                .log().all()
                .when()
                .put("https://restful-booker.herokuapp.com/booking/"+idToUpdate)
                .then()
                .log().body()
                .extract().response();
    }
}
