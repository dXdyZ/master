package com.specter.service;

import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.classfile.Code;
import org.hibernate.validator.internal.constraintvalidators.hv.CodePointLengthValidator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CodeService {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private List<String> codes = new LinkedList<>();

    public int collSize() {
        return codes.size();
    }

    public String getCode() {
        String code = generateCode();
        codes.add(code);
        //Планирования удаление элемента через 20 минут
        ScheduledFuture<?> scheduledTask = scheduler.schedule(() -> {
            clearCodes(code);
        }, 10, TimeUnit.MINUTES);
        return codes.stream().filter(codeFilter -> codeFilter.equals(code)).findFirst().get();
    }

    public boolean validationCode(String code) {
        //anyMatch - возвращает болево значение если хотя бы один элемент равен условию
        return codes.stream().anyMatch(validCode -> validCode.equals(code));
    }

    private String generateCode() {
        Random random = new Random();
        return String.valueOf(random.nextInt(9999));
    }

    private void clearCodes(String code) {
        codes.remove(code);
    }
}

