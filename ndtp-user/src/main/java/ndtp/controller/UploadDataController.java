package ndtp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import ndtp.config.PropertiesConfig;
import ndtp.domain.ConverterJob;
import ndtp.domain.Key;
import ndtp.domain.PageType;
import ndtp.domain.Pagination;
import ndtp.domain.UploadData;
import ndtp.domain.UploadDataFile;
import ndtp.domain.DataGroup;
import ndtp.domain.UserSession;
import ndtp.service.DataGroupService;
import ndtp.service.PolicyService;
import ndtp.service.UploadDataService;
import ndtp.service.DataGroupService;
import ndtp.utils.DateUtils;
import ndtp.utils.FormatUtils;

/**
 * 3D 데이터 파일 업로더
 * TODO 설계 파일 안의 texture 의 경우 설계 파일에서 참조하는 경우가 있으므로 이름 변경 불가.
 * @author jeongdae
 *
 */
@Slf4j
@Controller
@RequestMapping("/upload-data")
public class UploadDataController {
	
	// 파일 copy 시 버퍼 사이즈
	public static final int BUFFER_SIZE = 8192;
	
	@Autowired
	private PolicyService policyService;
	
	@Autowired
	private PropertiesConfig propertiesConfig;
	
	@Autowired
	private UploadDataService uploadDataService;
	@Autowired
	private DataGroupService dataGroupService;
	
	/**
	 * 데이터 upload 화면
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/input")
	public String input(HttpServletRequest request, Model model) {
		
		UserSession userSession = (UserSession)request.getSession().getAttribute(Key.USER_SESSION.name());
		DataGroup dataGroup = new DataGroup();
		dataGroup.setUserId(userSession.getUserId());
		DataGroup basicDataGroup = dataGroupService.getBasicDataGroup(dataGroup);
		List<DataGroup> dataGroupList = dataGroupService.getAllListDataGroup(dataGroup);
		
		UploadData uploadData = UploadData.builder().
											dataGroupId(basicDataGroup.getDataGroupId()).
											dataGroupName(basicDataGroup.getDataGroupName()).build();
		
		model.addAttribute("uploadData", uploadData);
		model.addAttribute("dataGroupList", dataGroupList);
		
		return "/upload-data/input";
	}
	
	/**
	 * 업로딩 파일 목록
	 * @param request
	 * @param uploadData
	 * @param pageNo
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/list")
	public String list(HttpServletRequest request, UploadData uploadData, @RequestParam(defaultValue="1") String pageNo, Model model) {
		log.info("@@ uploadData = {}", uploadData);
		
//		UserSession userSession = (UserSession)request.getSession().getAttribute(Key.USER_SESSION.name());
//		uploadData.setUserId(userSession.getUserId());
		
		String today = DateUtils.getToday(FormatUtils.YEAR_MONTH_DAY);
		if(StringUtils.isEmpty(uploadData.getStartDate())) {
			uploadData.setStartDate(today.substring(0,4) + DateUtils.START_DAY_TIME);
		} else {
			uploadData.setStartDate(uploadData.getStartDate().substring(0, 8) + DateUtils.START_TIME);
		}
		if(StringUtils.isEmpty(uploadData.getEndDate())) {
			uploadData.setEndDate(today + DateUtils.END_TIME);
		} else {
			uploadData.setEndDate(uploadData.getEndDate().substring(0, 8) + DateUtils.END_TIME);
		}
		
		long totalCount = uploadDataService.getUploadDataTotalCount(uploadData);
		
		Pagination pagination = new Pagination(request.getRequestURI(), getSearchParameters(PageType.LIST, uploadData), totalCount, Long.valueOf(pageNo).longValue());
		log.info("@@ pagination = {}", pagination);
		
		uploadData.setOffset(pagination.getOffset());
		uploadData.setLimit(pagination.getPageRows());
		List<UploadData> uploadDataList = new ArrayList<>();
		if(totalCount > 0l) {
			uploadDataList = uploadDataService.getListUploadData(uploadData);
		}
		
		model.addAttribute(pagination);
		model.addAttribute("uploadData", uploadData);
		model.addAttribute("converterJobForm", new ConverterJob());
		model.addAttribute("uploadDataList", uploadDataList);
		
		return "/upload-data/list";
	}
	
	/**
	 * 데이터 upload 수정
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/modify")
	public String modify(HttpServletRequest request, UploadData uploadData, Model model) {
		
//		UserSession userSession = (UserSession)request.getSession().getAttribute(Key.USER_SESSION.name());
//		uploadData.setUserId(userSession.getUserId());
		
		uploadData = uploadDataService.getUploadData(uploadData);
		List<UploadDataFile> uploadDataFileList = uploadDataService.getListUploadDataFile(uploadData);
		List<DataGroup> dataGroupList = dataGroupService.getListDataGroup(new DataGroup());
		
		model.addAttribute("uploadData", uploadData);
		model.addAttribute("uploadDataFileList", uploadDataFileList);
		model.addAttribute("dataGroupList", dataGroupList);
		
		return "/upload-data/modify";
	}
	
	/**
	 * 검색 조건
	 * @param search
	 * @return
	 */
	private String getSearchParameters(PageType pageType, UploadData uploadData) {
		StringBuffer buffer = new StringBuffer(uploadData.getParameters());
		boolean isListPage = true;
		if(pageType == PageType.MODIFY || pageType == PageType.DETAIL) {
			isListPage = false;
		}
		
//		if(!isListPage) {
//			buffer.append("pageNo=" + request.getParameter("pageNo"));
//			buffer.append("&");
//			buffer.append("list_count=" + uploadData.getList_counter());
//		}
		
		return buffer.toString();
	}
}
