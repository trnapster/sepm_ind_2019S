package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.IHorseDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.util.DBConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;


@Repository
public class HorseDao implements IHorseDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(HorseDao.class);
    private final DBConnectionManager dbConnectionManager;

    @Autowired
    public HorseDao(DBConnectionManager dbConnectionManager) {
        this.dbConnectionManager = dbConnectionManager;
    }

    private static Horse dbResultToHorseDto(ResultSet result) throws SQLException {
        return new Horse(
            result.getInt("public_id"),
            result.getString("name"),
            result.getString("breed"),
            result.getDouble("min_speed"),
            result.getDouble("max_speed"),
            result.getTimestamp("created").toLocalDateTime(),
            result.getTimestamp("updated").toLocalDateTime());
    }

    private int insert(Horse horse) throws PersistenceException {
        String sql = "INSERT INTO Horse "
            + "(public_id, name, breed, min_speed, max_speed, created, updated)"
            + "values(?,?,?,?,?,?,?)";

        try {
            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            if (horse.getId() != null) {
                statement.setInt(1, horse.getId());
            }
            else {
                statement.setNull(1, Types.INTEGER);
            }
            statement.setString(2, horse.getName());
            statement.setString(3, horse.getBreed());
            statement.setDouble(4, horse.getMinSpeed());
            statement.setDouble(5, horse.getMaxSpeed());
            statement.setTimestamp(6, Timestamp.valueOf(horse.getCreated()));
            statement.setTimestamp(7, Timestamp.valueOf(horse.getUpdated()));
            int rows = statement.executeUpdate();

            if (rows == 0) throw new SQLException("No new rows generated");

            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }
            else {
                throw new SQLException("No ID obtained");
            }
        } catch (SQLException e) {
            LOGGER.error("Problem while executing SQL insert statement for inserting horse: " + horse, e);
            throw new PersistenceException("Could not create horse: " + horse, e);
        }
    }

    private Horse getRow(int rowId) throws PersistenceException, SQLException, NotFoundException {
        String sql = "SELECT * FROM Horse WHERE id=?";
        Horse horse = null;

        PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql);
        statement.setInt(1, rowId);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            horse = dbResultToHorseDto(result);
        }

        if (horse != null) {
            return horse;
        } else {
            throw new NotFoundException("Could not find horse with id " + rowId);
        }
    }

    private void obsolete(int id) throws PersistenceException, SQLException, NotFoundException {
        String sql = "UPDATE Horse SET obsolete = TRUE WHERE public_id = ? AND NOT obsolete";

        PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql);
        statement.setInt(1, id);
        int rows = statement.executeUpdate();

        if (rows == 0) throw new SQLException("No rows deleted");
    }

    @Override
    public Horse findOneById(Integer id) throws PersistenceException, NotFoundException {
        LOGGER.info("Get horse with id " + id);
        String sql = "SELECT * FROM Horse WHERE public_id=? AND NOT obsolete";
        Horse horse = null;
        try {
            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                horse = dbResultToHorseDto(result);
            }
        } catch (SQLException e) {
            LOGGER.error("Problem while executing SQL select statement for reading horse with id " + id, e);
            throw new PersistenceException("Could not read horses with id " + id, e);
        }
        if (horse != null) {
            return horse;
        } else {
            throw new NotFoundException("Could not find horse with id " + id);
        }
    }

    @Override
    public Horse createOne(Horse horse) throws PersistenceException {
        LOGGER.info("Create horse: " + horse);
        try {
            return getRow(insert(horse));
        } catch (SQLException | NotFoundException e) {
            LOGGER.error("Problem while executing SQL insert statement for inserting horse: " + horse, e);
            throw new PersistenceException("Could not create horse: " + horse, e);
        }
    }

    @Override
    public Horse updateOne(Integer id, Horse horse) throws PersistenceException {
        LOGGER.info("Update horse: " + horse);
        try {
            obsolete(id);
            return getRow(insert(horse));
        } catch (SQLException | NotFoundException e) {
            LOGGER.error("Problem while executing SQL update statement for updating horse with id: " + id, e);
            throw new PersistenceException("Could not update horse with id" + id, e);
        }
    }

    @Override
    public void deleteOne(Integer id) throws PersistenceException, NotFoundException {
        LOGGER.info("Delete horse with id: " + id);
        try {
            obsolete(id);
        } catch (SQLException | NotFoundException e) {
            LOGGER.error("Problem while executing SQL delete statement for deleting horse with id : " + id, e);
            throw new PersistenceException("Could not delete horse with id" + id, e);
        }
    }
}
