package co.com.bancolombia.mvccrud.commons;

import com.amazonaws.AmazonClientException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestGlobalUtilities {

    /**
     * Test when aws credentials are wrong
     */
    @Test(expected = AmazonClientException.class)
    public void testCreateAWSClient_noCredentials() {
        GlobalUtilities.createAWSClient();
    }
}
