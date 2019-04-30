package ru.textanalysis.abstractservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.library.text.word.Word;
import ru.textanalysis.tfwwt.jmorfsdk.JMorfSdk;
import summarization.MethodsOfSummarizationAndElementsOfText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SummaryService {

    private MethodsOfSummarizationAndElementsOfText methods;

    @Autowired
    private MorfologyService morfologyService;

    @Autowired
    public SummaryService(){
        methods = new MethodsOfSummarizationAndElementsOfText();
    }

    public String getSummary(String fullText, int percent) {
        // Логика получения реферата и ключевых слов
        Map<Integer, String> referat = methods.getReferatOfMethodSymmetric(fullText,percent);
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

    public String getSmartSummary(String fullText, String markers, int procentOfText, int numberOfMethod) {
        MethodsOfSummarizationAndElementsOfText mS = new MethodsOfSummarizationAndElementsOfText();
        Map<Integer, String> summaryMap = new HashMap<>();

        switch (numberOfMethod){
            case 1:
                summaryMap = mS.getReferatOfMethodStatistic(fullText, procentOfText);
                break;
            case 2:
                summaryMap = mS.getReferatOfMethodSymmetric(fullText, procentOfText);
                break;
            case 3:
                summaryMap = mS.getReferatOfMethodPosition(fullText);
                break;
            case 4:
                summaryMap = mS.getReferatOfMethodPositionAndStatistic(fullText, procentOfText);
                break;
            case 5:
                summaryMap = mS.getReferatOfMethodPositionAndSymmetric(fullText, procentOfText);
                break;
            case 6:
                summaryMap = mS.getReferatOfMethodIndicator(fullText, markers, procentOfText);
                break;
            case 7:
                summaryMap = mS.getReferatOfMethodIndicatorAndStatistic(fullText, markers, procentOfText);
                break;
            case 8:
                summaryMap = mS.getRefeartOfMethodIndicatorAndSymmetric(fullText, markers, procentOfText);
                break;
            case 9:
                summaryMap = mS.getReferatOfMethodIntegration(fullText, markers, procentOfText);
                break;
            case 10:
                summaryMap = mS.getReferatOfMethodIntegrationRandom(fullText, markers, procentOfText);
                break;
        }
        String summary = mS.getReferatToString(summaryMap);

        return summary;
    }

    public List<Word> getSmartKeyWords (String fullText) {
        MethodsOfSummarizationAndElementsOfText ms = new MethodsOfSummarizationAndElementsOfText();
        List<Word> keyWords = ms.getKeyWords(fullText);
        return keyWords;
    }
}
