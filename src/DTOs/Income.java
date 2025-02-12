package DTOs;

import java.util.Date;

public class Income {
    private int incomeID = 0;
    private String title;
    private double amount;
    private Date dateEarned;

    public Income(String title, double amount, Date date) {
        this.incomeID = ++incomeID;
        this.title = title;
        this.amount = amount;
        this.dateEarned = date;
    }

    public Income() {
    }

    public int getIncomeID() {
        return incomeID;
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

    public Date getDateEarned() {
        return dateEarned;
    }

    public void setDateEarned(Date dateEarned) {
        this.dateEarned = dateEarned;
    }
}
