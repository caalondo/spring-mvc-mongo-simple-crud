package co.com.bancolombia.mvccrud.commons;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class FileUtilities {

    public File createSimpleFile (String fileName, String fileExtension, String text) {
        try {
            System.out.println("=> createSimpleFile => fileName: " + fileName + " => fileExtension: " + fileExtension);
            File file = File.createTempFile(fileName, fileExtension);
            System.out.println("File: " + file);
            file.deleteOnExit();

            Writer writer = new OutputStreamWriter(new FileOutputStream(file));
            writer.write(text);
            writer.close();
            System.out.println("Try/End createSimpleFile");
            return file;
        } catch (Exception e) {
            System.out.println("Error creating temporally file '" + fileName + "." + fileExtension + "'");
            return null;
        }
    }

    public static boolean checkIfFileExists (String objectKey) {
        GlobalUtilities globalUtilities = new GlobalUtilities(new ProfileCredentialsProvider());
        AmazonS3 s3 = globalUtilities.createAWSClient();
        String bucketName = GlobalUtilities.bucketName;
        return s3.doesObjectExist(bucketName, objectKey);
    }

}
