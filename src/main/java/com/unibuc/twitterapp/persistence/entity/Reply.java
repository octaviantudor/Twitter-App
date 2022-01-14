package com.unibuc.twitterapp.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "replies")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "is_deleted")
    @Builder.Default
    private Boolean deleted = Boolean.FALSE;

    @Column(name = "message")
    private String message;

    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



}
