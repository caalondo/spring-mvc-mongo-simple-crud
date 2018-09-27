package co.com.bancolombia.mvccrud.commons;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class FileUtilities {

    private GlobalUtilities globalUtilities;

    public FileUtilities () {
        globalUtilities = new GlobalUtilities();
    }

    public File createSimpleFile (String fileName, String fileExtension, String text) {
        try {
            File file = File.createTempFile(fileName, fileExtension);
            file.deleteOnExit();
            Writer writer = new OutputStreamWriter(new FileOutputStream(file));
            writer.write(text);
            writer.close();
            return file;
        } catch (Exception e) {
            System.out.println("Error creating temporally file '" + fileName + "." + fileExtension + "'");
            return null;
        }
    }

    public boolean checkIfFileExists (String objectKey) {
        AmazonS3 s3 = globalUtilities.createAWSClient();
        String bucketName = GlobalUtilities.bucketName;
        System.out.println("S3: " + s3);
        return s3.doesObjectExist(bucketName, objectKey);
    }

}
