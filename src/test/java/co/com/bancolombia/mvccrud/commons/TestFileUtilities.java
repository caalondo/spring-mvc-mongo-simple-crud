package co.com.bancolombia.mvccrud.commons;

import ch.qos.logback.core.util.FileUtil;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FileUtilities.class, File.class})
public class TestFileUtilities {

    private FileUtilities fileUtilities;

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void testCreateSimpleFile_exceptionCreateTempFile () {
        fileUtilities = new FileUtilities();
        PowerMockito.mockStatic(File.class);

        try {
            PowerMockito.when(File.createTempFile(anyString(), anyString())).thenThrow(Exception.class);
        } catch (Exception e) {
            System.out.println("Error!");
        }

        File response = fileUtilities.createSimpleFile("Hola", "txt", "Test 1!!!");
        assertNull(response);
    }

    @Test
    public void testCreateSimpleFile_exceptionDeleteOnExit () {
        fileUtilities = new FileUtilities();
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
}
