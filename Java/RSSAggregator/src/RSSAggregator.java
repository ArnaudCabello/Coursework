import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to convert an XML RSS (version 2.0) feed from a given URL into the
 * corresponding HTML output file.
 *
 * @author Arnaud Cabello
 *
 */
public final class RSSAggregator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private RSSAggregator() {
    }

    /**
     * Outputs the "opening" tags in the generated HTML file. These are the
     * expected elements generated by this method:
     *
     * <html> <head> <title>the channel tag title as the page title</title>
     * </head> <body>
     * <h1>the page title inside a link to the <channel> link</h1>
     * <p>
     * the channel description
     * </p>
     * <table border="1">
     * <tr>
     * <th>Date</th>
     * <th>Source</th>
     * <th>News</th>
     * </tr>
     *
     * @param channel
     *            the channel element XMLTree
     * @param out
     *            the output stream
     * @updates out.content
     * @requires [the root of channel is a <channel> tag] and out.is_open
     * @ensures out.content = #out.content * [the HTML "opening" tags]
     */
    private static void outputHeader(XMLTree channel, SimpleWriter out) {
        assert channel != null : "Violation of: channel is not null";
        assert out != null : "Violation of: out is not null";
        assert channel.isTag() && channel.label().equals("channel") : ""
                + "Violation of: the label root of channel is a <channel> tag";
        assert out.isOpen() : "Violation of: out.is_open";

        int titleEle = getChildElement(channel, "title");
        int descEle = getChildElement(channel, "description");
        int linkEle = getChildElement(channel, "link");
        String title = "No title available";
        String desc = "No description available";
        String link = "No link available";

        if (titleEle != -1 && channel.child(titleEle).numberOfChildren() > 0) {
            title = channel.child(titleEle).child(0).label();
        }
        if (descEle != -1 && channel.child(descEle).numberOfChildren() > 0) {
            desc = channel.child(descEle).child(0).label();
        }
        if (linkEle != -1 && channel.child(linkEle).numberOfChildren() > 0) {
            link = channel.child(linkEle).child(0).label();
        }
        out.print("<html><head><title>" + title + "</title></head>");
        out.print("<body><h1>" + link + "</h1>");
        out.print("<p>" + desc + "</p>");
        out.println("<table border = \"1\">");
        out.print("<tr><th>Date</th><th>Source</th><th>News</th></tr>");
        for (int i = 0; i < channel.numberOfChildren(); i++) {
            if (channel.child(i).isTag()) {
                if (channel.child(i).label().equals("item")) {
                    processItem(channel.child(i), out);
                }
            }
        }
        outputFooter(out);
    }

    /**
     * Outputs the "closing" tags in the generated HTML file. These are the
     * expected elements generated by this method:
     *
     * </table>
     * </body> </html>
     *
     * @param out
     *            the output stream
     * @updates out.contents
     * @requires out.is_open
     * @ensures out.content = #out.content * [the HTML "closing" tags]
     */
    private static void outputFooter(SimpleWriter out) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";

        out.println("</table>");
        out.println("</body> </html>");
    }

    /**
     * Finds the first occurrence of the given tag among the children of the
     * given {@code XMLTree} and return its index; returns -1 if not found.
     *
     * @param xml
     *            the {@code XMLTree} to search
     * @param tag
     *            the tag to look for
     * @return the index of the first child of type tag of the {@code XMLTree}
     *         or -1 if not found
     * @requires [the label of the root of xml is a tag]
     * @ensures <pre>
     * getChildElement =
     *  [the index of the first child of type tag of the {@code XMLTree} or
     *   -1 if not found]
     * </pre>
     */
    private static int getChildElement(XMLTree xml, String tag) {
        assert xml != null : "Violation of: xml is not null";
        assert tag != null : "Violation of: tag is not null";
        assert xml.isTag() : "Violation of: the label root of xml is a tag";
        int tagIndex = -1;
        for (int i = 0; i < xml.numberOfChildren(); i++) {
            if (xml.child(i).isTag()) {
                if (xml.child(i).label().equals(tag)) {
                    tagIndex = i;
                }
            }
        }
        return tagIndex;

    }

    /**
     * Processes one news item and outputs one table row. The row contains three
     * elements: the publication date, the source, and the title (or
     * description) of the item.
     *
     * @param item
     *            the news item
     * @param out
     *            the output stream
     * @updates out.content
     * @requires [the label of the root of item is an <item> tag] and
     *           out.is_open
     * @ensures <pre>
     * out.content = #out.content *
     *   [an HTML table row with publication date, source, and title of news item]
     * </pre>
     */
    private static void processItem(XMLTree item, SimpleWriter out) {
        assert item != null : "Violation of: item is not null";
        assert out != null : "Violation of: out is not null";
        assert item.isTag() && item.label().equals("item") : ""
                + "Violation of: the label root of item is an <item> tag";
        assert out.isOpen() : "Violation of: out.is_open";

        boolean hasLinkChild = false;

        String date = "No date available";
        String source = "";
        String title = "";
        String desc = "";
        String link = "";
        int dateEle = getChildElement(item, "pubDate");
        int sourceEle = getChildElement(item, "source");
        int titleEle = getChildElement(item, "title");
        int descEle = getChildElement(item, "description");
        int linkEle = getChildElement(item, "link");

        if (linkEle != -1 && item.child(linkEle).numberOfChildren() != 0) {
            hasLinkChild = true;
        }

        if (dateEle != -1 && item.child(dateEle).numberOfChildren() > 0) {
            date = item.child(dateEle).child(0).label();
        }
        if (sourceEle != -1 && item.child(sourceEle).numberOfChildren() > 0) {
            source = item.child(sourceEle).child(0).label();
        }
        if (titleEle != -1 && item.child(titleEle).numberOfChildren() > 0) {
            title = item.child(titleEle).child(0).label();
        }
        if (descEle != -1 && item.child(descEle).numberOfChildren() > 0) {
            desc = item.child(descEle).child(0).label();
        }
        if (linkEle != -1 && item.child(linkEle).numberOfChildren() > 0) {
            link = item.child(linkEle).child(0).label();
        }

        out.print("<tr><td>" + date + "</td>");
        if (sourceEle != -1) {
            out.print("<td><a href=" + source + ">" + source + "</td>");
        } else {
            out.print("<td>No source available</td>");
        }

        if (titleEle != -1 && item.child(titleEle).numberOfChildren() == 1
                && hasLinkChild) {
            out.println("<td><a href=" + '"' + link + '"' + '>' + title
                    + "</a></td>");
        } else if (descEle != -1 && item.child(descEle).numberOfChildren() != 0
                && hasLinkChild) {
            out.println("<td><a href=\"" + link + "\">" + desc + "</a></td>");
        } else if (hasLinkChild && titleEle == -1 && descEle == -1
                && hasLinkChild) {
            out.println("<td><a href=" + '"' + link + '"'
                    + ">No title available</a><td>");
        } else if (!hasLinkChild && titleEle != -1
                && item.child(titleEle).numberOfChildren() == 1) {
            out.println("<td>" + title + "</td>");
        } else {
            out.println("<td>" + desc + "</td>");
        }

        out.print("</tr>");

    }

    private static void processFeed(String url, String file, SimpleWriter out) {
        boolean x = true;
        XMLTree xml = new XMLTree1(url);
        xml.display();
        while (!xml.label().equals("rss") && !xml.hasAttribute("version")
                && !xml.attributeValue("version").equals("2.0")) {
            out.print("Invalid RSS feed.");
            x = false;
        }
        if (x) {
            xml = new XMLTree1(url);
            XMLTree channel = xml.child(getChildElement(xml, "channel"));
            SimpleWriter print = new SimpleWriter1L(file);
            outputHeader(channel, print);
        }
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        /*
         * TODO: fill in body
         */
        out.print("Please enter a xml:");
        String xmlFile = in.nextLine();
        out.print("Enter a name for the output file including .html: ");
        String htmlFile = in.nextLine();
        XMLTree xml = new XMLTree1(xmlFile);
        SimpleWriter display = new SimpleWriter1L(htmlFile);
        if (xml.hasAttribute("title")) {
            display.println("<html><head><title>" + xml.attributeValue("title")
                    + "</title></head>");
        }
        display.println("<body><h2>" + xml.attributeValue("title") + "</h2>");
        display.println("<ul>");
        for (int i = 0; i < xml.numberOfChildren(); i++) {

            processFeed(xml.child(i).attributeValue("url"),
                    xml.child(i).attributeValue("file"), out);
            display.println("<li><a href=" + xml.child(i).attributeValue("file")
                    + ">" + xml.child(i).attributeValue("name") + "</a></li>");
        }
        display.println("</ul></body></html>");
        in.close();
        out.close();
        display.close();
    }

}