package com.Scaler.userService.models;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Role extends BaseModel{
    private String role;
}
