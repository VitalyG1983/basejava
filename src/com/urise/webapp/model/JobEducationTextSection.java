package com.urise.webapp.model;

public class JobEducationTextSection {
    private String JobEducationName;
    private String JobEducationDate;
    private String JobEducationText;

    public JobEducationTextSection(String jobEducationName, String jobEducationDate) {
        JobEducationName = jobEducationName;
        JobEducationDate = jobEducationDate;
    }

    public String getJobEducationName() {
        return JobEducationName;
    }

    public String getJobEducationDate() {
        return JobEducationDate;
    }

    public void setJobEducationText(String jobEducationText) {
        JobEducationText = jobEducationText;
    }

    public String getJobEducationText() {
        return JobEducationText;
    }
}