package app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(Include.NON_NULL)
public class UserDto {

    @Null(message = "User id shouldn't be present")
    @ApiModelProperty(notes = "The database generated user ID")
    private Long id;

    @NotNull(message = "User name should be populated")
    @Size(min = 1, max = 100, message = "User name length must be between 1 and 100")
    @ApiModelProperty(notes = "User name", required = true)
    private String name;

    @NotNull(message = "User Email should be populated")
    @ApiModelProperty(notes = "User email", required = true)
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Null(message = "DateCreated shouldn't be populated")
    @ApiModelProperty(notes = "Date & time of user creation")
    @JsonIgnore
    private LocalDateTime dateCreated;

    @Null(message = "DateUpdated shouldn't be populated")
    @ApiModelProperty(notes = "Date & time of user update")
    @JsonIgnore
    private LocalDateTime dateUpdated;
}

