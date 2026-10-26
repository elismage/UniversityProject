package org.example;

import java.util.Arrays;

public final class IncomeCertificate {
    private final int year;
    private final String citizenName;
    private final String organization;
    private final double[] monthlyIncomes;

    public IncomeCertificate(int year, String citizenName, String organization, double[] monthlyIncomes) {
        if (citizenName == null || citizenName.isBlank()) {
            throw new IllegalArgumentException("Citizen name cannot be null or empty");
        }
        if (organization == null || organization.isBlank()) {
            throw new IllegalArgumentException("Organization cannot be null or empty");
        }
        if (monthlyIncomes == null || monthlyIncomes.length != 12) {
            throw new IllegalArgumentException("Monthly incomes must contain exactly 12 values");
        }
        for (double income : monthlyIncomes) {
            if (income < 0) {
                throw new IllegalArgumentException("Income cannot be negative");
            }
        }

        this.year = year;
        this.citizenName = citizenName;
        this.organization = organization;
        this.monthlyIncomes = Arrays.copyOf(monthlyIncomes, 12);
    }

    public int getYear() {
        return year;
    }

    public String getCitizenName() {
        return citizenName;
    }

    public String getOrganization() {
        return organization;
    }

    public double[] getMonthlyIncomes() {
        return Arrays.copyOf(monthlyIncomes, 12);
    }
}