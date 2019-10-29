package me.qninh.kiwi.command;

public @interface Label {
    
    String name();

    String[] list() default {};

    LabelType type() default LabelType.LIST;
}