package com.dkd.manage.controller;

import com.dkd.common.annotation.Log;
import com.dkd.common.core.controller.BaseController;
import com.dkd.common.core.domain.AjaxResult;
import com.dkd.common.core.page.TableDataInfo;
import com.dkd.common.enums.BusinessType;
import com.dkd.common.utils.poi.ExcelUtil;
import com.dkd.manage.domain.Sku;
import com.dkd.manage.service.ISkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 商品管理Controller
 *
 * @author biluo
 * @date 2024-07-30
 */
@RestController
@RequestMapping("/manage/sku")
public class SkuController extends BaseController {
	@Autowired
	private ISkuService skuService;

	/**
	 * 查询商品管理列表
	 */
	@PreAuthorize("@ss.hasPermi('manage:sku:list')")
	@GetMapping("/list")
	public TableDataInfo list(Sku sku) {
		startPage();
		List<Sku> list = skuService.selectSkuList(sku);
		return getDataTable(list);
	}

	/**
	 * 导出商品管理列表
	 */
	@PreAuthorize("@ss.hasPermi('manage:sku:export')")
	@Log(title = "商品管理", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public void export(HttpServletResponse response, Sku sku) {
		List<Sku> list = skuService.selectSkuList(sku);
		ExcelUtil<Sku> util = new ExcelUtil<Sku>(Sku.class);
		util.exportExcel(response, list, "商品管理数据");
	}

	/**
	 * 获取商品管理详细信息
	 */
	@PreAuthorize("@ss.hasPermi('manage:sku:query')")
	@GetMapping(value = "/{skuId}")
	public AjaxResult getInfo(@PathVariable("skuId") Long skuId) {
		return success(skuService.selectSkuBySkuId(skuId));
	}

	/**
	 * 新增商品管理
	 */
	@PreAuthorize("@ss.hasPermi('manage:sku:add')")
	@Log(title = "商品管理", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody Sku sku) {
		return toAjax(skuService.insertSku(sku));
	}

	/**
	 * 修改商品管理
	 */
	@PreAuthorize("@ss.hasPermi('manage:sku:edit')")
	@Log(title = "商品管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody Sku sku) {
		return toAjax(skuService.updateSku(sku));
	}

	/**
	 * 删除商品管理
	 */
	@PreAuthorize("@ss.hasPermi('manage:sku:remove')")
	@Log(title = "商品管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{skuIds}")
	public AjaxResult remove(@PathVariable Long[] skuIds) {
		return toAjax(skuService.deleteSkuBySkuIds(skuIds));
	}

    /**
     * 导入商品管理列表
     */
    @PreAuthorize("@ss.hasPermi('manage:sku:add')")
    @Log(title = "商品管理", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public AjaxResult importData(MultipartFile file) throws IOException {
        ExcelUtil<Sku> excelUtil = new ExcelUtil<>(Sku.class);
        List<Sku> skuList = excelUtil.importExcel(file.getInputStream());
        return toAjax(skuService.batchInsertSku(skuList));
    }
}
