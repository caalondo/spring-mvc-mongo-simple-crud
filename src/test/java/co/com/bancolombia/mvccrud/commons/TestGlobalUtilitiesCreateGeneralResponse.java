package co.com.bancolombia.mvccrud.commons;

import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;


/**
 * Tests for function 'createGeneralResponse'
 */
public class TestGlobalUtilitiesCreateGeneralResponse {
    // Test that verifies response structure of 'createGeneralResponse' function
    @Test
    public void testCreateGeneralResponse () {
        int status_test = 200;
        String message_test = "Ok!";
        JSONObject response_test = new JSONObject();
        JSONObject response = GlobalUtilities.createGeneralResponse(status_test, message_test, response_test);
        String json_expected = "{\"status\":" +
                status_test + ",\"message\":\"" +
                message_test + "\",\"response\":" +
                response_test.toString() + "}";
        JSONAssert.assertEquals(json_expected, response, false);
    }
}
