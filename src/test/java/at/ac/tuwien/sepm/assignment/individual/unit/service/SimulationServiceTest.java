package at.ac.tuwien.sepm.assignment.individual.unit.service;

import at.ac.tuwien.sepm.assignment.individual.persistence.ISimulationDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.IHorseDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.IJockeyDao;
import at.ac.tuwien.sepm.assignment.individual.service.ISimulationService;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Jockey;
import at.ac.tuwien.sepm.assignment.individual.entity.Simulation;
import at.ac.tuwien.sepm.assignment.individual.entity.SimulationParticipant;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class SimulationServiceTest {
    private static final Horse testHorse = new Horse(null,null,null,40.2344,50.6327,null,null);
    private static final Jockey testJockey = new Jockey(null,null,200.1238,null,null);
    private static final SimulationParticipant testParticipant = new SimulationParticipant(null,null,testHorse,testJockey,null,null,null,0.9824);
    private static final List<SimulationParticipant> testParticipants = Arrays.asList(testParticipant); 
    private static final Simulation testSimulation = new Simulation(null,null,null,testParticipants);
    
    @Autowired
    ISimulationService simulationService;

    @MockBean
    ISimulationDao simulationDao;

    @MockBean
    IHorseDao horseDao;

    @MockBean
    IJockeyDao jockeyDao;

    @Test
    public void whenCreateOne_thenCalculateCorrectHorseSpeed() throws ServiceException, PersistenceException, NotFoundException {
        when(horseDao.findOneById(null)).thenReturn(testHorse);
        when(jockeyDao.findOneById(null)).thenReturn(testJockey);
        simulationService.createOne(testSimulation);
        verify(simulationDao).createOne(argThat((Simulation simulation) -> 
              simulation.getSimulationParticipants().get(0).getHorseSpeed() == 43.6034));
    }

    @Test
    public void whenCreateOne_thenCalculateCorrectSkill() throws ServiceException, PersistenceException, NotFoundException {
        when(horseDao.findOneById(null)).thenReturn(testHorse);
        when(jockeyDao.findOneById(null)).thenReturn(testJockey);
        simulationService.createOne(testSimulation);
        verify(simulationDao).createOne(argThat((Simulation simulation) -> 
              simulation.getSimulationParticipants().get(0).getSkill() == 1.0738));
    }

    @Test
    public void whenCreateOne_thenCalculateCorrectAvgSpeed() throws ServiceException, PersistenceException, NotFoundException {
        when(horseDao.findOneById(null)).thenReturn(testHorse);
        when(jockeyDao.findOneById(null)).thenReturn(testJockey);
        simulationService.createOne(testSimulation);
        verify(simulationDao).createOne(argThat((Simulation simulation) -> 
              simulation.getSimulationParticipants().get(0).getAvgSpeed() == 45.9973));
    }
}
