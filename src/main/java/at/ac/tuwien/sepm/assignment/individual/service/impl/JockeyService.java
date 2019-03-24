package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Jockey;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.IJockeyDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.service.IJockeyService;
import at.ac.tuwien.sepm.assignment.individual.validator.IJockeyValidator;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JockeyService implements IJockeyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JockeyService.class);
    private final IJockeyDao jockeyDao;
    private final IJockeyValidator jockeyValidator;

    @Autowired
    public JockeyService(IJockeyDao jockeyDao, IJockeyValidator jockeyValidator) {
        this.jockeyDao = jockeyDao;
        this.jockeyValidator = jockeyValidator;
    }

    @Override
    public Jockey findOneById(Integer id) throws ServiceException, NotFoundException {
        LOGGER.info("Get jockey with id " + id);
        try {
            return jockeyDao.findOneById(id);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Jockey> getAllFiltered(Jockey filter) throws ServiceException {
        LOGGER.info("Get all jockeys with filter: " + filter);

        try {
            return jockeyDao.getAllFiltered(filter);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Jockey createOne(Jockey newJockey) throws ServiceException {
        LocalDateTime currentTime = LocalDateTime.now();
        newJockey.setId(null);
        newJockey.setCreated(currentTime);
        newJockey.setUpdated(currentTime);

        try {
            jockeyValidator.validate(newJockey);
            return jockeyDao.createOne(newJockey);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Jockey updateOne(Integer id, Jockey updatedJockey) throws ServiceException {
        LocalDateTime currentTime = LocalDateTime.now();
        updatedJockey.setUpdated(currentTime);

        try {
            Jockey oldJockey = jockeyDao.findOneById(id);
            updatedJockey.setId(id);

            if (updatedJockey.getName() == null) {
                updatedJockey.setName(oldJockey.getName());
            }
            if (updatedJockey.getSkill() == null) {
                updatedJockey.setSkill(oldJockey.getSkill());
            }
            updatedJockey.setCreated(oldJockey.getCreated());
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (NotFoundException e) {
            LOGGER.info("No jockey with id " + id);
        }

        try {
            jockeyValidator.validate(updatedJockey);
            return jockeyDao.updateOne(updatedJockey);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteOne(Integer id) throws ServiceException, NotFoundException {
        try {
            jockeyDao.deleteOne(id);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
