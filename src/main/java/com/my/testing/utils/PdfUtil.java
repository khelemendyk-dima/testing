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

/**
 * Create required PDF docs with itext PDF library
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class PdfUtil {
    private static final Logger logger = LogManager.getLogger(PdfUtil.class);
    private final ServletContext servletContext;
    /** Use this font fo cyrillic */
    private static final String FONT = "fonts/arial.ttf";
    private static final int TITLE_SIZE = 20;
    private static final int QUESTION_SIZE = 14;
    private static final Paragraph LINE_SEPARATOR = new Paragraph(new Text("\n"));

    /**
     * @param servletContext to properly define way to font file
     */
    public PdfUtil(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * Creates PDF document with test name, questions and correct answers in the end of the file.
     * @param test test entity to be placed in the document
     * @param questions list of questions to be placed in the document
     * @param answers list of answers to be placed in the document
     * @return outputStream to place it in response
     */
    public ByteArrayOutputStream createTestPdf(TestDTO test, List<QuestionDTO> questions, List<AnswerDTO> answers) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Document document = getDocument(output);

        document.add(getTestNameTitle(test.getName()));
        document.add(LINE_SEPARATOR);

        for (int i = 0; i < questions.size(); i++) {
            QuestionDTO question = questions.get(i);
            document.add(getQuestion((i + 1), question.getText()));
            document.add(getAnswersTable(question.getId(), answers));
        }

        document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        document.add(new Paragraph(new Text("Answers(Відповіді)")).setBold());
        document.add(getCorrectAnswers(questions, answers));

        document.close();

        return output;
    }

    /**
     * Creates document with A4 page size and font that supports cyrillic
     * @param output to create Document based on output
     * @return Document
     */
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

    /**
     * @return font that supports cyrillic or null if not able to load it
     */
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

    /**
     * @param testName name of the test
     * @return Paragraph that contains centered bold test name of the TITLE_SIZE font size
     */
    private Paragraph getTestNameTitle(String testName) {
        return new Paragraph(new Text(testName))
                .setFontSize(TITLE_SIZE)
                .setTextAlignment(TextAlignment.CENTER)
                .setBold();
    }

    /**
     * @param questionNumber sequence number of the question in the test
     * @param questionText question text
     * @return Paragraph that represents question text
     */
    private Paragraph getQuestion(int questionNumber, String questionText) {
        return new Paragraph(new Text(String.format("%s. %s", questionNumber, questionText)))
                .setFontSize(QUESTION_SIZE)
                .setBold();
    }

    /**
     * Creates table with two columns and transparent borders that represents answers for concrete question.
     * @param questionId id of the question
     * @param answers list of answers that must be added
     * @return Table with answers
     */
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

    /**
     * Creates paragraph with correct answers on the test
     * @param questions list of questions that need to be answered
     * @param answers list of answers on the questions
     * @return Paragraph with correct answers
     */
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
