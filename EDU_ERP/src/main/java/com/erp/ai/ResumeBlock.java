package com.erp.ai;

public class ResumeBlock {

    private String heading;

    private String content;

    private int start;

    private int end;

    public ResumeBlock() {
    }

    public ResumeBlock(String heading,
                       String content,
                       int start,
                       int end) {

        this.heading = heading;
        this.content = content;
        this.start = start;
        this.end = end;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

}