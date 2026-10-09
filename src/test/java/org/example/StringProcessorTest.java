package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringProcessorTest {

    //ТЕСТЫ ДЛЯ ЗАДАНИЯ 1
    @Test
    public void testStringRepeat_NormalCase() {
        assertEquals("abcabcabc", StringProcessor.stringRepeat("abc", 3));
    }

    @Test
    public void testStringRepeat_ZeroTimes() {
        assertEquals("", StringProcessor.stringRepeat("abc", 0));
    }

    @Test
    public void testStringRepeat_NegativeTimesThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            StringProcessor.stringRepeat("abc", -1);
        });
    }

    //ТЕСТЫ ДЛЯ ЗАДАНИЯ 2
    @Test
    public void testStringFindSecondInFirst_NormalCase() {
        assertEquals(2, StringProcessor.stringFindSecondInFirst("hello hello world", "hello"));
    }

    @Test
    public void testStringFindSecondInFirst_EmptySecondStringThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            StringProcessor.stringFindSecondInFirst("hello", "");
        });
    }

    @Test
    public void testStringFindSecondInFirst_NullSecondStringThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            StringProcessor.stringFindSecondInFirst("hello", null);
        });
    }

    //ТЕСТЫ ДЛЯ ЗАДАНИЯ 3
    @Test
    public void testStringNumberRenamer_NormalCase() {
        assertEquals("один два три тест", StringProcessor.stringNumberRenamer("1 2 3 тест"));
    }

    @Test
    public void testStringNumberRenamer_NullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            StringProcessor.stringNumberRenamer(null);
        });
    }

    //ТЕСТЫ ДЛЯ ЗАДАНИЯ 4
    @Test
    public void stringBuilder_NormalCase() {
        assertEquals("135", StringProcessor.stringBuilder("123456"));
    }

    //ТЕСТЫ ДЛЯ ЗАДАНИЯ 5
    @Test
    public void testStringReverse_NormalCase() {
        assertEquals(" dd cc bbb aaa", StringProcessor.stringReverse(" aaa bbb cc dd"));
    }

    @Test
    public void testStringReverse_MultipleSpaces() {
        assertEquals("   мир   привет ", StringProcessor.stringReverse("   привет   мир "));
    }

    //ТЕСТЫ ДЛЯ ЗАДАНИЯ 6
    @Test
    public void testReplaceHexToDec_NormalCase() {
        assertEquals("Васе 16 лет", StringProcessor.replaceHexToDec("Васе 0x00000010 лет"));
    }

    @Test
    public void testReplaceHexToDec_TooShortNotModified() {
        assertEquals("Код 0x000123 текст", StringProcessor.replaceHexToDec("Код 0x000123 текст"));
    }

    @Test
    public void testReplaceHexToDec_TooLongNotModified() {
        assertEquals("Адрес 0x000000001A", StringProcessor.replaceHexToDec("Адрес 0x000000001A"));
    }
}
