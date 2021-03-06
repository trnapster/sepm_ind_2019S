package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Jockey;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;

import java.util.List;

public interface IJockeyDao {

    /**
     * @param id of the jockey to find.
     * @return the jockey with the specified id.
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     * @throws NotFoundException    will be thrown if the jockey could not be found in the database.
     */
    Jockey findOneById(Integer id) throws PersistenceException, NotFoundException;

    /**
     * @param filter the filter that will be applied
     * @return all jockeys in the database that match the filter
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    List<Jockey> getAllFiltered(Jockey filter) throws PersistenceException;

    /**
     * @param jockey the jockey to be created
     * @return the jockey that has been created
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    Jockey createOne(Jockey jockey) throws PersistenceException;

    /**
     * @param jockey the new jockey
     * @return the jockey that has been updated
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     * @throws NotFoundException    will be thrown if the jockey could not be found in the database.
     */
    Jockey updateOne(Jockey jockey) throws PersistenceException, NotFoundException;

    /**
     * @param id of the jockey that will be deleted
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     * @throws NotFoundException    will be thrown if the jockey could not be found in the database.
     */
    void deleteOne(Integer id) throws PersistenceException, NotFoundException;
}
