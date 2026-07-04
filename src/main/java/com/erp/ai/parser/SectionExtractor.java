package com.erp.ai.parser;

import com.erp.ai.DetectedHeading;
import com.erp.ai.ResumeBlock;
import com.erp.ai.ResumeSection;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SectionExtractor {

    public ResumeSection extract(String text,
                                 List<DetectedHeading> headings) {

        ResumeSection resume = new ResumeSection();

        resume.setFullText(text);

        for (int i = 0; i < headings.size(); i++) {

            DetectedHeading current = headings.get(i);

            int start = current.getIndex();

            int end;

            if (i == headings.size() - 1) {

                end = text.length();

            } else {

                end = headings.get(i + 1).getIndex();

            }

            String content = text.substring(start, end).trim();

            ResumeBlock block = new ResumeBlock(
                    current.getType(),
                    content,
                    start,
                    end
            );

//            resume.getBlocks().put(
//                    current.getType(),
//                    block
//            );

        }

        return resume;

    }

}