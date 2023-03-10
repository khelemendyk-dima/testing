package com.my.testing.controller.actions.implementation.admin;

import com.my.testing.controller.actions.Action;
import com.my.testing.controller.context.AppContext;
import com.my.testing.dto.*;
import com.my.testing.exceptions.ServiceException;
import com.my.testing.model.services.*;
import com.my.testing.utils.PdfUtil;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;

import java.io.*;
import java.util.*;

import static com.my.testing.controller.actions.ActionUtil.getActionToRedirect;
import static com.my.testing.controller.actions.constants.ActionNames.SEARCH_TEST_ACTION;
import static com.my.testing.controller.actions.constants.Parameters.*;

/**
 * This is TestToPdfAction class. Accessible by admin. Allows to download test with correct answers in the end.
 * Implements PRG pattern
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class TestToPdfAction implements Action {
    private final Logger logger = LogManager.getLogger(TestToPdfAction.class);
    private final TestService testService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final PdfUtil pdfUtil;

    /**
     * @param appContext contains TestService, QuestionService, AnswerService, PDFUtil instances to use in action
     */
    public TestToPdfAction(AppContext appContext) {
        testService = appContext.getTestService();
        questionService = appContext.getQuestionService();
        answerService = appContext.getAnswerService();
        pdfUtil = appContext.getPdfUtil();
    }

    /**
     * Gets test's id from request. Then gets test, questions and answers via service.
     * Creates PDF document and sets it to response
     *
     * @param request to get test's id
     * @param response to set PDF document there
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String testId = request.getParameter(TEST_ID);
        TestDTO testDTO = testService.getById(testId);
        List<QuestionDTO> questions = questionService.getAllByTestId(testId);
        List<AnswerDTO> answers = new ArrayList<>();
        for (QuestionDTO questionDTO : questions) {
            answers.addAll(answerService.getAllByQuestionId(String.valueOf(questionDTO.getId())));
        }
        ByteArrayOutputStream testPdf = pdfUtil.createTestPdf(testDTO, questions, answers);
        setResponse(testId, response, testPdf);
        return getActionToRedirect(SEARCH_TEST_ACTION, ID, testId);
    }

    /**
     * Sets test in response to download. Configure response to download PDF document
     *
     * @param testId to name the document
     * @param response to set test there
     * @param output output stream that contains PDF document
     */
    private void setResponse(String testId, HttpServletResponse response, ByteArrayOutputStream output) {
        response.setContentType("application/pdf");
        response.setContentLength(output.size());
        response.setHeader("Content-Disposition", "attachment; filename=\"test-" + testId + ".pdf\"");
        try (OutputStream outputStream = response.getOutputStream()) {
            output.writeTo(outputStream);
            outputStream.flush();
            String infoString = String.format("Test (id = %s) was downloaded", testId);
            logger.info(infoString);
        } catch (IOException e) {
            logger.error(String.format("Couldn't set test to download because of %s", e.getMessage()));
        }
    }
}
