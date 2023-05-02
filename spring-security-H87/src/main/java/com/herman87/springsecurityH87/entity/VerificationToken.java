package com.herman87.springsecurityH87.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class VerificationToken {
    private static final int EXPIRATION_DATE = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expirationTime;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "USER_VERIFY_TOKEN"))
    private User user;

    public VerificationToken(User user, String token) {
        this.user = user;
        this.token = token;
        this.expirationTime = calculateExperitionDate();
    }

    public VerificationToken(String token) {
        this.token = token;
        this.expirationTime = calculateExperitionDate();
    }

    private Date calculateExperitionDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, EXPIRATION_DATE);
        return new Date(calendar.getTime().getTime());
    }
}
