package at.ac.tuwien.sepm.assignment.individual.validator.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Simulation;
import at.ac.tuwien.sepm.assignment.individual.validator.ISimulationValidator;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SimulationValidator implements ISimulationValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimulationValidator.class);

    public SimulationValidator() {
    }

    @Override
    public void validate(Simulation simulation) throws ServiceException {
    }
}
