<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/jdp/common/taglibs.jsp" %>
<c:set var="httpParamSubMenuId" value="<%=com.lmiky.jdp.constants.Constants.HTTP_PARAM_SUBMENU_ID %>"/>
<c:set var="httpParamOpenMode" value="<%=com.lmiky.jdp.form.controller.FormController.HTTP_PARAM_FORM_OEPNMODE %>"/>
<c:set var="readOpenMode" value="<%=com.lmiky.jdp.form.controller.FormController.OPEN_MODE_READ %>"/>
<c:set var="editOpenMode" value="<%=com.lmiky.jdp.form.controller.FormController.OPEN_MODE_EDIT %>"/>
<c:set var="createOpenMode" value="<%=com.lmiky.jdp.form.controller.FormController.OPEN_MODE_CTEATE %>"/>
<c:set var="defaultDateFormater" value="<%=(String)application.getAttribute(com.lmiky.jdp.constants.Constants.CONTEXT_KEY_FORMAT_DATE) %>"/>
<c:set var="defaultDateTimeFormater" value="<%=(String)application.getAttribute(com.lmiky.jdp.constants.Constants.CONTEXT_KEY_FORMAT_DATETIME) %>"/>
<%@ include file="/jdp/common/scripts.jsp" %>
<%@ include file="/jdp/common/style.jsp" %>