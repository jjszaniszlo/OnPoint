package main;

/**
 * <b>[THIS IS THE CLASS FILE SUMMARY]</b>
 * <br><br>
 * The following is a quick demonstration of our
 * coding convention for our workflow. Let's do nothing unnecessary,
 * but keep it simple so that our emphasis is on clarity and actually
 * contributing code!
 **/

// Sidenote:
// You'll also notice that if you hover over the implementation, or
// the class name itself, it will include this information!
// You can also use HTML formatting if you want it to look pretty,
// but not necessary!

public class TeamConventions {

    // This is MY branch!!!!
    // Federico added this from his branch

    // [This is an explanation of a global-scope variable]
    public String s = "This is the string";

    /// [THIS IS A HEADER] (Group methods or systems) ///

    /**
     * <b>This is a template explanation of a method or other implementation</b>
     * <br><br>
     * Description: You'll also notice that hovering over the name of the
     * method, or any other line including the method name,
     * it will include this explanation!
     */
    public void DemoOutput()
    {
        // [Explanation of a code block] Set our global string to a quote
        s = "This is a reset of string s";

        // Add to global variable s
        s += ", with additions!";


        // Print the built string here
        System.out.println(s);

        // Add by a defined char array (we can block this out if we are
        // still in-progress but want to commit our other changes!)
        /*
        char[] addTo = new char[] { ' ', 'a', 'd', 'd', ' ', 'm', 'e', '!'};
        for (char c : addTo) //noinspection StringConcatenationInLoop
            s += c;
        */

        // And comparatively build with the addition
        System.out.println(s);
    }

}
