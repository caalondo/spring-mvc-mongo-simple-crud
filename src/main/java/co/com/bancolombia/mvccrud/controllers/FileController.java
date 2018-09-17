package co.com.bancolombia.mvccrud.controllers;

import co.com.bancolombia.mvccrud.commons.GlobalUtilities;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import co.com.bancolombia.mvccrud.commons.FileUtilities;
import jdk.nashorn.internal.objects.Global;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value = "/file-aws")
public class FileController {

    @RequestMapping(value = {"/{objectKey}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String readFile (@PathVariable String objectKey, HttpServletResponse httpResponse) {

        GlobalUtilities globalUtilities = new GlobalUtilities(new ProfileCredentialsProvider());
        AmazonS3 s3 = globalUtilities.createAWSClient();

        // Getting object from S3 bucket
        String bucketName = GlobalUtilities.bucketName;
        try {
            S3Object testObject = s3.getObject(new GetObjectRequest(bucketName, objectKey));

            S3ObjectInputStream stream = testObject.getObjectContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));

            String contentFile;
            try {
                String temp;
                StringBuilder sb = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null){
                    sb = sb.append(temp);
                }
                bufferedReader.close();
                contentFile = sb.toString();
                stream.close();
            } catch (IOException e) {
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("error", e);
                JSONObject jsonResponse = GlobalUtilities.createGeneralResponse(
                        500,
                        "Exception while reading the string " + e,
                        jsonBody
                );
                httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return jsonResponse.toString();
            }

            JSONObject jsonFile = new JSONObject();
            jsonFile.put("content", contentFile);
            jsonFile.put("name", testObject.getKey());
            jsonFile.put("bucket", testObject.getBucketName());
            jsonFile.put("type", testObject.getObjectMetadata().getContentType());
            jsonFile.put("encoding", testObject.getObjectMetadata().getContentEncoding());

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("file", jsonFile);

