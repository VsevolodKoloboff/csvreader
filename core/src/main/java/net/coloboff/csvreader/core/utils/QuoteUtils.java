package net.coloboff.csvreader.core.utils;

public class QuoteUtils {
    private QuoteUtils() {
    }
    public static String removeQuoteIfExist(final String value) {
        if (value.startsWith("\"")&& value.endsWith("\"")) {
            return value.substring(1,value.length()-1);
        }
        return value;
    }
}
