package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Jockey;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.IJockeyDao;
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
public class JockeyDao implements IJockeyDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(JockeyDao.class);
    private final DBConnectionManager dbConnectionManager;

    @Autowired
    public JockeyDao(DBConnectionManager dbConnectionManager) {
        this.dbConnectionManager = dbConnectionManager;
    }

    private static Jockey dbResultToJockeyDto(ResultSet result) throws SQLException {
        return new Jockey(
            result.getInt("public_id"),
            result.getString("name"),
            result.getDouble("skill"),
            result.getTimestamp("created").toLocalDateTime(),
            result.getTimestamp("updated").toLocalDateTime());
    }

    private int insert(Jockey jockey) throws PersistenceException {
        String sql = "INSERT INTO Jockey "
            + "(public_id, name, skill, created, updated)"
            + "values(?,?,?,?,?)";

        try {
            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            if (jockey.getId() != null) {
                statement.setInt(1, jockey.getId());
            }
            else {
                statement.setNull(1, Types.INTEGER);
            }
            statement.setString(2, jockey.getName());
            statement.setDouble(3, jockey.getSkill());
            statement.setTimestamp(4, Timestamp.valueOf(jockey.getCreated()));
            statement.setTimestamp(5, Timestamp.valueOf(jockey.getUpdated()));
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
            LOGGER.error("Problem while executing SQL insert statement for inserting jockey: " + jockey, e);
            throw new PersistenceException("Could not create jockey: " + jockey, e);
        }
    }

    private Jockey getRow(int rowId) throws PersistenceException, SQLException, NotFoundException {
        String sql = "SELECT * FROM Jockey WHERE id=?";
        Jockey jockey = null;

        PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql);
        statement.setInt(1, rowId);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            jockey = dbResultToJockeyDto(result);
        }

        if (jockey != null) {
            return jockey;
        } else {
            throw new NotFoundException("Could not find jockey with id " + rowId);
        }
    }

    private void obsolete(int id) throws PersistenceException, SQLException, NotFoundException {
        String sql = "UPDATE Jockey SET obsolete = TRUE WHERE public_id = ? AND NOT obsolete";

        PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql);
        statement.setInt(1, id);
        int rows = statement.executeUpdate();

        if (rows == 0) throw new SQLException("No rows deleted");
    }

    @Override
    public Jockey findOneById(Integer id) throws PersistenceException, NotFoundException {
        LOGGER.info("Get jockey with id " + id);
        String sql = "SELECT * FROM Jockey WHERE public_id=? AND NOT obsolete";
        Jockey jockey = null;
        try {
            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                jockey = dbResultToJockeyDto(result);
            }
        } catch (SQLException e) {
            LOGGER.error("Problem while executing SQL select statement for reading jockey with id " + id, e);
            throw new PersistenceException("Could not read jockeys with id " + id, e);
        }
        if (jockey != null) {
            return jockey;
        } else {
            throw new NotFoundException("Could not find jockey with id " + id);
        }
    }

    @Override
    public List<Jockey> getAllFiltered(Jockey filter) throws PersistenceException {
        LOGGER.info("Get all jockeys with filter: " + filter);
        String sql = "SELECT * FROM Jockey WHERE NOT obsolete";
        int parameterCount = 0;
        int nameIndex = 0; 
        int skillIndex = 0;
        List<Jockey> jockeys = new ArrayList<Jockey>();

        if (filter.getName() != null) {
            sql += " AND LOWER(name) LIKE LOWER( ? )";
            parameterCount++;
            nameIndex = parameterCount;
        }
        if (filter.getSkill() != null) {
            sql += " AND skill >= ?";
            parameterCount++;
            skillIndex = parameterCount;
        }


        try {
            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql);

            if (filter.getName() != null) {
                statement.setString(nameIndex, "%" + filter.getName() + "%");
            }
            if (filter.getSkill() != null) {
                statement.setDouble(skillIndex, filter.getSkill());
            }

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Jockey jockey = dbResultToJockeyDto(result);
                jockeys.add(jockey);
            }
            return jockeys;
        } catch (SQLException e) {
            LOGGER.error("Problem while executing SQL select statement for reading jockey with id ", e);
            throw new PersistenceException("Could not read jockeys", e);
        }
    }

    @Override
    public Jockey createOne(Jockey jockey) throws PersistenceException {
        LOGGER.info("Create jockey: " + jockey);
        try {
            return getRow(insert(jockey));
        } catch (SQLException | NotFoundException e) {
            LOGGER.error("Problem while executing SQL insert statement for inserting jockey: " + jockey, e);
            throw new PersistenceException("Could not create jockey: " + jockey, e);
        }
    }

    @Override
    public Jockey updateOne(Integer id, Jockey jockey) throws PersistenceException {
        LOGGER.info("Update jockey: " + jockey);
        try {
            obsolete(id);
            return getRow(insert(jockey));
        } catch (SQLException | NotFoundException e) {
            LOGGER.error("Problem while executing SQL update statement for updating jockey with id: " + id, e);
            throw new PersistenceException("Could not update jockey with id" + id, e);
        }
    }

    @Override
    public void deleteOne(Integer id) throws PersistenceException, NotFoundException {
        LOGGER.info("Delete jockey with id: " + id);
        try {
            obsolete(id);
        } catch (SQLException | NotFoundException e) {
            LOGGER.error("Problem while executing SQL delete statement for deleting jockey with id : " + id, e);
            throw new PersistenceException("Could not delete jockey with id" + id, e);
        }
    }
}
