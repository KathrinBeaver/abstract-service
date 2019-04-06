package ru.textanalysis.abstractservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.textanalysis.tfwwt.jmorfsdk.JMorfSdk;
import ru.textanalysis.tfwwt.jmorfsdk.load.JMorfSdkLoad;

import java.util.Arrays;
import java.util.List;

@Service
public class SummaryService {

    @Autowired
    private MorfologyService morfologyService;

    @Autowired
    public SummaryService(){
    }

    public String getSummary(String fullText) {
        // Логика получения реферата и ключевых слов

        JMorfSdk jMorfSdk = morfologyService.getjMorfSdk();
        jMorfSdk.getMorfologyCharacteristics("столов");

        return "";
    }
}
