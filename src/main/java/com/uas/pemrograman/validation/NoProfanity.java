package com.uas.pemrograman.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoProfanityValidator.class)
public @interface NoProfanity {

    String message() default "Teks mengandung kata-kata yang tidak pantas.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
