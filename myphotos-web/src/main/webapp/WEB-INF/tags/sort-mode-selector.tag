<%@ tag import="org.myphotos.domain.model.SortMode" %>
<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="popularPhoto"><%= SortMode.POPULAR_PHOTO.name().toLowerCase() %></c:set>
<c:set var="popularAuthor"><%= SortMode.POPULAR_AUTHOR.name().toLowerCase() %></c:set>

<div class="selected-wrapper small">
	<select id="sort-mode-selector">
		<option value="${popularPhoto}" ${sortMode == popularPhoto ? "selected" : "" } >
			Sort by photo popularity
		</option>
		<option value="${popularAuthor}" ${sortMode == popularAuthor ? "selected" : "" } >
			Sort by author populatiry
		</option>
	</select>
</div>