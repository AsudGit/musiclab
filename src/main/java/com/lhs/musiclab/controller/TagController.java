package com.lhs.musiclab.controller;

import com.lhs.musiclab.pojo.Tag;
import com.lhs.musiclab.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
//origins  ： 允许可访问的域列表
//maxAge：准备响应前的缓存持续的最大时间（以秒为单位）。
@CrossOrigin(origins = "*", maxAge = 3600)
public class TagController {
    @Autowired
    private TagService tagService;

    /***
     * 返回博客下的标签
     * @param bid
     * @return 标签数组
     */
    @GetMapping("/list/{bid}")
    public List<Tag> listByBid(@PathVariable(value = "bid")String bid){
        List<Tag> list = tagService.list(bid);
        return list;
    }

    /***
     * 查询出最近的热词
     * @return 热度最高的标签
     */
    @GetMapping("/hot")
    @ResponseBody
    public List<Tag> hotTagList(@RequestParam(value = "start")Integer start,
                                @RequestParam(value = "size")Integer size){
        return tagService.getHotTag(start,size);
    }
}
