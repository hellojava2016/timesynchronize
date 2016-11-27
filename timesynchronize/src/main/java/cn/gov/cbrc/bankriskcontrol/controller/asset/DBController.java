package cn.gov.cbrc.bankriskcontrol.controller.asset;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.gov.cbrc.bankriskcontrol.dto.AssetQueryParam;
import cn.gov.cbrc.bankriskcontrol.entity.AppSystem;
import cn.gov.cbrc.bankriskcontrol.entity.DatabaseInfo;
import cn.gov.cbrc.bankriskcontrol.entity.OperateSystemInfo;
import cn.gov.cbrc.bankriskcontrol.entity.Organization;
import cn.gov.cbrc.bankriskcontrol.entity.User;
import cn.gov.cbrc.bankriskcontrol.service.account.UserService;
import cn.gov.cbrc.bankriskcontrol.service.asset.AppSystemService;
import cn.gov.cbrc.bankriskcontrol.service.asset.DatabaseInfoService;
import cn.gov.cbrc.bankriskcontrol.service.asset.OperateSystemInfoService;
import cn.gov.cbrc.bankriskcontrol.service.system.OrganizationService;
import cn.gov.cbrc.bankriskcontrol.util.ConvertUtils;
import cn.gov.cbrc.bankriskcontrol.util.ExportUtil;
import cn.gov.cbrc.bankriskcontrol.util.Page;
import cn.gov.cbrc.bankriskcontrol.util.RiskUtils;

@Controller
@RequestMapping(value = "/asset/db")
public class DBController {
	@Autowired
    private DatabaseInfoService databaseInfoService;
	
	@Autowired
    private AppSystemService appSystemService;
	
	@Autowired
    private OperateSystemInfoService operateSystemInfoService;
   
    @Autowired
	private OrganizationService organizationService;
    
    @Autowired
    private UserService userService;
    
    @RequestMapping(value = "/add_pre.do")
	public ModelAndView add_pre(HttpServletRequest request,ModelMap model) { 
    	User user=(User)request.getSession().getAttribute("user");
		List<OperateSystemInfo> list=operateSystemInfoService.getOperateSystemInfosByOrganization(user.getOrganization().getOrgId());
		model.addAttribute("oss", list);
		model.addAttribute("apps", RiskUtils.getJsonApps(appSystemService,null));
		model.addAttribute("id", 0);
		return new ModelAndView("/asset/db-add", model);
	}

