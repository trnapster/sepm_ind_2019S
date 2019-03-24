package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;

import java.util.List;

public interface IHorseDao {

    /**
     * @param id of the horse to find.
     * @return the horse with the specified id.
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     * @throws NotFoundException    will be thrown if the horse could not be found in the database.
     */
    Horse findOneById(Integer id) throws PersistenceException, NotFoundException;

    /**
     * @param filter the filter that will be applied
     * @return all horses in the database that match the filter. Return all horses if filter is empty.
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    List<Horse> getAllFiltered(Horse filter) throws PersistenceException;

    /**
     * @param horse the horse to be created
     * @return the horse that has been created
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    Horse createOne(Horse horse) throws PersistenceException;

    /**
     * @param horse the new horse
     * @return the horse that has been updated
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    Horse updateOne(Horse horse) throws PersistenceException, NotFoundException;

    /**
     * @param id of the horse that will be deleted
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    void deleteOne(Integer id) throws PersistenceException, NotFoundException;
}
