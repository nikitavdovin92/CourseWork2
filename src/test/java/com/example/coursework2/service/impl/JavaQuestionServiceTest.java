package com.example.coursework2.service.impl;

import com.example.coursework2.exception.QuestionAlreadyExistsException;
import com.example.coursework2.exception.QuestionNotFoundException;
import com.example.coursework2.exception.QuestionsAreEmptyException;
import com.example.coursework2.model.Question;
import com.example.coursework2.service.QuestionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;


public class JavaQuestionServiceTest {

    private final QuestionService questionService = new com.example.coursework2.service.impl.JavaQuestionService();

    @BeforeEach
    public void beforeEach() {
        questionService.add(new Question("Q1", "А1"));
        questionService.add(new Question("Q2", "А2"));
        questionService.add(new Question("Q3", "А3"));
    }

    @AfterEach
    public void afterEach() {
        new HashSet<>(questionService.getAll()).forEach(question -> questionService.remove(question));
    }

    @Test
    public void add1Test() {
        int beforeCount = questionService.getAll().size();
        Question question = new Question("Q4", "А4");


        Assertions.assertThat(questionService.add(question))
                .isEqualTo(question)
                .isIn(questionService.getAll());
        Assertions.assertThat(questionService.getAll()).hasSize(beforeCount + 1);

    }

    @Test
    public void add2Test() {
        int beforeCount = questionService.getAll().size();
        Question question = new Question("Q4", "А4");


        Assertions.assertThat(questionService.add("Q4", "A4"))
                .isEqualTo(question)
                .isIn(questionService.getAll());
        Assertions.assertThat(questionService.getAll()).hasSize(beforeCount + 1);

    }

    @Test
    public void add1NegativeTest() {
        Question question = new Question("Q1", "А1");


        Assertions.assertThatExceptionOfType(QuestionAlreadyExistsException.class)
                .isThrownBy(()-> questionService.add(question));

    }

    @Test
    public void add2NegativeTest() {
        Assertions.assertThatExceptionOfType(QuestionAlreadyExistsException.class)
                .isThrownBy(()-> questionService.add("Q1", "А1"));

    }

    @Test
    public void removeTest() {
        int beforeCount = questionService.getAll().size();
        Question question = new Question("Q2", "А2");


        Assertions.assertThat(questionService.remove(question))
                .isEqualTo(question)
                .isNotIn(questionService.getAll());
        Assertions.assertThat(questionService.getAll()).hasSize(beforeCount - 1);

    }

    @Test
    public void removeNegativeTest() {
        Assertions.assertThatExceptionOfType(QuestionNotFoundException.class)
                .isThrownBy(()-> questionService.remove(new Question("Q4", "A4")));

    }

    @Test
    public void getAllTest() {
        Assertions.assertThat(questionService.getAll())
                .hasSize(3)
                .containsExactlyInAnyOrder(
                        new Question("Q1", "А1"),
                        new Question("Q3", "А3"),
                        new Question("Q2", "А2")
                );
    }

    @Test
    public void getRandomQuestionTest() {
        Assertions.assertThat(questionService.getRandomQuestion())
                .isNotNull()
                .isIn(questionService.getAll());
    }

    @Test
    public void getRandomQuestionNegativeTest() {
        afterEach();
        Assertions.assertThatExceptionOfType(QuestionsAreEmptyException.class)
                .isThrownBy(()->questionService.getRandomQuestion());
    }



}
