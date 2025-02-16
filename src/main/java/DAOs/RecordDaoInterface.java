package DAOs;

import Exception.DaoException;

import java.util.List;

public interface RecordDaoInterface {
    boolean addRecord(Object object) throws DaoException;

    void deleteRecord(int id) throws DaoException;

    List<Object> getAllRecords(Integer monthNumber) throws DaoException;
}
