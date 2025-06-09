import io.qameta.allure.*;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.units.qual.A;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

@Epic("User management")
@Feature("Get booking")

public class GetAllBookingTest {
    private static final Logger logger= LogManager.getLogger(GetAllBookingTest.class);
    BookingInfo bookingInfo = BookingInfo.builder()
            .firstname("Nik")
            .lastname("Mekhanik")
            .totalprice(345)
            .depositpaid(true)
            .bookingdates(BookingInfo.BookingDates.builder()
                    .checkin("2025-05-07")
                    .checkout("2025-05-14")
                    .build())
            .additionalneeds("Breakfast")
            .build();

    @BeforeMethod
    void beforeGet(){
        APIServices apiServices = new APIServices();
        apiServices.getToken();
    }

    @Test
    @Story("Try to get all bookings without filters")
    @Severity(SeverityLevel.NORMAL)
    void getAllBookingWithoutFiltersTest(){
        logger.info("Scenario #1: Send get request without filters");
        APIServices apiServices = new APIServices();
        Response response = apiServices.getAllBooking();

        Allure.step("Response body : " + response.asString());

        Allure.step("Verify that expected status is 200");
        Assert.assertEquals(response.statusCode(), 200, "Expected status - 200");
    }

    @Test
    @Story("Try to get booking with specific filter")
    @Severity(SeverityLevel.NORMAL)
    void getBookingWithSpecificFilter(){
        logger.info("Scenario #2: Send get request with specific filter");
        APIServices apiServices = new APIServices();

        Allure.step("Create a new booking");
        apiServices.createBooking(bookingInfo);

        Response response = apiServices.getBookingWithParams("Nik","Mekhanik",null,null);

        Allure.step("Verify that expected status - 200");
        Assert.assertEquals(response.statusCode(),200, "Expected status - 200");

        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        Assert.assertFalse(bookingIds.isEmpty(), "No bookings found with firstname 'Nik'");
    }

    @Test
    @Story("Try to get booking using Id")
    @Severity(SeverityLevel.NORMAL)
    void getBookingById(){
        logger.info("Scenario #3 : GET booking by  valid ID");
        APIServices apiServices = new APIServices();

        String bookingId= "13";
        Response response = apiServices.getBookingById(bookingId);

        Allure.step("Verify that when use valid id in the request expected status code - 200");
        Assert.assertEquals(response.statusCode(),200,"Expected status code - 200");

        Allure.step("Verify that firstname field contains - 'John'");
        String actualFirstName = response.jsonPath().getString("firstname");
        Assert.assertEquals(actualFirstName, "John", "The firstName field doesn't contain - 'John'");
    }
}
