<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp" %>
<div id="geoserverLayerTab">
	<form:form id="geoserverLayer" modelAttribute="layer" method="post" onsubmit="return false;">
	<table class="input-table scope-row">
		<colgroup>
                  <col class="col-label l" style="width: 15%" >
                  <col class="col-input" style="width: 35%" >
                  <col class="col-label l" style="width: 15%" >
                  <col class="col-input" style="width: 35%" >
              </colgroup>
              <tr>
			<th class="col-label" scope="row">
				<form:label path="layerGroupName">Layer 그룹명</form:label>
				<span class="icon-glyph glyph-emark-dot color-warning"></span>
			</th>
			<td class="col-input">
				<form:hidden path="layerGroupId" />
				<form:input path="layerGroupName" cssClass="m" readonly="true" />
				<input type="button" id="geoserverLayerGroupButtion" value="레이어 그룹 선택" />
			</td>
			<th class="col-label" scope="row">
                      <label for="sharing">공유 유형</label>
                  </th>
                  <td class="col-input radio-set">
                      <form:radiobutton id="geoserverSharingPublic"  path="sharing" value="public" label="공개" />
				<form:radiobutton id="geoserverSharingPrivate" path="sharing" value="private" label="비공개" />
				<form:radiobutton id="geoserverSharingGroup" path="sharing" value="group" label="그룹" />
                  </td>
		</tr>
		<tr>
                  <th class="col-label" scope="row">
                      <form:label path="layerName">Layer 명</form:label>
                      <span class="icon-glyph glyph-emark-dot color-warning"></span>
                  </th>
                  <td class="col-input">
                      <form:input path="layerName" cssClass="m" maxlength="256" />
                      <form:errors path="layerName" cssClass="error" />
                  </td>
                  <th class="col-label" scope="row">
                      <form:label path="layerKey">Layer Key</form:label>
                      <span class="icon-glyph glyph-emark-dot color-warning"></span>
                  </th>
                  <td class="col-input">
                      <form:input path="layerKey" cssClass="m" maxlength="100" />
                      <form:errors path="layerKey" cssClass="error" />
                  </td>
              </tr>
              <tr>
                  <th class="col-label" scope="row">
                      <form:label path="serviceType">서비스 타입</form:label>
                      <span class="icon-glyph glyph-emark-dot color-warning"></span>
                  </th>
                  <td class="col-input">
                      <select name="serviceType">
					<option value="">선택</option>
					<option value="wms">WMS</option>
					<option value="wfs">WFS</option>
					<option value="wcs">WCS</option>
					<option value="wps">WPS</option>
				</select>
                  </td>
                  <th class="col-label" scope="row">
                      <form:label path="layerType">Layer 타입</form:label>
                      <span class="icon-glyph glyph-emark-dot color-warning"></span>
                  </th>
                  <td class="col-input">
                      <select name="layerType">
					<option value="">선택</option>
					<option value="Vector">Vector</option>
					<option value="Raster">Raster</option>
				</select>
                  </td>
              </tr>
              <tr>
                  <th class="col-label" scope="row">
                      <form:label path="geometryType">도형 타입</form:label>
                      <span class="icon-glyph glyph-emark-dot color-warning"></span>
                  </th>
			<td class="col-input">
				<select name="geometryType" class="forRaster">
					<option value="">선택</option>
					<option value="Point">Point</option>
					<option value="Line">Line</option>
					<option value="Polygon">Polygon</option>
				</select>
			</td>
                  <th class="col-label" scope="row">
                      <form:label path="geometryType">외곽선 색상</form:label>
                      <span class="icon-glyph glyph-emark-dot color-warning"></span>
                  </th>
			<td class="col-input">
				<input id="lineColorValue" placeholder="RGB" class="forRaster" />
				<input type="color" id="geoserverLayerLineColor" name="layerLineColor" class="picker" alt="외곽선 색상" />
			</td>
		</tr>
		<tr>
			<th class="col-label" scope="row">
                      <form:label path="layerLineStyle">외곽선 두께</form:label>
                  </th>
			<td class="col-input">
				<input type="number" id="geoserverLayerLineStyle"  name="layerLineStyle" class="forRaster" alt="외곽선 두께" min="0.1" max="5.0" size="3" step="0.1">
			</td>
			<th class="col-label" scope="row">
                      <form:label path="layerFillColor">채우기 색상</form:label>
                      <span class="icon-glyph glyph-emark-dot color-warning"></span>
                  </th>
			<td class="col-input">
				<input id="fillColorValue" placeholder="RGB" class="forRaster forPolygon">
				<input type="color" id="geoserverLayerFillColor" name="layerFillColor" class="picker forPolygon" alt="채우기 색상">
			</td>
		</tr>

              <tr>
              	<th class="col-label" scope="row">
                      <form:label path="layerAlphaStyle">투명도</form:label>
                      <span class="icon-glyph glyph-emark-dot color-warning"></span>
                  </th>
			<td class="col-input">
				<input type="text" id="geoserverSliderValue" name="layerAlphaStyle" class="slider" alt="투명도">
				<input type="range" id="geoserverSliderRange" min="0" max="100" value="100" alt="투명도">
			</td>
                  <th class="col-label" scope="row">
                      <label for="zIndex">표시 순서(Z-Index)</label>
                  </th>
                  <td class="col-input">
                      <form:input path="zIndex" cssClass="s" />
                      <form:errors path="zIndex" cssClass="error" />
                  </td>
              </tr>
              <tr>
                  <th class="col-label" scope="row">
                      <label for="defaultDisplayTrue">기본 표시</label>
                      <span class="icon-glyph glyph-emark-dot color-warning"></span>
                  </th>
                  <td class="col-input radio-set">
                      <form:radiobutton id="geoserverDefaultDisplayTrue"  path="defaultDisplay" value="true" label="사용" />
				<form:radiobutton id="geoserverDefaultDisplayFlase" path="defaultDisplay" value="false" label="미사용" />
                  </td>
                  <th class="col-label" scope="row">
                      <label for="useY">사용유무</label>
                      <span class="icon-glyph glyph-emark-dot color-warning"></span>
                  </th>
                  <td class="col-input radio-set">
                      <form:radiobutton id="geoserverAvailableTrue"  path="available" value="true" label="사용" />
				<form:radiobutton id="geoserverAvailableFalse" path="available" value="false" label="미사용" />
                  </td>
              </tr>
              <tr>
                  <th class="col-label" scope="row">
                      <label for="labelDisplayTrue">Label 표시 유무</label>
                  </th>
                  <td class="col-input radio-set">
                      <form:radiobutton id="geoserverLabelDisplayTrue"  path="labelDisplay" value="true" label="표시" />
				<form:radiobutton id="geoserverLabelDisplayFalse" path="labelDisplay" value="false" label="비표시" />
                  </td>
                  <th class="col-label" scope="row">
                      <form:label path="coordinate">좌표계</form:label>
                  </th>
                  <td class="col-input">
                      <form:input path="coordinate" cssClass="m" />
                      <form:errors path="coordinate" cssClass="error" />
                  </td>
              </tr>
              <tr>
                  <th class="col-label" scope="row">
                      <form:label path="description">설명</form:label>
                  </th>
                  <td class="col-input">
                      <form:input path="description" cssClass="l" />
                      <form:errors path="description" cssClass="error" />
                  </td>
                  <th class="col-label" scope="row">
                      <form:label path="shapeEncoding">SHP 파일 인코딩</form:label>
                  </th>
              </tr>
	</table>
	</form:form>
</div>