package co.com.bancolombia.mvccrud.commons;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.json.JSONObject;

public class GlobalUtilities {

    private ProfileCredentialsProvider provider;
    public static final String bucketName = "test-c4rl05";

    public GlobalUtilities () {
        this.provider = new ProfileCredentialsProvider();
    }

    public AmazonS3 createAWSClient () {
        // Getting aws credentials
        AWSCredentials credentials;
        try {
            credentials = provider.getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException("Error getting aws credentials: " + e);
        }
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    public static JSONObject createGeneralResponse (int status, String message, JSONObject response) {
        JSONObject body = new JSONObject();
        body.put("status", status);
        body.put("message", message);
        body.put("response", response);
        return body;
    }
}
