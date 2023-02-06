package com.my.testing.utils;

import com.my.testing.dto.*;
import com.my.testing.model.entities.*;
import com.my.testing.model.entities.enums.*;
import org.apache.commons.text.WordUtils;

public final class ConvertorUtil {

    private ConvertorUtil() {}

    public static User convertDTOToUser(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .build();
    }

    public static UserDTO convertUserToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .role(String.valueOf(Role.getRole(user.getRoleId())))
                .build();
    }

    public static Test convertDTOToTest(TestDTO testDTO) {
        return Test.builder()
                .id(testDTO.getId())
                .name(testDTO.getName())
                .subjectId(Subject.valueOf(testDTO.getSubject()).getValue())
                .difficultyId(Difficulty.valueOf(testDTO.getDifficulty()).getValue())
                .duration(testDTO.getDuration())
                .build();
    }

    public static TestDTO convertTestToTDO(Test test) {
        return TestDTO.builder()
                .id(test.getId())
                .name(test.getName())
                .subject(WordUtils.capitalizeFully(String.valueOf(Subject.getSubject(test.getSubjectId()))))
                .difficulty(WordUtils.capitalizeFully(String.valueOf(Difficulty.getDifficulty(test.getDifficultyId()))))
                .duration(test.getDuration())
                .numberOfQueries(test.getNumberOfQueries())
                .build();
    }

    public static Question convertDTOToQuestion(QuestionDTO questionDTO) {
        return Question.builder()
                .id(questionDTO.getId())
                .text(questionDTO.getText())
                .testId(questionDTO.getTestId())
                .build();
    }

    public static QuestionDTO convertQuestionToDTO(Question question) {
        return QuestionDTO.builder()
                .id(question.getId())
                .text(question.getText())
                .testId(question.getTestId())
                .build();
    }

    public static Answer convertDTOToAnswer(AnswerDTO answerDTO) {
        return Answer.builder()
                .id(answerDTO.getId())
                .text(answerDTO.getText())
                .isCorrect(answerDTO.isCorrect())
                .questionId(answerDTO.getQuestionId())
                .build();
    }

    public static AnswerDTO convertAnswerToDTO(Answer answer) {
        return AnswerDTO.builder()
                .id(answer.getId())
                .text(answer.getText())
                .isCorrect(answer.isCorrect())
                .questionId(answer.getQuestionId())
                .build();
    }
}
