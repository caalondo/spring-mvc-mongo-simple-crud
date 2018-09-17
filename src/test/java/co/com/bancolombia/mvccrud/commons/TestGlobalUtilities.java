package co.com.bancolombia.mvccrud.commons;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestGlobalUtilities {

    @Before
    public void setUp() {
        System.out.println("Before unit test! => ");
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test when aws credentials are wrong
     */
    @Test(expected = AmazonClientException.class)
    public void testCreateAWSClient_noCredentials() {

        AWSCredentials aws_credentials_fake = new AWSCredentials() {
            @Override
            public String getAWSAccessKeyId() {
                return "It works ID";
            }

            @Override
            public String getAWSSecretKey() {
                return "It works KEY";
            }
        };

        GlobalUtilities globalUtilities = new GlobalUtilities(new ProfileCredentialsProvider());
        ProfileCredentialsProvider profileCredentialsProviderMock = mock(ProfileCredentialsProvider.class);
        globalUtilities.setCredentials(profileCredentialsProviderMock);

        when(profileCredentialsProviderMock.getCredentials()).thenThrow(AmazonClientException.class);

//        GlobalUtilities globalUtilities =  spy(new GlobalUtilities());

//        when(profileCredentialsProviderMock.getCredentials()).thenReturn(aws_credentials_fake);
//        doReturn(aws_credentials_fake).when(profileCredentialsProviderMock).getCredentials();

//        AWSCredentials cred = profileCredentialsProviderMock.getCredentials();
//        System.out.println("===>>> Profile Cred KEY: " + cred.getAWSAccessKeyId());
//        System.out.println("===>>> Profile Cred SECRET: " + cred.getAWSSecretKey());

        globalUtilities.createAWSClient();
    }
}
