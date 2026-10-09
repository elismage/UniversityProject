package org.example;
//arraylist??


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
}
