package com.uas.pemrograman.validation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoProfanityValidator implements ConstraintValidator<NoProfanity, String> {

    private static final Set<String> PROFANITY_LIST = new HashSet<>(Arrays.asList(
            "anjing", "babi", "bangsat", "brengsek", "kontol", "memek", "perek", "goblok", "tolol", "idiot", "kampret", "setan",
            "asu", "tai", "sialan", "bajingan", "ngentot", "pepek", "titit", "jembut", "bencong", "banci",
            "lonte", "pelacur", "sundal", "bego", "bodoh", "dongo", "keparat", "monyet", "laknat", "brengsek",
            "bangke", "bahlul", "pecun", "pukimak", "pantek", "kimak", "anjrit", "tolol", "kampang", "peler"
    ));

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        String lowerCaseValue = value.toLowerCase();

        return PROFANITY_LIST.stream().noneMatch(lowerCaseValue::contains);
    }
}
