package com.github.kipperorigin.originapi.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static List<String> convertArguments(String[] args) {
        String fullString = "";

        for(String s:args) {
            fullString += s + " ";
        }
        return splitString(fullString);
    }
    public static List<String> splitString(String input) {
        List<String> result = new ArrayList<>();
        // This regex pattern matches:
        // 1. Quoted strings: "((?:\\.|[^"\\])*)""
        // 2. Unquoted strings: ((?:\\.|[^"\s\\])+)
        Pattern pattern = Pattern.compile("\"((?:\\\\.|[^\"\\\\])*)\"|((?:\\\\.|[^\"\\s\\\\])+)");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String match = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
            // Unescape characters
            match = match.replaceAll("\\\\(.)", "$1");
            result.add(match);
        }

        return result;
    }

    public static String convertToOrdinal(int i) {
        String ordinal = String.valueOf(i);
        int remainder = i%10;
        switch (remainder) {
            case 1:
            case 2:
            case 3:
                if (i > 10 && i < 20) {
                    ordinal += "th";
                    break;
                }
                switch (remainder) {
                    case 1:
                        ordinal += "st";
                        break;
                    case 2:
                        ordinal += "nd";
                        break;
                    case 3:
                        ordinal += "rd";
                        break;
                }
                break;
            default:
                ordinal += "th";
                break;
        }
        return ordinal;
    }

    public static String getAttributeStringFromAttribute(String attribute) {
        attribute = attribute.toLowerCase();
        int startValue = 0;
        for (int i = 0; i < attribute.length(); i++) {
            if(attribute.indexOf(i) == '_') startValue = 0;
        }
        String key = attribute.substring(0,startValue-1);
        String value = attribute.substring(0,startValue);
        return key + '.' + value;
    }

}
