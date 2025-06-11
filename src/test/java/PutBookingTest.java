import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("User management")
@Feature("Update booking")
public class PutBookingTest {
    private static final Logger logger = LogManager.getLogger(PutBookingTest.class);
    private String token;
    private String createdBooking;

    BookingInfo bookingInfoFirst = BookingInfo.builder()
            .firstname("Nikoki1")
            .lastname("Aerstofor1")
            .totalprice(757)
            .depositpaid(true)
            .bookingdates(BookingInfo.BookingDates.builder()
                    .checkin("2025-05-19")
                    .checkout("2025-05-29")
                    .build())
            .additionalneeds("Want more drinks")
            .build();
    @BeforeMethod
    void beforePut(){
        APIServices apiServices = new APIServices();
        //  a new token
        Response response= apiServices.getToken();
        // Save this token
         token = response.jsonPath().getString("token");
         // Create a new booking
        Response createResponse = apiServices.createBooking(bookingInfoFirst);
        // Save particular bookingId
        createdBooking= String.valueOf(createResponse.jsonPath().getInt("bookingid"));
        Allure.step("Create new user before update him : " + bookingInfoFirst);
    }

    @Test
    @Story("Try to put new changes in the created booking")
    @Severity(SeverityLevel.CRITICAL)
    void putBookingTest(){
        logger.info("Scenario #1: Try ti update already created booking");
        APIServices apiServices = new APIServices();
        BookingInfo bookingInfoToUpdate = BookingInfo.builder()
                .firstname("Niko")
                .lastname("Mecho")
                .totalprice(457)
                .depositpaid(true)
                .bookingdates(BookingInfo.BookingDates.builder()
                        .checkin("2025-07-24")
                        .checkout("2025-07-30")
                        .build())
                .additionalneeds("Nothing")
                .build();;
        Response response = apiServices.updateBooking(createdBooking,bookingInfoToUpdate,token);

        Allure.step("Verify that expected status code - 200");
        Assert.assertEquals(response.statusCode(),200, "Expected code should be 200");
    }
}
