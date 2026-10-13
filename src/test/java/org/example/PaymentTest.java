package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    // ТЕСТЫ КОНСТРУКТОРОВ И ГЕТТЕРОВ
    @Test
    public void testFullConstructorAndGetters() {
        Payment payment = new Payment("Иванов И.И.", 15, 5, 2026, 125050);

        assertEquals("Иванов И.И.", payment.getName());
        assertEquals(15, payment.getDateDay());
        assertEquals(5, payment.getDateMonth());
        assertEquals(2026, payment.getDateYear());
        assertEquals(125050, payment.getPayment());
    }

    @Test
    public void testDefaultConstructor() {
        Payment payment = new Payment();

        assertEquals("", payment.getName());
        assertEquals(0, payment.getDateDay());
        assertEquals(0, payment.getDateMonth());
        assertEquals(0, payment.getDateYear());
        assertEquals(0, payment.getPayment());
    }

    @Test
    public void testCopyConstructor_NormalCase() {
        Payment original = new Payment("Петров П.П.", 1, 12, 2025, 5000);
        Payment copy = new Payment(original);

        assertNotSame(original, copy);
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getDateDay(), copy.getDateDay());
        assertEquals(original.getPayment(), copy.getPayment());
    }


    // ТЕСТЫ СЕТТЕРОВ
    @Test
    public void testSetters() {
        Payment payment = new Payment();

        payment.setName("Сидоров С.С.");
        payment.setDateDay(20);
        payment.setDateMonth(8);
        payment.setDateYear(2026);
        payment.setPayment(300025);

        assertEquals("Сидоров С.С.", payment.getName());
        assertEquals(20, payment.getDateDay());
        assertEquals(8, payment.getDateMonth());
        assertEquals(2026, payment.getDateYear());
        assertEquals(300025, payment.getPayment());
    }


    // ТЕСТЫ МЕТОДА EQUALS
    @Test
    public void testEquals_SameObjects() {
        Payment p1 = new Payment("Тест", 1, 1, 2026, 100);

        assertEquals(p1, p1);
        assertEquals(p1, new Payment("Тест", 1, 1, 2026, 100));
    }

    @Test
    public void testEquals_DifferentObjects() {
        Payment p1 = new Payment("Тест", 1, 1, 2026, 100);

        assertNotEquals(p1, new Payment("Другой", 1, 1, 2026, 100));
        assertNotEquals(p1, new Payment("Тест", 2, 1, 2026, 100));
        assertNotEquals(p1, new Payment("Тест", 1, 1, 2026, 200));
        assertNotEquals(null, p1);
        assertNotEquals("просто строка", p1);
    }


    // ТЕСТЫ МЕТОДА HASHCODE
    @Test
    public void testHashCode_Consistency() {
        Payment p1 = new Payment("Денис", 10, 10, 2026, 500);
        Payment p2 = new Payment("Денис", 10, 10, 2026, 500);
        Payment p3 = new Payment("Антон", 10, 10, 2026, 500);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1.hashCode(), p3.hashCode());
    }


    // ТЕСТЫ МЕТОДА TOSTRING
    @Test
    public void testToString_Format() {
        Payment payment = new Payment("ООО Ромашка", 5, 6, 2026, 450);
        String expected = "Payment{name='ООО Ромашка', date=5.6.2026, payment (в копейках)=450}";

        assertEquals(expected, payment.toString());
    }
}
