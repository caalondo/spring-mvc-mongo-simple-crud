package co.com.bancolombia.mvccrud.commons;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TestDummyFunctions {

    @Test
    public void testCalcFunction () {

        DummyFunctions dummyFunctions = new DummyFunctions();
        DummyFunctions dummyFunctionsMock = spy(dummyFunctions);
        DummyFunctions2 dummyFunctions2Mock = mock(DummyFunctions2.class);

        when(dummyFunctions2Mock.calcSuma(anyDouble(), anyDouble())).thenReturn(100000.0);

        assertEquals(dummyFunctions2Mock.calcSuma(1, 1), 100000.0, 0.000001);
        System.out.println(dummyFunctionsMock.calcResult(10, 20));
    }
}
