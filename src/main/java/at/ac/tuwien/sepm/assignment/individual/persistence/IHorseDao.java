package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;

public interface IHorseDao {

    /**
     * @param id of the horse to find.
     * @return the horse with the specified id.
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     * @throws NotFoundException    will be thrown if the horse could not be found in the database.
     */
    Horse findOneById(Integer id) throws PersistenceException, NotFoundException;

    /**
     * @param horse the horse to be created
     * @return the horse that has been created
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    Horse createOne(Horse horse) throws PersistenceException;

    /**
     * @param id of the old horse
     * @param horse the new horse
     * @return the horse that has been updated
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    Horse updateOne(Integer id, Horse horse) throws PersistenceException;
}
