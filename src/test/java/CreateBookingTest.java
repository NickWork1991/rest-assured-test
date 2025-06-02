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

    BookingInfo invalidBookingInfo= BookingInfo.builder()
            .firstname("Nik")
            .lastname("Secoro")
            .totalprice(155)
            .depositpaid(true)
            .bookingdates(BookingInfo.BookingDates.builder()
                    .checkin("2025-05-07")
                    .checkout("2025-05-11")
                    .build())
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
        Allure.step("Create a new booking with all required fields");

        APIServices apiServices = new APIServices();
        Response response = apiServices.createBooking(bookingInfo);

        Allure.step("Verify that status code is 200");
        Assert.assertEquals(response.statusCode(),200,"Expected status code - 200");

        String actualFirstName = response.jsonPath().getString("booking.firstname");
        Allure.step("Verify that responseBody  contains firstname - 'Nik'");
        Assert.assertEquals(actualFirstName,"Nik","Firstname doesn't match in response");
    }


    @Test
    @Story("Try to create a new booking without required field")
    @Severity(SeverityLevel.CRITICAL)
    public void createBookingWithoutRequiredFieldTest(){
        logger.info("Scenario#3: Verify that can not create booking without required field - 'depositPaid'");
        Allure.step("Create a new booking without required field");

        APIServices apiServices = new APIServices();
        Response response = apiServices.createBooking(invalidBookingInfo);

        Allure.step("Verify that expected status is 400");
        Assert.assertEquals(response.statusCode(),400, "Expected status code -400");
    }

}
