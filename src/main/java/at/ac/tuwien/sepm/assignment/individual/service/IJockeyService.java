package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.entity.Jockey;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;

import java.util.List;

public interface IJockeyService {

    /**
     * @param id of the jockey to find.
     * @return the jockey with the specified id.
     * @throws ServiceException  will be thrown if something goes wrong during data processing.
     * @throws NotFoundException will be thrown if the jockey could not be found in the system.
     */
    Jockey findOneById(Integer id) throws ServiceException, NotFoundException;
    
    /**
     * @param filter the filter that will be applied
     * @return all jockeys in the system that match the filter
     * @throws ServiceException  will be thrown if something goes wrong during data processing.
     */
    List<Jockey> getAllFiltered(Jockey filter) throws ServiceException;

    /**
     * @param newJockey the jockey that will be created
     * @return the jockey that has been created
     * @throws ServiceException  will be thrown if something goes wrong during data processing.
     */
    Jockey createOne(Jockey jockey) throws ServiceException;

    /**
     * @param id of the old jockey
     * @param updatedJockey the new jockey
     * @return the updated jockey
     * @throws ServiceException  will be thrown if something goes wrong during data processing.
     */
    Jockey updateOne(Integer id, Jockey updatedJockey) throws ServiceException;

    /**
     * @param id of jockey that should be deleted
     * @throws ServiceException  will be thrown if something goes wrong during data processing.
     * @throws NotFoundException will be thrown if the jockey could not be found in the system.
     */
    void deleteOne(Integer id) throws ServiceException, NotFoundException;
}
