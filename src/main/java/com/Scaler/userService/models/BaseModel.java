package com.Scaler.userService.models;

import jakarta.persistence.*;
import lombok.Data;


@Data
@MappedSuperclass
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
