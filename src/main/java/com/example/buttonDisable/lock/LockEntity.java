package com.example.buttonDisable.lock;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "lock")
public class LockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String target;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "lock_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lockTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    public LockEntity() {

    }

    public LockEntity(String target, int userId, Date lockTime) {
        this.target = target;
        this.userId = userId;
        this.lockTime = lockTime;
    }
}