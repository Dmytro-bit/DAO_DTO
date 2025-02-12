package DTOs;

import java.util.Date;

public class Expense {
    private int expenseID = 0;
    private String title;
    private String category;
    private double amount;
    private Date dateIncurred;

    public Expense(int expenseID, String title, String category, double amount, Date dateIncurred) {
        this.expenseID = expenseID;
        this.title = title;
        this.category = category;
        this.amount = amount;
        this.dateIncurred = dateIncurred;
    }


    public Expense(String title, String category, double amount, Date dateIncurred) {
        this.expenseID = ++this.expenseID;
        this.title = title;
        this.category = category;
        this.amount = amount;
        this.dateIncurred = dateIncurred;
    }

    public Expense() {
    }

    //    GETTERS
    public int getExpenseID() {
        return expenseID;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDateIncurred() {
        return dateIncurred;
    }

//    SETTERS

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDateIncurred(Date dateIncurred) {
        this.dateIncurred = dateIncurred;
    }
}
