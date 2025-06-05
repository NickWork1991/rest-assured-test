import io.qameta.allure.*;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;
@Epic("User management")
@Feature("Create booking")
public class CreateBookingNULLValueTest {

    private static final Logger logger= LogManager.getLogger(CreateBookingNULLValueTest.class);

    @BeforeMethod
    void beforeCreateNullBooking(){
        APIServices apiServices= new APIServices();
        apiServices.getToken();
    }

    @Test(dataProvider = "invalidValue")
    @Story("Try to crete new booking with 'NULL' value in the required fields")
    @Severity(SeverityLevel.CRITICAL)
    void createBookingWithInvalidValueTest(String field,Object valueNull){
        logger.info("Create booking with value=NULL in required field : " +field + "+" + valueNull);

        BasePayload basePayload = new BasePayload();
        Map<String,Object> payload = basePayload.getBaseBookingPayload();
        payload.put(field,valueNull);
        APIServices apiServices= new APIServices();
        Response response = apiServices.createInvalidBooking(payload);

        Allure.step("Enter invalid value : " +field + " + " + valueNull);
        Allure.step("Response : " + response.asString());

        Assert.assertEquals(response.statusCode(),500, "Expected status code 500 when missing" + valueNull);
    }

    @DataProvider(name = "invalidValue")
    //Аннотация TestNG, указывающая, что тест будет выполняться +
    // многократно с разными входными значениями из DataProvider-а missingRequiredFields.
    public Object[][] requiredFields() {
        return new Object[][] {
                {"firstname", null},
                {"lastname", null},
                {"totalprice",null},
                {"depositpaid",null},
                {"bookingdates",null},
                {"additionalneeds",null} // не всегда required — можно исключить или вынести в отдельный тест
        };
    }
}
