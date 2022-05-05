package com.unibuc.twitterapp.persistence.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted=false")
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mail")
    private String mail;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy="to", cascade = CascadeType.ALL)
    private Set<Follow> followers;

    @OneToMany(mappedBy="from", cascade = CascadeType.ALL)
    private Set<Follow> following;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    private Set<Post> posts;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    private Set<Reply> replies;

    @Singular
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="authority_id", referencedColumnName = "id"))
    private Set<Authority> authorities;

    @Column(name= "is_deleted")
    @Builder.Default
    private Boolean deleted = Boolean.FALSE;

    @Column(name= "enabled")
    @Builder.Default
    private Boolean enabled = true;

    @Column(name= "account_not_expired")
    @Builder.Default
    private Boolean accountNotExpired = true;

    @Column(name= "account_not_locked")
    @Builder.Default
    private Boolean accountNotLocked = true;

    @Column(name= "credentials_not_expired")
    @Builder.Default
    private Boolean credentialsNotExpired = true;

}
