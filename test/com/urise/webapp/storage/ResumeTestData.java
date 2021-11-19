package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ResumeTestData {

    Resume createResume(String uuid, String fullName) {
        Resume res = new Resume(uuid, fullName);
        fillContacts(res);


        void fillSections () {


        }
        return res;
    }

    void fillContacts(res) {

    }

    public static void main(String[] args) {


    }

}