	@RequestMapping(value = "/add.do")
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) {
		try{
			DatabaseInfo info=new DatabaseInfo();
			getValue(request, info);
	        databaseInfoService.addDatabaseInfo(info);
		}catch(Exception ex){
			 String listurl=(String)request.getSession().getAttribute("listurl");
			 String queryparam=(String)request.getSession().getAttribute("queryparam");
			return new ModelAndView("redirect:"+listurl+"?message=adderror&"+queryparam);
		   
		}
		 String listurl=(String)request.getSession().getAttribute("listurl");
		String queryparam=(String)request.getSession().getAttribute("queryparam");
		return new ModelAndView("redirect:"+listurl+"?message=addsucess&"+queryparam);
	   
	}
	
	@RequestMapping(value = "/update_pre.do")
	public ModelAndView update_pre(HttpServletRequest request,
			HttpServletResponse response,ModelMap mode) {
		long id=Long.parseLong(request.getParameter("id"));
		DatabaseInfo db=databaseInfoService.getDatabaseInfoById(id);
		
		User user=(User)request.getSession().getAttribute("user");
		List<OperateSystemInfo> list=operateSystemInfoService.getOperateSystemInfosByOrganization(user.getOrganization().getOrgId());
		mode.addAttribute("oss", list);
		mode.addAttribute("apps", RiskUtils.getJsonApps(appSystemService,db.getAppSystems()));		
		
		mode.addAttribute("db", db);
		mode.addAttribute("id", id);
		return new ModelAndView("/asset/db-add", mode);
	}

	@RequestMapping(value = "/update.do")
	public ModelAndView update(HttpServletRequest request,
			HttpServletResponse response) {
		try{
			long id=Long.parseLong(request.getParameter("id"));
			DatabaseInfo db=databaseInfoService.getDatabaseInfoById(id);
	        getValue(request, db);
	        databaseInfoService.updateDatabaseInfo(db);
		}catch(Exception ex){
			 String listurl=(String)request.getSession().getAttribute("listurl");
				String queryparam=(String)request.getSession().getAttribute("queryparam");
				return new ModelAndView("redirect:"+listurl+"?message=updateerror&"+queryparam);
		}
		 String listurl=(String)request.getSession().getAttribute("listurl");
			String queryparam=(String)request.getSession().getAttribute("queryparam");
			return new ModelAndView("redirect:"+listurl+"?message=updatesucess&"+queryparam);
	   
	}

	@RequestMapping(value = "/list.do")
	public String getlist(
			@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			Model model, HttpServletRequest request) {
		AssetQueryParam param=new AssetQueryParam();
		Page<DatabaseInfo> page = new Page<DatabaseInfo>();
		page.setPageNo(pageNo);
		String pageSize = request.getParameter("pageSize");
		if(StringUtils.isNotEmpty(pageSize))
			  page.setPageSize(Integer.parseInt(pageSize));
		Page<DatabaseInfo> list=databaseInfoService.getDatabaseInfos(param, page);
		model.addAttribute("rates", list);
		
		Subject user = SecurityUtils.getSubject();
		String username=(String)user.getPrincipal();  
		User oldu = userService.getUserByUserName(username);
				oldu.setOnline(false);
		List<Organization> orgs=organizationService.getBelongBanksByUser(oldu);
		model.addAttribute("orgs", orgs);
		model.addAttribute("pageNo", pageNo);	
		String message = request.getParameter("message");
		if(StringUtils.isNotEmpty(message)){
			if("addsucess".equals(message))
				   model.addAttribute("message","添加数据库成功");
			else if("updatesucess".equals(message))
				   model.addAttribute("message","修改数据库成功");
			else if("deletesucess".equals(message))
				   model.addAttribute("message","删除数据库成功");
			else if("adderror".equals(message))
				   model.addAttribute("message","修改数据库失败,请重新添加");
			else if("deleteerror".equals(message))
				   model.addAttribute("message","删除数据库失败,请重新删除");
			else if("updateerror".equals(message))
				   model.addAttribute("message","修改数据库失败,请检查数据是否合法");
		}	
		return "/asset/db-list";
	}

	@RequestMapping(value = "/query.do")
	public String query(
			@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			Model model, HttpServletRequest request) {
		String name=request.getParameter("name");
		String uniqueVal=request.getParameter("uniqueVal");
		long orgId=Long.parseLong(request.getParameter("Organization"));
		AssetQueryParam param=new AssetQueryParam();
		param.setName(name);
		param.setUniqueVal(uniqueVal);
		param.setOrganizationId(orgId);
		Page<DatabaseInfo> page = new Page<DatabaseInfo>();
		page.setPageNo(pageNo);
		String pageSize = request.getParameter("pageSize");
		if(StringUtils.isNotEmpty(pageSize))
			  page.setPageSize(Integer.parseInt(pageSize));
		Page<DatabaseInfo> list=databaseInfoService.getDatabaseInfos(param, page);
		model.addAttribute("rates", list);
		
		Subject user = SecurityUtils.getSubject();
		String username=(String)user.getPrincipal();  
		User oldu = userService.getUserByUserName(username);
				oldu.setOnline(false);
		List<Organization> orgs=organizationService.getBelongBanksByUser(oldu);
		model.addAttribute("orgs", orgs);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("uniqueVal", uniqueVal);
		model.addAttribute("selectedorgid", orgId);
		model.addAttribute("name", name);
		String message = request.getParameter("message");
		if(StringUtils.isNotEmpty(message)){
			if("addsucess".equals(message))
				   model.addAttribute("message","添加数据库成功");
			else if("updatesucess".equals(message))
				   model.addAttribute("message","修改数据库成功");
			else if("deletesucess".equals(message))
				   model.addAttribute("message","删除数据库成功");
			else if("adderror".equals(message))
				   model.addAttribute("message","修改数据库失败,请重新添加");
			else if("deleteerror".equals(message))
				   model.addAttribute("message","删除数据库失败,请重新删除");
			else if("updateerror".equals(message))
				   model.addAttribute("message","修改数据库失败,请检查数据是否合法");
		}	
		return "/asset/db-list";
	}
	
	@RequestMapping(value = "/export.do")
	public String export(
			@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			Model model, HttpServletRequest request,HttpServletResponse response) {
		String name=request.getParameter("name");
		String uniqueVal=request.getParameter("uniqueVal");
		long orgId=Long.parseLong(request.getParameter("Organization"));
		AssetQueryParam param=new AssetQueryParam();
		param.setName(name);
		param.setUniqueVal(uniqueVal);
		param.setOrganizationId(orgId);
		Page<DatabaseInfo> page = new Page<DatabaseInfo>();
		page.setPageSize(Integer.MAX_VALUE);
		page.setPageNo(pageNo);
		Page<DatabaseInfo> list=databaseInfoService.getDatabaseInfos(param, page);
		List<String[]> valueList=new ArrayList<String[]>();
		valueList.add(new String[]{"所属机构","编号","数据库名称","所属操作系统","数据库类型","版本","补丁","应用系统"});
		for(DatabaseInfo db:list.getResult()){
			String orgName=db.getOrganization().getName();
			valueList.add(new String[]{orgName ,db.getUniqueVal(),db.getDatabaseName(),db.getOsinfo().getName(),db.getType(),db.getVersion(),db.getPatch(),db.getAppSystem() });
		}
		ExportUtil.exportExcel(valueList, "数据库列表", response);
        return null;
	}

	@RequestMapping(value = "/delete.do")
	public ModelAndView deleteSystemAvailableRate(HttpServletRequest request,
			HttpServletResponse response) {
		try{
			String deleteids = request.getParameter("deleteids");
			String[] ids = deleteids.split(",");
			for (String temp : ids) {
				long id = Long.parseLong(temp);
				databaseInfoService.deleteDatabaseInfoById(id);
			}
		}catch(Exception ex){
			 String listurl=(String)request.getSession().getAttribute("listurl");
				String queryparam=(String)request.getSession().getAttribute("queryparam");
				return new ModelAndView("redirect:"+listurl+"?message=deleteerror&"+queryparam);
		   
		}
		 String listurl=(String)request.getSession().getAttribute("listurl");
			String queryparam=(String)request.getSession().getAttribute("queryparam");
			return new ModelAndView("redirect:"+listurl+"?message=deletesucess&"+queryparam);
	   
	}
	
	@RequestMapping(value="/checkname.do")
	@ResponseBody
	public boolean checkUniqueVal (Model model,HttpServletRequest request){
		String id=request.getParameter("id");
		if(!id.equals("0"))
			return true;
		String uniqueVal= request.getParameter("uniqueVal");
		if(null==uniqueVal)
			return false;
		DatabaseInfo db=databaseInfoService.getDatabaseInfoByUniqueVal(uniqueVal);
	      if(null== db)
	    	  return true;
		return false;
	}
	
	private void getValue(HttpServletRequest request,DatabaseInfo info){
		String uniqueVal=request.getParameter("uniqueVal");
        String databaseName=request.getParameter("databaseName");
        String type=request.getParameter("type");
        String version=request.getParameter("version");
        String patch=request.getParameter("patch");
        String serverTime=request.getParameter("serverTime");
        String operateSystem=request.getParameter("operateSystem");
        OperateSystemInfo os=operateSystemInfoService.getOperateSystemInfoById(Long.parseLong(operateSystem));
        info.setUniqueVal(uniqueVal);
        info.setPatch(patch);
        info.setDatabaseName(databaseName);
        info.setType(type);
        info.setVersion(version);
        info.setOperateSystem(os.getUniqueVal());
        info.setOsinfo(os);
        info.setServerTime((Date) ConvertUtils.convertStringToObject(serverTime,Date.class));
        info.setRecordTime(new Date());
        User user=(User)request.getSession().getAttribute("user");
        info.setOrganization(user.getOrganization());
        String apps = request.getParameter("apps");
        info.setAppSystem(apps);
        Set<AppSystem> set = new HashSet<AppSystem>();
        if(StringUtils.isNotEmpty(apps)){
        	 for(String app :apps.split(",")){
        		 set.add(appSystemService.getAppSystemByName(app));
        	 }
        }
        info.setAppSystems(set);
	}
}
