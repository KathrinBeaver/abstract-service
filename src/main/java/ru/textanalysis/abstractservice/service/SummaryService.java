package ru.textanalysis.abstractservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.library.text.word.Word;
import ru.textanalysis.tfwwt.jmorfsdk.JMorfSdk;
import summarizaton.MethodsOfSummarization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SummaryService {

    private MethodsOfSummarization methods;

    @Autowired
    private MorfologyService morfologyService;

    @Autowired
    public SummaryService(){
        methods = new MethodsOfSummarization();
    }

    public String getSummary(String fullText) {
        // Логика получения реферата и ключевых слов
        Map<Integer, String> referat = methods.getReferatOfMethodSymmetric(fullText,50);
        String summary = methods.getReferatToString(referat);
        return summary;
    }

    public List<String> getKeyWords(String fullText){
        List<String> kWords = new ArrayList<>();

        List<Word> wKeyWords = methods.getKeyWords(fullText);
        List<List<String>> sentences = methods.getWordsInSentences(fullText);
        List<List<Word>> initSentences = methods.getWordsInSentencesInInitialForm(fullText);

        for(List<Word> initSentence : initSentences){
            for(Word initWord : initSentence){
                for(Word wKeyWord : wKeyWords){
                    if (wKeyWord.getWord().equals(initWord.getWord())){
                        kWords.add(sentences
                                        .get(initSentences.indexOf(initSentence))
                                        .get(initSentence.indexOf(initWord)));
                    }
                }
            }
        }
        return kWords;
    }
}
