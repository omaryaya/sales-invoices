package com.omaryaya.jetbrains.payload.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSummaryResponse {

    private Long id;
    private String username;
    private String name;
    
}
