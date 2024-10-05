package com.dkd.manage.mapper;

import com.dkd.manage.domain.VendingMachine;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 设备管理Mapper接口
 *
 * @author biluo
 * @date 2024-07-30
 */
public interface VendingMachineMapper
{
    /**
     * 查询设备管理
     *
     * @param id 设备管理主键
     * @return 设备管理
     */
    public VendingMachine selectVendingMachineById(Long id);

    /**
     * 查询设备管理列表
     *
     * @param vendingMachine 设备管理
     * @return 设备管理集合
     */
    public List<VendingMachine> selectVendingMachineList(VendingMachine vendingMachine);

    /**
     * 新增设备管理
     *
     * @param vendingMachine 设备管理
     * @return 结果
     */
    public int insertVendingMachine(VendingMachine vendingMachine);

    /**
     * 修改设备管理
     *
     * @param vendingMachine 设备管理
     * @return 结果
     */
    public int updateVendingMachine(VendingMachine vendingMachine);

    /**
     * 删除设备管理
     *
     * @param id 设备管理主键
     * @return 结果
     */
    public int deleteVendingMachineById(Long id);

    /**
     * 批量删除设备管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVendingMachineByIds(Long[] ids);

    /**
     * 根据innerCode查询
     * @param innerCode
     * @return
     */
    @Select("select * from Tb_vending_machine where inner_code = #{innerCode}")
    VendingMachine selectOneByInnerCode(String innerCode);
}
