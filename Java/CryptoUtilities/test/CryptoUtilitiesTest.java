import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * @author Arnaud Cabello
 *
 */
public class CryptoUtilitiesTest {

    @Test
    public void testReduceToGCD_0_0() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals("0", n.toString());
        assertEquals("0", m.toString());
    }

    @Test
    public void testReduceToGCD_30_21() {
        NaturalNumber n = new NaturalNumber2(30);
        NaturalNumber m = new NaturalNumber2(21);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals("3", n.toString());
        assertEquals("0", m.toString());
    }

    @Test
    public void testReduceToGCD_10_20() {
        NaturalNumber n = new NaturalNumber2(10);
        NaturalNumber m = new NaturalNumber2(20);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals("10", n.toString());
        assertEquals("0", m.toString());
    }

    @Test
    public void testReduceToGCD_128_1024() {
        NaturalNumber n = new NaturalNumber2(128);
        NaturalNumber m = new NaturalNumber2(1024);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals("128", n.toString());
        assertEquals("0", m.toString());
    }

    @Test
    public void testIsEven_0() {
        NaturalNumber n = new NaturalNumber2(0);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals("0", n.toString());
        assertTrue(result);
    }

    @Test
    public void testIsEven_1() {
        NaturalNumber n = new NaturalNumber2(1);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals("1", n.toString());
        assertTrue(!result);
    }

    @Test
    public void testPowerMod_0_0_2() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber p = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(2);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals("1", n.toString());
        assertEquals("0", p.toString());
        assertEquals("2", m.toString());
    }

    @Test
    public void testPowerMod_17_18_19() {
        NaturalNumber n = new NaturalNumber2(17);
        NaturalNumber p = new NaturalNumber2(18);
        NaturalNumber m = new NaturalNumber2(19);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals("1", n.toString());
        assertEquals("18", p.toString());
        assertEquals("19", m.toString());
    }

    @Test
    public void testIsWitnessToCompositeness_15_17() {
        NaturalNumber w = new NaturalNumber2(15);
        NaturalNumber n = new NaturalNumber2(17);

        boolean prime = CryptoUtilities.isWitnessToCompositeness(w, n);
        assertEquals(false, prime);
    }

    @Test
    public void testIsWitnessToCompositeness_14_24() {
        NaturalNumber w = new NaturalNumber2(14);
        NaturalNumber n = new NaturalNumber2(24);

        boolean prime = CryptoUtilities.isWitnessToCompositeness(w, n);
        assertEquals(true, prime);
    }

    @Test
    public void testIsPrime2_11() {
        NaturalNumber n = new NaturalNumber2(71);

        boolean prime2 = CryptoUtilities.isPrime2(n);
        assertEquals(true, prime2);
    }

    @Test
    public void testIsPrime2_78() {
        NaturalNumber n = new NaturalNumber2(82);

        boolean prime2 = CryptoUtilities.isPrime2(n);
        assertEquals(false, prime2);
    }

    public void testIsPrime2_99() {
        NaturalNumber n = new NaturalNumber2(99);

        boolean prime2 = CryptoUtilities.isPrime2(n);
        assertEquals(true, prime2);
    }

    @Test
    public void testIsPrime2_2() {
        NaturalNumber n = new NaturalNumber2(2);
        assertTrue(CryptoUtilities.isPrime2(n));
    }

    @Test
    public void testIsPrime2_80() {
        NaturalNumber n = new NaturalNumber2(80);
        assertTrue(!CryptoUtilities.isPrime2(n));
    }

    @Test
    public void testGenerateNextLikelyToPrime_12() {
        NaturalNumber n = new NaturalNumber2(12);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals("13", n.toString());

    }

    @Test
    public void testGenerateNextLikelyToPrime_72() {
        NaturalNumber n = new NaturalNumber2(72);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals("73", n.toString());
    }

}