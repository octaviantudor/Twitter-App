package com.cognizant.softvision.twitterapp.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE follows SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted=false")
@Table(name = "follows")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="from_user_id")
    private User from;

    @ManyToOne
    @JoinColumn(name="to_user_id")
    private User to;

    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;

    @Column(name= "is_deleted")
    @Builder.Default
    private Boolean deleted = Boolean.FALSE;
}
