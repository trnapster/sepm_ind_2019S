package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.IHorseDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.service.IHorseService;
import at.ac.tuwien.sepm.assignment.individual.validator.IHorseValidator;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HorseService implements IHorseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HorseService.class);
    private final IHorseDao horseDao;
    private final IHorseValidator horseValidator;

    @Autowired
    public HorseService(IHorseDao horseDao, IHorseValidator horseValidator) {
        this.horseDao = horseDao;
        this.horseValidator = horseValidator;
    }

    @Override
    public Horse findOneById(Integer id) throws ServiceException, NotFoundException {
        LOGGER.info("Read horse with ID: " + id);
        try {
            return horseDao.findOneById(id);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Horse> getAllFiltered(Horse filter) throws ServiceException {
        LOGGER.info("Read all horses with filter: " + filter);

        try {
            return horseDao.getAllFiltered(filter);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Horse createOne(Horse newHorse) throws ServiceException {
        LOGGER.info("Create new horse: " + newHorse);
        LocalDateTime currentTime = LocalDateTime.now();
        newHorse.setId(null);
        newHorse.setCreated(currentTime);
        newHorse.setUpdated(currentTime);

        try {
            horseValidator.validate(newHorse);
            return horseDao.createOne(newHorse);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Horse updateOne(Integer id, Horse updatedHorse) throws ServiceException, NotFoundException {
        LOGGER.info("Update horse with ID: " + id + "with horse: " + updatedHorse);
        LocalDateTime currentTime = LocalDateTime.now();
        updatedHorse.setUpdated(currentTime);

        try {
            Horse oldHorse = horseDao.findOneById(id);
            updatedHorse.setId(id);

            if (updatedHorse.getName() == null) {
                updatedHorse.setName(oldHorse.getName());
            }
            if (updatedHorse.getBreed() == null) {
                updatedHorse.setBreed(oldHorse.getBreed());
            }
            if (updatedHorse.getMinSpeed() == null) {
                updatedHorse.setMinSpeed(oldHorse.getMinSpeed());
            }
            if (updatedHorse.getMaxSpeed() == null) {
                updatedHorse.setMaxSpeed(oldHorse.getMaxSpeed());
            }
            updatedHorse.setCreated(oldHorse.getCreated());

            horseValidator.validate(updatedHorse);
            return horseDao.updateOne(updatedHorse);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteOne(Integer id) throws ServiceException, NotFoundException {
        LOGGER.info("Delete horse with ID: " + id);
        try {
            horseDao.deleteOne(id);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
