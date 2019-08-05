<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="body" fragment="true" %>

<c:if test="${empty errorModel}">
	<jsp:invoke fragment="body"/>
</c:if>