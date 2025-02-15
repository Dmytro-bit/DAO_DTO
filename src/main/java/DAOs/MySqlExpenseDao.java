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
    public boolean addRecord(Record record) throws DaoException {
        return false;
    }

    @Override
    public boolean deleteRecord(int id) throws DaoException {
        return false;
    }

    @Override
    public List<Expense> getAllRecords() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Expense> expenseList = new ArrayList<>();


        try {
            connection = getConnection();
            String query = "SELECT * FROM Expense";
            preparedStatement = connection.prepareStatement(query);

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
