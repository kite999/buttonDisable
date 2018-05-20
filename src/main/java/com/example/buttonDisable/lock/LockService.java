package com.example.buttonDisable.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LockService {
    private final LockRepository lockRepository;

    @Autowired
    public LockService(LockRepository lockRepository) {
        this.lockRepository = lockRepository;
    }

    public List<LockEntity> findAll(){
        return lockRepository.findAll(new Sort(Sort.Direction.ASC,"id"));
    }

    public LockEntity getByTarget(String target) {
        if (target == null || target.isEmpty()) {
            return null;
        }

        return lockRepository.findByTarget(target);
    }

    public LockEntity saveOrUpdate(LockEntity lockEntity) {
        lockRepository.save(lockEntity);

        return lockEntity;
    }

    public void deleteById(Long id) {
        if(id == null) {
            return;
        }

        lockRepository.deleteById(id);
    }
}
