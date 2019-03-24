package at.ac.tuwien.sepm.assignment.individual.entity;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Jockey;

import java.time.LocalDateTime;
import java.util.Objects;
import java.math.BigDecimal;

public class SimulationParticipant {
    private Integer id;
    private Integer rank;
    private Horse horse;
    private Jockey jockey;
    private BigDecimal avgSpeed;
    private BigDecimal horseSpeed;
    private BigDecimal skill;
    private BigDecimal luckFactor;

    public SimulationParticipant() {
    }

    public SimulationParticipant(Integer id, Integer rank, Horse horse, 
            Jockey jockey, BigDecimal avgSpeed, BigDecimal horseSpeed, 
            BigDecimal skill, BigDecimal luckFactor) {
        this.id = id;
        this.rank = rank;
        this.horse = horse;
        this.jockey = jockey;
        this.avgSpeed = avgSpeed;
        this.horseSpeed = horseSpeed;
        this.skill = skill;
        this.luckFactor = luckFactor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
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

    public BigDecimal getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(BigDecimal avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public BigDecimal getHorseSpeed() {
        return horseSpeed;
    }

    public void setHorseSpeed(BigDecimal horseSpeed) {
        this.horseSpeed = horseSpeed;
    }

    public BigDecimal getSkill() {
        return skill;
    }

    public void setSkill(BigDecimal skill) {
        this.skill = skill;
    }

    public BigDecimal getLuckFactor() {
        return luckFactor;
    }

    public void setLuckFactor(BigDecimal luckFactor) {
        this.luckFactor = luckFactor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimulationParticipant)) return false;
        SimulationParticipant simulationParticipant = (SimulationParticipant) o;
        return Objects.equals(id, simulationParticipant.id) &&
            Objects.equals(rank, simulationParticipant.rank) &&
            Objects.equals(horse, simulationParticipant.horse) &&
            Objects.equals(jockey, simulationParticipant.jockey) &&
            Objects.equals(avgSpeed, simulationParticipant.avgSpeed) &&
            Objects.equals(horseSpeed, simulationParticipant.horseSpeed) &&
            Objects.equals(skill, simulationParticipant.skill) &&
            Objects.equals(luckFactor, simulationParticipant.luckFactor);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, horse, jockey, avgSpeed, horseSpeed, 
            skill, luckFactor);
    }

    @Override
    public String toString() {
        return "SimulationParticipant{" +
            "id=" + id +
            "rank=" + rank +
            ", horse='" + horse + '\'' +
            ", jockey='" + jockey + '\'' +
            ", avgSpeed=" + avgSpeed +
            ", horseSpeed=" + horseSpeed +
            ", skill=" + skill +
            ", luckFactor=" + luckFactor +
            '}';
    }

}
