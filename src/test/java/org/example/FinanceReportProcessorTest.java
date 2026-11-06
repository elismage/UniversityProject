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

        sampleReport.paymentAccessWrite(0, "Петров П.П.", 12, 1, 2026, 150000);
        sampleReport.paymentAccessWrite(1, "Павлов П.А.", 12, 1, 2026, 50000);
        sampleReport.paymentAccessWrite(2, "Сидоров С.С.", 5, 3, 2026, 300000);
        sampleReport.paymentAccessWrite(3, "АО Вектор", 20, 12, 2025, 9000);
    }

    @Test
    public void testPaymentNameFinder_NormalCase() {
        FinanceReport result = FinanceReportProcessor.paymentNameFinder(sampleReport, 'П');

        assertNotNull(result);
        assertEquals(2, result.countOfPayments());
        assertEquals("Петров П.П.", result.paymentAccessRead(0)[0]);
        assertEquals("Павлов П.А.", result.paymentAccessRead(1)[0]);
    }

    @Test
    public void testPaymentNameFinder_CaseInsensitiveAndNoMatches() {
        FinanceReport lowerResult = FinanceReportProcessor.paymentNameFinder(sampleReport, 'с');
        assertEquals(1, lowerResult.countOfPayments());
        assertEquals("Сидоров С.С.", lowerResult.paymentAccessRead(0)[0]);

        FinanceReport emptyResult = FinanceReportProcessor.paymentNameFinder(sampleReport, 'Я');
        assertEquals(0, emptyResult.countOfPayments());
    }

    @Test
    public void testPaymentCostFinder_NormalCase() {
        FinanceReport result = FinanceReportProcessor.paymentCostFinder(sampleReport, 100000);

        assertNotNull(result);
        assertEquals(2, result.countOfPayments());
        assertEquals("Павлов П.А.", result.paymentAccessRead(0)[0]);
        assertEquals("АО Вектор", result.paymentAccessRead(1)[0]);
    }

    @Test
    public void testPaymentCostFinder_NoneBelowThreshold() {
        FinanceReport result = FinanceReportProcessor.paymentCostFinder(sampleReport, 1000);
        assertEquals(0, result.countOfPayments());
    }

    @Test
    public void testDateFinder_NormalCaseAndTwoDigitYear() {
        int sumJan12 = FinanceReportProcessor.dateFinder(sampleReport, "12.01.26");
        assertEquals(200000, sumJan12);

        int sumEmptyDate = FinanceReportProcessor.dateFinder(sampleReport, "01.01.26");
        assertEquals(0, sumEmptyDate);
    }

    @Test
    public void testDateFinder_InvalidFormatThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            FinanceReportProcessor.dateFinder(sampleReport, "2026-01-12");
        });
    }

    @Test
    public void testGetMonthsWithNoPayments_NormalCase() {
        List<String> emptyMonths2026 = FinanceReportProcessor.getMonthsWithNoPayments(sampleReport, 2026);

        assertEquals(10, emptyMonths2026.size());
        assertFalse(emptyMonths2026.contains("Январь"));
        assertFalse(emptyMonths2026.contains("Март"));
        assertTrue(emptyMonths2026.contains("Февраль"));
        assertTrue(emptyMonths2026.contains("Декабрь"));
    }

    @Test
    public void testGetMonthsWithNoPayments_YearWithNoPayments() {
        List<String> emptyMonths2024 = FinanceReportProcessor.getMonthsWithNoPayments(sampleReport, 2024);
        assertEquals(12, emptyMonths2024.size());
    }
}