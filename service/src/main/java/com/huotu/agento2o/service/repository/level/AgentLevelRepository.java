/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 *
 */

package com.huotu.agento2o.service.repository.level;

import com.huotu.agento2o.service.entity.level.AgentLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.OrderBy;
import java.util.List;

/**
 * Created by helloztt on 2016/5/11.
 */
@Repository(value = "agentLevelRepository")
public interface AgentLevelRepository extends JpaRepository<AgentLevel, Integer> {

    List<AgentLevel> findByCustomer_customerIdOrderByLevel(Integer customerId);

    @Query("select max(a.level) from AgentLevel a where a.customer.customerId = ?1")
    Integer findLastLevel(Integer customerId);

    AgentLevel findByLevelIdAndCustomer_customerId(Integer levelId,Integer customerId);

}
