import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;

public class GlossaryProjectTest {

    @Test
    public void generateElements_test() {
        String word = "Apples";
        Set<Character> check = new Set1L<Character>();
        Glossary.generateElements(word, check);
        Set<Character> result = check.newInstance();
        result.add('A');
        result.add('p');
        result.add('l');
        result.add('e');
        result.add('s');
        assertEquals(check, result);
    }

    @Test
    public void nextWordOrSeparator_test1() {
        final String separators = " \t, ";
        Set<Character> separatorSet = new Set1L<>();
        Glossary.generateElements(separators, separatorSet);
        String str = "Pineapples are yum";
        int position = 11;
        String nextWord = Glossary.nextWordOrSeparator(str, position,
                separatorSet);
        assertEquals(nextWord, "are");
    }

    @Test
    public void nextWordOrSeparator_test2() {
        final String separators = " \t, ";
        Set<Character> separatorSet = new Set1L<>();
        Glossary.generateElements(separators, separatorSet);
        String str = "Pineapples are yum";
        int position = 10;
        String nextWord = Glossary.nextWordOrSeparator(str, position,
                separatorSet);
        assertEquals(nextWord, " ");
    }

    @Test
    public void nextWordOrSeparator_test3() {
        final String separators = " \t, ";
        Set<Character> separatorSet = new Set1L<>();
        Glossary.generateElements(separators, separatorSet);
        String str = "Pineapples are yum";
        int position = 16;
        String nextWord = Glossary.nextWordOrSeparator(str, position,
                separatorSet);
        assertEquals(nextWord, "um");
    }

    @Test
    public void getDefine_test() {
        String definition = "something that one wishes to convey, especially by language";
        SimpleReader out = new SimpleReader1L("inputTest.txt");
        String check = Glossary.getDefine(out);
        assertEquals(definition, check);
        out.close();
    }

    @Test
    public void arraySort_test() {
        ArrayList<String> first = new ArrayList<String>();
        first.add("Apple");
        first.add("Strawberry");
        first.add("Orange");
        ArrayList<String> second = new ArrayList<String>();
        second.add("Apple");
        second.add("Orange");
        second.add("Strawberry");
        ArrayList<String> result = Glossary.sorter(first);
        assertEquals(result.get(0), second.get(0));
        assertEquals(result.get(1), second.get(1));
        assertEquals(result.get(2), second.get(2));
    }

}
