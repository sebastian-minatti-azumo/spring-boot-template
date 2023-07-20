package app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(Include.NON_NULL)
public class UserUpdateDto {

    @Size(min = 1, max = 100, message = "User name length must be between 1 and 100")
    @ApiModelProperty(notes = "User name")
    private String name;

    @ApiModelProperty(notes = "User email")
    @Email(message = "Invalid email format")
    private String email;
}
