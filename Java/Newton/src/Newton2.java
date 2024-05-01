import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class Newton2 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Newton2() {
    }

    /**
     * Put a short phrase describing the static method myMethod here.
     */
    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     *
     * @param x
     *            positive number to compute square root of
     * @return estimate of square root
     */
    private static double sqrt(double x) {
        double error = 0.0001;
        double r = x;

        while (!((Math.abs((r * r) - x)) / x < (error * error))) {
            r = (r + (x / r)) / 2;
        }

        return r;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Put your main program code here; it may call myMethod as shown
         */
        out.print("Would you like to calculate a square root?: ");
        String userAnswer = in.nextLine();
        while (userAnswer.equals("y")) {
            out.print("Enter a number: ");
            double userNumber = in.nextDouble();
            double sqrtAnswer = 0;
            if (userNumber != 0) {
                sqrtAnswer = sqrt(userNumber);
            }
            out.println(sqrtAnswer);
            out.print("Would you like to calculate a square root?: ");
            userAnswer = in.nextLine();
        }
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
