package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FinanceReportTest {

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
    public void testConstructor_NegativeCountThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FinanceReport("Петров П.П.", 12, 10, 2026, -1);
        });
    }

    @Test
    public void testCopyConstructor_WithNullElementsAndDeepCopy() {
        FinanceReport original = new FinanceReport("Иванов И.И.", 1, 1, 2026, 2);
        original.paymentAccessWrite(0, "АО Вектор", 15, 2, 2026, 50000);

        FinanceReport copy = new FinanceReport(original);

        assertNotSame(original, copy);
        assertEquals(original, copy);
        assertNull(copy.getPayment(1));
        assertArrayEquals(original.paymentAccessRead(0), copy.paymentAccessRead(0));

        original.paymentAccessWrite(0, "Новый Плательщик", 20, 3, 2026, 99900);
        assertEquals("АО Вектор", copy.paymentAccessRead(0)[0]);
        assertEquals("50000", copy.paymentAccessRead(0)[4]);
    }

    @Test
    public void testCopyConstructor_ThrowsExceptionOnNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FinanceReport(null);
        });
    }

    @Test
    public void testGetAndSetPayment() {
        FinanceReport report = new FinanceReport("Смирнов А.А.", 5, 5, 2026, 2);
        Payment payment = new Payment("ИП Сидоров", 10, 3, 2026, 123456);

        report.setPayment(0, payment);
        assertEquals(payment, report.getPayment(0));
        assertNotSame(payment, report.getPayment(0));

        payment.setName("Измененное Имя");
        assertEquals("ИП Сидоров", report.getPayment(0).getName());

        assertThrows(IndexOutOfBoundsException.class, () -> report.getPayment(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> report.getPayment(2));
        assertThrows(IndexOutOfBoundsException.class, () -> report.setPayment(2, payment));
    }

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

    @Test
    public void testToString_ExactFormat() {
        FinanceReport report = new FinanceReport("Васильев В.В.", 1, 6, 2026, 2);
        report.paymentAccessWrite(0, "ООО Альфа", 2, 6, 2026, 150005);
        report.paymentAccessWrite(1, "ИП Бета", 3, 6, 2026, 20075);

        String expected = "[Автор: Васильев В.В., дата: 1.6.2026, Платежи: [\n" +
                "Плательщик: ООО Альфа, дата: 2.6.2026 сумма: 1500 руб. 05 коп.\n" +
                "Плательщик: ИП Бета, дата: 3.6.2026 сумма: 200 руб. 75 коп.\n" +
                "]]";

        assertEquals(expected, report.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        FinanceReport r1 = new FinanceReport("Иванов И.И.", 1, 1, 2026, 1);
        r1.paymentAccessWrite(0, "Петров П.П.", 2, 2, 2026, 1000);

        FinanceReport r2 = new FinanceReport(r1);
        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());

        r2.paymentAccessWrite(0, "Сидоров С.С.", 2, 2, 2026, 1000);
        assertNotEquals(r1, r2);
    }
}