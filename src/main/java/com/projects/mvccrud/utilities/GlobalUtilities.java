package com.projects.mvccrud.utilities;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.json.JSONObject;

public final class GlobalUtilities {

    public static final String bucketName = "test-c4rl05";

    public static AmazonS3 createAWSClient () {
        // Getting aws credentials
        AWSCredentials credentials;
        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
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
