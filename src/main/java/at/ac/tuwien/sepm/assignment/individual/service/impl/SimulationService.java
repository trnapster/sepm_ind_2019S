package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Jockey;
import at.ac.tuwien.sepm.assignment.individual.entity.Simulation;
import at.ac.tuwien.sepm.assignment.individual.entity.SimulationParticipant;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.IHorseDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.IJockeyDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.ISimulationDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.service.ISimulationService;
import at.ac.tuwien.sepm.assignment.individual.validator.ISimulationValidator;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class SimulationService implements ISimulationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimulationService.class);
    private final ISimulationDao simulationDao;
    private final IHorseDao horseDao;
    private final IJockeyDao jockeyDao;
    private final ISimulationValidator simulationValidator;

    @Autowired
    public SimulationService(ISimulationDao simulationDao, IHorseDao horseDao, 
            IJockeyDao jockeyDao, ISimulationValidator simulationValidator) {
        this.simulationDao = simulationDao;
        this.horseDao = horseDao;
        this.jockeyDao = jockeyDao;
        this.simulationValidator = simulationValidator;
    }

    @Override
    public Simulation findOneById(Integer id) throws ServiceException, NotFoundException {
        LOGGER.info("Read simulation with ID: " + id);
        try {
            return simulationDao.findOneById(id);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Simulation> getAllFiltered(Simulation filter) throws ServiceException {
        LOGGER.info("Read all simulations with filter: " + filter);

        try {
            return simulationDao.getAllFiltered(filter);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Simulation createOne(Simulation newSimulation) throws ServiceException {
        LOGGER.info("Create new simulation: " + newSimulation);
        LocalDateTime currentTime = LocalDateTime.now();
        newSimulation.setId(null);
        newSimulation.setCreated(currentTime);

        try {
            for(SimulationParticipant participant : newSimulation.getSimulationParticipants()) {
                Horse horse = horseDao.findOneById(participant.getHorse().getId());
                Jockey jockey = jockeyDao.findOneById(participant.getJockey().getId());

                Double horseSpeed = calculateHorseSpeed(
                    participant.getLuckFactor(),
                    horse.getMinSpeed(),
                    horse.getMaxSpeed());

                Double skill = calculateSkill(jockey.getSkill());

                Double avgSpeed = calculateAvgSpeed(horseSpeed, skill, participant.getLuckFactor());

                participant.setHorseSpeed(horseSpeed);
                participant.setSkill(skill);
                participant.setAvgSpeed(avgSpeed);
            }

            List<SimulationParticipant> rankedParticipants = newSimulation.getSimulationParticipants()
                .stream()
                .sorted((p1, p2) -> p2.getAvgSpeed().compareTo(p1.getAvgSpeed()))
                .collect(Collectors.toList());

            for (int i = 0; i < rankedParticipants.size(); i++) {
                rankedParticipants.get(i).setRank(i + 1);
            }

            simulationValidator.validate(newSimulation);
            return simulationDao.createOne(newSimulation);
        } catch (NotFoundException | PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private static Double calculateHorseSpeed(Double g, Double pmin, Double pmax) {
        return round(((g - 0.95) * ((pmax - pmin)/(1.05 - 0.95))) + pmin);
    }

    private static Double calculateSkill(Double k) {
        return round(1 + (0.15 * (1.0/Math.PI) * Math.atan((1.0/5.0) * k)));
    }

    private static Double calculateAvgSpeed(Double p, Double k, Double g) {
        return round(p * k * g);
    }

    private static Double round(Double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(4, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
