package co.com.bancolombia.mvccrud.commons;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestGlobalUtilities {

    @InjectMocks
    private GlobalUtilities globalUtilities;

    @Mock
    private ProfileCredentialsProvider profileCredentialsProviderMock;

    @Before
    public void setUp () {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown () {}

    /**
     * Test when AWS credentials are wrong in 'createAWSClient' function
     */
    @Test(expected = AmazonClientException.class)
    public void testCreateAWSClient_wrongCredentials () {
        when(profileCredentialsProviderMock.getCredentials()).thenThrow(AmazonClientException.class);
        globalUtilities.createAWSClient();
    }

    /**
    * Test when AWS credentials are correct in 'createAWSClient' function
    */
    @Test
    public void testCreateAWSClient_correctCredentials () {
        AWSCredentials aws_credentials_fake = new AWSCredentials() {
            @Override
            public String getAWSAccessKeyId() {
                return "It works ID";
            }

            @Override
            public String getAWSSecretKey() {
                return "It works KEY";
            }
        };

        when(profileCredentialsProviderMock.getCredentials()).thenReturn(aws_credentials_fake);
        AmazonS3 response = globalUtilities.createAWSClient();

        verify(profileCredentialsProviderMock, times(1)).getCredentials();
        assertThat(response, instanceOf(AmazonS3.class));
    }

    /**
     * Test that verifies response structure of 'createGeneralResponse' function
     */
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
