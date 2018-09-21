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
        System.out.println("[1]");
        fileUtilities = new FileUtilities();
        File fileFake = new File("/c/");

        PowerMockito.mockStatic(File.class);
        try {
            PowerMockito.when(File.createTempFile(anyString(), anyString())).thenReturn(fileFake);
            File response = File.createTempFile("Test", "txt");
            System.out.println("RESP: " + response);
        } catch (Exception e) {
            System.out.println("Error!");
        }

        fileUtilities.createSimpleFile("Hola", "txt", "Test 1!!!");
    }
}
