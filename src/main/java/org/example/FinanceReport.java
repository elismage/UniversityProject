package org.example;

public class FinanceReport {
    private Payment[] financeReport;
    private String fio;
    private int day;
    private int month;
    private int year;

    //constructor
    public FinanceReport(String fio, int day, int month, int year, int paymentsCount) {
        this.fio = fio;
        this.day = day;
        this.month = month;
        this.year = year;
        this.financeReport = new Payment[paymentsCount]; // Выделяем память под массив
    }

    //getters
    public Payment[] getFinanceReport() {
        return financeReport;
    }

    public String getFIO() {
        return fio;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    //setters
    public void setFinanceReport(Payment[] financeReport) {
        this.financeReport = financeReport;
    }

    public void setFIO(String FIO) {
        this.fio = FIO;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    //other

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }



    //friendly-methods

    public int countOfPayments(){
        return this.financeReport.length;
    }

    public String[] paymentAccessRead(int index){
        if (this.financeReport == null || index < 0 || index >= this.financeReport.length || this.financeReport[index] == null) {
            return new String[]{"", "", "", "", ""};
        }

        String[] output = new String[5];
        output[0] = this.financeReport[index].getName();
        output[1] = Integer.toString(this.financeReport[index].getDateDay());
        output[2] = Integer.toString(this.financeReport[index].getDateMonth());
        output[3] = Integer.toString(this.financeReport[index].getDateYear());
        output[4] = Integer.toString(this.financeReport[index].getPayment());

        return output;
    }

    public void paymentAccessWrite(int index, String name, int day, int month, int year, int payment){
        if (this.financeReport == null || index < 0 || index >= this.financeReport.length) {
            return;
        }
        if (this.financeReport[index] == null) {
            this.financeReport[index] = new Payment();
        }

        this.financeReport[index].setName(name);
        this.financeReport[index].setDateDay(day);
        this.financeReport[index].setDateMonth(month);
        this.financeReport[index].setDateYear(year);
        this.financeReport[index].setPayment(payment);
    }


    //toString
    @Override
    public String toString(){
        String output= "";
        output+=String.format("[Автор: %s составителя, дата: %d.%d.%d, Платежи: [\n",this.fio,this.day,this.month,this.year);
        for(int i = 0; i < this.financeReport.length; i++){
            if (financeReport[i] == null) continue;
            output+=String.format("%d.Плательщик: %s, дата: %d.%d.%d сумма: %d руб. %d коп.\n",i,financeReport[i].getName(),financeReport[i].getDateDay(),financeReport[i].getDateMonth(),financeReport[i].getDateYear(),financeReport[i].getPayment()/100,financeReport[i].getPayment()%100);
        }
        output+="]";
        return output;
    }

    //copy
    public FinanceReport(FinanceReport other) {
        if (other == null) {
            throw new IllegalArgumentException("Копируемый отчет не может быть null");
        }

        // Копируем простые поля
        this.fio = other.getFIO();
        this.day = other.getDay();
        this.month = other.getMonth();
        this.year = other.getYear();

        if (other.getFinanceReport() != null) {
            this.financeReport = new Payment[other.getFinanceReport().length];
            for (int i = 0; i < other.getFinanceReport().length; i++) {
                if (other.getFinanceReport()[i] != null) {
                    this.financeReport[i] = new Payment(other.getFinanceReport()[i]);
                } else {
                    this.financeReport[i] = null;
                }
            }
        }
    }

}

