import DAOs.MySqlExpenseDao;
import DAOs.MySqlIncomeDao;
import DAOs.RecordDaoInterface;
import DTOs.Expense;
import DTOs.Income;
import Exception.DaoException;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        RecordDaoInterface IExpenseDao = new MySqlExpenseDao();
        RecordDaoInterface IIncomeDao = new MySqlIncomeDao();
        try {
            mainManu(IExpenseDao, IIncomeDao);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    public static void printObjects(List<Object> objects) {
        if (objects.isEmpty())
            System.out.println("There are no objects");
        else {
            if (objects.get(0) instanceof Expense) {
                System.out.printf("%5s | %-20s | %-15s | %-20s | %-12s |\n", "ID", "Title", "Category", "Amount", "Date");
                System.out.println("--------------------------------------------------------------------------------------------");
            } else {
                System.out.printf("%5s | %-20s | %-20s | %-12s |\n", "ID", "Title", "Amount", "Date Earned");
                System.out.println("---------------------------------------------------------------------");
            }

            for (Object object : objects) {
                if (object instanceof Expense expense) {
                    System.out.printf("%5d | %-20s | %-15s | %20.2f | %-12s |\n", expense.getExpenseID(), expense.getTitle(), expense.getCategory(), expense.getAmount(), expense.getDateIncurred());
                } else if (object instanceof Income income) {
                    System.out.printf("%5d | %-20s | %20.2f | %-12s |\n", income.getIncomeID(), income.getTitle(), income.getAmount(), income.getDateEarned());
                }
            }
        }
    }

    public static double calculateTotal(List<Object> objects) {
        double total = 0;
        if (objects.isEmpty())
            System.out.println("There are no objects");
        else {
            for (Object object : objects) {
                if (object instanceof Expense expense) {
                    total += expense.getAmount();
                } else if (object instanceof Income income) {
                    total += income.getAmount();
                }
            }
        }
        return total;
    }

    public static void mainManu(RecordDaoInterface IExpenseDao, RecordDaoInterface IIncomeDao) throws DaoException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("========= Finance Management =========");
            System.out.println("1) Expenses Management");
            System.out.println("2) Incomes Management");
            System.out.println("3) Month Statistics");
            System.out.println("4) Exit");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    expenseManagement(IExpenseDao);
                    break;
                case "2":
                    incomeManagement(IIncomeDao);
                    break;
                case "3":
                    monthStatistics(IExpenseDao, IIncomeDao);
                    break;
                case "4":
                    System.exit(0);
                    break;
                default:
                    mainManu(IExpenseDao, IIncomeDao);
                    break;
            }
        }
    }

    public static void monthStatistics(RecordDaoInterface IExpenseDao, RecordDaoInterface IIncomeDao) throws DaoException {
        Scanner scanner = new Scanner(System.in);
        String[] months = {
                "January", "February", "March", "April", "May",
                "June", "July", "August", "September", "October",
                "November", "December"
        };
        Integer monthNumber = null;
        do {
            System.out.println("Please enter month number: ");
            try {
                monthNumber = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid month number");
            }
        }
        while (monthNumber == null || monthNumber < 1 || monthNumber > 12);

        List<Object> expenseList = IExpenseDao.getAllRecords(monthNumber);
        List<Object> incomeList = IIncomeDao.getAllRecords(monthNumber);
        double totalExpenses = calculateTotal(expenseList);
        double totalIncomes = calculateTotal(incomeList);


        System.out.println("========= Finance Statistics for " + months[monthNumber - 1] + " =========");
        System.out.println("Expenses in month: " + totalExpenses);
        printObjects(expenseList);
        System.out.println("\nIncomes in month: " + totalIncomes);
        printObjects(incomeList);
        System.out.println();
        double balance = totalIncomes - totalExpenses;
        System.out.printf("\nMonth balance: " + (balance >= 0 ? "+" : "-") + "%.2f\n", balance);

    }

    public static void incomeManagement(RecordDaoInterface IIncomeDao) throws DaoException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("========= Income Management =========");
        System.out.println("1) All Incomes");
        System.out.println("2) Add New Income");
        System.out.println("3) Remove Income");
        System.out.println("4) <-- Back");

        String input = scanner.nextLine();
        switch (input) {
            case "1":
                List<Object> incomeList = IIncomeDao.getAllRecords(null);
                printObjects(incomeList);
                System.out.println("Total income: " + calculateTotal(incomeList));
                break;
            case "2":
                Income income = addNewIncome();
                IIncomeDao.addRecord(income);
                break;
            case "3":
                Integer incomeID = null;
                printObjects(IIncomeDao.getAllRecords(null));
                do {
                    System.out.println("Enter income ID: ");
                    try {
                        incomeID = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid expense ID");
                    }
                }
                while (incomeID == null);
                IIncomeDao.deleteRecord(incomeID);
                System.out.println("New Incomes: ");
                printObjects(IIncomeDao.getAllRecords(null));
                break;
            case "4":
                break;
            default:
                incomeManagement(IIncomeDao);
        }
    }

    public static void expenseManagement(RecordDaoInterface IExpenseDao) throws DaoException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("========= Expense Management =========");
        System.out.println("1) All Expenses");
        System.out.println("2) Add New Expense");
        System.out.println("3) Remove Expense");
        System.out.println("4) <-- Back");

        String input = scanner.nextLine();
        switch (input) {
            case "1":
                List<Object> expenseList = IExpenseDao.getAllRecords(null);
                printObjects(expenseList);
                System.out.println("Total expenses: " + calculateTotal(expenseList));
                break;
            case "2":
                Expense expense = addNewExpense();
                IExpenseDao.addRecord(expense);
                break;
            case "3":
                Integer expenseID = null;
                printObjects(IExpenseDao.getAllRecords(null));
                do {
                    System.out.println("Enter expense ID: ");
                    try {
                        expenseID = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid expense ID");
                    }
                }
                while (expenseID == null);
                IExpenseDao.deleteRecord(expenseID);
                System.out.println("New Expenses:");
                printObjects(IExpenseDao.getAllRecords(null));
                break;
            case "4":
                break;
        }
    }

    public static Income addNewIncome() {
        Scanner scanner = new Scanner(System.in);
        String title;
        Double amount = null;
        String date = "";

        System.out.println("========= Add New Income =========");
        System.out.println("Please enter the title: ");
        title = scanner.nextLine();
        do {
            System.out.println("Please enter the amount: ");
            try {
                amount = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        } while (amount == null);

        do {
            System.out.println("Please enter the date: (YYYY-MM-DD)");
            date = scanner.nextLine();
        } while (!checkDate(date));

        return new Income(title, amount, Date.valueOf(date));
    }

    public static Expense addNewExpense() {
        Scanner scanner = new Scanner(System.in);
        String title;
        String category;
        Double amount = null;
        String date = "";

        System.out.println("========= Add New Expense =========");
        System.out.println("Please enter the title: ");
        title = scanner.nextLine();
        System.out.println("Please enter the category: ");
        category = scanner.nextLine();
        do {
            System.out.println("Please enter the amount: ");
            try {
                amount = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        } while (amount == null);

        do {
            System.out.println("Please enter the date: (YYYY-MM-DD)");
            date = scanner.nextLine();
        } while (!checkDate(date));

        return new Expense(title, category, amount, Date.valueOf(date));

    }

    public static boolean checkDate(String date) {
        String regex = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }
}