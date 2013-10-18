package com.lmiky.jdp.system.menu.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.lmiky.jdp.authority.service.AuthorityService;
import com.lmiky.jdp.cache.CacheFactory;
import com.lmiky.jdp.cache.model.ObjectCache;
import com.lmiky.jdp.cache.model.SimpleCacheData;
import com.lmiky.jdp.constants.Constants;
import com.lmiky.jdp.module.pojo.Function;
import com.lmiky.jdp.service.BaseService;
import com.lmiky.jdp.session.model.SessionInfo;
import com.lmiky.jdp.session.service.SessionService;
import com.lmiky.jdp.system.menu.model.LeftMenu;
import com.lmiky.jdp.system.menu.model.SubMenu;
import com.lmiky.jdp.system.menu.model.TopMenu;
import com.lmiky.jdp.system.menu.service.MenuService;
import com.lmiky.jdp.util.Environment;
import com.lmiky.jdp.web.util.WebUtils;

/**
 * @author lmiky
 * @date 2013-6-16
 */
public class MenuServiceImpl implements MenuService {
	private static final String CACHE_KEY_TOP = "menuKey_top";
	private static final String CACHE_KEY_SUBMENU_PREFIX = "menuKey_sub_";
	
	private CacheFactory cacheFactory;
	private ObjectCache cache;
	private String cacheName; // 缓存名称
	private String menuConfigPath;
	private AuthorityService authorityService;
	private BaseService baseService;
	private SessionService sessionService;
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.system.menu.service.MenuService#init()
	 */
	public void init() throws Exception {
		cache = cacheFactory.getCache(cacheName);
		parse();
	}
	
	/**
	 * 解析菜单文件
	 * @author lmiky
	 * @date 2013-6-16
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void parse() throws Exception {
		List<TopMenu> topMenuList = new ArrayList<TopMenu>();
		
		SAXReader reader = new SAXReader();
		Document document = reader.read(Environment.getClassPath() + menuConfigPath);
		Element root = document.getRootElement();
		//顶层菜单
		for(Iterator topMenus = root.elementIterator("topmenu"); topMenus.hasNext(); ) {
			Element topMenuElement = (Element) topMenus.next();
			TopMenu topMenu = new TopMenu();
			topMenu.setId(topMenuElement.attributeValue("id"));
			topMenu.setLabel(topMenuElement.attributeValue("label"));
			List<LeftMenu> leftMenuList = new ArrayList<LeftMenu>();
			topMenu.setLeftMenus(leftMenuList);
			//左菜单
			for(Iterator leftMenus = topMenuElement.elementIterator("leftmenu"); leftMenus.hasNext(); ) {
				Element leftMenuElement = (Element) leftMenus.next();
				LeftMenu leftMenu = new LeftMenu();
				leftMenu.setId(leftMenuElement.attributeValue("id"));
				leftMenu.setLabel(leftMenuElement.attributeValue("label"));
				List<SubMenu> subMenuList = new ArrayList<SubMenu>();
				leftMenu.setSubMenus(subMenuList);
				//子菜单
				for(Iterator subMenus = leftMenuElement.elementIterator("submenu"); subMenus.hasNext(); ) {
					Element subMenuElement = (Element) subMenus.next();
					SubMenu subMenu = new SubMenu();
					subMenu.setId(subMenuElement.elementText("id"));
					subMenu.setLabel(subMenuElement.elementText("label"));
					subMenu.setModulePath(subMenuElement.elementText("modulePath"));
					String url = subMenuElement.elementText("url");
					if(url.indexOf("?") == -1) {
						url += "?";
					} else {
						url += "&";
					}
					subMenu.setUrl(url + Constants.HTTP_PARAM_MODULE_PATH + "=" + subMenu.getModulePath() + "&" + Constants.HTTP_PARAM_SUBMENU_ID + "=" + subMenu.getId());
					subMenu.setType(subMenuElement.elementText("type"));
					subMenu.setAuthority(subMenuElement.elementText("authority"));
					subMenuList.add(subMenu);
					cache.put(CACHE_KEY_SUBMENU_PREFIX + subMenu.getId(), new SimpleCacheData(subMenu));
				}
				if(subMenuList.isEmpty()) {
					continue;
				}
				leftMenuList.add(leftMenu);
			}
			if(leftMenuList.isEmpty()) {
				continue;
			}
			topMenuList.add(topMenu);
		}
		//设置缓存
		cache.put(CACHE_KEY_TOP, new SimpleCacheData(topMenuList));
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.system.menu.service.MenuService#parse(com.lmiky.jdp.session.model.SessionInfo)
	 */
	@SuppressWarnings("unchecked")
	public List<TopMenu> getTopMenus(SessionInfo sessionInfo) throws Exception {
		if(sessionInfo == null) {
			return new ArrayList<TopMenu>();
		}
		List<TopMenu> topMenuList = new ArrayList<TopMenu>();
		//读取缓存
		SimpleCacheData<List<TopMenu>> cacheData = (SimpleCacheData<List<TopMenu>>)cache.get(CACHE_KEY_TOP);
		//如果没有数据(缓存失效)，则重新解析
		if(cacheData == null) {
			parse();
			cacheData = (SimpleCacheData<List<TopMenu>>)cache.get(CACHE_KEY_TOP);
		}
		for(TopMenu topMenu : cacheData.getValue()) {
			List<LeftMenu> leftMenuList = new ArrayList<LeftMenu>();
			for(LeftMenu leftMenu : topMenu.getLeftMenus()) {
				List<SubMenu> subMenuList = new ArrayList<SubMenu>();
				for(SubMenu subMenu : leftMenu.getSubMenus()) {
					if(!StringUtils.isBlank(subMenu.getAuthority())) {
						//检查权限
						if(Function.DEFAULT_AUTHORITYCODE_LOAD.equals(subMenu.getAuthority())) {
							if(!WebUtils.checkAuthority(authorityService, sessionInfo, WebUtils.getModule(baseService, subMenu.getModulePath()), Function.DEFAULT_FUNCTIONID_LOAD)) {	//查询
								continue;
							}
						} else {
							if(!WebUtils.checkAuthority(baseService, authorityService, sessionInfo, WebUtils.getModule(baseService, subMenu.getModulePath()), subMenu.getAuthority())) {	//查询
								continue;
							}
						}
					}
					subMenuList.add(subMenu);
				}
				leftMenu.setSubMenus(subMenuList);
				if(!subMenuList.isEmpty()) {
					leftMenuList.add(leftMenu);
				}
			}
			if(!leftMenuList.isEmpty()) {
				topMenuList.add(topMenu);
			}
		}
		return topMenuList;
	}
	
