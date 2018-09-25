package co.com.bancolombia.mvccrud.commons;

import ch.qos.logback.core.util.FileUtil;
import com.amazonaws.services.s3.AmazonS3;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FileUtilities.class, File.class})
public class TestFileUtilities {

    private FileUtilities fileUtilities;

    @Before
    public void setUp() {
        fileUtilities = new FileUtilities();
    }

    @After
    public void tearDown() {

    }

    /**
     * Test when File.createTempFile throws an exception
     */
    @Test
    public void testCreateSimpleFile_exceptionCreateTempFile () {
        PowerMockito.mockStatic(File.class);

        try {
            PowerMockito.when(File.createTempFile(anyString(), anyString())).thenThrow(Exception.class);
        } catch (Exception e) {
            System.out.println("Error!");
        }

        File response = fileUtilities.createSimpleFile("Hola", "txt", "Test 1!!!");
        assertNull(response);
    }

    /**
     * Test when deleteOnExit throws an exception
     */
    @Test
    public void testCreateSimpleFile_exceptionDeleteOnExit () {
        PowerMockito.mockStatic(File.class);

        // Mock for file instance
        File fileMock = mock(File.class);
        try {
            doThrow(Exception.class).when(fileMock).deleteOnExit();
            PowerMockito.when(File.createTempFile(anyString(), anyString())).thenReturn(fileMock);
        } catch (Exception e) {
            System.out.println("Error!: " + e);
        }
        File response = fileUtilities.createSimpleFile("Hola", "txt", "Test 1!!!");

        verify(fileMock, times(1)).deleteOnExit();
        assertNull(response);
    }

    /**
     * Test when write throws an exception
     */
    @Test
    public void testCreateSimpleFile_exceptionWrite () {
        PowerMockito.mockStatic(OutputStreamWriter.class);
        OutputStreamWriter writerMock = mock(OutputStreamWriter.class);

        try {
            doThrow(Exception.class).when(writerMock).write(anyString());
            PowerMockito.whenNew(OutputStreamWriter.class).withArguments(any(OutputStream.class)).thenReturn(writerMock);
            File response = fileUtilities.createSimpleFile("Hola", "txt", "Test 1!!!");
            verify(writerMock, times(1)).write(anyString());
            assertNull(response);
        } catch (Exception e) {
            System.out.println("Error!: " + e);
        }
    }

    /**
     * Test when everything is ok
     */
    @Test
    public void testCreateSimpleFile_worksOk () {
        PowerMockito.mockStatic(OutputStreamWriter.class);
        OutputStreamWriter writerMock = mock(OutputStreamWriter.class);

        try {
            doNothing().when(writerMock).write(anyString());
            doNothing().when(writerMock).close();
            PowerMockito.whenNew(OutputStreamWriter.class).withArguments(any(OutputStream.class)).thenReturn(writerMock);

            File response = fileUtilities.createSimpleFile("Hola", "txt", "Test 1!!!");
            verify(writerMock, times(1)).write(anyString());
            verify(writerMock, times(1)).close();
            assertThat(response, instanceOf(File.class));
        } catch (Exception e) {
            System.out.println("Error!: " + e);
        }
    }
}
