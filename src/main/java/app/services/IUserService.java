package app.services;

import app.dto.UserDto;
import app.dto.UserUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {

    UserDto create(UserDto userDto);

    UserDto get(Long id);

    void update(Long id, UserUpdateDto userUpdateDto);

    void delete(Long id);

    List<UserDto> getAll(Integer pageNo, Integer pageSize, String sortBy);

    Page<UserDto> getAll(Pageable pageable);

    List<UserDto> findByNameOrEmail(String keyword, int page, int size);

    List<UserDto> getUsersByIds(List<Long> userIds);
}
