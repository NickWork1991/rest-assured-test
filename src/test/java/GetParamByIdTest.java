import io.qameta.allure.*;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Epic("User management")
@Feature("Get booking by Id")

public class GetParamByIdTest {
    private static final Logger logger= LogManager.getLogger(GetParamByIdTest.class);
    @BeforeMethod
    void beforeGetParam(){
        APIServices apiServices = new APIServices();
        apiServices.getToken();
    }

    // Создаем датапровайдер для ввода различных данных в параметризированный тест
    @DataProvider(name = "bookingIds")
    public Object[][] bookingIds(){
        return new Object[][]{
                {"766", 200}, // valid Id
                {"7777", 404}, // non-exist Id
                {"abc", 404}, // string
                {"-1", 404}, // invalid Id
                {"0", 404}, // zero
                {null, 404}  // null
        };
    }

    @Test(dataProvider = "bookingIds")
    @Story("Try to get booking Id using different valid and not valid value")
    @Severity(SeverityLevel.CRITICAL)
    void getParamTest(String id, int expectedStatusCode){
        APIServices apiServices = new APIServices();
        Response response = apiServices.getBookingById(id);

        Allure.step("Verify that when use particular Id : " + id + " expected status code is received : " + expectedStatusCode);
        Assert.assertEquals(response.statusCode(),expectedStatusCode, "Expected status code is applied : " +expectedStatusCode);
    }
}

