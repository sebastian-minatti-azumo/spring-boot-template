package app.mappers;

import app.dto.ProjectDto;
import app.dto.ProjectSummaryDto;
import app.dto.ProjectUpdateDto;
import app.persistence.entities.Project;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProjectMapper {

    ProjectDto toProjectDto(Project project);

    List<ProjectDto> toProjectDtoList(List<Project> projects);

    List<ProjectSummaryDto> toProjectSummaryDtoList(List<Project> projects);

    Project toProject(ProjectDto projectDto);

    void toProject(ProjectUpdateDto projectUpdateDto, @MappingTarget Project project);
}
