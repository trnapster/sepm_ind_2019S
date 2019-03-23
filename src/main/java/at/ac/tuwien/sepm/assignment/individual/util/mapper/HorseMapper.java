package at.ac.tuwien.sepm.assignment.individual.util.mapper;

import at.ac.tuwien.sepm.assignment.individual.rest.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

@Component
public class HorseMapper {

    public HorseDto entityToDto(Horse horse) {
        return new HorseDto(horse.getId(), horse.getName(), horse.getBreed(), horse.getMinSpeed(), horse.getMaxSpeed(), horse.getCreated(), horse.getUpdated());
    }

    public List<HorseDto> entityToDto(Collection<Horse> horses) {
        ArrayList<HorseDto> horseDtos = new ArrayList<HorseDto>();
        for(Horse horse : horses) {
            HorseDto horseDto = entityToDto(horse);
            horseDtos.add(horseDto);
        }
        return horseDtos;
    }

    public Horse dtoToEntity(HorseDto horseDto) {
        return new Horse(horseDto.getId(), horseDto.getName(), horseDto.getBreed(), horseDto.getMinSpeed(), horseDto.getMaxSpeed(), horseDto.getCreated(), horseDto.getUpdated());
    }
}
