package com.erp.ai.parser;

import com.erp.ai.DetectedHeading;
import com.erp.ai.ResumeHeading;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class HeadingDetector {

    public List<DetectedHeading> detect(String text) {

        String lower = text.toLowerCase();

        List<DetectedHeading> headings = new ArrayList<>();

        detectGroup(lower, ResumeHeading.SUMMARY, "SUMMARY", headings);
        detectGroup(lower, ResumeHeading.EDUCATION, "EDUCATION", headings);
        detectGroup(lower, ResumeHeading.SKILLS, "SKILLS", headings);
        detectGroup(lower, ResumeHeading.PROJECTS, "PROJECTS", headings);
        detectGroup(lower, ResumeHeading.EXPERIENCE, "EXPERIENCE", headings);
        detectGroup(lower, ResumeHeading.ACHIEVEMENTS, "ACHIEVEMENTS", headings);
        detectGroup(lower, ResumeHeading.CERTIFICATIONS, "CERTIFICATIONS", headings);
        detectGroup(lower, ResumeHeading.LANGUAGES, "LANGUAGES", headings);
        detectGroup(lower, ResumeHeading.HOBBIES, "HOBBIES", headings);

        headings.sort(Comparator.comparingInt(DetectedHeading::getIndex));

        return headings;
    }

    private void detectGroup(String text,
                             List<String> aliases,
                             String type,
                             List<DetectedHeading> list) {

        for (String alias : aliases) {

            int index = text.indexOf(alias.toLowerCase());

            if (index != -1) {

                list.add(new DetectedHeading(type, index));

                break;
            }

        }

    }

}