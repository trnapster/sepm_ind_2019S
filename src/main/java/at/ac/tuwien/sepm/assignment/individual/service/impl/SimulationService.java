package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Simulation;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
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

@Service
public class SimulationService implements ISimulationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimulationService.class);
    private final ISimulationDao simulationDao;
    private final ISimulationValidator simulationValidator;

    @Autowired
    public SimulationService(ISimulationDao simulationDao, ISimulationValidator simulationValidator) {
        this.simulationDao = simulationDao;
        this.simulationValidator = simulationValidator;
    }

    /*
    @Override
    public Simulation findOneById(Integer id) throws ServiceException, NotFoundException {
        LOGGER.info("Get simulation with id " + id);
        try {
            return simulationDao.findOneById(id);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    */

    @Override
    public List<Simulation> getAllFiltered(Simulation filter) throws ServiceException {
        LOGGER.info("Get all simulations with filter: " + filter);

        try {
            return simulationDao.getAllFiltered(filter);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /*
    @Override
    public Simulation createOne(Simulation newSimulation) throws ServiceException {
        LocalDateTime currentTime = LocalDateTime.now();
        newSimulation.setId(null);
        newSimulation.setCreated(currentTime);
        newSimulation.setUpdated(currentTime);

        try {
            simulationValidator.validate(newSimulation);
            return simulationDao.createOne(newSimulation);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Simulation updateOne(Integer id, Simulation updatedSimulation) throws ServiceException {
        LocalDateTime currentTime = LocalDateTime.now();
        updatedSimulation.setUpdated(currentTime);

        try {
            Simulation oldSimulation = simulationDao.findOneById(id);
            updatedSimulation.setId(oldSimulation.getId());

            if (updatedSimulation.getName() == null) {
                updatedSimulation.setName(oldSimulation.getName());
            }
            if (updatedSimulation.getSkill() == null) {
                updatedSimulation.setSkill(oldSimulation.getSkill());
            }
            updatedSimulation.setCreated(oldSimulation.getCreated());
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (NotFoundException e) {
            LOGGER.info("No simulation with id " + id);
        }

        try {
            simulationValidator.validate(updatedSimulation);
            return simulationDao.updateOne(id, updatedSimulation);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteOne(Integer id) throws ServiceException, NotFoundException {
        try {
            simulationDao.deleteOne(id);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    */
}