	/* (non-Javadoc)
	 * @see com.lmiky.jdp.system.menu.service.MenuService#getSubMenus(java.lang.String, com.lmiky.jdp.session.model.SessionInfo)
	 */
	@SuppressWarnings("unchecked")
	public SubMenu getSubMenus(String subMenuId, SessionInfo sessionInfo) throws Exception {
		SimpleCacheData<SubMenu> cacheData = (SimpleCacheData<SubMenu>)cache.get(CACHE_KEY_SUBMENU_PREFIX + subMenuId);
		if(cacheData == null) {
			return null;
		}
		SubMenu subMenu = cacheData.getValue();
		if(!StringUtils.isBlank(subMenu.getAuthority()) && sessionInfo != null) {
			//检查权限
			if(Function.DEFAULT_AUTHORITYCODE_LOAD.equals(subMenu.getAuthority())) {
				if(!WebUtils.checkAuthority(authorityService, sessionInfo, WebUtils.getModule(baseService, subMenu.getModulePath()), Function.DEFAULT_FUNCTIONID_LOAD)) {	//查询
					return null;
				}
			} else {
				if(!WebUtils.checkAuthority(baseService, authorityService, sessionInfo, WebUtils.getModule(baseService, subMenu.getModulePath()), subMenu.getAuthority())) {	//查询
					return null;
				}
			}
		}
		return subMenu;
	}
	
	/**
	 * @return the menuConfigPath
	 */
	public String getMenuConfigPath() {
		return menuConfigPath;
	}

	/**
	 * @param menuConfigPath the menuConfigPath to set
	 */
	public void setMenuConfigPath(String menuConfigPath) {
		this.menuConfigPath = menuConfigPath;
	}

	/**
	 * @return the authorityService
	 */
	public AuthorityService getAuthorityService() {
		return authorityService;
	}

	/**
	 * @param authorityService the authorityService to set
	 */
	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	/**
	 * @return the baseService
	 */
	public BaseService getBaseService() {
		return baseService;
	}

	/**
	 * @param baseService the baseService to set
	 */
	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

	/**
	 * @return the sessionService
	 */
	public SessionService getSessionService() {
		return sessionService;
	}

	/**
	 * @param sessionService the sessionService to set
	 */
	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	/**
	 * @return the cacheFactory
	 */
	public CacheFactory getCacheFactory() {
		return cacheFactory;
	}

	/**
	 * @param cacheFactory the cacheFactory to set
	 */
	public void setCacheFactory(CacheFactory cacheFactory) {
		this.cacheFactory = cacheFactory;
	}

	/**
	 * @return the cache
	 */
	public ObjectCache getCache() {
		return cache;
	}

	/**
	 * @param cache the cache to set
	 */
	public void setCache(ObjectCache cache) {
		this.cache = cache;
	}

	/**
	 * @return the cacheName
	 */
	public String getCacheName() {
		return cacheName;
	}

	/**
	 * @param cacheName the cacheName to set
	 */
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
}
