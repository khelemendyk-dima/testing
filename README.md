PROJECT DESCRIPTION

The task of the final project is to develop a web application that supports the functionality according to the task variant.

**Testing**

There are roles: student and system administrator.

The student registers in the system and after registration can pass one or more Tests. There is a list of tests in the system. For the list it is necessary to implement:
- choice of tests on a particular subject;
- sorting tests by name;
- sorting tests by difficulty;
- sorting tests by number of queries.

The student chooses the test and passes it. A certain period of time is allocated for passing the test, which is set for each test separately. The student has a personal account, which displays registration information, as well as a list of passed tests with the results.

The system administrator:
- creates, deletes or edits tests;
- blocks, unblocks, edits the user.

When creating a test, the administrator:
- sets the test time;
- sets the complexity of the test;
- adds Questions to the test.

A question can have one or more correct answers. The result of the test is the percentage of questions that the student answered correctly in relation to the total number of questions (it is considered that the student answered the question correctly if his answer coincides exactly with the correct answers).