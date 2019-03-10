package ru.textanalysis.abstractservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SummaryResponse {
    private String summary;
    private List<String> keyWords;
}
