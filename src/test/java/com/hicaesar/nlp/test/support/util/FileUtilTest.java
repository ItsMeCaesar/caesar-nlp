package com.hicaesar.nlp.test.support.util;


import com.hicaesar.nlp.support.Constants;
import com.hicaesar.nlp.support.exception.CaesarException;
import com.hicaesar.nlp.support.util.FileUtil;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Base64;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author samuelwaskow
 */
public final class FileUtilTest {

    private final FileUtil fu = new FileUtil();

    @Test
    public void testWriteToFileAndRetrieveIt() throws UnsupportedEncodingException, CaesarException {

        final String expected = "linha 1\n"
                + "linha 2\n"
                + "linha 3\n"
                + "linha 4\n"
                + "linha 5\n"
                + "linha 6\n";

        final InputStream is = new ByteArrayInputStream(expected.getBytes(Constants.ENCODING));
        final String dir = System.getProperty("java.io.tmpdir");
        final String filepath = dir + "/test.txt";
        fu.writeToFile(is, filepath);

        final String actualPure = fu.readFromFile(filepath, false);
        Assert.assertEquals(expected.replaceAll("\n", ""), actualPure);

        final String actual = fu.readFromFile(filepath, true);
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void testWriteToFileAndRetrieveItFromProject() throws UnsupportedEncodingException, CaesarException, MalformedURLException, IOException {

        final String filepath = "text.txt";
        final String actual = fu.readFromFile(filepath);
        Assert.assertEquals("test", actual);

    }

    @Test
    public void testWriteToFileAndReadBase64() throws UnsupportedEncodingException, CaesarException {

        final String expected = "linha 1\n"
                + "linha 2\n"
                + "linha 3\n"
                + "linha 4\n"
                + "linha 5\n";

        final String expectedBase64 = Base64.getEncoder().encodeToString(expected.getBytes(Constants.ENCODING));

        final InputStream is = new ByteArrayInputStream(expected.getBytes(Constants.ENCODING));
        
        final String dir = System.getProperty("java.io.tmpdir");
        final String filepath = dir + "/test.txt";
        fu.writeToFile(is, filepath);

        final String actual = fu.fileToBase64(filepath, true);
        Assert.assertEquals("expected [" + expectedBase64 + "]", expectedBase64, actual);

    }

    @Test(expected = CaesarException.class)
    public void testFileNotFound() throws CaesarException {

        final String dir = System.getProperty("java.io.tmpdir");
        final String file = dir + "/hapueaueoas.txt";
        fu.readFromFile(file, false);
    }

    @Test(expected = CaesarException.class)
    public void testFileClazzNotFound() throws CaesarException {

        final String dir = System.getProperty("java.io.tmpdir");
        final String file = dir + "/hapueaueoas.txt";
        fu.readFromFile(file);
    }

    @Test(expected = CaesarException.class)
    public void testNoFile() throws CaesarException {

        final String file = "";
        fu.readFromFile(file, false);
    }

    @Test(expected = CaesarException.class)
    public void convertToFileException() throws CaesarException {

        final String path = fu.getPath(this.getClass(), "FileUtil/test.pdf");

        final String base64 = fu.fileToBase64(path, true);

        fu.base64ToFile(base64, "test", "pdf");

    }

    @Test
    public void convertToFile() throws CaesarException {

        final String path = fu.getPath(this.getClass(), "FileUtil/test.pdf");

        final String base64 = fu.fileToBase64(path, true);

        final String prefix = ";base64,";

        final File f = fu.base64ToFile(prefix + base64, "test", "pdf");

        Assert.assertTrue(f.delete());
    }

    @Test
    public void testGetPath() throws CaesarException {

        final String dir = System.getProperty("java.io.tmpdir");
        final String file = dir + "/test.txt";
        final String path = fu.getPath(this.getClass(), file);
        Assert.assertEquals("", path);
    }

}
