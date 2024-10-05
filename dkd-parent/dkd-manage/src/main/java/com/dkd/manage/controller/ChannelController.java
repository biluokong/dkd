package com.dkd.manage.controller;

import com.dkd.common.annotation.Log;
import com.dkd.common.core.controller.BaseController;
import com.dkd.common.core.domain.AjaxResult;
import com.dkd.common.core.page.TableDataInfo;
import com.dkd.common.enums.BusinessType;
import com.dkd.common.utils.poi.ExcelUtil;
import com.dkd.manage.domain.Channel;
import com.dkd.manage.domain.dto.ChannelConfigDto;
import com.dkd.manage.service.IChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 售货机货道Controller
 *
 * @author biluo
 * @date 2024-07-30
 */
@RestController
@RequestMapping("/manage/channel")
public class ChannelController extends BaseController {
	@Autowired
	private IChannelService channelService;

	/**
	 * 查询售货机货道列表
	 */
	@PreAuthorize("@ss.hasPermi('manage:channel:list')")
	@GetMapping("/list")
	public TableDataInfo list(Channel channel) {
		startPage();
		List<Channel> list = channelService.selectChannelList(channel);
		return getDataTable(list);
	}

	/**
	 * 导出售货机货道列表
	 */
	@PreAuthorize("@ss.hasPermi('manage:channel:export')")
	@Log(title = "售货机货道", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public void export(HttpServletResponse response, Channel channel) {
		List<Channel> list = channelService.selectChannelList(channel);
		ExcelUtil<Channel> util = new ExcelUtil<Channel>(Channel.class);
		util.exportExcel(response, list, "售货机货道数据");
	}

	/**
	 * 获取售货机货道详细信息
	 */
	@PreAuthorize("@ss.hasPermi('manage:channel:query')")
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {
		return success(channelService.selectChannelById(id));
	}

	/**
	 * 新增售货机货道
	 */
	@PreAuthorize("@ss.hasPermi('manage:channel:add')")
	@Log(title = "售货机货道", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody Channel channel) {
		return toAjax(channelService.insertChannel(channel));
	}

	/**
	 * 修改售货机货道
	 */
	@PreAuthorize("@ss.hasPermi('manage:channel:edit')")
	@Log(title = "售货机货道", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody Channel channel) {
		return toAjax(channelService.updateChannel(channel));
	}

	/**
	 * 删除售货机货道
	 */
	@PreAuthorize("@ss.hasPermi('manage:channel:remove')")
	@Log(title = "售货机货道", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public AjaxResult remove(@PathVariable Long[] ids) {
		return toAjax(channelService.deleteChannelByIds(ids));
	}

    /**
     * 根据售货机编号查询货道信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:channel:list')")
    @GetMapping("/list/{innerCode}")
    public AjaxResult getChannelByVendingMachineId(@PathVariable("innerCode") String innerCode) {
        return success(channelService.selectChannelVoListByInnerCode(innerCode));
    }

	/**
	 * 更新货道关联商品
	 */
	@PreAuthorize("@ss.hasPermi('manage:channel:edit')")
	@Log(title = "售货机货道", businessType = BusinessType.UPDATE)
	@PutMapping("/config")
	public AjaxResult updateSku(@RequestBody ChannelConfigDto channelConfigDto) {
		return toAjax(channelService.updateChannel(channelConfigDto));
	}
}
