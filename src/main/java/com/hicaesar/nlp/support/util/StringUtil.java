package com.hicaesar.nlp.support.util;



import static com.hicaesar.nlp.support.log.CaesarLog.methodLog;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Arrays;
import org.apache.log4j.Logger;

/**
 *
 * @author samuelwaskow
 */
public final class StringUtil {

    private static final Logger LOG = Logger.getLogger(StringUtil.class);

    /**
     * Private Constructor
     */
    private StringUtil() {
        super();
    }

    /**
     * Capitalizes the first letter of every word in a sentence
     *
     * @param source
     * @return
     */
    public static String capFirstLetterSentence(final String source) {

        methodLog(LOG, "capFirstLetterSentence", "source [" + source + "]");

        if (source == null || source.isEmpty()) {
            return "";
        } else {

            final StringBuilder res = new StringBuilder();

            final String[] strArr = source.toLowerCase().split(" ");
            for (int i = 0; i < strArr.length; i++) {

                String str = strArr[i];

                if (i == 0 && str.length() <= 2) {
                    str = str.toUpperCase();
                } else if (str.length() > 2) {
                    char[] stringArray = str.trim().toCharArray();
                    stringArray[0] = Character.toUpperCase(stringArray[0]);
                    str = new String(stringArray);
                }
                res.append(str).append(" ");
            }
            return res.toString().trim();
        }
    }

    /**
     * Capitalize the first letter of a string
     *
     * @param source
     * @return
     */
    public static String capFirstLetter(final String source) {

        methodLog(LOG, "capFirstLetter", "source [" + source + "]");

        if (source == null || source.isEmpty()) {
            return "";
        } else if (source.length() == 1) {
            return source;
        } else {
            final String trimmed = source.trim();
            return trimmed.substring(0, 1).toUpperCase() + trimmed.substring(1);
        }
    }

    /**
     * Remove accents from a string
     *
     * @param text
     * @return
     */
    public static String simpler(final String text) {

        methodLog(LOG, "simpler", "text [" + text + "]");

        return (text == null) ? "" : Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase(); // - See more at: http://glaforge.appspot.com/article/how-to-remove-accents-from-a-string#sthash.n7tDmSC2.dpuf
    }

    /**
     * return the first not null and not empty string from args
     *
     * @param args
     * @return
     */
    public static String coalesceEmpty(final String... args) {

        methodLog(LOG, "coalesceEmpty", "args [" + Arrays.toString(args) + "]");

        String out = null;
        for (int i = 0; out == null && args != null && i < args.length; i++) {
            out = args[i] == null || args[i].trim().isEmpty() ? null : args[i].trim();
        }
        return out;
    }

    /**
     * Checks if a string is empty
     *
     * @param text
     * @return
     */
    public static boolean isEmpty(final String text) {

        methodLog(LOG, "isEmpty", "text [" + text + "]");

        return text == null || text.isEmpty();
    }

    /**
     * Return an empty string if it is null
     *
     * @param text
     * @return
     */
    public static String empty(final String text) {

        methodLog(LOG, "empty", "text [" + text + "]");

        if (text == null) {
            return "";
        }

        return text;
    }

    /**
     * Checks if a string is not empty
     *
     * @param text
     * @return
     */
    public static boolean isNotEmpty(final String text) {

        methodLog(LOG, "isEmpty", "text [" + text + "]");

        return !isEmpty(text);
    }

    /**
     * Get only numbers of a given string
     *
     * @param text
     * @return
     */
    public static String onlyNumbers(final String text) {

        methodLog(LOG, "onlyNumbers", "text [" + text + "]");

        if (text == null) {
            return "";
        } else {
            return text.replaceAll("\\D+", "");
        }
    }
    
    /**
     * Get only letters of a given string
     * 
     * @param text
     * @return 
     */
    public static String onlyLetters(final String text) {
        
        methodLog(LOG, "onlyLetters", "text [" + text + "]");

        if (text == null) {
            return "";
        } else {
            return text.replaceAll("[^A-ÿ ]", "").trim();
        }
    }
    
    /**
     * Removes diacritics from a string, making it ascii compatible
     * 
     * @param text
     * @return 
     */
    public static String removeDiacritics(final String text) {
        
        methodLog(LOG, "removeDiacritics", "text [" + text + "]");

        if (text == null) {
            return "";
        } else {
            String out = Normalizer.normalize(text, Normalizer.Form.NFD);
            out = out.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            return out;
        }
    }

    /**
     * Java's built-in java.text.NumberFormat object to see if, after parsing
     * the string the parser position is at the end of the string. If it is, we
     * can assume the entire string is numeric.
     *
     * @param text
     * @return
     */
    public static boolean isNumeric(final String text) {

        methodLog(LOG, "isNumeric", "text [" + text + "]");

        final NumberFormat formatter = NumberFormat.getInstance();
        final ParsePosition pos = new ParsePosition(0);
        formatter.parse(text, pos);
        return text.length() == pos.getIndex();
    }

    /**
     * Get only alphanumerical characters of given String
     *
     * @param text
     * @return
     */
    public static String onlyAlphanumerics(final String text) {

        methodLog(LOG, "onlyAlphanumerics", "text [" + text + "]");

        if (text == null) {
            return "";
        } else {
            return text.replaceAll("[^A-zÀ-ú0-9]", "");
        }
    }

}
