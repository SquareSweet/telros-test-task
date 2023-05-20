package prj.sqsw.telrostest.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import prj.sqsw.telrostest.user.dto.UserContactsDto;
import prj.sqsw.telrostest.user.dto.UserCreateDto;
import prj.sqsw.telrostest.user.dto.UserFullDto;
import prj.sqsw.telrostest.user.dto.UserUpdateDto;
import prj.sqsw.telrostest.user.exception.UserNotFoundException;
import prj.sqsw.telrostest.user.mapper.UserMapper;
import prj.sqsw.telrostest.user.model.User;
import prj.sqsw.telrostest.user.repository.UserRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public UserFullDto create(UserCreateDto userCreateDto) {
        User user = repository.save(mapper.toUser(userCreateDto));
        log.debug("Create user id: {}", user.getId());
        return mapper.toUserFullDto(user);
    }

    @Override
    public UserFullDto update(UserUpdateDto userUpdateDto, Long userId) {
        User user = findById(userId); //throws exception if user doesn't exist

        //since none of the fields are required in update json checking every field for null value
        if (userUpdateDto.getEmail() != null) {
            user.setEmail(userUpdateDto.getEmail());
        }
        if (userUpdateDto.getLastName() != null) {
            user.setLastName(userUpdateDto.getLastName());
        }
        if (userUpdateDto.getFirstName() != null) {
            user.setFirstName(userUpdateDto.getFirstName());
        }
        if (userUpdateDto.getMidName() != null) {
            user.setMidName(userUpdateDto.getMidName());
        }
        if (userUpdateDto.getBirthday() != null) {
            user.setBirthday(LocalDate.parse(userUpdateDto.getBirthday(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        if (userUpdateDto.getPhoneNumber() != null) {
            user.setPhoneNumber(userUpdateDto.getPhoneNumber());
        }

        user = repository.save(user);
        log.debug("Update user id: {}", user.getId());
        return mapper.toUserFullDto(user);
    }

    @Override
    public List<UserFullDto> findAll() {
        return repository.findAll().stream().map(mapper::toUserFullDto).collect(Collectors.toList());
    }

    @Override
    public User findById(Long userId) {
        return repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public void delete(Long userId) {
        User user = findById(userId); //throws exception if user doesn't exist
        repository.delete(user);
    }

    @Override
    public UserFullDto updateContacts(UserContactsDto userContactsDto, Long userId) {
        User user = findById(userId); //throws exception if user doesn't exist

        //since none of the fields are required in update json checking every field for null value
        if (userContactsDto.getEmail() != null) {
            user.setEmail(userContactsDto.getEmail());
        }
        if (userContactsDto.getPhoneNumber() != null) {
            user.setPhoneNumber(userContactsDto.getPhoneNumber());
        }

        user = repository.save(user);
        log.debug("Update user contacts id: {}", user.getId());
        return mapper.toUserFullDto(user);
    }

    @Override
    public UserContactsDto getContacts(Long userId) {
        return mapper.toUserContactsDto(repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId)));
    }

    @Override
    public User updatePhoto(String path, Long userId) {
        User user = findById(userId); //throws exception if user doesn't exist

        user.setPhoto(path);
        return repository.save(user);
    }

}
