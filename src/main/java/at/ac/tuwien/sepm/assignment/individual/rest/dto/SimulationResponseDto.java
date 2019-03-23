package at.ac.tuwien.sepm.assignment.individual.rest.dto;

import at.ac.tuwien.sepm.assignment.individual.rest.dto.HorseJockeyCombinationDto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.List;

public class SimulationResponseDto {
    private Integer id;
    private String name;
    private LocalDateTime created;
    private List<HorseJockeyCombinationDto> horseJockeyCombinations;

    public SimulationResponseDto() {
    }

    public SimulationResponseDto(Integer id, String name, LocalDateTime created, 
            List<HorseJockeyCombinationDto> horseJockeyCombinations) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.horseJockeyCombinations = horseJockeyCombinations;
    }

    public Integer getId() {
        return id;
    }

    public void setId() {
        this.id = id;
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

    public List<HorseJockeyCombinationDto> getHorseJockeyCombinations() {
        return horseJockeyCombinations;
    }

    public void setHorseJockeyCombinations(List<HorseJockeyCombinationDto> horseJockeyCombinations) {
        this.horseJockeyCombinations = horseJockeyCombinations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimulationResponseDto)) return false;
        SimulationResponseDto simulationResponseDto = (SimulationResponseDto) o;
        return Objects.equals(id, simulationResponseDto.id) &&
            Objects.equals(name, simulationResponseDto.name) &&
            Objects.equals(created, simulationResponseDto.created) &&
            Objects.equals(horseJockeyCombinations, simulationResponseDto.horseJockeyCombinations);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, created, horseJockeyCombinations);
    }

    @Override
    public String toString() {
        return "SimulationResponseDto{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", created=" + created +
            ", horseJockeyCombinations=" + horseJockeyCombinations +
            '}';
    }

}
