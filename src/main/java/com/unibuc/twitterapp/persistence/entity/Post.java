package com.unibuc.twitterapp.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@SQLDelete(sql = "UPDATE posts SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted=false")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "message")
    private String message;

    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;

    @OneToMany(mappedBy="post", cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(mappedBy="post", cascade = CascadeType.REMOVE)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy="post", cascade = CascadeType.ALL)
    private List<Mention> mentions;

    @Column(name= "is_deleted")
    @Builder.Default
    private Boolean deleted = Boolean.FALSE;

}
