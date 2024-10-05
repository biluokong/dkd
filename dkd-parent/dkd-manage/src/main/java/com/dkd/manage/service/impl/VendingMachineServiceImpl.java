package com.dkd.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.dkd.common.constant.DkdContants;
import com.dkd.common.exception.GlobalException;
import com.dkd.common.utils.DateUtils;
import com.dkd.common.utils.uuid.UUIDUtils;
import com.dkd.manage.domain.Channel;
import com.dkd.manage.domain.Node;
import com.dkd.manage.domain.VendingMachine;
import com.dkd.manage.domain.VmType;
import com.dkd.manage.mapper.ChannelMapper;
import com.dkd.manage.mapper.NodeMapper;
import com.dkd.manage.mapper.VendingMachineMapper;
import com.dkd.manage.mapper.VmTypeMapper;
import com.dkd.manage.service.IVendingMachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备管理Service业务层处理
 *
 * @author biluo
 * @date 2024-07-30
 */
@Service
@RequiredArgsConstructor
public class VendingMachineServiceImpl implements IVendingMachineService {
	private final VendingMachineMapper vendingMachineMapper;
	private final VmTypeMapper vmTypeMapper;
	private final NodeMapper nodeMapper;
	private final ChannelMapper channelMapper;

	/**
	 * 查询设备管理
	 *
	 * @param id 设备管理主键
	 * @return 设备管理
	 */
	@Override
	public VendingMachine selectVendingMachineById(Long id) {
		return vendingMachineMapper.selectVendingMachineById(id);
	}

	/**
	 * 查询设备管理列表
	 *
	 * @param vendingMachine 设备管理
	 * @return 设备管理
	 */
	@Override
	public List<VendingMachine> selectVendingMachineList(VendingMachine vendingMachine) {
		return vendingMachineMapper.selectVendingMachineList(vendingMachine);
	}

	/**
	 * 新增设备管理
	 *
	 * @param vendingMachine 设备管理
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insertVendingMachine(VendingMachine vendingMachine) {
		// 1.新增设备
		vendingMachine.setInnerCode(UUIDUtils.getUUID());
		VmType vmType = vmTypeMapper.selectVmTypeById(vendingMachine.getVmTypeId());
		vendingMachine.setChannelMaxCapacity(vmType.getChannelMaxCapacity());
		Node node = nodeMapper.selectNodeById(vendingMachine.getNodeId());
		BeanUtil.copyProperties(node, vendingMachine, "id"); // 商圈类型、区域、合作商
		vendingMachine.setAddr(node.getAddress());
		vendingMachine.setVmStatus(DkdContants.VM_STATUS_NODEPLOY);
		vendingMachine.setCreateTime(DateUtils.getNowDate());
		vendingMachine.setUpdateTime(DateUtils.getNowDate());
		int count = vendingMachineMapper.insertVendingMachine(vendingMachine);
		if (count < 1) {
			throw new GlobalException("新增设备失败");
		}

		// 2.新增货道
		ArrayList<Channel> channels = new ArrayList<>();
		for (int i = 1; i <= vmType.getVmRow(); i++) {
			for (int j = 1; j <= vmType.getVmCol(); j++) {
				Channel channel = new Channel();
				channel.setChannelCode(i + "-" + j);
				channel.setVmId(vendingMachine.getId());
				channel.setInnerCode(vendingMachine.getInnerCode());
				channel.setMaxCapacity(vmType.getChannelMaxCapacity());
				channel.setSkuId(0L);	// 0-没有关联商品
				channel.setCreateTime(DateUtils.getNowDate());
				channel.setUpdateTime(DateUtils.getNowDate());
				channels.add(channel);
			}
		}
		count = channelMapper.batchInsertChannel(channels);
		return count;
	}

	/**
	 * 修改设备管理
	 *
	 * @param vendingMachine 设备管理
	 * @return 结果
	 */
	@Override
	public int updateVendingMachine(VendingMachine vendingMachine) {
		if (vendingMachine.getNodeId() != null) {
			Node node = nodeMapper.selectNodeById(vendingMachine.getNodeId());
			BeanUtil.copyProperties(node, vendingMachine, "id");
			vendingMachine.setAddr(node.getAddress());
		}
		vendingMachine.setUpdateTime(DateUtils.getNowDate());
		return vendingMachineMapper.updateVendingMachine(vendingMachine);
	}

	/**
	 * 批量删除设备管理
	 *
	 * @param ids 需要删除的设备管理主键
	 * @return 结果
	 */
	@Override
	public int deleteVendingMachineByIds(Long[] ids) {
		return vendingMachineMapper.deleteVendingMachineByIds(ids);
	}

	/**
	 * 删除设备管理信息
	 *
	 * @param id 设备管理主键
	 * @return 结果
	 */
	@Override
	public int deleteVendingMachineById(Long id) {
		return vendingMachineMapper.deleteVendingMachineById(id);
	}

	@Override
	public VendingMachine selectVendingMachineByInnerCode(String innerCode) {
		return vendingMachineMapper.selectOneByInnerCode(innerCode);
	}
}
