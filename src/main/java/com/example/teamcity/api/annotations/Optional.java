package com.example.teamcity.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
// Fields marked with this annotation will NOT be filled with random or parameterized generated data.
// The values of such fields should be set manually
public @interface Optional {
}
