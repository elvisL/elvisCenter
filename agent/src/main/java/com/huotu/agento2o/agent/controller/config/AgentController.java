/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.agento2o.agent.controller.config;

import com.huotu.agento2o.agent.config.annotataion.AgtAuthenticationPrincipal;
import com.huotu.agento2o.agent.config.annotataion.RequestAttribute;
import com.huotu.agento2o.common.util.ApiResult;
import com.huotu.agento2o.service.entity.author.Agent;
import com.huotu.agento2o.service.entity.author.Author;
import com.huotu.agento2o.service.entity.author.Shop;
import com.huotu.agento2o.service.service.author.AgentService;
import com.huotu.agento2o.service.service.author.AuthorService;
import com.huotu.agento2o.service.service.author.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by WangJie on 2016/5/23.
 */
@Controller
@RequestMapping("/config")
@PreAuthorize("hasAnyRole('BASE_AGENT','AGENT')")
public class AgentController {

    @Autowired
    private AgentService agentService;
    @Autowired
    private AuthorService authorService;

    /**
     * 展示代理商基本信息(废弃)
     * todo delete
     * @param agent
     * @param model
     * @return
     */
    @RequestMapping(value = "/agentConfig", method = RequestMethod.GET)
    public String showAgent(@AgtAuthenticationPrincipal Agent agent, Model model) {
        model.addAttribute("agent", agentService.findByAgentId(agent.getId()));
        return "config/agentConfig";
    }
    /**
     * 基本资料设置
     *
     * @return
     */
    @RequestMapping("/baseConfig")
    public String baseConfig(@AuthenticationPrincipal Author author, Model model) throws Exception {
        //获取最新数据
        author = authorService.findById(author.getId());
        if(author == null){
            throw new Exception("没有权限");
        }
        if(author instanceof Agent){
            //代理商
            model.addAttribute("agent",author);
            return "config/agentConfig";
        }else if(author instanceof Shop){
            //门店
            model.addAttribute("shop",author);
            return "config/shopConfig";
        }
        throw new Exception("没有权限");
    }

    /**
     * 保存代理商基本信息
     *
     * @param agent
     * @param requestAgent
     * @return
     */
    @RequestMapping(value = "/saveAgentConfig", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult saveAgentConfig(@AgtAuthenticationPrincipal Agent agent, Agent requestAgent) {
        return agentService.saveAgentConfig(agent.getId(), requestAgent);
    }

}
