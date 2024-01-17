package org.sayres.springmvc.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Aliaksei Karabelnikau
 */
public class User {


    private int player_id;

    @NotEmpty(message = "Please enter your name")
    @Size(min = 2, max = 30, message = "Your name must be between 2 and 30 characters")
    private String player_name;


    @NotEmpty(message = "Please enter your email")
    @Email(message = "Please enter a valid email address")
    private String email;
    @NotEmpty(message = "Please enter your password")
    @Size(min = 6, max = 30, message = "Your password must be between 6 and 30 characters")
    private String password;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please enter your date of birth")
    @Past(message = "Birth date must be in the past")
    private LocalDate date_of_birth = LocalDate.now();
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created_at = LocalDateTime.now();
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime last_modified = LocalDateTime.now();
    private String location;

    public User() {

    }


    public User(int player_id, String player_name, String email, LocalDate date_of_birth, LocalDateTime created_at, LocalDateTime last_modified, String location) {
        this.player_id = player_id;
        this.player_name = player_name;
        this.email = email;
        this.date_of_birth = date_of_birth;
        this.created_at = created_at;
        this.last_modified = last_modified;
        this.location = location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPlayer_id() {
        return this.player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(LocalDateTime last_modified) {
        this.last_modified = last_modified;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
