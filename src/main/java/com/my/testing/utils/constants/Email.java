package com.my.testing.utils.constants;

public class Email {
    public static final String SUBJECT_GREETINGS = "Welcome to TestHub!";
    public static final String SUBJECT_NOTIFICATION = "TestHub notification!";
    public static final String HELLO = "Hello %s,<br>";
    public static final String INFORMATION = "We have some important information for you:";
    public static final String FOOTER = "Best wishes,<br>TestHub team";
    public static final String HELLO_UA = "Привіт %s,<br>";
    public static final String INFORMATION_UA = "У нас є важлива інформація для Вас:";
    public static final String FOOTER_UA = "З найкращими побажаннями,<br>команда TestHub";
    public static final String DOUBLE_ENTER = "<br><br>";
    public static final String MESSAGE_GREETINGS = HELLO +
            "Are you ready for a new brainstorm?<br>" +
            "Then you are in the right place! Just <a href=\"http://localhost:8080/testing/signIn.jsp\">sign in</a> and choose the suitable test for you. Our team develops new tests on different themes. Difficulty, number of questions and time for solving in test can differ. A question can have one or more correct answers. The result of the test is the percentage of questions that the student answered correctly in relation to the total number of questions.<br>" +
            "Thank you for choosing TestHub!<br>" +
            FOOTER +
            DOUBLE_ENTER +
            HELLO_UA +
            "Ви готові до нового мозкового штурму?<br>" +
            "Тоді ви в потрібному місці! Просто <a href=\"http://localhost:8080/testing/signIn.jsp\">увійдіть</a> і виберіть більш привабливий для себе тест. Наша команда розробляє нові тести на різні теми. Складність, кількість питань і час на розв'язування в тесті можуть відрізнятися. Питання може мати одну або декілька правильних відповідей. Результатом тесту є відсоток питань, на які користувач відповів правильно, по відношенню до загальної кількості запитань.<br>" +
            FOOTER_UA;

    public static final String MESSAGE_RESET_PASSWORD = HELLO +
            INFORMATION +
            DOUBLE_ENTER +
            "Your temporary password is %s. Do not forget to change it in your profile!" +
            DOUBLE_ENTER +
            "Enter your account <a href=\"http://localhost:8080/testing/signIn.jsp\">here</a>.<br>" +
            FOOTER +
            DOUBLE_ENTER +
            HELLO_UA +
            INFORMATION_UA +
            DOUBLE_ENTER +
            "Ваш тимчасовий пароль %s. Не забудьте змінити його у своєму профілі!" +
            DOUBLE_ENTER +
            "Введіть свій обліковий запис <a href=\"http://localhost:8080/testing/signIn.jsp\">тут</a>.<br>" +
            FOOTER_UA;

    private Email() {}
}
