package at.ac.tuwien.sepm.assignment.individual.util.mapper;

import at.ac.tuwien.sepm.assignment.individual.rest.dto.SimulationRequestDto;
import at.ac.tuwien.sepm.assignment.individual.rest.dto.SimulationResponseDto;
import at.ac.tuwien.sepm.assignment.individual.rest.dto.SimulationParticipantDto;
import at.ac.tuwien.sepm.assignment.individual.rest.dto.HorseJockeyCombinationDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Simulation;
import at.ac.tuwien.sepm.assignment.individual.entity.SimulationParticipant;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Jockey;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

@Component
public class SimulationMapper {

    public Simulation dtoToEntity(SimulationRequestDto simulationRequestDto) {
        List<SimulationParticipant> simulationParticipants = null;

        if(simulationRequestDto.getSimulationParticipants() != null) {
            simulationParticipants = new ArrayList<SimulationParticipant>();
            for (SimulationParticipantDto simulationParticipantDto 
                    : simulationRequestDto.getSimulationParticipants()) {
                simulationParticipants.add(dtoToEntity(simulationParticipantDto));
            }
        }

        return new Simulation(
            null, 
            simulationRequestDto.getName(), 
            simulationRequestDto.getCreated(), 
            simulationParticipants);
    }

    public SimulationResponseDto entityToDto(Simulation simulation) {
        List<HorseJockeyCombinationDto> horseJockeyCombinationDtos = null;
        if (simulation.getSimulationParticipants() != null) {
            horseJockeyCombinationDtos = new ArrayList<HorseJockeyCombinationDto>();
            for(SimulationParticipant participant : simulation.getSimulationParticipants()) {
                HorseJockeyCombinationDto horseJockeyCombinationDto = entityToDto(participant);
                horseJockeyCombinationDtos.add(horseJockeyCombinationDto);
            }
        }
        return new SimulationResponseDto(
            simulation.getId(),
            simulation.getName(),
            simulation.getCreated(),
            horseJockeyCombinationDtos);
    }

    public List<SimulationResponseDto> entityToDto(Collection<Simulation> simulations) {
        ArrayList<SimulationResponseDto> simulationResponseDtos = new ArrayList<SimulationResponseDto>();
        for(Simulation simulation : simulations) {
            SimulationResponseDto simulationResponseDto = entityToDto(simulation);
            simulationResponseDtos.add(simulationResponseDto);
        }
        return simulationResponseDtos;
    }

    private HorseJockeyCombinationDto entityToDto(SimulationParticipant participant) {
        return new HorseJockeyCombinationDto(
            participant.getId(),
            participant.getRank(),
            participant.getHorse().getName(),
            participant.getJockey().getName(),
            participant.getAvgSpeed(),
            participant.getHorseSpeed(),
            participant.getSkill(),
            participant.getLuckFactor());
    }

    private SimulationParticipant dtoToEntity(SimulationParticipantDto simulationParticipantDto) {
        Horse horse = new Horse(
            simulationParticipantDto.getHorseId(),
            null,
            null,
            null,
            null,
            null,
            null);
        
        Jockey jockey = new Jockey(
            simulationParticipantDto.getJockeyId(),
            null,
            null,
            null,
            null);

        return new SimulationParticipant(
            null,
            null,
            horse,
            jockey,
            null,
            null,
            null,
            simulationParticipantDto.getLuckFactor());
    }
}
