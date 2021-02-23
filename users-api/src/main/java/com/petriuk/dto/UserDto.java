package com.petriuk.dto;

import com.petriuk.entity.User;
import com.petriuk.validation.PasswordMatches;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;

@PasswordMatches(groups = { UserDto.EditGroup.class,
    UserDto.CreateGroup.class })
public class UserDto {
    public interface CreateGroup {
        //marker interface for user creation
    }

    public interface EditGroup {
        //marker interface for user editing (optional password)
    }

    @Pattern(regexp = "(^$|^[a-zA-Z\\d]{3,}$)", groups = {
        UserDto.EditGroup.class,
        UserDto.CreateGroup.class }, message = "{Pattern.user.login}")
    @NotEmpty(groups = { UserDto.EditGroup.class, UserDto.CreateGroup.class })
    @NotNull(groups = { UserDto.EditGroup.class, UserDto.CreateGroup.class })
    private String login;

    @Pattern(regexp = "(^$|^[a-zA-Z\\d]{3,}$)", groups = EditGroup.class, message = "{Pattern.user.password}")
    @Pattern(regexp = "(^[a-zA-Z\\d]{3,}$)", groups = CreateGroup.class, message = "{Pattern.user.password}")
    @NotEmpty(groups = CreateGroup.class)
    @NotNull(groups = UserDto.CreateGroup.class)
    private String password;

    @Pattern(regexp = "(^$|^[a-zA-Z\\d]{3,}$)", groups = EditGroup.class, message = "{Pattern.user.password}")
    @Pattern(regexp = "(^[a-zA-Z\\d]{3,}$)", groups = CreateGroup.class, message = "{Pattern.user.password}")
    @NotEmpty(groups = CreateGroup.class)
    @NotNull(groups = UserDto.CreateGroup.class)
    private String password2;

    @Email(groups = { UserDto.EditGroup.class, UserDto.CreateGroup.class })
    @NotEmpty(groups = { UserDto.EditGroup.class, UserDto.CreateGroup.class })
    @NotNull(groups = { UserDto.EditGroup.class, UserDto.CreateGroup.class })
    private String email;

    @Pattern(regexp = "^[a-zA-Z]+$", groups = { UserDto.EditGroup.class,
        UserDto.CreateGroup.class }, message = "{Pattern.user.firstName}")
    @NotEmpty(groups = { UserDto.EditGroup.class, UserDto.CreateGroup.class })
    @NotNull(groups = { UserDto.EditGroup.class, UserDto.CreateGroup.class })
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z]+$", groups = { UserDto.EditGroup.class,
        UserDto.CreateGroup.class }, message = "{Pattern.user.lastName}")
    @NotEmpty(groups = { UserDto.EditGroup.class, UserDto.CreateGroup.class })
    @NotNull(groups = { UserDto.EditGroup.class, UserDto.CreateGroup.class })
    private String lastName;

    @NotEmpty(groups = { UserDto.EditGroup.class, UserDto.CreateGroup.class })
    @Pattern(regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$",
        groups = {UserDto.EditGroup.class, UserDto.CreateGroup.class },
        message = "{Pattern.user.birthday}")
    private String birthday;

    @NotEmpty(groups = { UserDto.EditGroup.class, UserDto.CreateGroup.class })
    @Pattern(regexp = "^(user|admin)$",
        groups = { UserDto.EditGroup.class, UserDto.CreateGroup.class },
        message = "{Pattern.user.role}")
    @NotNull(groups = { UserDto.EditGroup.class, UserDto.CreateGroup.class })
    private String role;

    public UserDto(User user, String role) {
        this.login = user.getLogin();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.birthday = user.getBirthday().toString();
        this.role = role;
    }

    public UserDto() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User makeUser() {
        return new User(login, password, email, firstName, lastName,
            Date.valueOf(birthday));
    }

    @Override
    public String toString() {
        return "UserForm{" + "login='" + login + '\'' + ", password='"
            + password + '\'' + ", password2='" + password2 + '\'' + ", email='"
            + email + '\'' + ", firstName='" + firstName + '\'' + ", lastName='"
            + lastName + '\'' + ", birthday='" + birthday + '\'' + ", role='"
            + role + '\'' + '}';
    }
}
