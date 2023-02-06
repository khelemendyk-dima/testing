<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>TestHub. <fmt:message key="tests"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="icon" href="img/favicon.ico">
    <script src="js/bootstrap.bundle.min.js"></script>
</head>

<body>

<jsp:include page="header.jsp"/>

<div class="container">

    <div class="text-center mx-auto" style="max-width: 1000px;">

        <h3 class="mb-4"><fmt:message key="tests"/></h3>

        <c:if test="${empty requestScope.tests}">
            <p class="fs-6"><fmt:message key="zero.tests"/></p>
        </c:if>

        <form action="controller" class="flex" method="GET">
            <input type="hidden" name="action" value="view-tests">
            <input type="hidden" name="offset" value="0">

            <div class="form-floating">
                <input class="form-control" type="text" name="name" id="name"
                       placeholder="Name" pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'&quot:.,!?\- ]{1,100}"
                       title="<fmt:message key="test.name.requirements"/>"
                       value="${param.name}">
                <label for="name"><fmt:message key="test.name"/></label>
            </div>
            <div class="d-flex justify-content-between">
                <div class="flex-column">
                    <label>
                        <select name="subject" class="form-select mt-2">
                            <option value=""><fmt:message key="any.subject"/></option>
                            <option value="ENGLISH" ${param.subject eq "ENGLISH" ? "selected" : ""}>
                                <fmt:message key="ENGLISH"/>
                            </option>
                            <option value="UKRAINIAN" ${param.subject eq "UKRAINIAN" ? "selected" : ""}>
                                <fmt:message key="UKRAINIAN"/>
                            </option>
                            <option value="MATH" ${param.subject eq "MATH" ? "selected" : ""}>
                                <fmt:message key="MATH"/>
                            </option>
                            <option value="HISTORY" ${param.subject eq "HISTORY" ? "selected" : ""}>
                                <fmt:message key="HISTORY"/>
                            </option>
                            <option value="BIOLOGY" ${param.subject eq "BIOLOGY" ? "selected" : ""}>
                                <fmt:message key="BIOLOGY"/>
                            </option>
                            <option value="CHEMISTRY" ${param.subject eq "CHEMISTRY" ? "selected" : ""}>
                                <fmt:message key="CHEMISTRY"/>
                            </option>
                            <option value="PHYSICS" ${param.subject eq "PHYSICS" ? "selected" : ""}>
                                <fmt:message key="PHYSICS"/>
                            </option>
                        </select>
                    </label>
                    <label>
                        <select name="sortField" class="form-select mt-2">
                            <option value=""><fmt:message key="sort.field"/></option>
                            <option value="name" ${param.sortField eq "name" ? "selected" : ""}>
                                <fmt:message key="test.name"/>
                            </option>
                            <option value="difficulty_id" ${param.sortField eq "difficulty_id" ? "selected" : ""}>
                                <fmt:message key="difficulty"/>
                            </option>
                            <option value="number_of_queries" ${param.sortField eq "number_of_queries" ? "selected" : ""}>
                                <fmt:message key="number.of.queries"/>
                            </option>
                        </select>
                    </label>
                    <label>
                        <select name="order" class="form-select mt-2">
                            <option value="ASC" ${param.order eq "ASC" ? "selected" : ""}>
                                <fmt:message key="ASC"/>
                            </option>
                            <option value="DESC" ${param.order eq "DESC" ? "selected" : ""}>
                                <fmt:message key="DESC"/>
                            </option>
                        </select>
                    </label>
                </div>

                <div class="flex-column text-end">
                    <label for="records"><fmt:message key="number.records"/></label>
                    <input class="col-2" type="number" min="1" name="records" id="records"
                           value="${not empty requestScope.records ? requestScope.records : "5"}">

                    <button type="submit" class="btn btn-primary mt-2 mb-3"><fmt:message key="submit"/></button>
                    <a class="btn btn-danger mt-2 mb-3" href="controller?action=view-tests"><fmt:message key="reset"/></a>
                </div>
            </div>
        </form>


        <div class="bd-example-snippet bd-code-snippet">
            <div class="bd-example">
                <table class="table table-striped" aria-label="report-table">
                    <thead>
                    <tr>
                        <th class="text-start" scope="col"><fmt:message key="test.name"/></th>
                        <th scope="col"><fmt:message key="subject"/></th>
                        <th scope="col"><fmt:message key="difficulty"/></th>
                        <th scope="col"><fmt:message key="questions"/></th>
                        <th scope="col"><fmt:message key="action"/></th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="test" items="${requestScope.tests}">
                        <tr>
                            <td class="text-start"><c:out value="${test.name}"/></td>
                            <td><c:out value="${test.subject}"/></td>
                            <td><c:out value="${test.difficulty}"/></td>
                            <td><c:out value="${test.numberOfQueries}"/></td>
                            <td>
                                <a class="link-dark" href="controller?action=search-test&id=${test.id}">
                                    <fmt:message key="view"/>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <c:set var="href" scope="request"
               value="controller?action=view-tests&name=${param.name}&subject=${param.subject}&sort=${param.sortField}&order=${param.order}&"/>

        <jsp:include page="pagination.jsp"/>
    </div>

</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
