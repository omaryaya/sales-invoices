package com.omaryaya.jetbrains.payload.auth;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfileResponse {
    private Long id;
    private String username;
    private String name;
    private Instant joinedAt;
}
