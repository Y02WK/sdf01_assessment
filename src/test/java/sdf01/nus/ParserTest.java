package sdf01.nus;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class ParserTest {
    private String[] args1;
    private String[] args2;
    private String[] args3;
    private String[] args4;
    private String[] args5;
    private String[] args6;

    @Before
    public void setUp() {
        args1 = new String[] {};
        args2 = new String[] { "--port", "8080" };
        args3 = new String[] { "--docRoot", "./target" };
        args4 = new String[] { "--port", "8080", "--docRoot", "./target" };
        args5 = new String[] { "--port", "8080", "--docRoot", "./target:/opt/tmp/www" };
        args6 = new String[] { "--docRoot", "./target:/opt/tmp/www" };
    }

    @After
    public void tearDown() {
        args1 = null;
        args2 = null;
        args3 = null;
        args4 = null;
    }

    @Test
    public void TestEmptyArgs() {
        ParsedArgs pa = new ParsedArgs(args1);
        String[] docRoot = pa.getDocRoot();
        int port = pa.getPort();

        String[] testDocRoot = new String[] { "static" };

        assertArrayEquals(docRoot, testDocRoot);
        assertEquals(port, 3000);
    }

    @Test
    public void Test2Args1() {
        ParsedArgs pa = new ParsedArgs(args2);
        String[] docRoot = pa.getDocRoot();
        int port = pa.getPort();

        String[] testDocRoot = new String[] { "static" };

        assertArrayEquals(docRoot, testDocRoot);
        assertEquals(port, 8080);
    }

    @Test
    public void Test2Args2() {
        ParsedArgs pa = new ParsedArgs(args3);
        String[] docRoot = pa.getDocRoot();
        int port = pa.getPort();

        String[] testDocRoot = new String[] { "./target" };

        assertArrayEquals(docRoot, testDocRoot);
        assertEquals(port, 3000);
    }

    @Test
    public void Test2Args3() {
        ParsedArgs pa = new ParsedArgs(args6);
        String[] docRoot = pa.getDocRoot();
        int port = pa.getPort();

        String[] testDocRoot = new String[] { "./target", "/opt/tmp/www" };

        assertArrayEquals(docRoot, testDocRoot);
        assertEquals(port, 3000);
    }

    @Test
    public void Test4Args1() {
        ParsedArgs pa = new ParsedArgs(args4);
        String[] docRoot = pa.getDocRoot();
        int port = pa.getPort();

        String[] testDocRoot = new String[] { "./target" };

        assertArrayEquals(docRoot, testDocRoot);
        assertEquals(port, 8080);
    }

    @Test
    public void Test4Args2() {
        ParsedArgs pa = new ParsedArgs(args5);
        String[] docRoot = pa.getDocRoot();
        int port = pa.getPort();

        String[] testDocRoot = new String[] { "./target", "/opt/tmp/www" };

        assertArrayEquals(docRoot, testDocRoot);
        assertEquals(port, 8080);
    }
}