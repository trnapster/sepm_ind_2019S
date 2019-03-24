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

import java.util.List;
import java.util.ArrayList;
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

    private int insert(Horse horse) throws PersistenceException, SQLException {
        String sql = "INSERT INTO Horse "
            + "(public_id, name, breed, min_speed, max_speed, created, updated)"
            + "values(?,?,?,?,?,?,?)";

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
            LOGGER.debug("Jockey inserted into database with ID: " + rs.getInt(1));
            return rs.getInt(1);
        }
        else {
            throw new SQLException("No ID obtained");
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
            LOGGER.debug("Horse read from database: " + horse);
            return horse;
        } else {
            LOGGER.warn("Could not find horse with row_id: " + rowId);
            throw new NotFoundException("Could not find horse with ID: " + rowId);
        }
    }

    private void obsolete(int id) throws PersistenceException, SQLException, NotFoundException {
        String sql = "UPDATE Horse SET obsolete = TRUE WHERE public_id = ? AND NOT obsolete";

        PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql);
        statement.setInt(1, id);
        int rows = statement.executeUpdate();

        if (rows == 0) throw new NotFoundException("Could not delete Horse with ID: " + id);

        LOGGER.debug("Horse with ID: " + id + "set to obsolete in database");
    }

    @Override
    public Horse findOneById(Integer id) throws PersistenceException, NotFoundException {
        LOGGER.debug("Reading horse with public_id: " + id + " from database");
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
            LOGGER.error("Problem while executing SQL select statement for reading horse with public_id: " + id, e);
            throw new PersistenceException("Could not read horse with ID: " + id, e);
        }
        if (horse != null) {
            LOGGER.info("Read horse from database:" + horse);
            return horse;
        } else {
            LOGGER.warn("Could not find horse with public_id: " + id);
            throw new NotFoundException("Could not find horse with id " + id);
        }
    }

    @Override
    public List<Horse> getAllFiltered(Horse filter) throws PersistenceException {
        LOGGER.debug("Reading all horses with filter: " + filter + " from database");
        String sql = "SELECT * FROM Horse WHERE NOT obsolete";
        int parameterCount = 0;
        int nameIndex = 0; 
        int breedIndex = 0; 
        int minSpeedIndex = 0; 
        int maxSpeedIndex = 0;
        List<Horse> horses = new ArrayList<Horse>();

        if (filter.getName() != null) {
            sql += " AND LOWER(name) LIKE LOWER( ? )";
            parameterCount++;
            nameIndex = parameterCount;
        }
        if (filter.getBreed() != null) {
            sql += " AND LOWER(breed) LIKE LOWER( ? )";
            parameterCount++;
            breedIndex = parameterCount;
        }
        if (filter.getMinSpeed() != null) {
            sql += " AND min_speed >= ?";
            parameterCount++;
            minSpeedIndex = parameterCount;
        }
        if (filter.getMaxSpeed() != null) {
            sql += " AND max_speed <= ?";
            parameterCount++;
            maxSpeedIndex = parameterCount;
        }


        try {
            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql);

            if (filter.getName() != null) {
                statement.setString(nameIndex, "%" + filter.getName() + "%");
            }
            if (filter.getBreed() != null) {
                statement.setString(breedIndex, "%" + filter.getBreed() + "%");
            }
            if (filter.getMinSpeed() != null) {
                statement.setDouble(minSpeedIndex, filter.getMinSpeed());
            }
            if (filter.getMaxSpeed() != null) {
                statement.setDouble(maxSpeedIndex, filter.getMaxSpeed());
            }

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Horse horse = dbResultToHorseDto(result);
                horses.add(horse);
            }
            LOGGER.info("Read horses: " + horses + " from database");
            return horses;
        } catch (SQLException e) {
            LOGGER.error("Problem while executing SQL select statement for reading horses with filter: " + filter, e);
            throw new PersistenceException("Could not read horses", e);
        }
    }

    @Override
    public Horse createOne(Horse horse) throws PersistenceException {
        LOGGER.debug("Saving horse: " + horse + " in database");
        try {
            Horse newHorse =  getRow(insert(horse));
            LOGGER.info("Saved horse: " + newHorse + " in database");
            return newHorse;
        } catch (SQLException | NotFoundException e) {
            LOGGER.error("Problem while executing SQL insert statement for inserting horse: " + horse, e);
            throw new PersistenceException("Could not create horse", e);
        }
    }

    @Override
    public Horse updateOne(Horse horse) throws PersistenceException, NotFoundException {
        LOGGER.debug("Updating horse: " + horse + " in database");
        try {
            obsolete(horse.getId());
            Horse newHorse = getRow(insert(horse));
            LOGGER.info("Updated horse: " + newHorse + " in database");
            return newHorse;
        } catch (SQLException e) {
            LOGGER.error("Problem while executing SQL for updating horse with id: " + horse.getId(), e);
            throw new PersistenceException("Could not update horse with ID: " + horse.getId(), e);
        }
    }

    @Override
    public void deleteOne(Integer id) throws PersistenceException, NotFoundException {
        LOGGER.debug("Deleting horse with ID: " + id + " from database");
        try {
            obsolete(id);
            LOGGER.info("Deleted horse with ID: " + id + " from database");
        } catch (SQLException e) {
            LOGGER.error("Problem while executing SQL delete statement for deleting horse with id : " + id, e);
            throw new PersistenceException("Could not delete horse with ID: " + id, e);
        }
    }
}
