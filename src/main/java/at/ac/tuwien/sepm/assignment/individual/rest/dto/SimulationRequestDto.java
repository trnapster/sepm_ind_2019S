package at.ac.tuwien.sepm.assignment.individual.rest.dto;

import at.ac.tuwien.sepm.assignment.individual.rest.dto.SimulationParticipantDto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.List;

public class SimulationRequestDto {
    private String name;
    private LocalDateTime created;
    private List<SimulationParticipantDto> simulationParticipants;

    public SimulationRequestDto() {
    }

    public SimulationRequestDto(String name, LocalDateTime created, 
            List<SimulationParticipantDto> simulationParticipants) {
        this.name = name;
        this.created = created;
        this.simulationParticipants = simulationParticipants;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public List<SimulationParticipantDto> getSimulationParticipants() {
        return simulationParticipants;
    }

    public void setSimulationParticipants(List<SimulationParticipantDto> simulationParticipants) {
        this.simulationParticipants = simulationParticipants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimulationRequestDto)) return false;
        SimulationRequestDto simulationRequestDto = (SimulationRequestDto) o;
        return Objects.equals(name, simulationRequestDto.name) &&
            Objects.equals(created, simulationRequestDto.created) &&
            Objects.equals(simulationParticipants, simulationRequestDto.simulationParticipants);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, created, simulationParticipants);
    }

    @Override
    public String toString() {
        return "SimulationRequestDto{" +
            ", name='" + name + '\'' +
            ", created=" + created +
            ", simulationParticipants=" + simulationParticipants +
            '}';
    }

}
