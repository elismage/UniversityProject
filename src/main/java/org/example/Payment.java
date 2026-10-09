package org.example;


public class Payment {
    private String name;
    private int dateDay;
    private int dateMonth;
    private int dateYear;
    private int payment;

    //construction
    public Payment(String name,int day, int month, int year, int payment){
        this.name = name;
        this.dateDay = day;
        this.dateMonth =  month;
        this.dateYear = year;
        this.payment = payment;
    }
    //null construction;
    public Payment(){
        this.name = "";
        this.dateDay = 0;
        this.dateMonth =  0;
        this.dateYear = 0;
        this.payment = 0;
    }


    //getters
    public String getName() {
        return name;
    }

    public int getDateDay() {
        return dateDay;
    }

    public int getDateMonth() {
        return dateMonth;
    }

    public int getDateYear() {
        return dateYear;
    }

    public int getPayment() {
        return payment;
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDateDay(int dateDay) {
        this.dateDay = dateDay;
    }

    public void setDateMonth(int dateMonth) {
        this.dateMonth = dateMonth;
    }

    public void setDateYear(int dateYear) {
        this.dateYear = dateYear;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    //equals

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Payment other = (Payment) obj;

        if (dateDay != other.dateDay) return false;
        if (dateMonth != other.dateMonth) return false;
        if (dateYear != other.dateYear) return false;
        if (payment != other.payment) return false;

        if (name == null) {
            return other.name == null;
        } else {
            return name.equals(other.name);
        }
    }

    //hash

    @Override
    public int hashCode() {
        int result = 17;

        result = 31 * result + (name != null ? name.hashCode() : 0);

        result = 31 * result + dateDay;
        result = 31 * result + dateMonth;
        result = 31 * result + dateYear;
        result = 31 * result + payment;

        return result;
    }
    //toString


    @Override
    public String toString() {
        return "Payment{" +
                "name='" + name + '\'' +
                ", date=" + dateDay + "." + dateMonth + "." + dateYear +
                ", payment (в копейках)=" + payment +
                '}';
    }

    public Payment(Payment other) {
        if (other != null) {
            this.name = other.getName();
            this.dateDay = other.getDateDay();
            this.dateMonth = other.getDateMonth();
            this.dateYear = other.getDateYear();
            this.payment = other.getPayment();
        }
    }

}
