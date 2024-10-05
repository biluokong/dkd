package com.dkd.manage.controller;

import com.dkd.common.annotation.Log;
import com.dkd.common.core.controller.BaseController;
import com.dkd.common.core.domain.AjaxResult;
import com.dkd.common.core.page.TableDataInfo;
import com.dkd.common.enums.BusinessType;
import com.dkd.common.utils.poi.ExcelUtil;
import com.dkd.manage.domain.TaskDetails;
import com.dkd.manage.service.ITaskDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 工单详情Controller
 *
 * @author biluo
 * @date 2024-07-31
 */
@RestController
@RequestMapping("/manage/taskDetails")
public class TaskDetailsController extends BaseController {
	@Autowired
	private ITaskDetailsService taskDetailsService;

	/**
	 * 查询工单详情列表
	 */
	@PreAuthorize("@ss.hasPermi('manage:taskDetails:list')")
	@GetMapping("/list")
	public TableDataInfo list(TaskDetails taskDetails) {
		startPage();
		List<TaskDetails> list = taskDetailsService.selectTaskDetailsList(taskDetails);
		return getDataTable(list);
	}

	/**
	 * 导出工单详情列表
	 */
	@PreAuthorize("@ss.hasPermi('manage:taskDetails:export')")
	@Log(title = "工单详情", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public void export(HttpServletResponse response, TaskDetails taskDetails) {
		List<TaskDetails> list = taskDetailsService.selectTaskDetailsList(taskDetails);
		ExcelUtil<TaskDetails> util = new ExcelUtil<TaskDetails>(TaskDetails.class);
		util.exportExcel(response, list, "工单详情数据");
	}

	/**
	 * 获取工单详情详细信息
	 */
	@PreAuthorize("@ss.hasPermi('manage:taskDetails:query')")
	@GetMapping(value = "/{detailsId}")
	public AjaxResult getInfo(@PathVariable("detailsId") Long detailsId) {
		return success(taskDetailsService.selectTaskDetailsByDetailsId(detailsId));
	}

	/**
	 * 新增工单详情
	 */
	@PreAuthorize("@ss.hasPermi('manage:taskDetails:add')")
	@Log(title = "工单详情", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody TaskDetails taskDetails) {
		return toAjax(taskDetailsService.insertTaskDetails(taskDetails));
	}

	/**
	 * 修改工单详情
	 */
	@PreAuthorize("@ss.hasPermi('manage:taskDetails:edit')")
	@Log(title = "工单详情", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody TaskDetails taskDetails) {
		return toAjax(taskDetailsService.updateTaskDetails(taskDetails));
	}

	/**
	 * 删除工单详情
	 */
	@PreAuthorize("@ss.hasPermi('manage:taskDetails:remove')")
	@Log(title = "工单详情", businessType = BusinessType.DELETE)
	@DeleteMapping("/{detailsIds}")
	public AjaxResult remove(@PathVariable Long[] detailsIds) {
		return toAjax(taskDetailsService.deleteTaskDetailsByDetailsIds(detailsIds));
	}

	/**
	 * 查看工单补货详情
	 */
	@PreAuthorize("@ss.hasPermi('manage:taskDetails:list')")
	@GetMapping(value = "/byTaskId/{taskId}")
	public AjaxResult byTaskId(@PathVariable("taskId") Long taskId) {
		TaskDetails taskDetailsParam = new TaskDetails();
		taskDetailsParam.setTaskId(taskId);
		return success(taskDetailsService.selectTaskDetailsList(taskDetailsParam));
	}
}
