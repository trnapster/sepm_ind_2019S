package at.ac.tuwien.sepm.assignment.individual.entity;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Jockey;

import java.time.LocalDateTime;
import java.util.Objects;
import java.math.BigDecimal;

public class SimulationParticipant {
    private Integer id;
    private Horse horse;
    private Jockey jockey;
    private Double luckFactor;

    public SimulationParticipant() {
    }

    public SimulationParticipant(Integer id, Horse horse, Jockey jockey, Double luckFactor) {
        this.id = id;
        this.horse = horse;
        this.jockey = jockey;
        this.luckFactor = luckFactor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Horse getHorse() {
        return horse;
    }

    public void setHorse(Horse horse) {
        this.horse = horse;
    }

    public Jockey getJockey() {
        return jockey;
    }

    public void setJockey(Jockey jockey) {
        this.jockey = jockey;
    }

    public Double getLuckFactor() {
        return luckFactor;
    }

    public void setLuckFactor(Double luckFactor) {
        this.luckFactor = luckFactor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimulationParticipant)) return false;
        SimulationParticipant simulationParticipant = (SimulationParticipant) o;
        return Objects.equals(id, simulationParticipant.id) &&
            Objects.equals(horse, simulationParticipant.horse) &&
            Objects.equals(jockey, simulationParticipant.jockey) &&
            Objects.equals(luckFactor, simulationParticipant.luckFactor);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, horse, jockey, luckFactor);
    }

    @Override
    public String toString() {
        return "SimulationParticipant{" +
            "id=" + id +
            ", horse='" + horse + '\'' +
            ", jockey='" + jockey + '\'' +
            ", luckFactor=" + luckFactor +
            '}';
    }

}
