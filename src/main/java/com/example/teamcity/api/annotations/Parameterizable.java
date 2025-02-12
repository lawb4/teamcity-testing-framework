package com.example.teamcity.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
// Fields marked with this annotation will be filled with a value, passed in a method
public @interface Parameterizable {
}
