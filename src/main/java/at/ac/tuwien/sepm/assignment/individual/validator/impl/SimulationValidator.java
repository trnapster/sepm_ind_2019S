package at.ac.tuwien.sepm.assignment.individual.validator.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Jockey;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Simulation;
import at.ac.tuwien.sepm.assignment.individual.entity.SimulationParticipant;
import at.ac.tuwien.sepm.assignment.individual.validator.ISimulationValidator;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

@Component
public class SimulationValidator implements ISimulationValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimulationValidator.class);

    private static final Double MINLUCKFACTOR = 0.95;
    private static final Double MAXLUCKFACTOR = 1.05;

    public SimulationValidator() {
    }

    @Override
    public void validate(Simulation simulation) throws ServiceException {
        validateHorses(simulation.getSimulationParticipants());
        validateJockeys(simulation.getSimulationParticipants());
        validateLuckFactor(simulation.getSimulationParticipants());
    }
    
    private void validateHorses(List<SimulationParticipant> participants) throws ServiceException {
        Set<Horse> horses = new HashSet<Horse>();
        for(SimulationParticipant participant : participants) {
            boolean success = horses.add(participant.getHorse());
            if (!success) throw new ServiceException("Horses can't be in the same Simulation twice");
        }
    }

    private void validateJockeys(List<SimulationParticipant> participants) throws ServiceException {
        Set<Jockey> jockeys = new HashSet<Jockey>();
        for(SimulationParticipant participant : participants) {
            boolean success = jockeys.add(participant.getJockey());
            if (!success) throw new ServiceException("Jockey can't be in the same Simulation twice");
        }
    }

    private void validateLuckFactor(List<SimulationParticipant> participants) throws ServiceException {
        for(SimulationParticipant participant : participants) {
            if (participant.getLuckFactor().compareTo(MINLUCKFACTOR) < 0
                    || participant.getLuckFactor().compareTo(MAXLUCKFACTOR) > 0) {
                throw new ServiceException("value of luckFactor has to be between "
                    + MINLUCKFACTOR + " and " + MAXLUCKFACTOR);
            }
        }
    }
}
