package com.dkd.manage.service.impl;

import com.dkd.common.utils.DateUtils;
import com.dkd.manage.domain.Region;
import com.dkd.manage.domain.vo.RegionVo;
import com.dkd.manage.mapper.EmpMapper;
import com.dkd.manage.mapper.RegionMapper;
import com.dkd.manage.service.IRegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 区域管理Service业务层处理
 *
 * @author biluo
 * @date 2024-07-27
 */
@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements IRegionService {
	private final RegionMapper regionMapper;
	private final EmpMapper empMapper;

	/**
	 * 查询区域管理
	 *
	 * @param id 区域管理主键
	 * @return 区域管理
	 */
	@Override
	public Region selectRegionById(Long id) {
		return regionMapper.selectRegionById(id);
	}

	/**
	 * 查询区域管理列表
	 *
	 * @param region 区域管理
	 * @return 区域管理
	 */
	@Override
	public List<Region> selectRegionList(Region region) {
		return regionMapper.selectRegionList(region);
	}

	/**
	 * 新增区域管理
	 *
	 * @param region 区域管理
	 * @return 结果
	 */
	@Override
	public int insertRegion(Region region) {
		region.setCreateTime(DateUtils.getNowDate());
		return regionMapper.insertRegion(region);
	}

	/**
	 * 修改区域管理
	 *
	 * @param region 区域管理
	 * @return 结果
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updateRegion(Region region) {
		empMapper.updateByRegionId(region.getId(), region.getRegionName());
		region.setUpdateTime(DateUtils.getNowDate());
		return regionMapper.updateRegion(region);
	}

	/**
	 * 批量删除区域管理
	 *
	 * @param ids 需要删除的区域管理主键
	 * @return 结果
	 */
	@Override
	public int deleteRegionByIds(Long[] ids) {
		return regionMapper.deleteRegionByIds(ids);
	}

	/**
	 * 删除区域管理信息
	 *
	 * @param id 区域管理主键
	 * @return 结果
	 */
	@Override
	public int deleteRegionById(Long id) {
		return regionMapper.deleteRegionById(id);
	}

	/**
	 * 查询区域管理列表
	 *
	 * @param region 区域管理
	 * @return RegionVoList
	 */
	@Override
	public List<RegionVo> selectRegionVoList(Region region) {
		return regionMapper.selectRegionVoList(region);
	}
}
