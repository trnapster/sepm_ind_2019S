package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.rest.dto.SimulationResponseDto;
import at.ac.tuwien.sepm.assignment.individual.rest.dto.SimulationRequestDto;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.service.ISimulationService;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.util.mapper.SimulationMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/simulations")
public class SimulationEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimulationEndpoint.class);
    private static final String BASE_URL = "/api/v1/simulations";
    private final ISimulationService simulationService;
    private final SimulationMapper simulationMapper;

    @Autowired
    public SimulationEndpoint(ISimulationService simulationService, SimulationMapper simulationMapper) {
        this.simulationService = simulationService;
        this.simulationMapper = simulationMapper;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public SimulationResponseDto getOneById(@PathVariable("id") Integer id) {
        LOGGER.info("GET " + BASE_URL + "/" + id);
        try {
            return simulationMapper.entityToDto(simulationService.findOneById(id));
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error during read simulation with ID: " + id, e);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Error during reading simulation: " + e.getMessage(), e);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<SimulationResponseDto> getAllFiltered(@RequestParam Optional<String> name) {
        LOGGER.info("GET " + BASE_URL);
        String actualName = name.orElse(null);

        SimulationRequestDto filter = new SimulationRequestDto(
            actualName, 
            null,
            null);

        try {
            return simulationMapper.entityToDto(simulationService.getAllFiltered(
                  simulationMapper.dtoToEntity(filter)));
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error during read simulations" , e);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public SimulationResponseDto createOne(@RequestBody SimulationRequestDto newSimulation) {
        LOGGER.info("POST " + BASE_URL + "/ Body: " + newSimulation);
        try {
            return simulationMapper.entityToDto(simulationService.createOne(simulationMapper.dtoToEntity(newSimulation)));
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                "Error during saving simulation: " + e.getMessage(), e);
        }
    }
}
