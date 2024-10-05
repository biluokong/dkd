package com.dkd.manage.controller;

import com.dkd.common.annotation.Log;
import com.dkd.common.constant.DkdContants;
import com.dkd.common.core.controller.BaseController;
import com.dkd.common.core.domain.AjaxResult;
import com.dkd.common.core.page.TableDataInfo;
import com.dkd.common.enums.BusinessType;
import com.dkd.common.utils.poi.ExcelUtil;
import com.dkd.manage.domain.Emp;
import com.dkd.manage.domain.VendingMachine;
import com.dkd.manage.service.IEmpService;
import com.dkd.manage.service.IVendingMachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 人员列表Controller
 *
 * @author biluo
 * @date 2024-07-30
 */
@RestController
@RequestMapping("/manage/emp")
@RequiredArgsConstructor
public class EmpController extends BaseController {
	private final IEmpService empService;
	private final IVendingMachineService vendingMachineService;

	/**
	 * 查询人员列表列表
	 */
	@PreAuthorize("@ss.hasPermi('manage:emp:list')")
	@GetMapping("/list")
	public TableDataInfo list(Emp emp) {
		startPage();
		List<Emp> list = empService.selectEmpList(emp);
		return getDataTable(list);
	}

	/**
	 * 导出人员列表列表
	 */
	@PreAuthorize("@ss.hasPermi('manage:emp:export')")
	@Log(title = "人员列表", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public void export(HttpServletResponse response, Emp emp) {
		List<Emp> list = empService.selectEmpList(emp);
		ExcelUtil<Emp> util = new ExcelUtil<Emp>(Emp.class);
		util.exportExcel(response, list, "人员列表数据");
	}

	/**
	 * 获取人员列表详细信息
	 */
	@PreAuthorize("@ss.hasPermi('manage:emp:query')")
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {
		return success(empService.selectEmpById(id));
	}

	/**
	 * 新增人员列表
	 */
	@PreAuthorize("@ss.hasPermi('manage:emp:add')")
	@Log(title = "人员列表", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody Emp emp) {
		return toAjax(empService.insertEmp(emp));
	}

	/**
	 * 修改人员列表
	 */
	@PreAuthorize("@ss.hasPermi('manage:emp:edit')")
	@Log(title = "人员列表", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody Emp emp) {
		return toAjax(empService.updateEmp(emp));
	}

	/**
	 * 删除人员列表
	 */
	@PreAuthorize("@ss.hasPermi('manage:emp:remove')")
	@Log(title = "人员列表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public AjaxResult remove(@PathVariable Long[] ids) {
		return toAjax(empService.deleteEmpByIds(ids));
	}

	/**
	 * 根据售货机编码查询运营人员列表
	 */
	@PreAuthorize("@ss.hasPermi('manage:emp:list')")
	@GetMapping("/businessList/{innerCode}")
	public AjaxResult getVendingMachineByInnerCode(@PathVariable String innerCode) {
		VendingMachine vendingMachine = vendingMachineService.selectVendingMachineByInnerCode(innerCode);
		if (vendingMachine == null) {
			return error("售货机不存在");
		}
		Emp emp = new Emp();
		emp.setRegionId(vendingMachine.getRegionId());
		emp.setRoleCode(DkdContants.ROLE_CODE_BUSINESS);
		emp.setStatus(DkdContants.EMP_STATUS_NORMAL);
		return success(empService.selectEmpList(emp));
	}
	/**
	 * 根据售货机编码查询运维人员列表
	 */
	@PreAuthorize("@ss.hasPermi('manage:emp:list')")
	@GetMapping("/operationList/{innerCode}")
	public AjaxResult getOperationListByInnerCode(@PathVariable String innerCode) {
		VendingMachine vendingMachine = vendingMachineService.selectVendingMachineByInnerCode(innerCode);
		if (vendingMachine == null) {
			return error("售货机不存在");
		}
		Emp emp = new Emp();
		emp.setRegionId(vendingMachine.getRegionId());
		emp.setRoleCode(DkdContants.ROLE_CODE_OPERATOR);
		emp.setStatus(DkdContants.EMP_STATUS_NORMAL);
		return success(empService.selectEmpList(emp));
	}
}
