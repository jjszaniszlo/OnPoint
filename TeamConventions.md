
Generally speaking, you should follow a guideline which falls inline with standard Java conventions, but many of us may not be familiar, so here is a brief document with some project specific guidelines.

Every other formatting decision should be made with Java standards and cleanliness in mind for exceptional situations.

For a more comprehensive guide on javadoc see [this](https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html).

# class file summaries
All class files will be prefixed with a summary which are formatted as follows:

```Java
/*
 * BRIEF DESCRIPTION HERE
 */

// you can use HTML tags to make it look better on the javadoc page.
public ClassName {}
```

# method/function javadoc annotations

All major methods that aren't inherently obvious or that have side effects should have a javadoc comment associated with it.

We will likely interface with exceptions very rarely (only for antiquated APIs that need it), so we don't need to add those.

```Java
public ClassName {
    /*
     * This method performs magic (please create an actual good description)
     * @param a magic number 1
     * @param b magic number 2
     * @return the output of magic
     */
    public int doMagic(int a, int b) {
        ...
    }
}
```
