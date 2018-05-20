package com.example.buttonDisable.thymeleaf.common;

import com.example.buttonDisable.lock.LockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import java.util.HashSet;
import java.util.Set;

@Component
public class SampleDialect extends AbstractProcessorDialect {

    private static final String DIALECT_NAME = "Sample Dialect";
    @Autowired
    private LockService lockService;


    public SampleDialect() {
        super(DIALECT_NAME, "sample", StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<>();
        processors.add(new LockButtonTagProcessor(dialectPrefix, lockService));
        return processors;
    }
}
