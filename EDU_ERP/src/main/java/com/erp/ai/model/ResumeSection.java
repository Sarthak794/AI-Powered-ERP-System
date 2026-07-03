package com.erp.ai.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.erp.ai.ResumeBlock;

public class ResumeSection {

    private String fullText;

    private Map<String, ResumeBlock> blocks = new LinkedHashMap<>();

    private List<DetectedKeyword> detectedKeywords = new ArrayList<>();

    public ResumeSection() {
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public Map<String, ResumeBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(Map<String, ResumeBlock> blocks) {
        this.blocks = blocks;
    }

    public List<DetectedKeyword> getDetectedKeywords() {
        return detectedKeywords;
    }

    public void setDetectedKeywords(List<DetectedKeyword> detectedKeywords) {
        this.detectedKeywords = detectedKeywords;
    }

    public ResumeBlock getBlock(String heading) {
        return blocks.get(heading);
    }

    public boolean hasBlock(String heading) {
        return blocks.containsKey(heading);
    }

}