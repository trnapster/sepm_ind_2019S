package at.ac.tuwien.sepm.assignment.individual.unit.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.IHorseDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.util.DBConnectionManager;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class HorseDaoTest {

    private static final LocalDateTime testTime = LocalDateTime.now();
    private static final Horse validTestHorse = new Horse(null,"testhorse","breed",40.2344,50.6327,testTime,testTime);
    private static final Horse invalidTestHorse = new Horse(null,null,"breed",40.2344,50.6327,testTime,testTime);


    @Autowired
    IHorseDao horseDao;
    @Autowired
    DBConnectionManager dbConnectionManager;

    /**
     * It is important to close the database connection after each test in order to clean the in-memory database
     */
    @After
    public void afterEachTest() throws PersistenceException {
        dbConnectionManager.closeConnection();
    }

    @Test(expected = NotFoundException.class)
    public void givenNothing_whenFindHorseByIdWhichNotExists_thenNotFoundException()
        throws PersistenceException, NotFoundException {
        horseDao.findOneById(1);
    }

    @Test
    public void givenValidHorse_whenCreateHorse_thenInsertHorseIntoDatabase()
        throws PersistenceException {
        Horse dbHorse = horseDao.createOne(validTestHorse);
        assertEquals((Integer) 1, dbHorse.getId());
        assertEquals(validTestHorse.getName(), dbHorse.getName());
        assertEquals(validTestHorse.getBreed(), dbHorse.getBreed());
        assertEquals(validTestHorse.getMinSpeed(), dbHorse.getMinSpeed());
        assertEquals(validTestHorse.getMaxSpeed(), dbHorse.getMaxSpeed());
        assertEquals(validTestHorse.getCreated(), dbHorse.getCreated());
        assertEquals(validTestHorse.getUpdated(), dbHorse.getUpdated());
    }

    @Test(expected = PersistenceException.class)
    public void givenInvalidHorse_whenCreateHorse_thenPersistenceException()
        throws PersistenceException {
        Horse dbHorse = horseDao.createOne(invalidTestHorse);
    }
}

