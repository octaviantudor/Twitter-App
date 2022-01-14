package com.unibuc.twitterapp.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MentionDto {

    private Long idPost;

    private String postMessage;

    private LocalDateTime postDate;
}
