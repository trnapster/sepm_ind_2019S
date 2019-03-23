package at.ac.tuwien.sepm.assignment.individual.validator;

import at.ac.tuwien.sepm.assignment.individual.entity.Jockey;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;

public interface IJockeyValidator {

    /**
     * @param jockey to be validated
     * @throws ServiceException if the jockey is invalid
     */
    void validate(Jockey jockey) throws ServiceException;
}
