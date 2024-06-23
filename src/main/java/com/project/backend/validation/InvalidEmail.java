package com.project.backend.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = InvalidEmailImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface InvalidEmail {

	public String message() default "Invalid Email or Password";

	public Class<?>[] groups() default {};

	public Class<? extends Payload>[] payload() default {};
}
