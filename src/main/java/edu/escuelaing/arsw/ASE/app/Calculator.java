package edu.escuelaing.arsw.ASE.app;

/**
 * Class that provides basic arithmetic operations.
 */
public class Calculator {
    
    /**
     * Adds two integers.
     * @param a The first integer.
     * @param b The second integer.
     * @return The sum of a and b.
     */
    public static int add(int a, int b) {
        return a + b;
    }

    /**
     * Subtracts two integers.
     * @param a The first integer.
     * @param b The second integer.
     * @return The result of subtracting b from a.
     */
    public static int subtract(int a, int b) {
        return a - b;
    }

    /**
     * Multiplies two integers.
     * @param a The first integer.
     * @param b The second integer.
     * @return The product of a and b.
     */
    public static int multiply(int a, int b) {
        return a * b;
    }

    /**
     * Divides two integers.
     * @param a The dividend.
     * @param b The divisor.
     * @return The result of dividing a by b.
     * @throws IllegalArgumentException if b is zero.
     */
    public static int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return a / b;
    }
}

