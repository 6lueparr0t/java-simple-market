package com.rgbplace.dto;

import com.sun.istack.NotNull;
import lombok.*;

@Data
@RequiredArgsConstructor
@Builder
public class LoginDto {
    @NotNull
    private String uid;
    @NotNull
    private String pw;
    @NotNull
    private String email;
}