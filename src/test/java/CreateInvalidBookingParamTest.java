import io.qameta.allure.Allure;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.units.qual.A;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

public class CreateInvalidBookingParamTest {

    private static final Logger logger = LogManager.getLogger(CreateInvalidBookingParamTest.class);

    @BeforeMethod
    void beforeCreateInvalidBooking(){
        APIServices apiServices = new APIServices();
        apiServices.getToken();

    }

    @Test(dataProvider ="missingRequiredFields")
    @Story("Create param test to check how system works without required fields")
    @Severity(SeverityLevel.CRITICAL)
    void createInvalidBookingTest(String fieldToRemove){
        logger.info("Scenario #1:Create param test to check how system works without required fields " + fieldToRemove);

        BasePayload basePayload =new BasePayload();
        Map<String,Object>payload=basePayload.getBaseBookingPayload();
        payload.remove(fieldToRemove);
        // Удаляется из Map то поле, которое в этом прогоне теста считается отсутствующим (например "lastname")
        APIServices apiServices = new APIServices();

        Response response = apiServices.createInvalidBooking(payload);
        // Отправляется POST-запрос на создание бронирования с модифицированным телом (без одного required поля).

        Allure.step("Remove particular field : " + fieldToRemove);
        Allure.step("Response : " + response.asString());

        Assert.assertTrue(response.statusCode()>=400, "Expected status code 400/ 500 when missing" + fieldToRemove);
    }
    @DataProvider(name = "missingRequiredFields")
    //Аннотация TestNG, указывающая, что тест будет выполняться
    // многократно с разными входными значениями из DataProvider-а missingRequiredFields.
    public Object[][] requiredFields() {
        return new Object[][] {
                {"firstname"},
                {"lastname"},
                {"totalprice"},
                {"depositpaid"},
                {"bookingdates"},
                {"additionalneeds"} // не всегда required — можно исключить или вынести в отдельный тест
        };
    }
}
