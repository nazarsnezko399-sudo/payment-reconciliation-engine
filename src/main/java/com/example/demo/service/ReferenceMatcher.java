package com.example.demo.service;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Optional;

@Component
public class ReferenceMatcher {
    private static final String ORDER_PATTERN = "ORD-\\d+";

    public Optional<String> extractOrderNumber(String input){
        if (input == null || input.isEmpty()){
            return Optional.empty();
        }

        //compile pattern
        Pattern pattern = Pattern.compile(ORDER_PATTERN);
        //search pattern in text
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()){
            return Optional.of(matcher.group());
        }
        return Optional.empty();
    }
}
