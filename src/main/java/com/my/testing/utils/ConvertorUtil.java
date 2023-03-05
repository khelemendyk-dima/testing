package com.my.testing.utils;

import com.my.testing.dto.*;
import com.my.testing.model.entities.*;
import com.my.testing.model.entities.enums.*;
import lombok.*;

/**
 * Converts DTO to Entities and vise versa
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConvertorUtil {

    /**
     * Converts UserDTO into User
     * @param userDTO to convert
     * @return User
     */
    public static User convertDTOToUser(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .build();
    }

    /**
     * Converts User into UserDTO
     * @param user to convert
     * @return UserDTO
     */
    public static UserDTO convertUserToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .role(String.valueOf(Role.getRole(user.getRoleId())))
                .build();
    }

    /**
     * Converts TestDTO into Test
     * @param testDTO to convert
     * @return Test
     */
    public static Test convertDTOToTest(TestDTO testDTO) {
        return Test.builder()
                .id(testDTO.getId())
                .name(testDTO.getName())
                .subjectId(Subject.valueOf(testDTO.getSubject()).getValue())
                .difficultyId(Difficulty.valueOf(testDTO.getDifficulty()).getValue())
                .duration(testDTO.getDuration())
                .build();
    }

    /**
     * Converts Test into TestDTO
     * @param test to convert
     * @return TestDTO
     */
    public static TestDTO convertTestToTDO(Test test) {
        return TestDTO.builder()
                .id(test.getId())
                .name(test.getName())
                .subject(String.valueOf(Subject.getSubject(test.getSubjectId())))
                .difficulty(String.valueOf(Difficulty.getDifficulty(test.getDifficultyId())))
                .duration(test.getDuration())
                .numberOfQueries(test.getNumberOfQueries())
                .build();
    }

    /**
     * Converts QuestionDTO into Question
     * @param questionDTO to convert
     * @return Question
     */
    public static Question convertDTOToQuestion(QuestionDTO questionDTO) {
        return Question.builder()
                .id(questionDTO.getId())
                .text(questionDTO.getText())
                .testId(questionDTO.getTestId())
                .build();
    }

    /**
     * Converts Question into QuestionDTO
     * @param question to convert
     * @return QuestionDTO
     */
    public static QuestionDTO convertQuestionToDTO(Question question) {
        return QuestionDTO.builder()
                .id(question.getId())
                .text(question.getText())
                .testId(question.getTestId())
                .build();
    }

    /**
     * Converts AnswerDTO into Answer
     * @param answerDTO to convert
     * @return Answer
     */
    public static Answer convertDTOToAnswer(AnswerDTO answerDTO) {
        return Answer.builder()
                .id(answerDTO.getId())
                .text(answerDTO.getText())
                .isCorrect(answerDTO.isCorrect())
                .questionId(answerDTO.getQuestionId())
                .build();
    }

    /**
     * Converts Answer into AnswerDTO
     * @param answer to convert
     * @return AnswerDTO
     */
    public static AnswerDTO convertAnswerToDTO(Answer answer) {
        return AnswerDTO.builder()
                .id(answer.getId())
                .text(answer.getText())
                .isCorrect(answer.isCorrect())
                .questionId(answer.getQuestionId())
                .build();
    }

    /**
     * Converts TestResultDTO into TestResult
     * @param testResultDTO to convert
     * @return TestResult
     */
    public static TestResult convertDTOToTestResult(TestResultDTO testResultDTO) {
        return TestResult.builder()
                .id(testResultDTO.getId())
                .userId(testResultDTO.getUserId())
                .testId(testResultDTO.getTestId())
                .result(testResultDTO.getResult())
                .testName(testResultDTO.getTestName())
                .build();
    }

    /**
     * Converts TestResult into TestResultDTO
     * @param testResult to convert
     * @return TestResultDTO
     */
    public static TestResultDTO convertTestResultToDTO(TestResult testResult) {
        return TestResultDTO.builder()
                .id(testResult.getId())
                .userId(testResult.getUserId())
                .testId(testResult.getTestId())
                .result(testResult.getResult())
                .testName(testResult.getTestName())
                .build();
    }
}
