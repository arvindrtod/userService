package com.Scaler.userService.models;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Session extends BaseModel {
    private String token;
    private Date expiryAt;
    @ManyToOne
    @JoinColumn
    private User user;
    @Enumerated(EnumType.STRING)
    private SessionStatus sessionStatus;
}
