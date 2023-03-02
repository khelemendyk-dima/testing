package com.my.testing.utils;

import com.itextpdf.kernel.font.*;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.*;
import com.my.testing.dto.*;
import jakarta.servlet.ServletContext;
import org.apache.logging.log4j.*;

import java.io.*;
import java.net.URL;
import java.util.List;

public class PdfUtil {
    private static final Logger logger = LogManager.getLogger(PdfUtil.class);
    private final ServletContext servletContext;
    private static final String FONT = "fonts/arial.ttf";
    private static final int TITLE_SIZE = 20;
    private static final int QUESTION_SIZE = 14;
    private static final Paragraph LINE_SEPARATOR = new Paragraph(new Text("\n"));

    public PdfUtil(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public ByteArrayOutputStream createTestPdf(TestDTO test, List<QuestionDTO> questions, List<AnswerDTO> answers) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Document document = getDocument(output);

        document.add(getTestNameTitle(test.getName()));
        document.add(LINE_SEPARATOR);

        for (int i = 0; i < questions.size(); i++) {
            QuestionDTO question = questions.get(i);
            document.add(getQuestion((i + 1), question));
            document.add(getAnswersTable(question.getId(), answers));
        }

        document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        document.add(new Paragraph(new Text("Answers(Відповіді)")).setBold());
        document.add(getCorrectAnswers(questions, answers));

        document.close();

        return output;
    }

    private Document getDocument(ByteArrayOutputStream output) {
        PdfWriter writer = new PdfWriter(output);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);
        PdfFont font = getPdfFont();
        if (font != null) {
            document.setFont(font);
        }

        return document;
    }

    private PdfFont getPdfFont() {
        try {
            URL resource = servletContext.getResource(FONT);
            String fontUrl = resource.getFile();
            return PdfFontFactory.createFont(fontUrl);
        } catch (IOException e) {
            logger.warn(String.format("Couldn't load font, will use default one because of %s", e.getMessage()));
            return null;
        }
    }

    private Paragraph getTestNameTitle(String testName) {
        return new Paragraph(new Text(testName))
                .setFontSize(TITLE_SIZE)
                .setTextAlignment(TextAlignment.CENTER)
                .setBold();
    }

    private Paragraph getQuestion(int questionNumber, QuestionDTO question) {
        return new Paragraph(new Text(String.format("%s. %s", questionNumber, question.getText())))
                .setFontSize(QUESTION_SIZE)
                .setBold();
    }

    private Table getAnswersTable(long questionId, List<AnswerDTO> answers) {
        Table table = new Table(UnitValue.createPercentArray(new float[] {1, 1}))
                .setWidth(UnitValue.createPercentValue(100))
                .setFixedLayout();
        for (AnswerDTO answer : answers) {
            if (questionId == answer.getQuestionId()) {
                table.addCell(new Cell().add(new Paragraph("▢ " + answer.getText()))
                        .setBorder(Border.NO_BORDER));
            }
        }

        return table.setMarginLeft(30);
    }

    private Paragraph getCorrectAnswers(List<QuestionDTO> questions, List<AnswerDTO> answers) {
        StringBuilder correctAnswers = new StringBuilder();

        for (int i = 0; i < questions.size(); i++) {
            QuestionDTO question = questions.get(i);
            correctAnswers.append(i+1).append(". ");

            for (AnswerDTO answer : answers) {
                if (question.getId() == answer.getQuestionId() && answer.isCorrect()) {
                    correctAnswers.append(answer.getText()).append("; ");
                }
            }

            correctAnswers.append("\n");
        }

        return new Paragraph(new Text(correctAnswers.toString()));
    }
}
