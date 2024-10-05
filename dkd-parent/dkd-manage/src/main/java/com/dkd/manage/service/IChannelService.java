package com.dkd.manage.service;

import com.dkd.manage.domain.Channel;
import com.dkd.manage.domain.dto.ChannelConfigDto;
import com.dkd.manage.domain.vo.ChannelVo;

import java.util.List;

/**
 * 售货机货道Service接口
 *
 * @author biluo
 * @date 2024-07-30
 */
public interface IChannelService
{
    /**
     * 查询售货机货道
     *
     * @param id 售货机货道主键
     * @return 售货机货道
     */
    public Channel selectChannelById(Long id);

    /**
     * 查询售货机货道列表
     *
     * @param channel 售货机货道
     * @return 售货机货道集合
     */
    public List<Channel> selectChannelList(Channel channel);

    /**
     * 新增售货机货道
     *
     * @param channel 售货机货道
     * @return 结果
     */
    public int insertChannel(Channel channel);

    /**
     * 修改售货机货道
     *
     * @param channel 售货机货道
     * @return 结果
     */
    public int updateChannel(Channel channel);

    /**
     * 批量删除售货机货道
     *
     * @param ids 需要删除的售货机货道主键集合
     * @return 结果
     */
    public int deleteChannelByIds(Long[] ids);

    /**
     * 删除售货机货道信息
     *
     * @param id 售货机货道主键
     * @return 结果
     */
    public int deleteChannelById(Long id);

    /**
     * 根据innerCode查询货道
     * @param innerCode
     * @return
     */
    List<ChannelVo> selectChannelVoListByInnerCode(String innerCode);

    /**
     * 更新货道
     * @param channelConfigDto
     * @return
     */
    int updateChannel(ChannelConfigDto channelConfigDto);
}
