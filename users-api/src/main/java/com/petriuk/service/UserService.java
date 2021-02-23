package com.petriuk.service;

import com.petriuk.dao.UserDao;
import com.petriuk.dto.RoleDto;
import com.petriuk.dto.UserDto;
import com.petriuk.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private UserDao userDao;

    @Autowired
    private RolesRestService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDto> getAllUsersAsDto() {
        List<User> userList = userDao.findAll();
        return userList.stream()
            .map(u -> new UserDto(u, roleService.getRoleByUserId(u.getId())))
            .collect(Collectors.toList());
    }

    @Transactional
    public boolean addUser(UserDto userDto) {
        User user = userDto.makeUser();
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.create(user);
            User createdUser = userDao.findByLogin(userDto.getLogin())
                .orElseThrow(UserNotFoundException::new);
            roleService.postUserRole(
                new RoleDto(userDto.getRole(), createdUser.getId()));
            return true;
        } catch (DataIntegrityViolationException e) {
            throw new UserExistException();
        }
    }

    @Transactional
    public void deleteUser(String login) {
        User user = userDao.findByLogin(login)
            .orElseThrow(UserNotFoundException::new);
        userDao.remove(user);
        roleService.deleteUserRole(user.getId());
    }

    public UserDto getUserDto(String login) {
        User user = userDao.findByLogin(login)
            .orElseThrow(UserNotFoundException::new);
        String role = roleService.getRoleByUserId(user.getId());
        return new UserDto(user, role);
    }

    @Transactional
    public void updateUser(UserDto editedUser) {
        User user = userDao.findByLogin(editedUser.getLogin())
            .orElseThrow(UserNotFoundException::new);
        user.setEmail(editedUser.getEmail());
        user.setFirstName(editedUser.getFirstName());
        user.setLastName(editedUser.getLastName());
        user.setBirthday(Date.valueOf(editedUser.getBirthday()));
        roleService
            .putUserRole(new RoleDto(editedUser.getRole(), user.getId()));

        if (editedUser.getPassword() != null && !editedUser.getPassword()
            .isEmpty()) {
            user.setPassword(passwordEncoder.encode(editedUser.getPassword()));
        }
        userDao.update(user);
    }
}
