package com.projects.clientscrud.controllers;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.projects.clientscrud.utilities.GlobalUtilities;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/file-aws")
public class FileController {

    @RequestMapping(value = {"/{objectKey}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String readFile (@PathVariable String objectKey) {

        // Getting aws credentials
        AWSCredentials credentials;
        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException("Error getting aws credentials: " + e);
        }
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();

        // Getting object from S3 bucket
        String bucketName = "test-c4rl05";
        S3Object testObject;
        JSONObject jsonResponse = new JSONObject();
        try {
            testObject = s3.getObject(new GetObjectRequest(bucketName, objectKey));

            JSONObject jsonFile = new JSONObject();
            jsonFile.put("name", testObject.getKey());
            jsonFile.put("bucket", testObject.getBucketName());
            jsonFile.put("type", testObject.getObjectMetadata().getContentType());
            jsonFile.put("encoding", testObject.getObjectMetadata().getContentEncoding());

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("file", jsonFile);

            jsonResponse = GlobalUtilities.createGeneralResponse(
                    200,
                    "File read correctly!",
                    jsonBody
            );
            return jsonResponse.toString();
        } catch (Exception e) {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("error", e);
            jsonResponse = GlobalUtilities.createGeneralResponse(
                    500,
                    "Error getting object '" + objectKey + "' from bucket " + bucketName + "! ",
                    jsonBody
            );
            return jsonResponse.toString();
        }
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public String createFile () {
        return "Creating...";
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.PUT)
    public String replaceFile () {
        return "Updating...";
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.DELETE)
    public String deleteFile () {
        return "Deleting...";
    }
}
