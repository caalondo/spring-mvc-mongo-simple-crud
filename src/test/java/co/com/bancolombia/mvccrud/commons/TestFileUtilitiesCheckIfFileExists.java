package co.com.bancolombia.mvccrud.commons;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


/**
 * Tests for function 'checkIfFileExists'
 */
@RunWith(MockitoJUnitRunner.class)
public class TestFileUtilitiesCheckIfFileExists {
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
