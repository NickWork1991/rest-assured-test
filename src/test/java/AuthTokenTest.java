import io.qameta.allure.*;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("User management")
@Feature("Get token")
public class AuthTokenTest {
    private static final Logger logger= LogManager.getLogger(AuthTokenTest.class);
    @Test(priority = 1)
    @Story("Try to get token")
    @Severity(SeverityLevel.CRITICAL)
    public void getTokenTest(){
        logger.info("Scenario N1: Verify that can get token");
        Allure.step("Get new instance - apiServices");
        APIServices apiServices = new APIServices();
        Response response = apiServices.getToken();

        Allure.step("Verify that expected status code - 200");
        Assert.assertEquals(response.statusCode(), 200, "Expected status code - 200");

        Allure.step("Verify that response has containKey - 'token'");
        Assert.assertTrue(response.jsonPath().getMap("").containsKey("token"),"Verify that response has containKey - 'token'");

        String token = response.jsonPath().getString("token");
        Allure.step("Verify that token is not NULL"   +  token);
        Assert.assertNotNull(token,"Verify that token is not NULL");

        Allure.step("Verify that token is not EMPTY");
        Assert.assertFalse(token.isEmpty(), "Verify that token is not EMPTY");
    }
}
