package com.dkd.manage.mapper;

import com.dkd.manage.domain.Sku;

import java.util.List;

/**
 * 商品管理Mapper接口
 *
 * @author biluo
 * @date 2024-07-30
 */
public interface SkuMapper
{
    /**
     * 查询商品管理
     *
     * @param skuId 商品管理主键
     * @return 商品管理
     */
    public Sku selectSkuBySkuId(Long skuId);

    /**
     * 查询商品管理列表
     *
     * @param sku 商品管理
     * @return 商品管理集合
     */
    public List<Sku> selectSkuList(Sku sku);

    /**
     * 新增商品管理
     *
     * @param sku 商品管理
     * @return 结果
     */
    public int insertSku(Sku sku);

    /**
     * 修改商品管理
     *
     * @param sku 商品管理
     * @return 结果
     */
    public int updateSku(Sku sku);

    /**
     * 删除商品管理
     *
     * @param skuId 商品管理主键
     * @return 结果
     */
    public int deleteSkuBySkuId(Long skuId);

    /**
     * 批量删除商品管理
     *
     * @param skuIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSkuBySkuIds(Long[] skuIds);

    /**
	 * 批量插入
	 * @param skuList
	 * @return
	 */
	int batchInsertSku(List<Sku> skuList);
}
