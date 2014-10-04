<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/jdp/common/common.jsp" %>
<%@ page import="com.lmiky.jdp.area.controller.AreaController" %>
<script type="text/javascript">
	<!--
		$(document).ready(function() {
			<c:choose>
				<c:when test="${(openMode == 'edit' or openMode == 'create') and (not empty pojo.id)}">
					parent.reAsyncChildNodes('<%=AreaController.AREA_TYPE_PROVINCE%>${param.parentId}', '<%=AreaController.AREA_TYPE_CITY%>${pojo.id}');
				</c:when>
				<c:otherwise>
					parent.reAsyncChildNodes('<%=AreaController.AREA_TYPE_PROVINCE%>${param.parentId}');
				</c:otherwise>
			</c:choose>
		});
	//-->
</script>
<div class="control-group">
	<label  class="mini-control-label" style="width: 60px;">名称 <span class="req">*</span></label>
	<div class="controls mini-controls">
		<input name="name" type="text" value="${pojo.name}" class="larger"/>
	</div>
</div>
<div class="form-actions mini-form-actions">
	<button type="submit" class="btn btn-primary"><c:choose><c:when test="${openMode == 'edit'}">修改</c:when><c:otherwise>添加</c:otherwise></c:choose></button>
</div>