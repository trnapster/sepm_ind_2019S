package at.ac.tuwien.sepm.assignment.individual.util.mapper;

import at.ac.tuwien.sepm.assignment.individual.rest.dto.JockeyDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Jockey;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

@Component
public class JockeyMapper {

    public JockeyDto entityToDto(Jockey jockey) {
        return new JockeyDto(jockey.getId(), jockey.getName(), jockey.getSkill(), jockey.getCreated(), jockey.getUpdated());
    }

    public List<JockeyDto> entityToDto(Collection<Jockey> jockeys) {
        ArrayList<JockeyDto> jockeyDtos = new ArrayList<JockeyDto>();
        for(Jockey jockey : jockeys) {
            JockeyDto jockeyDto = entityToDto(jockey);
            jockeyDtos.add(jockeyDto);
        }
        return jockeyDtos;
    }

    public Jockey dtoToEntity(JockeyDto jockeyDto) {
        return new Jockey(jockeyDto.getId(), jockeyDto.getName(), jockeyDto.getSkill(), jockeyDto.getCreated(), jockeyDto.getUpdated());
    }
}
