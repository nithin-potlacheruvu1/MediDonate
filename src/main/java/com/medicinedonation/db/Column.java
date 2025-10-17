package com.medicinedonation.db;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    String name();
    String type();
    boolean primaryKey() default false;
    boolean autoIncrement() default false;
    boolean notNull() default false;
    boolean unique() default false;
}
