package app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectDto {

    @Null(message = "Project id shouldn't be present")
    @ApiModelProperty(notes = "The database generate the project ID")
    private Long id;

    @Null
    @ApiModelProperty(notes = "Users in the project")
    private Set<UserDto> users;

    @NotNull(message = "Project name should be populated")
    @Size(min = 1, max = 100, message = "Project name length must be between 1 and 100")
    @ApiModelProperty(notes = "Project name", required = true)
    private String name;

    @NotNull(message = "Project description should be populated")
    @ApiModelProperty(notes = "Project description", required = true)
    @NotBlank(message = "Project description is required")
    private String description;

    @Null(message = "DateCreated shouldn't be populated")
    @ApiModelProperty(notes = "Date & time of project creation")
    @JsonIgnore
    private LocalDateTime dateCreated;

    @Null(message = "DateUpdated shouldn't be populated")
    @ApiModelProperty(notes = "Date & time of project update")
    @JsonIgnore
    private LocalDateTime dateUpdated;
}
