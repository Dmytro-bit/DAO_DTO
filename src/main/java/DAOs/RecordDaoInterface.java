package DAOs;

import DTOs.Expense;
import Exception.DaoException;

import java.util.List;

public interface RecordDaoInterface {
    public boolean addRecord(Record record) throws DaoException;

    public boolean deleteRecord(int id) throws DaoException;

    public List<Expense> getAllRecords() throws DaoException;
}
