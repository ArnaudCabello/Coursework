import java.util.ArrayList;

import components.map.Map;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Arnaud Cabello
 *
 */
public final class Glossary {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Glossary() {
    }

    /**
     * Put a short phrase describing the static method myMethod here.
     */

    public static void heading(SimpleWriter out) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + "Glossary" + "</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>" + "Glossary" + "</h2>");
        out.println("<hr />");
        out.println("<h3>Index</h3>");
    }

    static void generateElements(String str, Set<Character> strSet) {
        for (int i = 0; i < str.length(); i++) {
            char x = str.charAt(i);
            boolean subSet = strSet.contains(x);
            if (subSet == false) {
                strSet.add(x);
            }
        }
    }

    public static String getDefine(SimpleReader glossaryName) {
        String definition = "";
        if (!glossaryName.atEOS()) {
            definition = glossaryName.nextLine();
            if (!glossaryName.atEOS() && !definition.equals("")) {
                definition += " " + getDefine(glossaryName);
            }
        }
        return definition;
    }

    public static ArrayList<String> sorter(ArrayList<String> allWords) {
        ArrayList<String> order = new ArrayList<String>();
        while (allWords.size() != 1) {
            int temp = 0;
            for (int i = 1; i < allWords.size(); i++) {
                int first = allWords.get(temp).charAt(0);
                int second = allWords.get(i).charAt(0);
                if (first > second) {
                    temp = i;
                }
            }
            order.add(allWords.get(temp));
            allWords.remove(temp);
        }
        order.add(allWords.get(0));
        return order;

    }

    public static void contents(ArrayList<String> allWords, SimpleWriter out) {
        if (allWords.size() != 0) {
            out.println("<ul>");
            for (int i = 0; i < allWords.size(); i++) {
                out.println("<li><a href=" + '"' + allWords.get(i) + ".html"
                        + '"' + ">" + allWords.get(i) + "</a></li>");
            }
            out.println("</ul>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    public static void outputTerms(String word, String definition,
            Set<String> wordSet, Set<Character> separator, String GlossaryName,
            SimpleWriter out) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + word + "</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2><b><i><font color=" + '"' + "red" + '"' + ">" + word
                + "</font></i></b></h2>");
        out.print("<blockquote>");
        int check = 0;
        while (check < definition.length()) {
            String nextWord = nextWordOrSeparator(definition, check, separator);
            if (wordSet.contains(nextWord)) {
                out.print("<a href=" + '"' + nextWord + ".html" + '"' + ">"
                        + nextWord + "</a>");
            } else {
                out.print(nextWord);
            }
            check += nextWord.length();
        }
        out.println("</blockquote>");
        out.println("<hr />");
        out.println("<p>Return to <a href=" + '"' + GlossaryName + ".html" + '"'
                + ">" + "Index" + "</a>.</p>");
        out.println("</body>");
        out.println("</html>");
    }

    static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {

        int length = text.length();
        String nextOrSeparator = "";

        char x = text.charAt(position);
        boolean subset = separators.contains(x);

        if (subset == true) {
            while (subset == true && position < length) {
                x = text.charAt(position);
                nextOrSeparator += x;
                int size = position + 1;
                if (size < length) {
                    char test = text.charAt(position + 1);
                    subset = separators.contains(test);
                }
                position++;
            }
        } else {
            while (subset == false && position < length) {
                x = text.charAt(position);
                nextOrSeparator += x;
                int len = position + 1;
                if (len < length) {
                    char test = text.charAt(position + 1);
                    subset = separators.contains(test);
                }

                position++;

            }
        }
        return nextOrSeparator;
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

        final String separator = " \t, ";
        Set<Character> separatorSet = new Set1L<>();
        generateElements(separator, separatorSet);

        out.print("Enter a file name:");
        String userInput = in.nextLine();
        SimpleReader glossaryName = new SimpleReader1L(userInput);

        Set<String> wordSet = new Set1L<>();
        Map<String, String> glossary = new Map1L<>();
        ArrayList<String> allWords = new ArrayList<>();

        while (!glossaryName.atEOS()) {
            String word = glossaryName.nextLine();
            String definition = getDefine(glossaryName);
            glossary.add(word, definition);
            allWords.add(word);
            wordSet.add(word);
        }

        allWords = sorter(allWords);
        out.print("Enter a name for the output file:");
        String name = in.nextLine();
        SimpleWriter outWithHtml = new SimpleWriter1L(name + ".html");

        heading(outWithHtml);
        contents(allWords, outWithHtml);
        for (int i = 0; i < allWords.size(); i++) {
            String word = allWords.get(i);
            SimpleWriter wordWithHtml = new SimpleWriter1L(word + ".html");
            outputTerms(word, glossary.value(word), wordSet, separatorSet, name,
                    wordWithHtml);
        }
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
