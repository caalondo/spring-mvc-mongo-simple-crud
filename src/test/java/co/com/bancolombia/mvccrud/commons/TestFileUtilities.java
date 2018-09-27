package co.com.bancolombia.mvccrud.commons;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.*;


@RunWith(Enclosed.class)
public class TestFileUtilities {

    /**
     * Tests for function 'createSimpleFile'
     */
    @RunWith(PowerMockRunner.class)
    @PrepareForTest({FileUtilities.class, File.class})
    public static class TestCreateSimpleFile {

        private FileUtilities fileUtilities;

        @Before
        public void setUp() {
            fileUtilities = new FileUtilities();
        }

        @After
        public void tearDown() {}

        // Test when File.createTempFile throws an exception
        @Test
        public void testCreateSimpleFile_exceptionCreateTempFile () throws Exception {
            PowerMockito.mockStatic(File.class);
            PowerMockito.when(File.createTempFile(anyString(), anyString())).thenThrow(Exception.class);

            File response = fileUtilities.createSimpleFile("Hola", "txt", "Test 1!!!");
            assertNull(response);
        }

        // Test when deleteOnExit throws an exception
        @Test
        public void testCreateSimpleFile_exceptionDeleteOnExit () throws Exception {
            PowerMockito.mockStatic(File.class);

            // Mock for file instance
            File fileMock = mock(File.class);
            doThrow(Exception.class).when(fileMock).deleteOnExit();
            PowerMockito.when(File.createTempFile(anyString(), anyString())).thenReturn(fileMock);

            File response = fileUtilities.createSimpleFile("Hola", "txt", "Test 1!!!");

            verify(fileMock, times(1)).deleteOnExit();
            assertNull(response);
        }

        // Test when write throws an exception
        @Test
        public void testCreateSimpleFile_exceptionWrite () throws Exception {
            PowerMockito.mockStatic(OutputStreamWriter.class);
            OutputStreamWriter writerMock = mock(OutputStreamWriter.class);

            doThrow(Exception.class).when(writerMock).write(anyString());
            PowerMockito.whenNew(OutputStreamWriter.class).withArguments(any(OutputStream.class)).thenReturn(writerMock);
            File response = fileUtilities.createSimpleFile("Hola", "txt", "Test 1!!!");
            verify(writerMock, times(1)).write(anyString());
            assertNull(response);
        }

        // Test when everything is ok
        @Test
        public void testCreateSimpleFile_worksOk () throws Exception {
            PowerMockito.mockStatic(OutputStreamWriter.class);
            OutputStreamWriter writerMock = mock(OutputStreamWriter.class);

            doNothing().when(writerMock).write(anyString());
            doNothing().when(writerMock).close();
            PowerMockito.whenNew(OutputStreamWriter.class).withArguments(any(OutputStream.class)).thenReturn(writerMock);

            File response = fileUtilities.createSimpleFile("Hola", "txt", "Test 1!!!");
            verify(writerMock, times(1)).write(anyString());
            verify(writerMock, times(1)).close();
            assertThat(response, instanceOf(File.class));
        }
    }

    /**
     * Tests for function 'checkIfFileExists'
     */
    @RunWith(MockitoJUnitRunner.class)
    public static class TestCheckIfFileExists {

        @InjectMocks
        private FileUtilities fileUtilities;

        @Mock
        private GlobalUtilities globalUtilitiesMock;

        @Before
        public void setUp() {}

        @After
        public void tearDown() {}

        // Test methods called correctly
        @Test
        public void testCheckIfFileExists_methodsAreCalled () {
            AmazonS3 amazonS3Mock = mock(AmazonS3.class);
            doReturn(true).when(amazonS3Mock).doesObjectExist(anyString(), anyString());
            doReturn(amazonS3Mock).when(globalUtilitiesMock).createAWSClient();

            fileUtilities.checkIfFileExists("objectxyz");
            verify(globalUtilitiesMock, times(1)).createAWSClient();
            verify(amazonS3Mock, times(1)).doesObjectExist(anyString(), anyString());
        }
    }
}