package com.example.buttonDisable.lock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LockRepository extends JpaRepository<LockEntity,Long> {
    public LockEntity findByTarget(String target);
}