            JSONObject jsonResponse = GlobalUtilities.createGeneralResponse(
                    200,
                    "File read correctly!",
                    jsonBody
            );
            httpResponse.setStatus(HttpServletResponse.SC_CREATED);
            return jsonResponse.toString();
        } catch (Exception e) {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("error", e);
            JSONObject jsonResponse = GlobalUtilities.createGeneralResponse(
                    500,
                    "Error getting object '" + objectKey + "' from bucket " + bucketName + "! ",
                    jsonBody
            );
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return jsonResponse.toString();
        }
    }

    @RequestMapping(
            value = "/{objectKey}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String createFile (@PathVariable String objectKey, HttpServletResponse httpResponse) {

        GlobalUtilities globalUtilities = new GlobalUtilities(new ProfileCredentialsProvider());
        AmazonS3 s3 = globalUtilities.createAWSClient();
        String bucketName = GlobalUtilities.bucketName;

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String currentDatetime = sdf.format(date);

        String textFile = "=== FILE TEST ===\n\nFile creation date: " + currentDatetime;
        File file = FileUtilities.createSimpleFile(objectKey, ".txt", textFile);

        if (!FileUtilities.checkIfFileExists(objectKey)) {
            try {
                s3.putObject(new PutObjectRequest(bucketName, objectKey, file));
                JSONObject jsonFile = new JSONObject();
                jsonFile.put("objectKey", objectKey);
                jsonFile.put("name", objectKey + ".txt");
                jsonFile.put("bucket", bucketName);
                jsonFile.put("extension", ".txt");

                JSONObject jsonBody = new JSONObject();
                jsonBody.put("file", jsonFile);
                JSONObject jsonResponse = GlobalUtilities.createGeneralResponse(
                        201,
                        "File '" + objectKey + ".txt' created successfully",
                        jsonBody
                );
                httpResponse.setStatus(HttpServletResponse.SC_CREATED);
                return jsonResponse.toString();
            } catch (Exception e) {
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("error", e);
                JSONObject jsonResponse = GlobalUtilities.createGeneralResponse(
                        500,
                        "Error creating object '" + objectKey + "' at bucket " + bucketName + "!",
                        jsonBody
                );
                httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return jsonResponse.toString();
            }
        } else {
            JSONObject jsonBody = new JSONObject();
            JSONObject jsonResponse = GlobalUtilities.createGeneralResponse(
                    400,
                    "File '" + objectKey + ".txt' already exists at bucket '" + bucketName + "'! Consider to change file name",
                    jsonBody
            );
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return jsonResponse.toString();
        }
    }

    @RequestMapping(value = "/{objectKey}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public String replaceFile (@PathVariable String objectKey, HttpServletResponse httpResponse) {
        GlobalUtilities globalUtilities = new GlobalUtilities(new ProfileCredentialsProvider());
        AmazonS3 s3 = globalUtilities.createAWSClient();
        String bucketName = GlobalUtilities.bucketName;

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String currentDatetime = sdf.format(date);

        String textFile = "=== FILE TEST ===\n\nFile creation date: " + currentDatetime;
        File file = FileUtilities.createSimpleFile(objectKey, ".txt", textFile);

        if (FileUtilities.checkIfFileExists(objectKey)) {
            try {
                s3.putObject(new PutObjectRequest(bucketName, objectKey, file));
                JSONObject jsonFile = new JSONObject();
                jsonFile.put("objectKey", objectKey);
                jsonFile.put("name", objectKey + ".txt");
                jsonFile.put("bucket", bucketName);
                jsonFile.put("extension", ".txt");

                JSONObject jsonBody = new JSONObject();
                jsonBody.put("file", jsonFile);
                JSONObject jsonResponse = GlobalUtilities.createGeneralResponse(
                        201,
                        "File '" + objectKey + ".txt' updated successfully",
                        jsonBody
                );
                httpResponse.setStatus(HttpServletResponse.SC_CREATED);
                return jsonResponse.toString();
            } catch (Exception e) {
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("error", e);
                JSONObject jsonResponse = GlobalUtilities.createGeneralResponse(
                        500,
                        "Error updating object '" + objectKey + "' at bucket " + bucketName + "!",
                        jsonBody
                );
                httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return jsonResponse.toString();
            }
        } else {
            JSONObject jsonBody = new JSONObject();
            JSONObject jsonResponse = GlobalUtilities.createGeneralResponse(
                    400,
                    "File '" + objectKey + ".txt' does not exist at bucket '" + bucketName + "'!",
                    jsonBody
            );
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return jsonResponse.toString();
        }
    }

    @RequestMapping(value = "/{objectKey}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteFile (@PathVariable String objectKey, HttpServletResponse httpResponse) {

        GlobalUtilities globalUtilities = new GlobalUtilities(new ProfileCredentialsProvider());
        AmazonS3 s3 = globalUtilities.createAWSClient();
        String bucketName = GlobalUtilities.bucketName;

        if (FileUtilities.checkIfFileExists(objectKey)) {
            try {
                s3.deleteObject(bucketName, objectKey);
                JSONObject jsonBody = new JSONObject();
                JSONObject jsonResponse = GlobalUtilities.createGeneralResponse(
                        200,
                        "File '" + objectKey + ".txt' deleted successfully from bucket '" + bucketName + "'!",
                        jsonBody
                );
                httpResponse.setStatus(HttpServletResponse.SC_OK);
                return jsonResponse.toString();
            } catch (Exception e) {
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("error", e);
                JSONObject jsonResponse = GlobalUtilities.createGeneralResponse(
                        500,
                        "Error deleting object '" + objectKey + "' at bucket " + bucketName + "!",
                        jsonBody
                );
                httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return jsonResponse.toString();
            }
        } else {
            JSONObject jsonBody = new JSONObject();
            JSONObject jsonResponse = GlobalUtilities.createGeneralResponse(
                    400,
                    "File '" + objectKey + ".txt' does not exist at bucket '" + bucketName + "'!",
                    jsonBody
            );
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return jsonResponse.toString();
        }
    }
}
