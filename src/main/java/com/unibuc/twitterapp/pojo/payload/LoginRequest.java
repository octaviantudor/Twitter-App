package com.unibuc.twitterapp.pojo.payload;

import com.unibuc.twitterapp.annotations.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotEmpty
    private String mail;

    @ValidPassword
    @Size(min = 8, max = 30)
    private String password;
}
