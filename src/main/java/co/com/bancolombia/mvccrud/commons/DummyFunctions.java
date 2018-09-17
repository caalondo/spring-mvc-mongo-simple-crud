package co.com.bancolombia.mvccrud.commons;


public class DummyFunctions {

    public double calcResult (double val1, double val2) {
        System.out.println("===== [DummyFunctions] =====");
        System.out.println("=> DF1 [1] val1: " + val1 + " - val2: " + val2);
        DummyFunctions2 dummyFunctions2 = new DummyFunctions2();

        double result = dummyFunctions2.calcSuma(val1, val2); // ==>>> mock!!!

        System.out.println("=> DF1 [2] calcResult: " + result);
        return result;
    }
}
