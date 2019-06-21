package ru.textanalysis.abstractservice.model;

import lombok.Data;

@Data
public class AbstractDataDto {
    private String text;
    private String keyWords;
    private int percentOfText;
    private int numberMethod;
}
