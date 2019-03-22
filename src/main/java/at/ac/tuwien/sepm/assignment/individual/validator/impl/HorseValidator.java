package at.ac.tuwien.sepm.assignment.individual.validator.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.validator.IHorseValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class HorseValidator implements IHorseValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(HorseValidator.class);

    @Autowired
    public HorseValidator() {
    }

    @Override
    public boolean validate(Horse horse) {
      return this.hasValidName(horse);
    }

    private boolean hasValidName(Horse horse) {
      return !horse.getName().isEmpty() && horse.getName() != null;
    }
}
