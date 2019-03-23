package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Simulation;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;

import java.util.List;

public interface ISimulationDao {

    /**
     * @param id of the simulation to find.
     * @return the simulation with the specified id.
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     * @throws NotFoundException    will be thrown if the simulation could not be found in the database.
     */
    //Simulation findOneById(Integer id) throws PersistenceException, NotFoundException;

    /**
     * @param filter the filter that will be applied
     * @return all simulations in the database that match the filter
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    List<Simulation> getAllFiltered(Simulation filter) throws PersistenceException;

    /**
     * @param simulation the simulation to be created
     * @return the simulation that has been created
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    //Simulation createOne(Simulation simulation) throws PersistenceException;
}
