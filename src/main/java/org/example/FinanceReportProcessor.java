package org.example;
import java.util.ArrayList;
import java.util.List;


public class FinanceReportProcessor {
    public static FinanceReport paymentNameFinder(FinanceReport originalReport, char nameFirstChar){

        int count = 0;
        for (int i = 0; i < originalReport.getFinanceReport().length; i++){
            if (originalReport.getFinanceReport()[i] == null) continue;
            if (originalReport.getFinanceReport()[i].getName().charAt(0) == nameFirstChar){
                count++;
            }
        }
        FinanceReport financeReport = new FinanceReport(
                originalReport.getFIO(),
                originalReport.getDay(),
                originalReport.getMonth(),
                originalReport.getYear(),
                count);

        int newIndex = 0;
        for (int i = 0; i < originalReport.getFinanceReport().length; i++){
            if (originalReport.getFinanceReport()[i] == null) continue;

            Payment p = originalReport.getFinanceReport()[i];

            if (p.getName().charAt(0) == nameFirstChar){
                financeReport.paymentAccessWrite(
                        newIndex,
                        p.getName(),
                        p.getDateDay(),
                        p.getDateMonth(),
                        p.getDateYear(),
                        p.getPayment()
                );
                newIndex++;
            }
        }

        return financeReport;
    }

    public static FinanceReport paymentCostFinder(FinanceReport originalReport, int minimalCost){

        int count = 0;
        for (int i = 0; i < originalReport.getFinanceReport().length; i++){
            if (originalReport.getFinanceReport()[i] == null) continue;
            if (originalReport.getFinanceReport()[i].getPayment() < minimalCost){
                count++;
            }
        }
        FinanceReport financeReport = new FinanceReport(
                originalReport.getFIO(),
                originalReport.getDay(),
                originalReport.getMonth(),
                originalReport.getYear(),
                count);

        int newIndex = 0;
        for (int i = 0; i < originalReport.getFinanceReport().length; i++){
            if (originalReport.getFinanceReport()[i] == null) continue;

            Payment p = originalReport.getFinanceReport()[i];

            if (p.getPayment() < minimalCost){
                financeReport.paymentAccessWrite(
                        newIndex,
                        p.getName(),
                        p.getDateDay(),
                        p.getDateMonth(),
                        p.getDateYear(),
                        p.getPayment()
                );
                newIndex++;
            }
        }

        return financeReport;
    }



    public static int dateFinder(FinanceReport originalReport, String date){
        if (originalReport == null || originalReport.getFinanceReport() == null) {
            return 0;
        }

        String[] dateClassified = date.split("\\.");
        if (dateClassified.length != 3) {
            throw new IllegalArgumentException("Illegal date format.Need DD.MM.YY");
        }

        int searchDay = Integer.parseInt(dateClassified[0].trim());
        int searchMonth = Integer.parseInt(dateClassified[1].trim());
        int searchYear = Integer.parseInt(dateClassified[2].trim());

        if (searchYear < 100) {
            searchYear += 2000;
        }

        int count = 0;
        Payment[] payments = originalReport.getFinanceReport();

        for (int i = 0; i < payments.length; i++) {
            if (payments[i] == null) {
                continue;
            }


            if (payments[i].getDateDay() == searchDay &&
                    payments[i].getDateMonth() == searchMonth &&
                    payments[i].getDateYear() == searchYear) {

                count++;
            }
        }

        return count;
    }

    public static List<String> getMonthsWithNoPayments(FinanceReport originalReport, int searchYear) {
        String[] monthNames = {
                "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
                "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
        };

        boolean[] hasPayments = new boolean[12];

        if (originalReport == null || originalReport.getFinanceReport() == null) {
            return List.of(monthNames);
        }

        Payment[] payments = originalReport.getFinanceReport();

        for (int i = 0; i < payments.length; i++) {
            if (payments[i] == null) {
                continue;
            }

            int paymentYear = payments[i].getDateYear();
            if (paymentYear < 100) paymentYear += 2000;

            int targetYear = searchYear < 100 ? searchYear + 2000 : searchYear;

            if (paymentYear == targetYear) {
                int monthIndex = payments[i].getDateMonth() - 1;
                if (monthIndex >= 0 && monthIndex < 12) {
                    hasPayments[monthIndex] = true;
                }
            }
        }

        List<String> emptyMonths = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            if (!hasPayments[i]) {
                emptyMonths.add(monthNames[i]);
            }
        }
        return emptyMonths;
    }
}
