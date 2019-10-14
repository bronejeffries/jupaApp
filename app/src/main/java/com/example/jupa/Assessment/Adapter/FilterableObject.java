package com.example.jupa.Assessment.Adapter;

import com.example.jupa.Candidate.Candidate;
import com.example.jupa.Question.Question;

public class FilterableObject {

    Integer object_position;

    Candidate candidate;

    Question question;

    public Integer getObject_position() {
        return object_position;
    }

    public void setObject_position(Integer object_position) {
        this.object_position = object_position;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {

        this.candidate = candidate;

    }

    public Question getQuestion() {

        return question;

    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
