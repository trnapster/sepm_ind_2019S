package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Jockey;
import at.ac.tuwien.sepm.assignment.individual.entity.Simulation;
import at.ac.tuwien.sepm.assignment.individual.entity.SimulationParticipant;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.ISimulationDao;
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
import java.time.LocalDateTime;


@Repository
public class SimulationDao implements ISimulationDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimulationDao.class);
    private final DBConnectionManager dbConnectionManager;

    @Autowired
    public SimulationDao(DBConnectionManager dbConnectionManager) {
        this.dbConnectionManager = dbConnectionManager;
    }

    private static Simulation dbResultToSimulationDto(ResultSet result, boolean returnParticipants) throws SQLException {
        Integer id = result.getInt("id");
        String name = result.getString("name");
        LocalDateTime created = result.getTimestamp("created").toLocalDateTime();
        List<SimulationParticipant> simulationParticipants = null;

        if (returnParticipants) {
            simulationParticipants = new ArrayList<SimulationParticipant>();
            do {
                Horse horse = new Horse(
                    null,
                    result.getString("horse_name"),
                    null,
                    null,
                    null,
                    null,
                    null);

                Jockey jockey = new Jockey(
                    null,
                    result.getString("jockey_name"),
                    null,
                    null,
                    null);

                SimulationParticipant simulationParticipant = new SimulationParticipant(
                    result.getInt("participant_id"),
                    result.getInt("rank"),
                    horse,
                    jockey,
                    result.getBigDecimal("avg_speed"),
                    result.getBigDecimal("horse_speed"),
                    result.getBigDecimal("skill"),
                    result.getDouble("luck_factor"));
                simulationParticipants.add(simulationParticipant);
            } while(result.next());
        }

        return new Simulation(
            id,
            name,
            created,
            simulationParticipants);
    }
    
    /*

    private int insert(Simulation simulation) throws PersistenceException {
        String sql = "INSERT INTO Simulation "
            + "(public_id, name, skill, created, updated)"
            + "values(?,?,?,?,?)";

        try {
            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            if (simulation.getId() != null) {
                statement.setInt(1, simulation.getId());
            }
            else {
                statement.setNull(1, Types.INTEGER);
            }
            statement.setString(2, simulation.getName());
            statement.setDouble(3, simulation.getSkill());
            statement.setTimestamp(4, Timestamp.valueOf(simulation.getCreated()));
            statement.setTimestamp(5, Timestamp.valueOf(simulation.getUpdated()));
            int rows = statement.executeUpdate();

            if (rows == 0) throw new PersistenceException("No new rows generated");

            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }
            else {
                throw new PersistenceException("No ID obtained");
            }
        } catch (SQLException e) {
            LOGGER.error("Problem while executing SQL insert statement for inserting simulation: " + simulation, e);
            throw new PersistenceException("Could not create simulation: " + simulation, e);
        }
    }

    private Simulation getRow(int rowId) throws PersistenceException, SQLException, NotFoundException {
        String sql = "SELECT * FROM Simulation WHERE id=?";
        Simulation simulation = null;

        PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql);
        statement.setInt(1, rowId);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            simulation = dbResultToSimulationDto(result);
        }

        if (simulation != null) {
            return simulation;
        } else {
            throw new NotFoundException("Could not find simulation with id " + rowId);
        }
    }

    private void obsolete(int id) throws PersistenceException, SQLException, NotFoundException {
        String sql = "UPDATE Simulation SET obsolete = TRUE WHERE public_id = ? AND NOT obsolete";

        PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql);
        statement.setInt(1, id);
        int rows = statement.executeUpdate();

        if (rows == 0) throw new NotFoundException("Could not delete simulation with id: " + id);
    }
    */

    @Override
    public Simulation findOneById(Integer id) throws PersistenceException, NotFoundException {
        LOGGER.info("Get simulation with id " + id);
        String sql = "SELECT simulation.id id, simulation.name name," 
            + " simulation.created created, simulation_participant.id participant_id,"
            + " simulation_participant.rank rank, horse.name horse_name,"
            + " jockey.name jockey_name, simulation_participant.avg_speed avg_speed," 
            + " simulation_participant.horse_speed horse_speed, simulation_participant.skill skill," 
            + " simulation_participant.luck_factor luck_factor"
            + " FROM simulation_participant"
            + " JOIN horse ON horse.id=simulation_participant.horse_id"
            + " JOIN jockey ON jockey.id=simulation_participant.jockey_id"
            + " RIGHT JOIN simulation ON simulation.id=simulation_participant.simulation_id"
            + " WHERE simulation.id = ?"
            + " ORDER BY rank ASC";
        Simulation simulation = null;
        try {
            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                simulation = dbResultToSimulationDto(result, true);
            }
        } catch (SQLException e) {
            LOGGER.error("Problem while executing SQL select statement for reading simulation with id " + id, e);
            throw new PersistenceException("Could not read simulations with id " + id, e);
        }
        if (simulation != null) {
            return simulation;
        } else {
            throw new NotFoundException("Could not find simulation with id " + id);
        }
    }

    @Override
    public List<Simulation> getAllFiltered(Simulation filter) throws PersistenceException {
        LOGGER.info("Get all simulations with filter: " + filter);
        String sql = "SELECT * FROM Simulation";
        List<Simulation> simulations = new ArrayList<Simulation>();

        if (filter.getName() != null) {
            sql += " AND LOWER(name) LIKE LOWER( ? )";
        }

        try {
            PreparedStatement statement = dbConnectionManager.getConnection().prepareStatement(sql);

            if (filter.getName() != null) {
                statement.setString(1, "%" + filter.getName() + "%");
            }

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Simulation simulation = dbResultToSimulationDto(result, false);
                simulations.add(simulation);
            }
            return simulations;
        } catch (SQLException e) {
            LOGGER.error("Problem while executing SQL select statement for reading simulation with id ", e);
            throw new PersistenceException("Could not read simulations", e);
        }
    }

    /*
    @Override
    public Simulation createOne(Simulation simulation) throws PersistenceException {
        LOGGER.info("Create simulation: " + simulation);
        try {
            return getRow(insert(simulation));
        } catch (SQLException | NotFoundException e) {
            LOGGER.error("Problem while executing SQL insert statement for inserting simulation: " + simulation, e);
            throw new PersistenceException("Could not create simulation: " + simulation, e);
        }
    }

    @Override
    public Simulation updateOne(Integer id, Simulation simulation) throws PersistenceException {
        LOGGER.info("Update simulation: " + simulation);
        try {
            obsolete(id);
            return getRow(insert(simulation));
        } catch (SQLException | NotFoundException e) {
            LOGGER.error("Problem while executing SQL update statement for updating simulation with id: " + id, e);
            throw new PersistenceException("Could not update simulation with id" + id, e);
        }
    }

    @Override
    public void deleteOne(Integer id) throws PersistenceException, NotFoundException {
        LOGGER.info("Delete simulation with id: " + id);
        try {
            obsolete(id);
        } catch (SQLException | NotFoundException e) {
            LOGGER.error("Problem while executing SQL delete statement for deleting simulation with id : " + id, e);
            throw new PersistenceException("Could not delete simulation with id" + id, e);
        }
    }
    */
}
