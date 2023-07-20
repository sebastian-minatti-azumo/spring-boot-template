package app.services.impl;

import app.dto.ProjectDto;
import app.dto.ProjectSummaryDto;
import app.dto.ProjectUpdateDto;
import app.error.exception.ProjectNotFoundException;
import app.error.exception.UserNotFoundException;
import app.mappers.ProjectMapper;
import app.mappers.ProjectSummaryMapper;
import app.persistence.dao.ProjectRepository;
import app.persistence.dao.UserRepository;
import app.persistence.entities.Project;
import app.persistence.entities.User;
import app.services.IProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService implements IProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;
    private final ProjectSummaryMapper projectSummaryMapper;

    @Override
    public ProjectDto create(ProjectDto projectDto) {
        Project entity = projectMapper.toProject(projectDto);
        Project savedEntity = projectRepository.save(entity);
        return projectMapper.toProjectDto(savedEntity);
    }

    @Override
    public ProjectDto get(Long id) {
        return projectRepository.findById(id)
                .map(projectMapper::toProjectDto)
                .orElseThrow(() -> new ProjectNotFoundException(id));
    }

    @Override
    public void update(Long id, ProjectUpdateDto projectUpdateDto) {
        projectRepository.findById(id)
                .map( project -> {
                    projectMapper.toProject(projectUpdateDto, project);
                    Project savedProject = projectRepository.save(project);
                    return projectMapper.toProjectDto(savedProject);
                })
                .orElseThrow(()-> new ProjectNotFoundException(id));
    }

    @Override
    public void delete(Long id) {
        if(projectRepository.existsById(id)){
            projectRepository.deleteById(id);
        } else throw new ProjectNotFoundException(id);
    }

    @Override
    public List<ProjectSummaryDto> getAll(Integer pageNo, Integer pageSize) {
        Pageable pageRequest = PageRequest.of(pageNo, pageSize);
        Page<Project> pagedResult = projectRepository.findAll(pageRequest);
        return projectMapper.toProjectSummaryDtoList(pagedResult.getContent());
    }

    @Override
    public List<ProjectSummaryDto> findByName(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        final Page<Project> pagedResult = projectRepository.findByNameContainingIgnoreCase(keyword, pageable);
        return projectMapper.toProjectSummaryDtoList(pagedResult.getContent());
    }

    @Override
    public void assignUserToProject(Long projectId, Long userId) {
        projectRepository.findById(projectId)
                .map( project -> {
                    User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(userId));
                    project.getUsers().add(user);
                    return projectRepository.save(project);
                })
                .orElseThrow(()-> new ProjectNotFoundException(projectId));
    }
}
