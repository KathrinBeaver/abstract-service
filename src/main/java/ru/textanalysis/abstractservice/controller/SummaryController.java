package ru.textanalysis.abstractservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.textanalysis.abstractservice.model.TextDto;
import ru.textanalysis.abstractservice.payload.SummaryResponse;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/summary")
public class SummaryController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @PostMapping("/create")
    public SummaryResponse createSummary(@RequestBody TextDto text) {
        String textEncoded = text.getText();
        String fullText = new String(Base64.getUrlDecoder().decode(textEncoded), Charset.forName("UTF-8"));
        logger.info(fullText);

        // Логика получения реферата и ключевых слов

        String summary = "В траве сидел кузнечик"; // полученный реферат
        List<String> keyWords = new ArrayList<String>(); // полученный список ключевых слов
        keyWords.add("кузнечик");
        keyWords.add("трава");

        Base64.Encoder encoder = Base64.getUrlEncoder();

        String summaryEncoded = null;
        try {
            summaryEncoded = encoder.encodeToString(summary.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        List<String> keyWordsEncoded = new ArrayList<String>();
        for (String keyWord: keyWords) {
            try {
                keyWordsEncoded.add(encoder.encodeToString(keyWord.getBytes("utf-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return new SummaryResponse(summaryEncoded, keyWordsEncoded);
    }

}
