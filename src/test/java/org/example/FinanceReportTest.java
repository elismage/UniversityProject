package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FinanceReportTest {

    // ТЕСТЫ КОНСТРУКТОРОВ И ГЕТТЕРОВ
    @Test
    public void testConstructorAndGetters() {
        FinanceReport report = new FinanceReport("Петров П.П.", 12, 10, 2026, 3);

        assertEquals("Петров П.П.", report.getFIO());
        assertEquals(12, report.getDay());
        assertEquals(10, report.getMonth());
        assertEquals(2026, report.getYear());
        assertNotNull(report.getFinanceReport());
        assertEquals(3, report.countOfPayments());
    }

    @Test
    public void testCopyConstructor_NormalCase() {
        FinanceReport original = new FinanceReport("Иванов И.И.", 1, 1, 2026, 1);
        original.paymentAccessWrite(0, "АО Вектор", 15, 2, 2026, 50000);

        FinanceReport copy = new FinanceReport(original);

        assertNotSame(original, copy);
        assertEquals(original.getFIO(), copy.getFIO());
        assertEquals(original.countOfPayments(), copy.countOfPayments());

        assertNotSame(original.getFinanceReport(), copy.getFinanceReport());
        assertNotSame(original.getFinanceReport()[0], copy.getFinanceReport()[0]);
        assertEquals(original.getFinanceReport()[0], copy.getFinanceReport()[0]);
    }

    @Test
    public void testCopyConstructor_ThrowsExceptionOnNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FinanceReport((FinanceReport) null);
        });
    }

    // ТЕСТЫ МЕТОДОВ ДОСТУПА ПО ИНДЕКСУ
    @Test
    public void testPaymentAccessWriteAndRead_NormalCase() {
        FinanceReport report = new FinanceReport("Смирнов А.А.", 5, 5, 2026, 2);

        report.paymentAccessWrite(0, "ИП Сидоров", 10, 3, 2026, 123456);

        String[] data = report.paymentAccessRead(0);
        assertEquals("ИП Сидоров", data[0]);
        assertEquals("10", data[1]);
        assertEquals("3", data[2]);
        assertEquals("2026", data[3]);
        assertEquals("123456", data[4]);
    }

    @Test
    public void testPaymentAccessRead_EmptyOrOutOfBoundsIndex() {
        FinanceReport report = new FinanceReport("Смирнов А.А.", 5, 5, 2026, 2);

        String[] emptyData = report.paymentAccessRead(0);
        assertArrayEquals(new String[]{"", "", "", "", ""}, emptyData);

        assertArrayEquals(new String[]{"", "", "", "", ""}, report.paymentAccessRead(-1));
        assertArrayEquals(new String[]{"", "", "", "", ""}, report.paymentAccessRead(2));
    }

    @Test
    public void testPaymentAccessWrite_OutOfBoundsIndex() {
        FinanceReport report = new FinanceReport("Смирнов А.А.", 5, 5, 2026, 2);

        assertDoesNotThrow(() -> report.paymentAccessWrite(-1, "Тест", 1, 1, 2026, 100));
        assertDoesNotThrow(() -> report.paymentAccessWrite(2, "Тест", 1, 1, 2026, 100));
    }

    // ТЕСТЫ СЕТТЕРОВ
    @Test
    public void testSetters() {
        FinanceReport report = new FinanceReport("Тест", 1, 1, 2020, 1);

        report.setFIO("Новый Составитель");
        report.setDay(31);
        report.setMonth(12);
        report.setYear(2027);

        assertEquals("Новый Составитель", report.getFIO());
        assertEquals(31, report.getDay());
        assertEquals(12, report.getMonth());
        assertEquals(2027, report.getYear());
    }

    // ТЕСТ МЕТОДА TOSTRING
    @Test
    public void testToString_Format() {
        FinanceReport report = new FinanceReport("Васильев В.В.", 1, 6, 2026, 1);
        report.paymentAccessWrite(0, "ООО Альфа", 2, 6, 2026, 150075); // 1500 руб. 75 коп.

        String actualString = report.toString();

        assertTrue(actualString.contains("Васильев В.В."));
        assertTrue(actualString.contains("дата: 1.6.2026"));
        assertTrue(actualString.contains("Плательщик: ООО Альфа"));
        assertTrue(actualString.contains("сумма: 1500 руб. 75 коп."));
    }
}
