package app.services;

import app.dto.ProjectDto;
import app.dto.ProjectSummaryDto;
import app.dto.ProjectUpdateDto;

import java.util.List;

public interface IProjectService {
    ProjectDto create(ProjectDto projectDto);

    ProjectDto get(Long id);

    void update(Long id, ProjectUpdateDto projectUpdateDto);

    void delete(Long id);

    List<ProjectSummaryDto> getAll(Integer pageNo, Integer pageSize);

    List<ProjectSummaryDto> findByName(String keyword, int page, int size);

    void assignUserToProject(Long projectId, Long userId);
}
