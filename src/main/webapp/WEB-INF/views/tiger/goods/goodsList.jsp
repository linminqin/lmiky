<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jdp/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="/jdp/common/header.jsp"%>
		<%@ include file="/jdp/view/header.jsp"%>
	</head>
	<body>
		<form id="mainForm" action="<c:url value="/tiger/goods/list.shtml"/>" method="post">
			<input type="hidden" name="modulePath" value="${modulePath }"/>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="2" align="center" valign="top">
						<table width="98%" height="30" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td align="center" class="listTitle">
									&nbsp;<img src="${images }/jt-5.gif" width="16" height="16" align="absmiddle" />&nbsp;商品管理
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center" valign="top">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" align="center" valign="top">
						<table width="98%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									商品名称：<lhtml:propertyFilter inputType="text" compareType="LIKE" propertyName="title" styleClass="bian medium"/>
									&nbsp;
									<input class="btnClass" type="submit" value="查询" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center" valign="top">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<table width="98%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td align="left" valign="bottom" class="listMenu">
									<table border="0" cellpadding="0" cellspacing="0">
										<tr>
											<lauthority:checkAuthority authorityCode="tiger_goods_add">
												<td align="center">
														<table>
															<tr>
																<td align="center" class="btn_menu btnClass_td" onClick="openDialog('<c:url value="/tiger/goods/load.shtml?${httpParamOpenMode }=${createOpenMode }&modulePath=${modulePath }"/>', 800, 600)">添加</td>
															</tr>
														</table>
												</td>
											</lauthority:checkAuthority>
											<lauthority:checkAuthority authorityCode="tiger_goods_delete">
												<td align="center">
													<table>
														<tr>
															<td align="center" class="btn_menu btnClass_td" onClick="batchDelete('<c:url value="/tiger/goods/batchDelete.shtml"/>')">批量删除</td>
														</tr>
													</table>
												</td>
											</lauthority:checkAuthority>
											<td align="center">
												<jsp:include page="/jdp/include/favoriteMenu.jsp">
													<jsp:param value="tiger_goods_load" name="authorityCode"/>
												</jsp:include>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center" valign="top">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" align="center" valign="top">
						<table class="listContent"  width="98%" cellspacing="0" cellpadding="0" rules="cols" border="0">
							<tbody>
								<tr>
									<th class="no">&nbsp;</th>
									<th class="sortable sorted_title"><a href="javascript:pageSort('title')">商品名称</a></th>
									<th class="sortable sorted_salePrice"><a href="javascript:pageSort('salePrice')">销售价</a></th>
									<th>市场价</th>
									<th>优惠券折扣</th>
									<th>操作</th>
									<lauthority:checkAuthority authorityCode="tiger_goods_delete">
										<th>
											<input type="checkbox" name="batctSelectDelete"  id="batctSelectDelete" class="bian" value="" onclick="batchSelectDelete()"/>
										</th>
									</lauthority:checkAuthority>
								</tr>
								<c:forEach var="item" items="${page.items}" varStatus="status">
									<c:set var="rowClass" value="odd"/>
									<c:if test="${(status.index % 2) == 0}">
										<c:set var="rowClass" value="even"/>
									</c:if>
									<tr class="${rowClass } hover">
										<td>${status.count + (page.currentPage - 1) * page.pageSize}</td>
										<td>${item.title}</td>
										<td>${item.salePrice}</td>
										<td>${item.marketPrice}</td>
										<td>${item.couponDiscount}</td>
										<td>
											<lauthority:checkAuthority authorityCode="tiger_goods_modify">
												<a href="javascript:void(0)" class="td_2" onclick="openDialog('<c:url value="/tiger/goods/load.shtml?id=${item.id}&${httpParamOpenMode }=${editOpenMode }"/>&modulePath=${modulePath }', 800, 600)">
													修改
												</a>
											</lauthority:checkAuthority>
											<lauthority:checkAuthority authorityCode="tiger_goods_load">
												&nbsp;
												<a href="javascript:void(0)" class="td_2" onclick="openDialog('<c:url value="/tiger/goods/load.shtml?id=${item.id}&${httpParamOpenMode }=${readOpenMode }"/>&modulePath=${modulePath }', 800, 600)">
													查看
												</a>
											</lauthority:checkAuthority>
											<lauthority:checkAuthority authorityCode="tiger_goods_delete">
												&nbsp;
												<a href="javascript:void(0)" onclick="deletePojo('<c:url value="/tiger/goods/delete.shtml?id=${item.id}"/>')" class="td_2">
													删除
												</a>
											</lauthority:checkAuthority>
										</td>
										<lauthority:checkAuthority authorityCode="tiger_goods_delete">
											<td>
												<input type="checkbox" name="batchDeleteId" class="bian" value="${item.id}" />
											</td>
										</lauthority:checkAuthority>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center" valign="top">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" align="center" valign="top">
						<%@ include file="/jdp/view/page.jsp" %>
					</td>
				</tr>
				<tr>
					<td height="13"></td>
				</tr>
			</table>
			<%@ include file="/jdp/view/sort.jsp" %>
		</form>
	</body>
</html>