package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.rest.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.service.IHorseService;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.util.mapper.HorseMapper;

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
@RequestMapping("/api/v1/horses")
public class HorseEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(HorseEndpoint.class);
    private static final String BASE_URL = "/api/v1/horses";
    private final IHorseService horseService;
    private final HorseMapper horseMapper;

    @Autowired
    public HorseEndpoint(IHorseService horseService, HorseMapper horseMapper) {
        this.horseService = horseService;
        this.horseMapper = horseMapper;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HorseDto getOneById(@PathVariable("id") Integer id) {
        LOGGER.info("GET " + BASE_URL + "/" + id);
        try {
            return horseMapper.entityToDto(horseService.findOneById(id));
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error during read horse with id " + id, e);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Error during reading horse: " + e.getMessage(), e);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<HorseDto> getAllFiltered(
            @RequestParam Optional<String> name, 
            @RequestParam Optional<String> breed,
            @RequestParam Optional<Double> minSpeed, 
            @RequestParam Optional<Double> maxSpeed) {
        LOGGER.info("GET " + BASE_URL);

        String actualName = name.orElse(null);
        String actualBreed = breed.orElse(null);
        Double actualMinSpeed = minSpeed.orElse(null);
        Double actualMaxSpeed = maxSpeed.orElse(null);

        HorseDto filter = new HorseDto(
            null, 
            actualName, 
            actualBreed, 
            actualMinSpeed, 
            actualMaxSpeed, 
            null, 
            null);

        try {
            return horseMapper.entityToDto(horseService.getAllFiltered(horseMapper.dtoToEntity(filter)));
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error during read horses" , e);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public HorseDto createOne(@RequestBody HorseDto newHorse) {
        LOGGER.info("POST " + BASE_URL + "/ Body: " + newHorse);
        try {
            return horseMapper.entityToDto(horseService.createOne(horseMapper.dtoToEntity(newHorse)));
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                "Error during saving horse: " + e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public HorseDto updateOne(@PathVariable("id") Integer id, @RequestBody HorseDto updatedHorse) {
        LOGGER.info("PUT " + BASE_URL + "/" + id + " Body: " + updatedHorse);
        try {
            return horseMapper.entityToDto(horseService.updateOne(id, horseMapper.dtoToEntity(updatedHorse)));
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                "Error during updating of horse: " + e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteOne(@PathVariable("id") Integer id) {
        LOGGER.info("PUT " + BASE_URL + "/" + id);
        try {
            horseService.deleteOne(id);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error during deletion of horse: " + e.getMessage(), e);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Error during deletion of horse: " + e.getMessage(), e);
        }
    }
}
