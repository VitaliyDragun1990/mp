<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<header id="header">
	<div class="inner">
		<a href="javascript:void(0);" class="image avatar">
			<img src="${profile.avatarUrl}" alt="${profile.fullName}" />
		</a>
		<h1><strong>${profile.fullName}</strong></h1>
		<h4>${profile.jobTitle} in ${profile.location}</h4>
	</div>
</header>
<div id="main">
	<section>
		<fmt:parseDate value="${photos[0].created}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
		<fmt:formatDate type="DATE" dateStyle="SHORT" value="${parsedDateTime}" var="lastPhotoCreated" />
		
		<h3>${profile.photoCount} Photos | Last photo: ${lastPhotoCreated}</h3>
		<div id="photo-container" class="row" data-page="1" data-total-count="${profile.photoCount}" data-more-url="/photos/profile/more?profileId=${profile.id}">
			<jsp:include page="../fragment/more-photos.jsp"/>
		</div>
		<c:if test="${profile.photoCount > fn:length(photos)}">
			<div id="load-more-container">
				<hr />
				<div class="text-center">
					<a id="load-more-button" class="button small">Load more photos</a>
				</div>
			</div>
		</c:if>
	</section>
</div>