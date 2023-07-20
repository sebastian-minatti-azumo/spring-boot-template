package app.dto;

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

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectSummaryDto {
    @Null(message = "Project id shouldn't be present")
    @ApiModelProperty(notes = "The database generate the project ID")
    private Long id;

    @NotNull(message = "Project name should be populated")
    @Size(min = 1, max = 100, message = "Project name length must be between 1 and 100")
    @ApiModelProperty(notes = "Project name", required = true)
    private String name;

    @NotNull(message = "Project description should be populated")
    @ApiModelProperty(notes = "Project description", required = true)
    @NotBlank(message = "Project description is required")
    private String description;
}
