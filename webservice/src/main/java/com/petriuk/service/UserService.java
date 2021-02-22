package com.petriuk.service;

import com.petriuk.dao.RoleDao;
import com.petriuk.dao.UserDao;
import com.petriuk.dto.UserDto;
import com.petriuk.entity.Role;
import com.petriuk.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDto> getAllUsersAsDto() {
        List<User> userList = userDao.findAll();
        return userList.stream().map(UserDto::new).collect(Collectors.toList());
    }

    @Transactional
    public boolean addUser(User user) {
        try {
            Role role = roleDao.findByName(user.getRole().getName())
                .orElseThrow(PersistenceException::new);
            user.setRole(role);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.create(user);
            return true;
        } catch (DataIntegrityViolationException e) {
            throw new UserExistException();
        }
    }

    public List<String> getAllRoles() {
        return roleDao.findAll().stream().map(Role::getName)
            .collect(Collectors.toList());
    }

    @Transactional
    public void deleteUser(String login) {
        User user = userDao.findByLogin(login).orElseThrow(UserNotFoundException::new);
        userDao.remove(user);
    }

    public UserDto getUserDto(String login) {
        return new UserDto(userDao.findByLogin(login).orElseThrow(UserNotFoundException::new));
    }

    @Transactional
    public void updateUser(User editedUser) {
        User user = userDao.findByLogin(editedUser.getLogin())
            .orElseThrow(UserNotFoundException::new);
        user.setEmail(editedUser.getEmail());
        user.setFirstName(editedUser.getFirstName());
        user.setLastName(editedUser.getLastName());
        user.setBirthday(editedUser.getBirthday());
        if (!editedUser.getRole().equals(user.getRole())) {
            Role role = roleDao.findByName(editedUser.getRole().getName())
                .orElseThrow(PersistenceException::new);
            user.setRole(role);
        }
        if (editedUser.getPassword() != null && !editedUser.getPassword()
            .isEmpty()) {
            user.setPassword(passwordEncoder.encode(editedUser.getPassword()));
        }
        userDao.update(user);
    }
}
