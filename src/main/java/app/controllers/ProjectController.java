package app.controllers;

import app.dto.ProjectDto;
import app.dto.ProjectSummaryDto;
import app.dto.ProjectUpdateDto;
import app.services.IProjectService;
import app.services.IUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final IProjectService projectService;
    private final IUserService userService;

    @ApiOperation(value = "Create new project", response = ProjectDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Project successfully created"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDto createProject(
            @ApiParam("New project's data")
            @Validated
            @RequestBody ProjectDto newProjectDto
    ) {
        return projectService.create(newProjectDto);
    }

    @ApiOperation(value = "Get project by id", response = ProjectDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project successfully retrieved"),
            @ApiResponse(code = 404, message = "Project not found"),
    })
    @GetMapping("/{id}")
    public ProjectDto readProject(
            @ApiParam("Project's id")
            @NotNull
            @PathVariable Long id
    ) {
        return projectService.get(id);
    }

    @ApiOperation("Delete project by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Delete successfully deleted"),
            @ApiResponse(code = 404, message = "Delete not found"),
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(
            @ApiParam("Project's id")
            @NotNull
            @PathVariable Long id
    ) {
        projectService.delete(id);
    }

    @ApiOperation("Update project")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project successfully updated"),
            @ApiResponse(code = 404, message = "Project not found"),
    })
    @PatchMapping("/{id}")
    public void updateProject(
            @ApiParam("User's id")
            @NotNull
            @PathVariable Long id,
            @ApiParam("Updated user 's fields")
            @Validated
            @RequestBody ProjectUpdateDto projectUpdateDto
    ) {
        projectService.update(id, projectUpdateDto);
    }

    @ApiOperation(value = "Read project paged", response = Page.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Projects successfully retrieved"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
            @ApiImplicitParam(name = "pageSize", dataType = "integer", paramType = "query",
                    value = "Number of records per page.", defaultValue = "50"),
    })
    @GetMapping
    public ResponseEntity<List<ProjectSummaryDto>> getAllProjects(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        List<ProjectSummaryDto> projects = projectService.getAll(pageNo, pageSize);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProjectSummaryDto>> searchProjects(@RequestParam("keyword") String keyword,
                                                                  @RequestParam(defaultValue = "0") int pageNo,
                                                                  @RequestParam(defaultValue = "10") int pageSize) {
        List<ProjectSummaryDto> projects = projectService.findByName(keyword, pageNo, pageSize);
        return ResponseEntity.ok(projects);
    }

    @PostMapping("/{projectId}/assignUser/{userId}")
    public ResponseEntity<String> assignUsersToProject(@PathVariable("projectId") Long projectId, @PathVariable Long userId) {
        // Assign user to the project
        projectService.assignUserToProject(projectId, userId);

        return ResponseEntity.ok("User assigned to project successfully.");
    }
}
