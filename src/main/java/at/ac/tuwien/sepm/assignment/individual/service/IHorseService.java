package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;

import java.util.List;

public interface IHorseService {

    /**
     * @param id of the horse to find.
     * @return the horse with the specified id.
     * @throws ServiceException  will be thrown if something goes wrong during data processing.
     * @throws NotFoundException will be thrown if the horse could not be found in the system.
     */
    Horse findOneById(Integer id) throws ServiceException, NotFoundException;

    /**
     * @return all horses in the system
     * @throws ServiceException  will be thrown if something goes wrong during data processing.
     */
    List<Horse> getAll() throws ServiceException;

    /**
     * @param newHorse the horse that will be created
     * @return the horse that has been created
     * @throws ServiceException  will be thrown if something goes wrong during data processing.
     */
    Horse createOne(Horse horse) throws ServiceException;

    /**
     * @param id of the old horse
     * @param updatedHorse the new horse
     * @return the updated horse
     * @throws ServiceException  will be thrown if something goes wrong during data processing.
     */
    Horse updateOne(Integer id, Horse updatedHorse) throws ServiceException;

    /**
     * @param id of horse that should be deleted
     * @throws ServiceException  will be thrown if something goes wrong during data processing.
     * @throws NotFoundException will be thrown if the horse could not be found in the system.
     */
    void deleteOne(Integer id) throws ServiceException, NotFoundException;
}
