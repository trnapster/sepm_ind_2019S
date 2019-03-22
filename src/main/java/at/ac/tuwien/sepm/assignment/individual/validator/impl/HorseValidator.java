package at.ac.tuwien.sepm.assignment.individual.validator.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.validator.IHorseValidator;
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
    public boolean validate(Horse horse) {
      return this.hasValidName(horse) && this.hasValidSpeed(horse);
    }

    private boolean hasValidName(Horse horse) {
      return !horse.getName().isEmpty() && horse.getName() != null;
    }

    private boolean hasValidSpeed(Horse horse) {
        return horse.getMinSpeed() >= MINSPEED && horse.getMaxSpeed() >= MINSPEED
            && horse.getMinSpeed() <= MAXSPEED && horse.getMaxSpeed() <= MAXSPEED
            && horse.getMinSpeed() <= horse.getMaxSpeed();
    }
}
