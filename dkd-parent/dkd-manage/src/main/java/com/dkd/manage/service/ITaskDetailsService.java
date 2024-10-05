package com.dkd.manage.service;

import com.dkd.manage.domain.TaskDetails;

import java.util.List;

/**
 * 工单详情Service接口
 *
 * @author biluo
 * @date 2024-07-31
 */
public interface ITaskDetailsService
{
    /**
     * 查询工单详情
     *
     * @param detailsId 工单详情主键
     * @return 工单详情
     */
    public TaskDetails selectTaskDetailsByDetailsId(Long detailsId);

    /**
     * 查询工单详情列表
     *
     * @param taskDetails 工单详情
     * @return 工单详情集合
     */
    public List<TaskDetails> selectTaskDetailsList(TaskDetails taskDetails);

    /**
     * 新增工单详情
     *
     * @param taskDetails 工单详情
     * @return 结果
     */
    public int insertTaskDetails(TaskDetails taskDetails);

    /**
     * 修改工单详情
     *
     * @param taskDetails 工单详情
     * @return 结果
     */
    public int updateTaskDetails(TaskDetails taskDetails);

    /**
     * 批量删除工单详情
     *
     * @param detailsIds 需要删除的工单详情主键集合
     * @return 结果
     */
    public int deleteTaskDetailsByDetailsIds(Long[] detailsIds);

    /**
     * 删除工单详情信息
     *
     * @param detailsId 工单详情主键
     * @return 结果
     */
    public int deleteTaskDetailsByDetailsId(Long detailsId);

    /**
     * 批量新增工单详情
     * @param taskDetailsList
     * @return 结果
     */
    int batchInsertTaskDetails(List<TaskDetails> taskDetailsList);
}
