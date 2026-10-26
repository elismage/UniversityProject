package org.example;

public class StringProcessor {

    public static String stringRepeat(String string, int N) {
        if (string == null) {
            throw new IllegalArgumentException("String is null.");
        }
        if (N < 0) {
            throw new IllegalArgumentException("Repeat times below zero.");
        }
        if (N == 0) {
            return "";
        }

        StringBuilder repeatedString = new StringBuilder(string.length() * N);
        for (int i = 0; i < N; i++) {
            repeatedString.append(string);
        }
        return repeatedString.toString();
    }

    public static int stringFindSecondInFirst(String string1, String string2) {
        if (string2 == null || string2.isEmpty()) {
            throw new IllegalArgumentException("Second string is null or empty");
        }
        if (string1 == null) {
            return 0;
        }

        int index = 0;
        int counter = 0;
        while ((index = string1.indexOf(string2, index)) != -1) {
            counter++;
            index++;
        }
        return counter;
    }

    public static String stringNumberRenamer(String string) {
        if (string == null) {
            throw new IllegalArgumentException("String is null");
        }
        if (string.isEmpty()) {
            return "";
        }

        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (c == '1') {
                newString.append("один");
            } else if (c == '2') {
                newString.append("два");
            } else if (c == '3') {
                newString.append("три");
            } else {
                newString.append(c);
            }
        }
        return newString.toString();
    }

    public static void stringBuilder(StringBuilder string) {
        if (string == null) {
            throw new IllegalArgumentException("String is null");
        }
        for (int i = string.length() - 1; i >= 0; i--) {
            if (i % 2 != 0) {
                string.deleteCharAt(i);
            }
        }
    }

    public static String stringReverse(String string) {
        if (string == null) {
            throw new IllegalArgumentException("String is null");
        }
        if (string.isEmpty()) {
            return "";
        }

        StringBuilder output = new StringBuilder(string.length());
        int j = string.length() - 1;

        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == ' ') {
                output.append(' ');
            } else if (i == 0 || string.charAt(i - 1) == ' ') {
                while (j >= 0 && string.charAt(j) == ' ') {
                    j--;
                }
                int wordEnd = j;
                while (j >= 0 && string.charAt(j) != ' ') {
                    j--;
                }
                int wordStart = j + 1;
                output.append(string, wordStart, wordEnd + 1);
            }
        }

        return output.toString();
    }

    public static String replaceHexToDec(String string) {
        if (string == null) {
            throw new IllegalArgumentException("String is null");
        }
        if (string.isEmpty()) {
            return "";
        }

        StringBuilder output = new StringBuilder();
        int i = 0;

        while (i < string.length()) {
            boolean validPrefix = (i == 0) || !isHexDigit(string.charAt(i - 1));
            if (validPrefix && i <= string.length() - 10 && string.charAt(i) == '0' && (string.charAt(i + 1) == 'x' || string.charAt(i + 1) == 'X')) {
                boolean isExactHex = true;
                for (int k = 0; k < 8; k++) {
                    if (!isHexDigit(string.charAt(i + 2 + k))) {
                        isExactHex = false;
                        break;
                    }
                }
                if (isExactHex && i + 10 < string.length() && isHexDigit(string.charAt(i + 10))) {
                    isExactHex = false;
                }

                if (isExactHex) {
                    String hexPart = string.substring(i + 2, i + 10);
                    long decimalValue = Long.parseLong(hexPart, 16);
                    output.append(decimalValue);
                    i += 10;
                    continue;
                }
            }
            output.append(string.charAt(i));
            i++;
        }

        return output.toString();
    }

    private static boolean isHexDigit(char c) {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
    }
}