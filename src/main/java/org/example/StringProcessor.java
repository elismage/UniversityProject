package org.example;
import java.util.ArrayList;
import java.util.List;

public class StringProcessor {

    public static String stringRepeat(String string, int N) {
        String repeatedString = "";
        if (N == 0) {
            return "";
        } else if (N < 0) {
            throw new IllegalArgumentException("Repeat times belows zero.");
        }

        for (int i = 0; i < N; i++) {
            repeatedString += string;
        }
        return repeatedString;
    }

    public static int stringFindSecondInFirst(String string1, String string2) {
        if (string2 == null) {
            throw new IllegalArgumentException("Second string is null");
        }
        if (string2.equals("")) {
            throw new IllegalArgumentException("Second string is empty");
        }
        if (string1 == null) return 0;


        int[] indexes;
        int index = 0;
        int counter = 0;
        while ((index = string1.indexOf(string2, index)) != -1) {
            counter += 1;
            index += 1;
        }
        return counter;
    }

    public static String stringNumberRenamer(String string) {
        if (string == null) {
            throw new IllegalArgumentException("String is null");
        }
        if (string.equals("")) {
            return "";
        }

        String newString = "";
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '1') {
                newString += "один";
            } else if (string.charAt(i) == '2') {
                newString += "два";
            } else if (string.charAt(i) == '3') {
                newString += "три";
            } else {
                newString += string.charAt(i);
            }
        }
        return newString;
    }

    //заставило подумать
    public static String stringBuilder(String string){
        if (string == null) {
            throw new IllegalArgumentException("String is null");
        }
        if (string.equals("")) {
            return "";
        }

        int lenght = string.length();
        for (int i = 0; i < lenght;i++){
            if (i % 2 == 0){
                string+=string.charAt(i);
            }
        }
        string = string.substring(lenght);
        return string;
    }

    public static String stringReverse(String string) {
        if (string == null) {
            throw new IllegalArgumentException("String is null");
        }
        if (string.equals("")) {
            return "";
        }
        String output = "";
        String[] buffer = new String[string.length()];
        int j = string.length() - 1;

        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == ' ') {
                buffer[i] = " ";
            } else {
                if (i == 0 || string.charAt(i - 1) == ' ') {

                    while (j >= 0 && string.charAt(j) == ' ') {
                        j--;
                    }
                    int wordEnd = j;
                    while (j >= 0 && string.charAt(j) != ' ') {
                        j--;
                    }
                    int wordStart = j + 1;

                    String bufferForWords = "";
                    for (int k = wordStart; k <= wordEnd; k++) {
                        bufferForWords += string.charAt(k);
                    }

                    buffer[i] = bufferForWords;
                } else {
                    buffer[i] = "";
                }
            }
        }

        for (int i = 0; i < buffer.length; i++) {
            output += buffer[i];
        }
        return output;
    }


    public static String replaceHexToDec(String string) {
        if (string == null) {
            throw new IllegalArgumentException("String is null");
        }
        if (string.equals("")) {
            return "";
        }
        String output = "";
        int i = 0;

        while (i < string.length()) {
            if (i <= string.length() - 10 && string.charAt(i) == '0' && string.charAt(i + 1) == 'x') {

                boolean isExactHex = true;
                String hexPart = "";
                for (int k = 0; k < 8; k++) {
                    char c = string.charAt(i + 2 + k);
                    if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F')) {
                        hexPart += c;
                    } else {
                        isExactHex = false;
                        break;
                    }
                }
                if (isExactHex && i + 10 < string.length()) {
                    char nextChar = string.charAt(i + 10);
                    if ((nextChar >= '0' && nextChar <= '9') ||
                            (nextChar >= 'a' && nextChar <= 'f') ||
                            (nextChar >= 'A' && nextChar <= 'F')) {
                        isExactHex = false;
                    }
                }

                if (isExactHex) {
                    long decimalValue = Long.parseLong(hexPart, 16);
                    output += decimalValue;
                    i += 10;
                    continue;
                }
            }

            output += string.charAt(i);
            i++;
        }

        return output;
    }


}
