package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.entity.Simulation;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;

import java.util.List;

public interface ISimulationService {

    /**
     * @param id of the simulation to find.
     * @return the simulation with the specified id.
     * @throws ServiceException  will be thrown if something goes wrong during data processing.
     * @throws NotFoundException will be thrown if the simulation could not be found in the system.
     */
    Simulation findOneById(Integer id) throws ServiceException, NotFoundException;
    
    /**
     * @param filter the filter that will be applied
     * @return all simulations in the system that match the filter. If filter is empty return all simulations.
     * @throws ServiceException  will be thrown if something goes wrong during data processing.
     */
    List<Simulation> getAllFiltered(Simulation filter) throws ServiceException;

    /**
     * @param newSimulation the simulation that will be created
     * @return the simulation that has been created
     * @throws ServiceException  will be thrown if something goes wrong during data processing.
     */
    //Simulation createOne(Simulation simulation) throws ServiceException;
}
