import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * It takes multiple inputs from the user and then finds the exponential values
 * to make the w^a*x^b*y^c*z^d as close to the users input of mu and also
 * returns the error yield
 *
 * @author Arnaud Cabello
 *
 */
public final class ABCDGuesser2 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ABCDGuesser2() {
    }

    /**
     * Repeatedly asks the user for a positive real number until the user enters
     * one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number entered by the user
     */
    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {
        double positiveDouble = -1;
        while (positiveDouble <= 0) {
            String userInput = in.nextLine();
            if (FormatChecker.canParseDouble(userInput)) {
                positiveDouble = Double.parseDouble(userInput);
            }
            if (positiveDouble <= 0) {
                out.print("Enter a new number: ");
            }
        }
        return positiveDouble;
    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number not equal to 1.0 entered by the user
     */
    private static double getPositiveDoubleNotOne(SimpleReader in,
            SimpleWriter out) {
        double posValueNotZero = 0;
        while (posValueNotZero <= 1) {
            String userInput = in.nextLine();
            if (FormatChecker.canParseDouble(userInput)) {
                posValueNotZero = Double.parseDouble(userInput);
            }
            if (posValueNotZero <= 1) {
                out.print("Enter a new number greater than 1: ");
            }
        }
        return posValueNotZero;
    }

    private static void pseudoscience(double w, double x, double y, double z,
            double mu, SimpleWriter out) {
        double exp1 = 0;
        double exp2 = 0;
        double exp3 = 0;
        double exp4 = 0;
        double current = mu / 2;
        double relativeError = 0;
        final double[] exp = { -5, -4, -3, -2, -1, -1 / 2.0, -1 / 3.0, -1 / 4.0,
                0, 1 / 4.0, 1 / 3.0, 1 / 2.0, 1, 2, 3, 4, 5 };
        for (int a = 0; a < exp.length; a++) {
            for (int b = 0; b < exp.length; b++) {
                for (int c = 0; c < exp.length; c++) {
                    for (int d = 0; d < exp.length; d++) {
                        double temp = (Math.pow(w, exp[a]))
                                * (Math.pow(x, exp[b])) * (Math.pow(y, exp[c]))
                                * (Math.pow(z, exp[d]));
                        if (Math.abs(temp - mu) < Math.abs(current - mu)) {
                            current = temp;
                            exp1 = exp[a];
                            exp2 = exp[b];
                            exp3 = exp[c];
                            exp4 = exp[d];
                        }
                    }
                }
            }
        }
        out.print("The exponent values in order are: ");
        out.print(exp1 + ", ");
        out.print(exp2 + ", ");
        out.print(exp3 + ", ");
        out.print(exp4);
        out.println("");
        relativeError = Math.abs((100 * (current / mu)) - 100);
        out.println("The relative error is: " + relativeError + "%");
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
        out.print("Enter a value for w: ");
        double wNumber = getPositiveDoubleNotOne(in, out);
        out.print("Enter a value for x: ");
        double xNumber = getPositiveDoubleNotOne(in, out);
        out.print("Enter a value for y: ");
        double yNumber = getPositiveDoubleNotOne(in, out);
        out.print("Enter a value for z: ");
        double zNumber = getPositiveDoubleNotOne(in, out);
        out.print("Enter a value for mu: ");
        double muNumber = getPositiveDouble(in, out);
        pseudoscience(wNumber, xNumber, yNumber, zNumber, muNumber, out);

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
