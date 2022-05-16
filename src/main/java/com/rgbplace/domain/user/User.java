package com.rgbplace.domain.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@Table(name = "TBL_USER")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    @Column(unique = true, length = 100, nullable = false, name = "id")
    private String id;
    @Column(unique = true, length = 100, nullable = false, name = "name")
    private String name;
    @Column(unique = true, length = 300, nullable = false, name = "mail")
    private String mail;
    @Column(length = 100, nullable = false, name = "pswd")
    private String pswd;

    @Column(name = "cdtm")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "udtm")
    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Builder
    public User(String id, String name, String pswd) {
        this.id = id;
        this.name = name;
        this.pswd = pswd;
    }
}
