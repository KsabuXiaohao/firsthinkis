/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.cms.web.front;

import com.google.common.collect.Lists;
import com.thinkis.common.persistence.Page;
import com.thinkis.common.utils.StringUtils;
import com.thinkis.common.web.BaseController;
import com.thinkis.common.web.Result;
import com.thinkis.common.web.ResultGenerator;
import com.thinkis.modules.cms.entity.*;
import com.thinkis.modules.cms.service.*;
import com.thinkis.modules.cms.utils.CmsUtils;
import com.thinkis.modules.sys.entity.User;
import com.thinkis.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 网站Controller
 * @author liuzhiping
 * @version 2018年12月1日
 */
@Controller
@RequestMapping(value = "${frontPath}")
public class FrontController extends BaseController{
	
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleDataService articleDataService;
	@Autowired
	private LinkService linkService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private SiteService siteService;
	/**
	 * 网站首页
	 */
	@RequestMapping
	public String index(Model model) {
		Site site = CmsUtils.getSite(Site.defaultSiteId());
		model.addAttribute("site", site);
		model.addAttribute("isIndex", true);
		return "templates/"+site.getTheme()+"/index";
	}

	/**
	 * 前端登录页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "login-{siteId}${urlSuffix}")
	public String login(@PathVariable String siteId,Model model) {
		Site site = CmsUtils.getSite(siteId);
		model.addAttribute("site", site);
		return "templates/"+site.getTheme()+"/user/login";
	}

	/**
	 * 前端注册页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "reg-{siteId}${urlSuffix}")
	public String register(@PathVariable String siteId,Model model) {
		Site site = CmsUtils.getSite(siteId);
		model.addAttribute("site", site);
		return "templates/"+site.getTheme()+"/user/reg";
	}

	/**
	 * 前端基本设置页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "set-{siteId}${urlSuffix}")
	public String set(@PathVariable String siteId,Model model) {
		Site site = CmsUtils.getSite(siteId);
		model.addAttribute("site", site);
		return "templates/"+site.getTheme()+"/user/set";
	}

	/**
	 * 前端用户中心页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "userIndex-{siteId}${urlSuffix}")
	public String userIndex(@PathVariable String siteId,Model model) {
		Site site = CmsUtils.getSite(siteId);
		model.addAttribute("site", site);
		return "templates/"+site.getTheme()+"/user/index";
	}

	/**
	 * 前端发帖页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "add-{siteId}${urlSuffix}")
	public String add(@PathVariable String siteId,Model model) {
		Site site = CmsUtils.getSite(siteId);
		model.addAttribute("site", site);
		User user = UserUtils.getUser();
		if(StringUtils.isNotBlank(user.getId())){
			return "templates/"+site.getTheme()+"/user/add";
		}else{
			return "templates/"+site.getTheme()+"/user/login";
		}
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public Result save(Article article, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, article)){
			return ResultGenerator.genFailResult("表单验证失败");
		}
		articleService.save(article);
		return ResultGenerator.genSuccessResult("发帖成功");
	}

	/**
	 * 子网站首页
	 */
	@RequestMapping(value = "/index/{siteTheme}${urlSuffix}")
	public String site(@PathVariable String siteTheme,Model model) {
		Site site = siteService.getByTheme(siteTheme);
		model.addAttribute("site", site);
		model.addAttribute("isIndex", true);
		// 子站有独立页面，则显示独立页面
		if (StringUtils.isNotBlank(site.getCustomIndexView())){
			return "templates/"+site.getTheme()+"/index"+site.getCustomIndexView();
		}else {
			return "templates/" + site.getTheme() + "/index";
		}
	}

	/**
	 * 子网站首页
	 */
	/*@RequestMapping(value = "index-{siteId}${urlSuffix}")
	public String index(@PathVariable String siteId, Model model) {
		if (siteId.equals("1")){
			return "redirect:"+Global.getFrontPath();
		}
		Site site = CmsUtils.getSite(siteId);
		// 子站有独立页面，则显示独立页面
		if (StringUtils.isNotBlank(site.getCustomIndexView())){
			model.addAttribute("site", site);
			model.addAttribute("isIndex", true);
			return "templates/"+site.getTheme()+"/index"+site.getCustomIndexView();
		}
		// 否则显示子站第一个栏目
		List<Category> mainNavList = CmsUtils.getMainNavList(siteId);
		if (mainNavList.size() > 0){
			String firstCategoryId = CmsUtils.getMainNavList(siteId).get(0).getId();
			return "redirect:"+Global.getindexPath()+"/list-"+firstCategoryId+Global.getUrlSuffix();
		}else{
			model.addAttribute("site", site);
			return "templates/"+site.getTheme()+"/indexListCategory";
		}
	}*/

