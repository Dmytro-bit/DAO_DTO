package DTOs;

import java.util.Date;

public class Income {
    private int incomeID = 0;
    private String title;
    private double amount;
    private Date dateEarned;

    public Income(int incomeID, String title, double amount, Date date) {
        this.incomeID = incomeID;
        this.title = title;
        this.amount = amount;
        this.dateEarned = date;
    }

    public Income(String title, double amount, Date date) {
        this.title = title;
        this.amount = amount;
        this.dateEarned = date;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public java.sql.Date getDateEarned() {
        return (java.sql.Date) dateEarned;
    }

    public void setDateEarned(Date dateEarned) {
        this.dateEarned = dateEarned;
    }

    @Override
    public String toString() {
        return "Income [incomeID=" + incomeID + ", title=" + title + ", amount=" + amount + ", dateEarned=" + dateEarned + "]";
    }
}
