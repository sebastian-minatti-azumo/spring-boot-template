package app.mappers;

import app.dto.UserDto;
import app.dto.UserUpdateDto;
import app.persistence.entities.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserDto toUserDto(User user);

    List<UserDto> toUserDtoList(Iterable<User> users);

    User toUser(UserDto userDto);

    void toUser(UserUpdateDto userUpdateDto, @MappingTarget User user);
}
