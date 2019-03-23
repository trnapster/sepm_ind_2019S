package at.ac.tuwien.sepm.assignment.individual.validator;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;

public interface IHorseValidator {

    /**
     * @param horse to be validated
     * @throws ServiceException if the horse is invalid
     */
    void validate(Horse horse) throws ServiceException;
}
