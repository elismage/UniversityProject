package org.example;

import java.util.Arrays;

public final class TaxDeclaration {
    private static final double ZERO_TAX_LIMIT = 24000.0;
    private static final double REDUCED_TAX_LIMIT = 240000.0;
    private static final double REDUCED_TAX_RATE = 0.13;
    private static final double HIGH_TAX_RATE = 0.20;

    private final int year;
    private final String citizenName;
    private final double[] monthlyIncomes;
    private final double[] cumulativeIncomes;
    private final double[] cumulativeTaxes;

    public TaxDeclaration(int year, String citizenName, IncomeCertificate... certificates) {
        if (citizenName == null || citizenName.isBlank()) {
            throw new IllegalArgumentException("Citizen name cannot be null or empty");
        }
        if (certificates == null) {
            throw new IllegalArgumentException("Certificates array cannot be null");
        }

        this.year = year;
        this.citizenName = citizenName;
        this.monthlyIncomes = new double[12];
        this.cumulativeIncomes = new double[12];
        this.cumulativeTaxes = new double[12];

        for (IncomeCertificate cert : certificates) {
            if (cert == null) {
                continue;
            }
            if (cert.getYear() != year || !cert.getCitizenName().equals(citizenName)) {
                throw new IllegalArgumentException("Certificate metadata does not match declaration header");
            }
            double[] certIncomes = cert.getMonthlyIncomes();
            for (int i = 0; i < 12; i++) {
                this.monthlyIncomes[i] += certIncomes[i];
            }
        }

        double runningTotal = 0.0;
        for (int i = 0; i < 12; i++) {
            runningTotal += this.monthlyIncomes[i];
            this.cumulativeIncomes[i] = runningTotal;
            this.cumulativeTaxes[i] = calculateTaxForCumulativeIncome(runningTotal);
        }
    }

    private static double calculateTaxForCumulativeIncome(double cumulativeIncome) {
        if (cumulativeIncome <= ZERO_TAX_LIMIT) {
            return 0.0;
        }
        if (cumulativeIncome <= REDUCED_TAX_LIMIT) {
            return (cumulativeIncome - ZERO_TAX_LIMIT) * REDUCED_TAX_RATE;
        }
        double middleBracketTax = (REDUCED_TAX_LIMIT - ZERO_TAX_LIMIT) * REDUCED_TAX_RATE;
        double highBracketTax = (cumulativeIncome - REDUCED_TAX_LIMIT) * HIGH_TAX_RATE;
        return middleBracketTax + highBracketTax;
    }

    public int getYear() {
        return year;
    }

    public String getCitizenName() {
        return citizenName;
    }

    public double[] getMonthlyIncomes() {
        return Arrays.copyOf(monthlyIncomes, 12);
    }

    public double[] getCumulativeIncomes() {
        return Arrays.copyOf(cumulativeIncomes, 12);
    }

    public double[] getCumulativeTaxes() {
        return Arrays.copyOf(cumulativeTaxes, 12);
    }

    public double getTotalTaxForYear() {
        return cumulativeTaxes[11];
    }
}