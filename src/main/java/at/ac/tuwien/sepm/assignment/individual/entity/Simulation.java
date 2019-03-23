package at.ac.tuwien.sepm.assignment.individual.entity;

import at.ac.tuwien.sepm.assignment.individual.entity.SimulationParticipant;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.List;

public class Simulation {
    private Integer id;
    private String name;
    private LocalDateTime created;
    private List<SimulationParticipant> simulationParticipants;

    public Simulation() {
    }

    public Simulation(Integer id, String name, LocalDateTime created, 
            List<SimulationParticipant> simulationParticipants) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.simulationParticipants = simulationParticipants;
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

    public List<SimulationParticipant> getSimulationParticipants() {
        return simulationParticipants;
    }

    public void setSimulationParticipants(List<SimulationParticipant> simulationParticipants) {
        this.simulationParticipants = simulationParticipants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Simulation)) return false;
        Simulation simulation = (Simulation) o;
        return Objects.equals(id, simulation.id) &&
            Objects.equals(name, simulation.name) &&
            Objects.equals(created, simulation.created) &&
            Objects.equals(simulationParticipants, simulation.simulationParticipants);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, created, simulationParticipants);
    }

    @Override
    public String toString() {
        return "Simulation{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", created=" + created +
            ", simulationParticipants=" + simulationParticipants +
            '}';
    }

}
