<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<header class="container d-flex flex-wrap align-items-center justify-content-md-between py-3 mb-4 border-bottom fixed-top bg-white" >
    <span class="col-md-4 d-flex">
        <a href="index.jsp" class="d-flex text-dark text-decoration-none fs-4 fw-bold">
          <span class="">Test</span>
          <span class="bg-primary text-white rounded">Hub</span>
        </a>
    </span>

    <ul class="nav col-md-auto mb-md-0">
        <li><a href="index.jsp" class="nav-link px-2 link-dark"><fmt:message key="home"/></a></li>
        <li><a href="aboutUs.jsp" class="nav-link px-2 link-dark"><fmt:message key="about"/></a></li>
        <li><a href="contacts.jsp" class="nav-link px-2 link-dark"><fmt:message key="contacts"/></a></li>
    </ul>

    <div class="col-md-4 d-flex justify-content-end">
        <c:choose>
            <c:when test="${sessionScope.role eq 'ADMIN'}">
                <div class="dropdown">
                    <button class="btn border-0 dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <fmt:message key="tests"/>
                    </button>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="controller?action=view-tests"><fmt:message key="solve.tests"/></a></li>
                        <li><a class="dropdown-item" href="createTest.jsp"><fmt:message key="create.test"/></a></li>
                        <li><a class="dropdown-item" href="#"><fmt:message key="find.test"/></a></li>
                        <li><a class="dropdown-item" href="#"><fmt:message key="view.results"/></a></li>
                    </ul>
                </div>
                <a href="findUser.jsp" class="nav-link link-dark px-2 my-auto"><fmt:message key="find.student"/></a>
                <a href="profile.jsp" class="nav-link link-dark px-2 my-auto border-end"><fmt:message key="profile"/></a>
            </c:when>
            <c:when test="${sessionScope.role eq 'STUDENT'}">
                <div class="dropdown">
                    <button class="btn border-0 dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <fmt:message key="tests"/>
                    </button>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="#"><fmt:message key="solve.tests"/></a></li>
                        <li><a class="dropdown-item" href="#"><fmt:message key="view.results"/></a></li>
                    </ul>
                </div>
                <a href="profile.jsp" class="nav-link link-dark px-2 my-auto border-end"><fmt:message key="profile"/></a>
            </c:when>
            <c:when test="${sessionScope.role eq 'BLOCKED'}">
                <p class="text-danger my-auto px-2"><fmt:message key="blocked.account"/></p>
                <a href="profile.jsp" class="nav-link link-dark px-2 my-auto border-end"><fmt:message key="profile"/></a>
            </c:when>
        </c:choose>

        <c:if test="${sessionScope.loggedUser eq null}">
            <a href="signIn.jsp" class="btn btn-outline-primary me-2" role="button"><fmt:message key="sign.in"/></a>
            <a href="signUp.jsp" class="btn btn-primary me-3" role="button"><fmt:message key="sign.up"/></a>
        </c:if>

        <div class="my-auto ms-1">
            <form method="POST">
                <label>
                    <select class="border-0 rounded" name="locale" onchange='submit();'>
                        <option value="en" ${sessionScope.locale eq 'en' ? 'selected' : ''}>
                            EN
                        </option>
                        <option value="uk_UA" ${sessionScope.locale eq 'uk_UA' ? 'selected' : ''}>
                            UA
                        </option>
                    </select>
                </label>
            </form>
        </div>
    </div>

</header>
