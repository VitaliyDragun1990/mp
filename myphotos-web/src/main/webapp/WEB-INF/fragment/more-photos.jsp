<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:forEach var="photo" items="${photos}" varStatus="status">
	<c:set var="classes" value="${status.index % 2 != (firstExists ? 1 : 0) ? '6u$ 12u$(xsmall)' : '6u 12u$(xsmall)'}" />
	<article class="${classes} photo-item not-init">
		<figure>
			<a href="${photo.largeUrl}" class="image fit thumb"> <img src="${photo.smallUrl}" alt="Photo" />
			</a>
			<figcaption>
				<p class="author">
					<c:choose>
						<c:when test="${profilePhotos}">
							<fmt:parseDate value="${photo.created}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
							<fmt:formatDate type="DATE" dateStyle="SHORT" value="${parsedDateTime}" var="uploadedDate" />
							<span class="upload-date"><i class="fa fa-calendar" aria-hidden="true"></i> Uploaded: ${uploadedDate} </span>
						</c:when>
						<c:otherwise>
							<a href="/${photo.profile.uid}" class="img"><img src="${photo.profile.avatarUrl}" alt="${photo.profile.fullName}" /></a>
							<span class="name"><a href="/${photo.profile.uid}">${photo.profile.fullName}</a></span>
						</c:otherwise>
					</c:choose>
				</p>
				<p class="stat">
					<span class="delim">|</span> <span><i class="fa fa-eye" aria-hidden="true"></i> ${photo.views } <span class="txt">Views</span> |</span>
					<span><i class="fa fa-download" aria-hidden="true"></i> ${photo.downloads } <span class="txt">Downloads</span></span>
				</p>
				<p class="actions">
					<a class="button special small fit" href="#">Download</a>
				</p>
			</figcaption>
		</figure>
	</article>
</c:forEach>