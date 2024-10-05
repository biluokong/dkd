package com.dkd.manage.domain;

import com.dkd.common.annotation.Excel;
import com.dkd.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 区域管理对象 tb_region
 *
 * @author biluo
 * @date 2024-07-27
 */
public class Region extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	private Long id;

	/**
	 * 区域名称
	 */
	@Excel(name = "区域名称")
	private String regionName;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getRegionName() {
		return regionName;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("id", getId())
				.append("regionName", getRegionName())
				.append("createTime", getCreateTime())
				.append("updateTime", getUpdateTime())
				.append("createBy", getCreateBy())
				.append("updateBy", getUpdateBy())
				.append("remark", getRemark())
				.toString();
	}
}
