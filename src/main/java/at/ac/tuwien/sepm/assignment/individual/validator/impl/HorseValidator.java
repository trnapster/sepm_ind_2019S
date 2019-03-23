package at.ac.tuwien.sepm.assignment.individual.validator.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.validator.IHorseValidator;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HorseValidator implements IHorseValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(HorseValidator.class);

    private static final int MINSPEED = 40;
    private static final int MAXSPEED = 60;

    public HorseValidator() {
    }

    @Override
    public void validate(Horse horse) throws ServiceException {
        validateName(horse);
        validateSpeed(horse);
    }

    private void validateName(Horse horse) throws ServiceException {
      if (horse.getName() != null && horse.getName().isEmpty()) {
          throw new ServiceException("Name must be set");
      }
    }

    private void validateSpeed(Horse horse) throws ServiceException {
        String message = null;
        if (horse.getMinSpeed() == null) {
            throw new ServiceException("Minimum Speed must be set");
        }
        if (horse.getMaxSpeed() == null) {
            throw new ServiceException("Maximum Speed must be set");
        }
        if (horse.getMinSpeed() < MINSPEED || horse.getMinSpeed() > MAXSPEED) {
            throw new ServiceException("Minimum Speed has to be between " + MINSPEED + " and " + MAXSPEED);
        }
        if (horse.getMaxSpeed() < MINSPEED || horse.getMaxSpeed() > MAXSPEED) {
            throw new ServiceException("Minimum Speed has to be between " + MINSPEED + " and " + MAXSPEED);
        }
        if (horse.getMinSpeed() > horse.getMaxSpeed()) {
            throw new ServiceException("Minimum Speed has to be smaller than Maximum Speed");
        }
    }
}
