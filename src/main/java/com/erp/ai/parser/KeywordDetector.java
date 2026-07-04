package com.erp.ai.parser;

import com.erp.ai.ResumeKeywordLibrary;
import com.erp.ai.model.DetectedKeyword;
import com.erp.ai.model.ResumeSection;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class KeywordDetector {

    public void detect(ResumeSection resume) {

        List<DetectedKeyword> found =
                new ArrayList<>();

        String text =
                resume.getFullText().toLowerCase();

        for (Map.Entry<String,List<String>> category :
                ResumeKeywordLibrary.CATEGORY_KEYWORDS.entrySet()) {

            for(String keyword : category.getValue()){

                if(text.contains(keyword.toLowerCase())){

                    found.add(

                            new DetectedKeyword(

                                    category.getKey(),

                                    keyword

                            )

                    );

                }

            }

        }

        resume.setDetectedKeywords(found);

    }

}