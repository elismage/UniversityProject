package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaxDeclarationTest {

    @Test
    public void testIncomeCertificate_ImmutabilityAndGetters() {
        double[] incomes = {1200, 2300, 24000, 25000, 0, 0, 0, 0, 0, 0, 0, 0};
        IncomeCertificate cert = new IncomeCertificate(2020, "Иванов И.И.", "ООО Рога и копыта", incomes);

        incomes[0] = 999999;
        assertEquals(1200.0, cert.getMonthlyIncomes()[0], 1e-6);

        double[] returned = cert.getMonthlyIncomes();
        returned[1] = 888888;
        assertEquals(2300.0, cert.getMonthlyIncomes()[1], 1e-6);

        assertEquals(2020, cert.getYear());
        assertEquals("Иванов И.И.", cert.getCitizenName());
        assertEquals("ООО Рога и копыта", cert.getOrganization());
    }

    @Test
    public void testIncomeCertificate_ValidationThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new IncomeCertificate(2020, "", "ООО Тест", new double[12]);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new IncomeCertificate(2020, "Иванов И.И.", "", new double[12]);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new IncomeCertificate(2020, "Иванов И.И.", "ООО Тест", new double[11]);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            double[] negativeIncome = new double[12];
            negativeIncome[3] = -100.0;
            new IncomeCertificate(2020, "Иванов И.И.", "ООО Тест", negativeIncome);
        });
    }

    @Test
    public void testTaxDeclaration_SpecExampleTwoSources() {
        double[] incomes1 = {1200.0, 2300.0, 24000.0, 25000.0, 0, 0, 0, 0, 0, 0, 0, 0};
        double[] incomes2 = {0.0, 50.0, 0.0, 0.0, 0, 0, 0, 0, 0, 0, 0, 0};

        IncomeCertificate cert1 = new IncomeCertificate(2020, "Иванов И.И.", "ООО «Рога и копыта»", incomes1);
        IncomeCertificate cert2 = new IncomeCertificate(2020, "Иванов И.И.", "казино «Поле чудес»", incomes2);

        TaxDeclaration declaration = new TaxDeclaration(2020, "Иванов И.И.", cert1, cert2);

        assertEquals(2020, declaration.getYear());
        assertEquals("Иванов И.И.", declaration.getCitizenName());

        double[] monthly = declaration.getMonthlyIncomes();
        double[] cumulative = declaration.getCumulativeIncomes();
        double[] taxes = declaration.getCumulativeTaxes();

        assertEquals(1200.0, monthly[0], 1e-6);
        assertEquals(2350.0, monthly[1], 1e-6);
        assertEquals(24000.0, monthly[2], 1e-6);
        assertEquals(25000.0, monthly[3], 1e-6);

        assertEquals(1200.0, cumulative[0], 1e-6);
        assertEquals(3550.0, cumulative[1], 1e-6);
        assertEquals(27550.0, cumulative[2], 1e-6);
        assertEquals(52550.0, cumulative[3], 1e-6);

        assertEquals(0.0, taxes[0], 1e-6);
        assertEquals(0.0, taxes[1], 1e-6);
        assertEquals(461.50, taxes[2], 1e-6);
        assertEquals(3711.50, taxes[3], 1e-6);
        assertEquals(3711.50, declaration.getTotalTaxForYear(), 1e-6);
    }

    @Test
    public void testTaxDeclaration_HighIncomeBrackets() {
        double[] incomes = {
                6000, 6000, 6000, 8000,
                60000, 60000, 60000, 60000,
                100000, 100000, 100000, 100000
        };
        IncomeCertificate cert = new IncomeCertificate(2020, "Петров П.П.", "АО Вектор", incomes);
        TaxDeclaration declaration = new TaxDeclaration(2020, "Петров П.П.", cert);

        double[] taxes = declaration.getCumulativeTaxes();

        assertEquals(0.0, taxes[2], 1e-6);
        assertEquals(260.0, taxes[3], 1e-6);
        assertEquals(216000.0 * 0.13 + 26000.0 * 0.20, taxes[7], 1e-6);
        assertEquals(216000.0 * 0.13 + 426000.0 * 0.20, declaration.getTotalTaxForYear(), 1e-6);
    }

    @Test
    public void testTaxDeclaration_ImmutabilityOfReturnedArrays() {
        double[] incomes = {10000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        IncomeCertificate cert = new IncomeCertificate(2020, "Иванов И.И.", "ООО Тест", incomes);
        TaxDeclaration declaration = new TaxDeclaration(2020, "Иванов И.И.", cert);

        declaration.getMonthlyIncomes()[0] = 999999;
        declaration.getCumulativeIncomes()[0] = 999999;
        declaration.getCumulativeTaxes()[0] = 999999;

        assertEquals(10000.0, declaration.getMonthlyIncomes()[0], 1e-6);
        assertEquals(10000.0, declaration.getCumulativeIncomes()[0], 1e-6);
        assertEquals(0.0, declaration.getCumulativeTaxes()[0], 1e-6);
    }

    @Test
    public void testTaxDeclaration_MismatchedCertificateThrowsException() {
        double[] incomes = new double[12];
        IncomeCertificate wrongYearCert = new IncomeCertificate(2021, "Иванов И.И.", "ООО Тест", incomes);
        IncomeCertificate wrongNameCert = new IncomeCertificate(2020, "Петров П.П.", "ООО Тест", incomes);

        assertThrows(IllegalArgumentException.class, () -> {
            new TaxDeclaration(2020, "Иванов И.И.", wrongYearCert);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new TaxDeclaration(2020, "Иванов И.И.", wrongNameCert);
        });
    }
}