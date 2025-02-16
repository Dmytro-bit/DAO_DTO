package DAOs;

import DTOs.Expense;
import Exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MySqlExpenseDao extends MySqlDao implements RecordDaoInterface {

    @Override
    public boolean addRecord(Object object) throws DaoException {
        Expense expense = (Expense) object;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            String query = "INSERT INTO expense (`title`, `category`, `amount`, `dateIncurred`) VALUES (?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, expense.getTitle());
            preparedStatement.setString(2, expense.getCategory());
            preparedStatement.setDouble(3, expense.getAmount());
            preparedStatement.setDate(4, expense.getDateIncurred());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Expense addRecord() " + e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("addRecord() " + e.getMessage());
            }
        }
        return true;
    }

    @Override
    public void deleteRecord(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            String query = "DELETE FROM expense WHERE expenseID = ?;";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Expense deleteRecord() " + e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("deleteRecord() " + e.getMessage());
            }
        }
    }

    @Override
    public List<Object> getAllRecords(Integer monthNumber) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Object> expenseList = new ArrayList<>();
        String query;

        try {
            connection = getConnection();
            if (monthNumber == null) {
                query = "SELECT * FROM expense";
            } else {
                query = "SELECT * FROM expense WHERE MONTH(dateIncurred) = ?";
            }
            preparedStatement = connection.prepareStatement(query);

            if (monthNumber != null) preparedStatement.setInt(1, monthNumber);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int expenseID = resultSet.getInt("expenseID");
                String title = resultSet.getString("title");
                String category = resultSet.getString("category");
                double amount = resultSet.getDouble("amount");
                Date dateIncurred = resultSet.getDate("dateIncurred");
                Expense expense = new Expense(expenseID, title, category, amount, dateIncurred);
                expenseList.add(expense);
            }
        } catch (SQLException e) {
            throw new DaoException("getAllRecords() " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("getAllRecords() " + e.getMessage());
            }
        }
        return expenseList;
    }
}
