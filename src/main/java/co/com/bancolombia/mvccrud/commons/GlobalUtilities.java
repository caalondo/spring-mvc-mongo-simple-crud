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

    public GlobalUtilities (ProfileCredentialsProvider provider) {
        this.provider = provider;
    }

    void setCredentials (ProfileCredentialsProvider provider) {
        this.provider = provider;
    }

    public String getBucketName () {
        return bucketName;
    }

    public AmazonS3 createAWSClient () {
        // Getting aws credentials
        AWSCredentials credentials;
        try {
            System.out.println("Running try/except =>> ");

            credentials = provider.getCredentials(); // Mock!!!

            System.out.println("CREDENTIALS ID: " + credentials.getAWSAccessKeyId());
            System.out.println("CREDENTIALS KEY: " + credentials.getAWSSecretKey());
        } catch (Exception e) {
            System.out.println("ERROOOOOOOOOOOOOOORRR!!!!");
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
