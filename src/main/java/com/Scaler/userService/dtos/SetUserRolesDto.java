package com.Scaler.userService.dtos;

import lombok.Data;

import java.util.List;
@Data
public class SetUserRolesDto {

    private List<Long> roleIds;
}
