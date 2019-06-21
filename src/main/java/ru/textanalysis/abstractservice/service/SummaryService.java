package ru.textanalysis.abstractservice.service;

import methodOfSummarizationAndElementsOfText.MethodsOfSummarizationAndElementsOfText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.library.text.word.Word;

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

    public String getSmartSummary(String fullText, String markers, int percentOfText, int numberOfMethod) {
        MethodsOfSummarizationAndElementsOfText mS = new MethodsOfSummarizationAndElementsOfText();
        Map<Integer, String> summaryMap = new HashMap<>();

        switch (numberOfMethod){
            case 1:
                summaryMap = mS.getReferatOfMethodStatistic(fullText, percentOfText);
                break;
            case 2:
                summaryMap = mS.getReferatOfMethodSymmetric(fullText, percentOfText);
                break;
            case 3:
                summaryMap = mS.getReferatOfMethodPosition(fullText);
                break;
            case 4:
                summaryMap = mS.getReferatOfMethodPositionAndStatistic(fullText, percentOfText);
                break;
            case 5:
                summaryMap = mS.getReferatOfMethodPositionAndSymmetric(fullText, percentOfText);
                break;
            case 6:
                summaryMap = mS.getReferatOfMethodIndicator(fullText, markers, percentOfText);
                break;
            case 7:
                summaryMap = mS.getReferatOfMethodIndicatorAndStatistic(fullText, markers, percentOfText);
                break;
            case 8:
                summaryMap = mS.getRefeartOfMethodIndicatorAndSymmetric(fullText, markers, percentOfText);
                break;
            case 9:
                summaryMap = mS.getReferatOfMethodIntegration(fullText, markers, percentOfText);
                break;
            case 10:
                summaryMap = mS.getReferatOfMethodIntegrationRandom(fullText, markers, percentOfText);
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
