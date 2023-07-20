package app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
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
public class ProjectUpdateDto {
    @Size(min = 1, max = 100, message = "User name length must be between 1 and 100")
    @ApiModelProperty(notes = "Project name")
    private String name;

    @ApiModelProperty(notes = "Project description")
    private String description;
}
