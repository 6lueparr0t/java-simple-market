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
    private Long id;

    @Column(length = 200, nullable = false, name = "name")
    private String name;

    @Column(unique = true, length = 200, nullable = false, name = "uid")
    private String uid;

    @Column(length = 400, nullable = false, name = "pw")
    private String pw;

    @Column(length = 400, nullable = false, name = "email")
    private String email;

    @Column(length = 400, name = "access")
    private String accessToken;

    @Column(name = "cdtm")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "adtm")
    private LocalDateTime accessDate;

    @Column(name = "udtm")
    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Builder
    public User(String name, String uid, String pw, String email) {
        this.name = name;
        this.uid = uid;
        this.pw = pw;
        this.email = email;
    }
}
