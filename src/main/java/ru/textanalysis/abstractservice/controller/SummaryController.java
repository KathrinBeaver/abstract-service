package ru.textanalysis.abstractservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.library.text.word.Word;
import ru.textanalysis.abstractservice.exception.EncodeException;
import ru.textanalysis.abstractservice.exception.KeyWordsException;
import ru.textanalysis.abstractservice.exception.SummaryException;
import ru.textanalysis.abstractservice.model.AbstractDataDto;
import ru.textanalysis.abstractservice.model.TextDto;
import ru.textanalysis.abstractservice.payload.SummaryResponse;
import ru.textanalysis.abstractservice.service.SummaryService;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/summary")
public class SummaryController {

    @Autowired
    private SummaryService summaryService;

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @PostMapping("/create")
    public SummaryResponse createSummary(@RequestBody TextDto text) {
        String textEncoded = text.getText();
        String fullText = new String(Base64.getUrlDecoder().decode(textEncoded), Charset.forName("UTF-8"));
        logger.info(fullText);
        fullText = fullText.replaceAll("\n{2,}","\n");

        String summary = "";
        try {
            summary = summaryService.getSummary(fullText, text.getPercent()); // полученный реферат
        } catch (Exception e){
            logger.error("Unable to get the summary");
            throw new SummaryException("Unable to get the summary", e);
        }

        List<String> keyWords = new ArrayList<>();
        try {
            keyWords = summaryService.getKeyWords(fullText); // полученный список ключевых слов
        } catch (Exception e){
            logger.error("Keywords list is empty");
            throw new KeyWordsException("Keywords list is empty", e);
        }

        Base64.Encoder encoder = Base64.getUrlEncoder();

        String summaryEncoded = null;
        try {
            summaryEncoded = encoder.encodeToString(summary.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("Encode error.");
            throw new EncodeException("Encode error", e);
        }

        List<String> keyWordsEncoded = new ArrayList<String>();
        for (String keyWord: keyWords) {
            try {
                keyWordsEncoded.add(encoder.encodeToString(keyWord.getBytes("utf-8")));
            } catch (UnsupportedEncodingException e) {
                logger.error("Encode error.");
                throw new EncodeException("Encode error", e);
            }
        }

        return new SummaryResponse(summaryEncoded, keyWordsEncoded);
    }

    @PostMapping("/do")
    public SummaryResponse doSummary(@RequestBody AbstractDataDto text) {
        String textEncoded = text.getText();
        String fullText = new String(Base64.getDecoder().decode(textEncoded), Charset.forName("UTF-8"));
        logger.info(fullText);
        fullText = fullText.replaceAll("\n{2,}","\n");

        String keyWordsInTextEncoder = text.getKeyWords();
        String allKeyWordsInText = new String(Base64.getDecoder().decode(keyWordsInTextEncoder), Charset.forName("UTF-8"));
        logger.info(allKeyWordsInText);

        int procent = text.getPercentOfText();
        logger.info(String.valueOf(procent));

        //номер метода реферирования
        int numberM = text.getNumberMethod();

        String summary = "";
        try {
            summary = summaryService.getSmartSummary(fullText, allKeyWordsInText, procent, numberM); // полученный реферат
        } catch (Exception e){
            logger.error("Unable to get the summary");
            throw new SummaryException("Unable to get the summary", e);
        }

        List<Word> keyWordsWord  = new ArrayList<>();
        try {
            keyWordsWord = summaryService.getSmartKeyWords(fullText); //ключевые слова
        } catch (Exception e){
            logger.error("Keywords list is empty");
            throw new KeyWordsException("Keywords list is empty", e);
        }
        List<String> keyWords = new ArrayList<>();
        for(int i = 0 ; i < keyWordsWord.size(); i++){
            keyWords.add(keyWordsWord.get(i).getWord());
        }

        Base64.Encoder encoder = Base64.getEncoder();

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
