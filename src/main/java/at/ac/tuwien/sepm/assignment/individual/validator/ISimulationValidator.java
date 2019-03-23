package at.ac.tuwien.sepm.assignment.individual.validator;

import at.ac.tuwien.sepm.assignment.individual.entity.Simulation;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;

public interface ISimulationValidator {

    /**
     * @param simulation to be validated
     * @throws ServiceException if the simulation is invalid
     */
    void validate(Simulation simulation) throws ServiceException;
}
