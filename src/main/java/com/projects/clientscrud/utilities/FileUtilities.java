package com.projects.clientscrud.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public final class FileUtilities {

    public static File createSimpleFile (String fileName, String fileExtension, String text) {
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

}
