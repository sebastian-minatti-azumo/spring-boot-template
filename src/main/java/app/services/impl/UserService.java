package app.services.impl;

import app.dto.UserDto;
import app.dto.UserUpdateDto;
import app.error.exception.DuplicateUserException;
import app.error.exception.UserNotFoundException;
import app.mappers.UserMapper;
import app.persistence.dao.UserRepository;
import app.persistence.entities.User;
import app.services.IUserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserDto create(UserDto articleDto) {
        if(userRepository.existsByEmail(articleDto.getEmail())){
            throw new DuplicateUserException("User email is already present");
        }
        User entity = userMapper.toUser(articleDto);
        User savedEntity = userRepository.save(entity);
        return userMapper.toUserDto(savedEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto get(Long id) {
        return userRepository.findById(id)
            .map(userMapper::toUserDto)
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    @Override
    public void update(Long id, UserUpdateDto userUpdateDto) {
        if(userUpdateDto.getEmail() != null){
            if( userRepository.existsByEmail(userUpdateDto.getEmail() )) {
                throw new DuplicateUserException("User email is already present");
            }
        }
        userRepository.findById(id)
            .map(user -> {
                userMapper.toUser(userUpdateDto, user);
                User savedUser = userRepository.save(user);
                return userMapper.toUserDto(savedUser);
            }).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (userRepository.existsById(id)) {
            User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
            user.getProjects().forEach( project -> project.getUsers().remove(this));
            userRepository.save(user);
            userRepository.deleteById(id);
        } else throw new UserNotFoundException(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getAll(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<User> pagedResult = userRepository.findAll(pageRequest);
        return userMapper.toUserDtoList(pagedResult.getContent());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<UserDto> getAll(Pageable pageable) {
        final Page<User> pagedResult = userRepository.findAll(pageable);
        return pagedResult.map(userMapper::toUserDto);
    }

    @Override
    public List<UserDto> findByNameOrEmail(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        final Page<User> pagedResult = userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword, pageable);
        return userMapper.toUserDtoList(pagedResult.getContent());
    }

    @Override
    public List<UserDto> getUsersByIds(List<Long> userIds){
        List<User> users = userRepository.findByIdIn(userIds);
        return userMapper.toUserDtoList(users);
    }
}
