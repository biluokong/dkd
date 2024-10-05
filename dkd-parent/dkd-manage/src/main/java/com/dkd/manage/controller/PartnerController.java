package com.dkd.manage.controller;

import com.dkd.common.annotation.Log;
import com.dkd.common.core.controller.BaseController;
import com.dkd.common.core.domain.AjaxResult;
import com.dkd.common.core.page.TableDataInfo;
import com.dkd.common.enums.BusinessType;
import com.dkd.common.utils.SecurityUtils;
import com.dkd.common.utils.poi.ExcelUtil;
import com.dkd.manage.domain.Partner;
import com.dkd.manage.domain.vo.PartnerVo;
import com.dkd.manage.service.IPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 合作商管理Controller
 *
 * @author biluo
 * @date 2024-07-27
 */
@RestController
@RequestMapping("/manage/partner")
public class PartnerController extends BaseController {
	@Autowired
	private IPartnerService partnerService;

	/**
	 * 查询合作商管理列表
	 */
	@PreAuthorize("@ss.hasPermi('manage:partner:list')")
	@GetMapping("/list")
	public TableDataInfo list(Partner partner) {
		startPage();
		List<PartnerVo> list = partnerService.selectPartnerVoList(partner);
		return getDataTable(list);
	}

	/**
	 * 导出合作商管理列表
	 */
	@PreAuthorize("@ss.hasPermi('manage:partner:export')")
	@Log(title = "合作商管理", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public void export(HttpServletResponse response, Partner partner) {
		List<Partner> list = partnerService.selectPartnerList(partner);
		ExcelUtil<Partner> util = new ExcelUtil<Partner>(Partner.class);
		util.exportExcel(response, list, "合作商管理数据");
	}

	/**
	 * 获取合作商管理详细信息
	 */
	@PreAuthorize("@ss.hasPermi('manage:partner:query')")
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {
		return success(partnerService.selectPartnerById(id));
	}

	/**
	 * 新增合作商管理
	 */
	@PreAuthorize("@ss.hasPermi('manage:partner:add')")
	@Log(title = "合作商管理", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody Partner partner) {
		return toAjax(partnerService.insertPartner(partner));
	}

	/**
	 * 修改合作商管理
	 */
	@PreAuthorize("@ss.hasPermi('manage:partner:edit')")
	@Log(title = "合作商管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody Partner partner) {
		return toAjax(partnerService.updatePartner(partner));
	}

	/**
	 * 删除合作商管理
	 */
	@PreAuthorize("@ss.hasPermi('manage:partner:remove')")
	@Log(title = "合作商管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public AjaxResult remove(@PathVariable Long[] ids) {
		return toAjax(partnerService.deletePartnerByIds(ids));
	}

	/**
	 * 重置合作商密码
	 */
	@PreAuthorize("@ss.hasPermi('manage:partner:edit')")
	@Log(title = "合作商管理", businessType = BusinessType.UPDATE)
	@PutMapping("resetPwd/{id}")
	public AjaxResult resetPwd(@PathVariable Long id) {
		Partner partner = new Partner();
		partner.setId(id);
		partner.setPassword(SecurityUtils.encryptPassword("123456"));
		return toAjax(partnerService.updatePartner(partner));
	}
}
