package com.cognizant.softvision.twitterapp.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted=false")
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mail")
    private String mail;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy="to", cascade = CascadeType.ALL)
    private List<Follow> followers;

    @OneToMany(mappedBy="from", cascade = CascadeType.ALL)
    private List<Follow> following;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    private List<Reply> replies;

    @Column(name= "is_deleted")
    @Builder.Default
    private Boolean deleted = Boolean.FALSE;

}
