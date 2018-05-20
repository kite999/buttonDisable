package com.example.buttonDisable.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class LockController {
    private final LockService lockService;

    @Autowired
    public LockController(LockService lockService) {
        this.lockService = lockService;
    }

    @GetMapping("/lock/find/{target}")
    public LockEntity find(@PathVariable String target) {
        return this.lockService.getByTarget(target);
    }

    @GetMapping("/lock/islock/{target}")
    public String islock(@PathVariable String target) {
        //TODO セッションのユーザIDがLockEntityのユーザIDと同じならfalseで返却
        LockEntity lockEntity = this.lockService.getByTarget(target);
        if (lockEntity == null) {
            return "false";
        }
        return Integer.toString(lockEntity.getUserId());
    }

    @GetMapping("/lock/create/{target}")
    public LockEntity create(@PathVariable String target) {
        LockEntity lockEntity = this.lockService.getByTarget(target);
        if (lockEntity != null) {
            return null;
        }

        //useridにはセッションに保管しているユーザIDを入れる。ここはサンプルなので固定
        return this.lockService.saveOrUpdate(
                new LockEntity(target, 123, new Date()));
    }

    @GetMapping("/lock/delete/{target}")
    public LockEntity delete(@PathVariable String target) {
        LockEntity lockEntity = this.lockService.getByTarget(target);
        if (lockEntity == null) {
            return null;
        }
        this.lockService.deleteById(lockEntity.getId());
        return lockEntity;
    }
}
