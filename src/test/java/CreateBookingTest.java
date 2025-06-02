import io.qameta.allure.*;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("User management")
@Feature("Create booking")
public class CreateBookingTest {

    private static final Logger logger= LogManager.getLogger(CreateBookingTest.class);
    BookingInfo bookingInfo = BookingInfo.builder()
            .firstname("Nik")
            .lastname("Mekhanikov")
            .totalprice(147)
            .depositpaid(true)
            .bookingdates(BookingInfo.BookingDates.builder()
                    .checkin("2025-05-07")
                    .checkout("2025-05-11")
                    .build())
            .additionalneeds("Breakfast")
            .build();

    @BeforeMethod
    void beforeCreate(){
        APIServices apiServices = new APIServices();
        apiServices.getToken();
    }

    @Test
    @Story("Try no create a new booking")
    @Severity(SeverityLevel.CRITICAL)
    public void createBookingWithAllFieldsTest(){
        logger.info("Scenario #1: Verify that can create booking with valid data");
        Allure.step("Create a new booking with all required fields" + bookingInfo);

        APIServices apiServices = new APIServices();
        Response response = apiServices.createBooking(bookingInfo);

        Allure.step("Verify that status code is 200");
        Assert.assertEquals(response.statusCode(),200,"Expected status code - 200");
    }
}
