package app.controllers;

import app.dto.UserDto;
import app.dto.UserUpdateDto;
import app.services.IUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final IUserService userService;

    @ApiOperation(value = "Create new user", response = UserDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "User successfully created"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(
        @ApiParam("New user's data")
        @Validated
        @RequestBody UserDto newUserDto
    ) {
       return userService.create(newUserDto);
    }

    @ApiOperation(value = "Get user by id", response = UserDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "User successfully retrieved"),
        @ApiResponse(code = 404, message = "User not found"),
    })
    @GetMapping("/{id}")
    public UserDto readUser(
        @ApiParam("User's id")
        @NotNull
        @PathVariable Long id
    ) {
        return userService.get(id);
    }

    @ApiOperation("Update user")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "User successfully updated"),
        @ApiResponse(code = 404, message = "User not found"),
    })
    @PatchMapping("/{id}")
    public void updateUser(
        @ApiParam("User's id")
        @NotNull
        @PathVariable Long id,
        @ApiParam("Updated user 's fields")
        @Validated
        @RequestBody UserUpdateDto userUpdateDto
    ) {
        userService.update(id, userUpdateDto);
    }

    @ApiOperation("Delete user by id")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "User successfully deleted"),
        @ApiResponse(code = 404, message = "User not found"),
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(
        @ApiParam("User's id")
        @NotNull
        @PathVariable Long id
    ) {
        userService.delete(id);
    }

    @ApiOperation(value = "Read users paged", response = Page.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Users successfully retrieved"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
            @ApiImplicitParam(name = "pageSize", dataType = "integer", paramType = "query",
                    value = "Number of records per page.", defaultValue = "50"),
    })
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        List<UserDto> users = userService.getAll(pageNo, pageSize, "id");
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUsers(@RequestParam("keyword") String keyword,
                                                     @RequestParam(defaultValue = "0") int pageNo,
                                                     @RequestParam(defaultValue = "10") int pageSize) {
        List<UserDto> users = userService.findByNameOrEmail(keyword, pageNo, pageSize);
        return ResponseEntity.ok(users);
    }
}
