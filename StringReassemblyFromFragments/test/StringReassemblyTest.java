import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class StringReassemblyTest {

    @Test
    public void testOverlapPineApple() {
        String str1 = "pineap";
        String str2 = "apple";
        int overlap = StringReassembly.overlap(str1, str2);
        assertEquals(2, overlap);
    }

    @Test
    public void testOverlapVeryLargeOverlap() {
        String str1 = "Verylargeover";
        String str2 = "largeoverlap";
        int overlap = StringReassembly.overlap(str1, str2);
        assertEquals(9, overlap);
    }

    @Test
    public void testOverlapNoOverLap() {
        String str1 = "Banana";
        String str2 = "Strawberry";
        int overlap = StringReassembly.overlap(str1, str2);
        assertEquals(0, overlap);
    }

    @Test
    public void testCombinationPineapple() {
        String str1 = "pineap";
        String str2 = "apple";
        int overlap = 2;
        String combine = StringReassembly.combination(str1, str2, overlap);
        assertEquals("pineapple", combine);
    }

    @Test
    public void testCombinationVeryLargeOverlap() {
        String str1 = "Verylargeover";
        String str2 = "largeoverlap";
        int overlap = 9;
        String combine = StringReassembly.combination(str1, str2, overlap);
        assertEquals("Verylargeoverlap", combine);
    }

    @Test
    public void testAddToSetAvoidingSubstrings1() {
        Set<String> strSet = new Set1L<>();
        strSet.add("pineapple");
        strSet.add("strawberry");
        strSet.add("water");
        String str = "watermelon";
        Set<String> expect = new Set1L<>();
        expect.add("pineapple");
        expect.add("strawberry");
        expect.add("watermelon");
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        assertEquals(expect, strSet);
    }

    @Test
    public void testAddToSetAvoidingSubstrings2() {
        Set<String> strSet = new Set1L<>();
        strSet.add("fruit");
        strSet.add("vegetable");
        strSet.add("meats");
        String str = "meat";
        Set<String> expect = new Set1L<>();
        expect.add("fruit");
        expect.add("vegetable");
        expect.add("meats");
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        assertEquals(expect, strSet);
    }

    @Test
    public void testPrintWithLineSeparators1() {
        SimpleWriter out = new SimpleWriter1L("test.txt");
        SimpleReader in = new SimpleReader1L("test.txt");
        String text = "pineapple~watermelon";
        String expect = "pineapple" + "\n" + "watermelon";
        StringReassembly.printWithLineSeparators(text, out);
        String test = in.nextLine();
        String test2 = in.nextLine();
        in.close();
        out.close();
        assertEquals(expect, test + "\n" + test2);
    }

    @Test
    public void testPrintWithLineSeparators2() {
        SimpleWriter out = new SimpleWriter1L("test.txt");
        SimpleReader in = new SimpleReader1L("test.txt");
        String text = "pine apples are yum~fruit is cool~Ive enjoyed strawberrys since 3";
        String expect = "pine apples are yum" + "\n" + "fruit is cool" + "\n"
                + "Ive enjoyed strawberrys since 3";
        StringReassembly.printWithLineSeparators(text, out);
        String test = in.nextLine();
        String test2 = in.nextLine();
        String test3 = in.nextLine();
        in.close();
        out.close();
        assertEquals(expect, test + "\n" + test2 + "\n" + test3);
    }

    @Test
    public void testAssemble1() {
        Set<String> strSet = new Set1L<>();
        strSet.add("fruit wa");
        strSet.add("was ma");
        strSet.add("ade in 1992");
        Set<String> expect = new Set1L<>();
        expect.add("fruit was made in 1992");
        StringReassembly.assemble(strSet);
        assertEquals(expect, strSet);
    }

    @Test
    public void testAssemble2() {
        Set<String> strSet = new Set1L<>();
        strSet.add("fruit wa");
        strSet.add("was ma");
        strSet.add("cheese");
        strSet.add("ade in 1992");
        strSet.add("applesause");
        Set<String> expect = new Set1L<>();
        expect.add("fruit was made in 1992");
        expect.add("applesause");
        expect.add("cheese");
        StringReassembly.assemble(strSet);
        assertEquals(expect, strSet);
    }

}