	/**
	 * 内容列表
	 */
	@RequestMapping(value = "/list/{categoryId}${urlSuffix}")
	public String list(@PathVariable String categoryId, @RequestParam(required=false, defaultValue="1") Integer pageNo,
					   @RequestParam(required=false, defaultValue="10") Integer pageSize, Model model) {
		Category category = categoryService.get(categoryId);
		if (category==null){
			Site site = CmsUtils.getSite(Site.defaultSiteId());
			model.addAttribute("site", site);
			return "error/404";
		}
		Site site = siteService.get(category.getSite().getId());
		model.addAttribute("site", site);
		// 2：简介类栏目，栏目第一条内容
		if("2".equals(category.getShowModes()) && "article".equals(category.getModule())){
			// 如果没有子栏目，并父节点为跟节点的，栏目列表为当前栏目。
			List<Category> categoryList = Lists.newArrayList();
			if (category.getParent().getId().equals("1")){
				categoryList.add(category);
			}else{
				categoryList = categoryService.findByParentId(category.getParent().getId(), category.getSite().getId());
			}
			model.addAttribute("category", category);
			model.addAttribute("categoryList", categoryList);
			// 获取文章内容
			Page<Article> page = new Page<Article>(1, 1, -1);
			Article article = new Article(category);
			page = articleService.findPage(page, article, false);
			if (page.getList().size()>0){
				article = page.getList().get(0);
				article.setArticleData(articleDataService.get(article.getId()));
				articleService.updateHitsAddOne(article.getId());
			}
			model.addAttribute("article", article);
			CmsUtils.addViewConfigAttribute(model, category);
			CmsUtils.addViewConfigAttribute(model, article.getViewConfig());
			return "templates/"+site.getTheme()+"/"+getTpl(article);
		}else{
			List<Category> categoryList = categoryService.findByParentId(category.getId(), category.getSite().getId());
			// 展现方式为1 、无子栏目或公共模型，显示栏目内容列表
			if("1".equals(category.getShowModes())||categoryList.size()==0){
				// 有子栏目并展现方式为1，则获取第一个子栏目；无子栏目，则获取同级分类列表。
				if(categoryList.size()>0){
					category = categoryList.get(0);
				}else{
					// 如果没有子栏目，并父节点为跟节点的，栏目列表为当前栏目。
					if (category.getParent().getId().equals("1")){
						categoryList.add(category);
					}else{
						categoryList = categoryService.findByParentId(category.getParent().getId(), category.getSite().getId());
					}
				}
				model.addAttribute("category", category);
				model.addAttribute("categoryList", categoryList);
				// 获取内容列表
				if ("article".equals(category.getModule())){
					Page<Article> page = new Page<Article>(pageNo, pageSize);
					//System.out.println(page.getPageNo());
					page = articleService.findPage(page, new Article(category), false);
					model.addAttribute("page", page);
					// 如果第一个子栏目为简介类栏目，则获取该栏目第一篇文章
					if ("2".equals(category.getShowModes())){
						Article article = new Article(category);
						if (page.getList().size()>0){
							article = page.getList().get(0);
							article.setArticleData(articleDataService.get(article.getId()));
							articleService.updateHitsAddOne(article.getId());
						}
						model.addAttribute("article", article);
						CmsUtils.addViewConfigAttribute(model, category);
						CmsUtils.addViewConfigAttribute(model, article.getViewConfig());
						return "templates/"+site.getTheme()+"/"+getTpl(article);
					}
				}else if ("link".equals(category.getModule())){
					Page<Link> page = new Page<Link>(1, -1);
					page = linkService.findPage(page, new Link(category), false);
					model.addAttribute("page", page);
				}
				String view = "/list";
				if (StringUtils.isNotBlank(category.getCustomListView())){
					view = "/"+category.getCustomListView();
				}
				CmsUtils.addViewConfigAttribute(model, category);
				site =siteService.get(category.getSite().getId());
				//System.out.println("else 栏目第一条内容 _2 :"+category.getSite().getTheme()+","+site.getTheme());
				return "templates/"+siteService.get(category.getSite().getId()).getTheme()+view;
				//return "templates/"+category.getSite().getTheme()+view;
			}
			// 有子栏目：显示子栏目列表
			else{
				model.addAttribute("category", category);
				model.addAttribute("categoryList", categoryList);
				String view = "/listCategory";
				if (StringUtils.isNotBlank(category.getCustomListView())){
					view = "/"+category.getCustomListView();
				}
				CmsUtils.addViewConfigAttribute(model, category);
				return "templates/"+site.getTheme()+view;
			}
		}
	}

	/**
	 * 内容列表（通过url自定义视图）
	 */
	@RequestMapping(value = "/listc/{categoryId}/{customView}${urlSuffix}")
	public String listCustom(@PathVariable String categoryId, @PathVariable String customView, @RequestParam(required=false, defaultValue="1") Integer pageNo,
							 @RequestParam(required=false, defaultValue="10") Integer pageSize, Model model) {
		Category category = categoryService.get(categoryId);
		if (category==null){
			Site site = CmsUtils.getSite(Site.defaultSiteId());
			model.addAttribute("site", site);
			return "error/404";
		}
		Site site = siteService.get(category.getSite().getId());
		model.addAttribute("site", site);
		List<Category> categoryList = categoryService.findByParentId(category.getId(), category.getSite().getId());
		model.addAttribute("category", category);
		model.addAttribute("categoryList", categoryList);
		CmsUtils.addViewConfigAttribute(model, category);
		return "templates/"+site.getTheme()+"/listCategory"+customView;
	}

