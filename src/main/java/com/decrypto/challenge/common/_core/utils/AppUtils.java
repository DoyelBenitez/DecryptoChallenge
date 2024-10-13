package com.decrypto.challenge.common._core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * @Author dbenitez
 */
public class AppUtils {

    public static Boolean isNotNullOrEmpty(Object object) {
        if (object instanceof List) {
            return !((List<?>) object).isEmpty();
        }
        return (object != null && object != "");
    }

    public static String convertToTitleCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        String[] words = input.split(" ");
        StringBuilder titleCase = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                titleCase.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return titleCase.toString().trim();
    }

    public static String convertToUpperCase(String input) {
        if (input == null) {
            return null;
        }
        return input.toUpperCase().replaceAll("[^A-Z0-9]", "_").trim();
    }

    public static String convertToJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
