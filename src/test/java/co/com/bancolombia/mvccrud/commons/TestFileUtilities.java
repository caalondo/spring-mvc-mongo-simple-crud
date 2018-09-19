package co.com.bancolombia.mvccrud.commons;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.io.File;

public class TestFileUtilities {

    @InjectMocks
    private FileUtilities fileUtilities;

    @Mock
    private File fileMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testCreateSimpleFile_exceptionCreateTempFile () {
        try {
            when(File.createTempFile(anyString(), anyString())).thenReturn(null);
        } catch(Exception e) {
            System.out.println("Error Test: " + e);
        }

        File response = FileUtilities.createSimpleFile("name1", "txt", "Test 1!!!");
        System.out.println("File: " + response);
    }
}
