package at.ac.tuwien.sepm.assignment.individual.validator.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Jockey;
import at.ac.tuwien.sepm.assignment.individual.validator.IJockeyValidator;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JockeyValidator implements IJockeyValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(JockeyValidator.class);

    public JockeyValidator() {
    }

    @Override
    public void validate(Jockey jockey) throws ServiceException {
        validateName(jockey);
        validateSkill(jockey);
    }

    private void validateName(Jockey jockey) throws ServiceException {
        if (jockey.getName() == null || jockey.getName().isEmpty()) {
            throw new ServiceException("Name must be set");
        }
    }

    private void validateSkill(Jockey jockey) throws ServiceException {
        if (jockey.getSkill() == null) {
            throw new ServiceException("Skill must be set");
        }
    }
}
