package com.unibuc.twitterapp.pojo.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyRequest {

    @NotEmpty
    private String postId;

    @Size(min = 1)
    private String message;


}
