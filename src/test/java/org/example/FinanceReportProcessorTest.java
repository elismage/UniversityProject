package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class FinanceReportProcessorTest {

    private FinanceReport sampleReport;

    @BeforeEach
    public void setUp() {
        sampleReport = new FinanceReport("Иванов И.И.", 12, 10, 2026, 4);

        sampleReport.paymentAccessWrite(0, "Петров П.П.", 12, 1, 2026, 150000); // П, 1500 руб
        sampleReport.paymentAccessWrite(1, "Павлов П.А.", 12, 1, 2026, 50000);  // П, 500 руб
        sampleReport.paymentAccessWrite(2, "Сидоров С.С.", 5, 3, 2026, 300000); // С, 3000 руб
        sampleReport.paymentAccessWrite(3, "АО Вектор", 20, 12, 2025, 9000);   // А, 90 руб
    }

    // ТЕСТЫ paymentNameFinder
    @Test
    public void testPaymentNameFinder_NormalCase() {
        FinanceReport result = FinanceReportProcessor.paymentNameFinder(sampleReport, 'П');

        assertNotNull(result);
        assertEquals(2, result.countOfPayments());
        assertEquals("Петров П.П.", result.paymentAccessRead(0)[0]);
        assertEquals("Павлов П.А.", result.paymentAccessRead(1)[0]);

        assertEquals(sampleReport.getFIO(), result.getFIO());
        assertEquals(sampleReport.getYear(), result.getYear());
    }

    @Test
    public void testPaymentNameFinder_NoMatches() {
        FinanceReport result = FinanceReportProcessor.paymentNameFinder(sampleReport, 'З');

        assertNotNull(result);
        assertEquals(0, result.countOfPayments());
    }

    // ТЕСТЫ paymentCostFinder
    @Test
    public void testPaymentCostFinder_NormalCase() {
        FinanceReport result = FinanceReportProcessor.paymentCostFinder(sampleReport, 100000);

        assertNotNull(result);
        assertEquals(2, result.countOfPayments());
        assertEquals("Павлов П.А.", result.paymentAccessRead(0)[0]);
        assertEquals("АО Вектор", result.paymentAccessRead(1)[0]);
    }

    @Test
    public void testPaymentCostFinder_NoMatches() {
        FinanceReport result = FinanceReportProcessor.paymentCostFinder(sampleReport, 10);

        assertEquals(0, result.countOfPayments());
    }

    // ТЕСТЫ dateFinder
    @Test
    public void testDateFinder_NormalCase() {
        assertEquals(2, FinanceReportProcessor.dateFinder(sampleReport, "12.01.2026"));
    }

    @Test
    public void testDateFinder_ShortYearFormat() {
        assertEquals(2, FinanceReportProcessor.dateFinder(sampleReport, "12.01.26"));
    }

    @Test
    public void testDateFinder_IllegalDateFormatThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            FinanceReportProcessor.dateFinder(sampleReport, "12/01/2026");
        });
    }


    // ТЕСТЫ getMonthsWithNoPayments
    @Test
    public void testGetMonthsWithNoPayments_NormalCase() {
        // За 2026 год есть платежи в Январе (1) и Марте (3). Должно вернуть список из 10 месяцев, где нет Января и Марта.
        List<String> emptyMonths = FinanceReportProcessor.getMonthsWithNoPayments(sampleReport, 2026);

        assertEquals(10, emptyMonths.size());
        assertFalse(emptyMonths.contains("Январь"));
        assertFalse(emptyMonths.contains("Март"));
        assertTrue(emptyMonths.contains("Февраль"));
        assertTrue(emptyMonths.contains("Декабрь"));
    }

    @Test
    public void testGetMonthsWithNoPayments_NullReportReturnsAllMonths() {
        List<String> emptyMonths = FinanceReportProcessor.getMonthsWithNoPayments(null, 2026);
        assertEquals(12, emptyMonths.size());
    }
}
