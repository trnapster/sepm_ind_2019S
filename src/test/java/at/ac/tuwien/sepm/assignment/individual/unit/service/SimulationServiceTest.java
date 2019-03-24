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
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class SimulationServiceTest {
    private static final Horse testHorse1 = new Horse(null,null,null,40.2344,50.6327,null,null);
    private static final Horse testHorse2 = new Horse(null,null,null,50.2344,55.1623,null,null);
    private static final Horse testHorse3 = new Horse(null,null,null,42.2344,59.1243,null,null);
    private static final Jockey testJockey1 = new Jockey(null,null,523.1238,null,null);
    private static final Jockey testJockey2 = new Jockey(null,null,13.2351,null,null);
    private static final Jockey testJockey3 = new Jockey(null,null,52.6213,null,null);
    private static final SimulationParticipant testParticipant1 = new SimulationParticipant(null,null,testHorse1,testJockey1,null,null,null,0.9824);
    private static final SimulationParticipant testParticipant2 = new SimulationParticipant(null,null,testHorse2,testJockey2,null,null,null,1.0451);
    private static final SimulationParticipant testParticipant3 = new SimulationParticipant(null,null,testHorse3,testJockey3,null,null,null,0.9513);
    private static final List<SimulationParticipant> testParticipants = Arrays.asList(testParticipant1,testParticipant2,testParticipant3); 
    private static final Simulation testSimulation = new Simulation(null,null,null,testParticipants);
    
    @Autowired
    ISimulationService simulationService;

    @MockBean
    ISimulationDao simulationDao;

    @MockBean
    IHorseDao horseDao;

    @MockBean
    IJockeyDao jockeyDao;

    @Before
    public void beforeEach() throws PersistenceException, NotFoundException {
        when(horseDao.findOneById(null)).thenAnswer(new Answer() {
            private int i = 0;

            public Horse answer(InvocationOnMock invocation) {
                i++;
                if (i == 1) {
                    return testHorse1;
                }
                else if (i == 2) {
                    return testHorse2;
                }
                else {
                    return testHorse3;
                }
            }
        });

        when(jockeyDao.findOneById(null)).thenAnswer(new Answer() {
            private int i = 0;

            public Jockey answer(InvocationOnMock invocation) {
                i++;
                if (i == 1) {
                    return testJockey1;
                }
                else if (i == 2) {
                    return testJockey2;
                }
                else {
                    return testJockey3;
                }
            }
        });
    }

    @Test
    public void whenCreateOne_thenCalculateCorrectHorseSpeed() throws ServiceException, PersistenceException {
        simulationService.createOne(testSimulation);
        verify(simulationDao).createOne(argThat((Simulation simulation) -> 
              (simulation.getSimulationParticipants().get(0).getHorseSpeed() == 43.6034) &&
              (simulation.getSimulationParticipants().get(1).getHorseSpeed() == 54.9208) &&
              (simulation.getSimulationParticipants().get(2).getHorseSpeed() == 42.454)));
    }

    @Test
    public void whenCreateOne_thenCalculateCorrectSkill() throws ServiceException, PersistenceException {
        simulationService.createOne(testSimulation);
        verify(simulationDao).createOne(argThat((Simulation simulation) -> 
              (simulation.getSimulationParticipants().get(0).getSkill() == 1.0745) &&
              (simulation.getSimulationParticipants().get(1).getSkill() == 1.0578) &&
              (simulation.getSimulationParticipants().get(2).getSkill() == 1.0705)));
    }

    @Test
    public void whenCreateOne_thenCalculateCorrectAvgSpeed() throws ServiceException, PersistenceException {
        simulationService.createOne(testSimulation);
        verify(simulationDao).createOne(argThat((Simulation simulation) -> 
              (simulation.getSimulationParticipants().get(0).getAvgSpeed() == 46.0273) &&
              (simulation.getSimulationParticipants().get(1).getAvgSpeed() == 60.7153) &&
              (simulation.getSimulationParticipants().get(2).getAvgSpeed() == 43.2337)));
    }

    @Test
    public void whenCreateOne_setCorrectRank() throws ServiceException, PersistenceException {
        simulationService.createOne(testSimulation);
        verify(simulationDao).createOne(argThat((Simulation simulation) -> 
              (simulation.getSimulationParticipants().get(0).getRank() == 2) &&
              (simulation.getSimulationParticipants().get(1).getRank() == 1) &&
              (simulation.getSimulationParticipants().get(2).getRank() == 3)));
    }
}
