package com.Scaler.userService.dtos;

import com.Scaler.userService.models.Role;
import com.Scaler.userService.models.User;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UserDto {
    private long Id;
    private String email;
    private List<Role> roles;
    //deep copy
    public static UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        // set other values when required
        return userDto;
    }


}
