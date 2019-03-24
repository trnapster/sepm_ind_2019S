package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.rest.dto.JockeyDto;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.service.IJockeyService;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.util.mapper.JockeyMapper;

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
@RequestMapping("/api/v1/jockeys")
public class JockeyEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(JockeyEndpoint.class);
    private static final String BASE_URL = "/api/v1/jockeys";
    private final IJockeyService jockeyService;
    private final JockeyMapper jockeyMapper;

    @Autowired
    public JockeyEndpoint(IJockeyService jockeyService, JockeyMapper jockeyMapper) {
        this.jockeyService = jockeyService;
        this.jockeyMapper = jockeyMapper;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public JockeyDto getOneById(@PathVariable("id") Integer id) {
        LOGGER.info("GET " + BASE_URL + "/" + id);
        try {
            return jockeyMapper.entityToDto(jockeyService.findOneById(id));
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error during read jockey with ID: " + id, e);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Error during reading jockey: " + e.getMessage(), e);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<JockeyDto> getAllFiltered(
            @RequestParam Optional<String> name, 
            @RequestParam Optional<Double> skill) {
        LOGGER.info("GET " + BASE_URL);

        String actualName = name.orElse(null);
        Double actualSkill = skill.orElse(null);

        JockeyDto filter = new JockeyDto(
            null, 
            actualName, 
            actualSkill, 
            null, 
            null);

        try {
            return jockeyMapper.entityToDto(jockeyService.getAllFiltered(jockeyMapper.dtoToEntity(filter)));
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error during read jockeys" , e);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public JockeyDto createOne(@RequestBody JockeyDto newJockey) {
        LOGGER.info("POST " + BASE_URL + "/ Body: " + newJockey);
        try {
            return jockeyMapper.entityToDto(jockeyService.createOne(jockeyMapper.dtoToEntity(newJockey)));
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                "Error during saving jockey: " + e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public JockeyDto updateOne(@PathVariable("id") Integer id, @RequestBody JockeyDto updatedJockey) {
        LOGGER.info("PUT " + BASE_URL + "/" + id + " Body: " + updatedJockey);
        try {
            return jockeyMapper.entityToDto(jockeyService.updateOne(id, jockeyMapper.dtoToEntity(updatedJockey)));
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                "Error during updating of jockey: " + e.getMessage(), e);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Error during updating of jockey: " + e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteOne(@PathVariable("id") Integer id) {
        LOGGER.info("PUT " + BASE_URL + "/" + id);
        try {
            jockeyService.deleteOne(id);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error during deletion of jockey: " + e.getMessage(), e);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Error during deletion of jockey: " + e.getMessage(), e);
        }
    }
}
