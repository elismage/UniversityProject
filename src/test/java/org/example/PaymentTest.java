package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

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
        assertEquals(original, copy);
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getDateDay(), copy.getDateDay());
        assertEquals(original.getDateMonth(), copy.getDateMonth());
        assertEquals(original.getDateYear(), copy.getDateYear());
        assertEquals(original.getPayment(), copy.getPayment());
    }

    @Test
    public void testCopyConstructor_NullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment(null);
        });
    }

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
        assertNotEquals(p1, new Payment("Тест", 1, 2, 2026, 100));
        assertNotEquals(p1, new Payment("Тест", 1, 1, 2027, 100));
        assertNotEquals(p1, new Payment("Тест", 1, 1, 2026, 200));
        assertFalse(p1.equals(null));
        assertFalse(p1.equals("просто строка"));
    }

    @Test
    public void testEqualsAndHashCode_NullName() {
        Payment pNull1 = new Payment(null, 1, 1, 2026, 100);
        Payment pNull2 = new Payment(null, 1, 1, 2026, 100);
        Payment pNamed = new Payment("Тест", 1, 1, 2026, 100);

        assertEquals(pNull1, pNull2);
        assertEquals(pNull1.hashCode(), pNull2.hashCode());
        assertNotEquals(pNull1, pNamed);
        assertNotEquals(pNamed, pNull1);
    }

    @Test
    public void testHashCode_Consistency() {
        Payment p1 = new Payment("Денис", 10, 10, 2026, 500);
        Payment p2 = new Payment("Денис", 10, 10, 2026, 500);
        Payment p3 = new Payment("Антон", 10, 10, 2026, 500);

        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1.hashCode(), p3.hashCode());
    }

    @Test
    public void testToString_Format() {
        Payment payment = new Payment("ООО Ромашка", 5, 6, 2026, 450);
        String expected = "Payment{name='ООО Ромашка', date=5.6.2026, payment (в копейках)=450}";

        assertEquals(expected, payment.toString());
    }
}