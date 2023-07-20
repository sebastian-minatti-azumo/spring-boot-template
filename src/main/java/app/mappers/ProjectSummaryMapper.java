package app.mappers;

import app.dto.ProjectSummaryDto;
import app.persistence.entities.Project;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProjectSummaryMapper {
    List<ProjectSummaryDto> toProjectSummaryDtoList(List<Project> projects);
}
