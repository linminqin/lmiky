package com.lmiky.cms.directory.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lmiky.cms.directory.pojo.CmsDirectory;
import com.lmiky.jdp.base.controller.BaseController;
import com.lmiky.jdp.base.view.BaseCode;
import com.lmiky.jdp.base.view.BaseInfoCodeJsonView;
import com.lmiky.jdp.service.BaseService;
import com.lmiky.jdp.tree.controller.TreeController;

/**
 * 栏目
 * @author lmiky
 * @date 2014-1-3
 */
@Controller
@RequestMapping("/cms/directory")
public class DirectoryController extends TreeController<CmsDirectory> {

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#getAddAuthorityCode(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getAddAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "cms_directory_add";
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#getModifyAuthorityCode(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getModifyAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "cms_directory_modify";
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#getDeleteAuthorityCode(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getDeleteAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "cms_directory_delete";
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.base.controller.BaseController#getLoadAuthorityCode(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected String getLoadAuthorityCode(ModelMap modelMap, HttpServletRequest request) {
		return "cms_directory_load";
	}

	/**
	 * @author lmiky
	 * @date 2014-1-3
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadTree.shtml")
	public String loadTree(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse) throws Exception {
		return executeLoadTree(modelMap, request, resopnse);
	}
	
	/**
	 * @author lmiky
	 * @date 2014-1-4
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/treeList.shtml")
	public String treeList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse, @RequestParam(value = "id", required = false) Long id) throws Exception {
		return executeTreeList(modelMap, request, resopnse, id);
	}
	
	/**
	 * @author lmiky
	 * @date 2014-1-5
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/load.shtml")
	public String load(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse,
			@RequestParam(value = "id", required = false) Long id) throws Exception {
		return executeLoad(modelMap, request, resopnse, id);
	}

	/**
	 * @author lmiky
	 * @date 2014-1-5
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save.shtml")
	public String save(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse,
			@RequestParam(value = "id", required = false) Long id) throws Exception {
		return executeSave(modelMap, request, resopnse, id);
	}


	/**
	 * @author lmiky
	 * @date 2014-1-5
	 * @param modelMap
	 * @param request
	 * @param resopnse
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete.shtml")
	public String delete(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse,
			@RequestParam(value = "id", required = true) Long id) throws Exception {
		return executeDelete(modelMap, request, resopnse, id, BaseController.REQUESTTYPE_AJAX);
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.form.controller.FormController#getExecuteDeleteRet(org.springframework.ui.ModelMap, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected String getExecuteDeleteRet(ModelMap modelMap, HttpServletRequest request, HttpServletResponse resopnse) throws Exception {
		modelMap.put(BaseInfoCodeJsonView.KEY_CODE_NAME, BaseCode.CODE_SUCCESS);
		return "baseInfoCodeJsonView";
	}

	/* (non-Javadoc)
	 * @see com.lmiky.jdp.base.controller.BaseController#setService(com.lmiky.jdp.service.BaseService)
	 */
	@Resource(name="cmsDirectoryService")
	public void setService(BaseService service) {
		super.setService(service);
	}
}