package at.ac.tuwien.sepm.assignment.individual.validator;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;

public interface IHorseValidator {

    /**
     * @param horse to be validated
     * @return if the horse is valid
     */
    boolean validate(Horse horse);
}