	/**
	 * 显示内容
	 */
	@RequestMapping(value = "/view/{categoryId}/{contentId}${urlSuffix}")
	public String view(@PathVariable String categoryId, @PathVariable String contentId, Model model, HttpServletRequest request, HttpServletResponse response) {
		Category category = categoryService.get(categoryId);
		if (category==null){
			Site site = CmsUtils.getSite(Site.defaultSiteId());
			model.addAttribute("site", site);
			return "error/404";
		}
		model.addAttribute("site", category.getSite());
		if ("article".equals(category.getModule())){
			// 如果没有子栏目，并父节点为跟节点的，栏目列表为当前栏目。
			List<Category> categoryList = Lists.newArrayList();
			if (category.getParent().getId().equals("1")){
				categoryList.add(category);
			}else{
				categoryList = categoryService.findByParentId(category.getParent().getId(), category.getSite().getId());
			}
			// 获取文章内容
			Article article = articleService.get(contentId);
			if (article==null || !Article.DEL_FLAG_NORMAL.equals(article.getDelFlag())){
				return "error/404";
			}
			// 文章阅读次数+1
			articleService.updateHitsAddOne(contentId);
			// 获取推荐文章列表
			List<Object[]> relationList = articleService.findByIds(articleDataService.get(article.getId()).getRelation());

			//文章评论
			Page<Comment> commentList = new Page<Comment>(request, response);
			Comment c = new Comment();
			c.setCategory(category);
			c.setContentId(contentId);
			c.setDelFlag(Comment.DEL_FLAG_NORMAL);
			commentList = commentService.findPage(commentList, c);

			// 将数据传递到视图
			model.addAttribute("category", categoryService.get(article.getCategory().getId()));
			model.addAttribute("categoryList", categoryList);
			article.setArticleData(articleDataService.get(article.getId()));
			model.addAttribute("article", article);
			model.addAttribute("relationList", relationList);
			CmsUtils.addViewConfigAttribute(model, article.getCategory());
			CmsUtils.addViewConfigAttribute(model, article.getViewConfig());
			Site site = siteService.get(category.getSite().getId());
			model.addAttribute("site", site);
			model.addAttribute("commentList", commentList);
//			return "templates/"+category.getSite().getTheme()+"/"+getTpl(article);
			return "templates/"+site.getTheme()+"/"+getTpl(article);
		}
		return "error/404";
	}

	/**
	 * 内容评论
	 */
	@RequestMapping(value = "comment", method=RequestMethod.GET)
	public String comment(String theme, Comment comment, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Comment> page = new Page<Comment>(request, response);
		Comment c = new Comment();
		c.setCategory(comment.getCategory());
		c.setContentId(comment.getContentId());
		c.setDelFlag(Comment.DEL_FLAG_NORMAL);
		page = commentService.findPage(page, c);
		model.addAttribute("page", page);
		model.addAttribute("comment", comment);
		return "templates/"+theme+"/comment";
	}

	/**
	 * 内容评论保存
	 */
	@ResponseBody
	@RequestMapping(value = "comment", method=RequestMethod.POST)
	public Result commentSave(Comment comment, String validateCode,@RequestParam(required=false) String replyId, HttpServletRequest request) {
		User user = UserUtils.getUser();
		if(StringUtils.isBlank(user.getId())){
			return ResultGenerator.genFailResult("未登录");
		}else {
			comment.setName(user.getName());
			comment.setIp(request.getRemoteAddr());
			comment.setCreateDate(new Date());
			comment.setDelFlag(Comment.DEL_FLAG_NORMAL);
			commentService.save(comment);
			return ResultGenerator.genSuccessResult("提交成功");
		}
	}

	/**
	 * 站点地图
	 */
	@RequestMapping(value = "/map/{siteId}${urlSuffix}")
	public String map(@PathVariable String siteId, Model model) {
		Site site = CmsUtils.getSite(siteId!=null?siteId:Site.defaultSiteId());
		model.addAttribute("site", site);
		return "templates/"+site.getTheme()+"/frontMap";
	}

	private String getTpl(Article article){
		if(StringUtils.isBlank(article.getCustomContentView())){
			String view = null;
			Category c = article.getCategory();
			boolean goon = true;
			do{
				if(StringUtils.isNotBlank(c.getCustomContentView())){
					view = c.getCustomContentView();
					goon = false;
				}else if(c.getParent() == null || c.getParent().isRoot()){
					goon = false;
				}else{
					c = c.getParent();
				}
			}while(goon);
			return StringUtils.isBlank(view) ? Article.DEFAULT_TEMPLATE : view;
		}else{
			return article.getCustomContentView();
		}
	}

}
