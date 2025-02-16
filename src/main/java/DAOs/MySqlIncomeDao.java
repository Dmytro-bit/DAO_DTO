package DAOs;

import DTOs.Income;
import Exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MySqlIncomeDao extends MySqlDao implements RecordDaoInterface {

    @Override
    public boolean addRecord(Object object) throws DaoException {
        Income income = (Income) object;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            String query = "INSERT INTO income (`title`, `amount`, `dateEarned`) VALUES (?,?,?)";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, income.getTitle());
            preparedStatement.setDouble(2, income.getAmount());
            preparedStatement.setDate(3, income.getDateEarned());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Income addRecord() " + e.getMessage());
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
            String query = "DELETE FROM income WHERE incomeID = ?;";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Income deleteRecord() " + e.getMessage());
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
        List<Object> incomeList = new ArrayList<>();
        String query;

        try {
            connection = getConnection();
            if (monthNumber == null) {
                query = "SELECT * FROM income";
            } else {
                query = "SELECT * FROM income WHERE MONTH(dateEarned) = ?";
            }
            preparedStatement = connection.prepareStatement(query);

            if (monthNumber != null) preparedStatement.setInt(1, monthNumber);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int incomeID = resultSet.getInt("incomeID");
                String title = resultSet.getString("title");
                double amount = resultSet.getDouble("amount");
                Date dateEarned = resultSet.getDate("dateEarned");
                Income income = new Income(incomeID, title, amount, dateEarned);
                incomeList.add(income);
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
        return incomeList;
    }
}
