<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:edit-form gotoProfileAvailable="false"
				header="To complete registration please fill in the following form"
				isAgreeCheckboxAvailable="true"
				isCancelBtnAvailable="false"
				saveAction="/sign-up/complete"
				saveCaption="Complete registration"
				isUploadAvatarAvailable="false"/>