package com.hicaesar.nlp.support.util;

import com.hicaesar.nlp.support.Constants;
import com.hicaesar.nlp.support.exception.CaesarException;
import static com.hicaesar.nlp.support.log.CaesarLog.methodLog;
import static com.hicaesar.nlp.support.log.CaesarLog.param;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Base64;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;

/**
 * Manages file operations
 */
public final class FileUtil {

    private static final Logger LOG = Logger.getLogger(FileUtil.class);

    /**
     * Save upstream to a file
     *
     * @param is
     * @param filepath
     * @throws CaesarException
     */
    public void writeToFile(final InputStream is, final String filepath) throws CaesarException {

        methodLog(LOG, "writeToFile", "is [" + is + "]", "filepath [" + filepath + "]");

        try {

            int read;
            byte[] bytes = new byte[1024];

            final File file = new File(filepath);
            try (final OutputStream out = new FileOutputStream(file)) {
                while ((read = is.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.flush();
            }
        } catch (IOException e) {
            throw CaesarException.exception(e, Status.INTERNAL_SERVER_ERROR, "Error persisting file");
        }
    }

    /**
     * Transforms a file into a base64 string
     *
     * @param filepath
     * @param readnewline
     * @return
     * @throws CaesarException
     */
    public String fileToBase64(final String filepath, final boolean readnewline) throws CaesarException {

        methodLog(LOG, "fileToBase64", "filepath [" + filepath + "]", "readnewline [" + readnewline + "]");

        final String filecontent = readFromFile(filepath, readnewline);

        return Base64.getMimeEncoder().encodeToString(filecontent.getBytes());
    }

    /**
     * Transforms a base64 string into a file
     *
     * @param base64
     * @param name
     * @param extension
     * @return
     * @throws CaesarException
     */
    public File base64ToFile(final String base64, final String name, final String extension) throws CaesarException {

        methodLog(LOG, "base64ToFile", "base64 [" + base64 + "]", "name [" + name + "]", "extension [" + extension + "]");

        try {

            final int index = base64.indexOf(";base64,");
            if (index == -1) {
                throw CaesarException.exception(Status.INTERNAL_SERVER_ERROR, "Error parsing file");
            }

            final String parsedBase64 = base64.substring(index + 8);
            final File out = File.createTempFile(name, extension);

            try (final FileOutputStream fop = new FileOutputStream(out)) {

                final byte[] input = Base64.getMimeDecoder().decode(parsedBase64);
                fop.write(input);
            }

            return out;

        } catch (IOException e) {
            throw CaesarException.exception(e, Status.INTERNAL_SERVER_ERROR, "IO Error");
        }
    }

    /**
     * Read file from the file system
     *
     * @param filepath
     * @param readnewline
     * @return
     * @throws CaesarException
     */
    public String readFromFile(final String filepath, final boolean readnewline) throws CaesarException {

        methodLog(LOG, "readFromFile", "filepath [" + filepath + "]", "readnewline [" + readnewline + "]");

        final File file = new File(filepath);
        return getFileContent(file, readnewline);
    }

    /**
     * Read file from the project
     *
     * @param filepath
     * @return
     * @throws CaesarException
     */
    public String readFromFile(final String filepath) throws CaesarException {
        methodLog(LOG, "readFromFile", "filepath [" + filepath + "]");

        try {

            final URL path = getClass().getClassLoader().getResource(filepath);
            if (path == null) {
                throw CaesarException.exception(Status.INTERNAL_SERVER_ERROR, "Error parsing file");
            }
            final File file = new File(path.toURI().getPath());
            return getFileContent(file, false);

        } catch (URISyntaxException e) {
            throw CaesarException.exception(e, Status.INTERNAL_SERVER_ERROR, "Error parsing file");
        }
    }

    /**
     * Common method to retrieve the file content
     *
     * @param file
     * @param readnewline
     * @return
     * @throws CaesarException
     */
    private String getFileContent(final File file, final boolean readnewline) throws CaesarException {

        methodLog(LOG, "getFileContent", "file [" + file + "]", "readnewline [" + readnewline + "]");

        final StringBuilder out = new StringBuilder();
        final int buffer = 4096;
        try {

            try (final FileInputStream fileInputStream = new FileInputStream(file); final BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream, Constants.ENCODING), buffer)) {
                String str;
                while ((str = br.readLine()) != null) {
                    out.append(str).append(readnewline ? "\n" : "");
                }
            }
        } catch (final FileNotFoundException e) {
            throw CaesarException.exception(e, Status.INTERNAL_SERVER_ERROR, "Error parsing file");
        } catch (IOException e) {
            throw CaesarException.exception(e, Status.INTERNAL_SERVER_ERROR, "IO Error");
        }
        return out.toString();
    }

    /**
     * Return the file path considering the classloader
     *
     * @param clazz
     * @param sourcepath
     * @return
     * @throws CaesarException
     */
    public String getPath(final Class<?> clazz, final String sourcepath) throws CaesarException {

        methodLog(LOG, "getPath", param("clazz", clazz), param("sourcepath", sourcepath));

        try {

            final URL resource = clazz.getClassLoader().getResource(sourcepath);
            if (resource == null) {
                return "";
            } else {
                return resource.toURI().getPath();
            }

        } catch (URISyntaxException ex) {
            throw CaesarException.exception(ex, Status.INTERNAL_SERVER_ERROR, "IO Error");
        }
    }
}
