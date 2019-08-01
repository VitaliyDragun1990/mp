<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>

<script src="/static/js/jquery.min.js"></script>
<script src="/static/js/jquery.poptrox.min.js"></script>
<script src="/static/js/skel.min.js"></script>
<script src="/static/js/util.js"></script>
<script src="/static/js/fine-uploader.js"></script>
<!--[if lte IE 8]><script src="/static/js/ie/respond.min.js"></script><![endif]-->
<script src="/static/js/main.js"></script>
<script src="/static/js/messages.js"></script>

<c:if test="${currentRequestUrl eq '/' }">
	<script src="https://apis.google.com/js/api:client.js"></script>
	<script>
		var googlePlusClientId = '${googlePlusClientId}';
	</script>
</c:if>
<script src="/static/js/app.js"></script>