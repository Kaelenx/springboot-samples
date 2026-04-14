package com.cookie.springbootstudyweek06.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_special")
// 实体类描述
@Schema(description = "专题表实体类")
public class Special {

    @Schema(description = "专题主键ID", example = "1965084851284735395", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableId
    private String id;

    @Schema(description = "专题浏览量", example = "1731503")
    @TableField("view_count")
    private Integer viewCount;

    @Schema(description = "专题关注人数", example = "9")
    @TableField("followers_count")
    private Integer followersCount;

    @Schema(description = "是否关注", example = "false", allowableValues = {"true", "false"})
    @TableField("is_following")
    private String isFollowing;

    @Schema(description = "专题标题", example = "2025重阳｜孝满中华 德润人心")
    private String title;

    @Schema(description = "专题简介", example = "登高望远，尊老助老，共度中国节。")
    private String introduction;

    @Schema(description = "专题章节列表JSON字符串", example = "[{\"section_id\":\"1965085377053319925\",\"section_title\":\"重阳习俗\"}]")
    @TableField("section_list")
    private String sectionList;

    @Schema(description = "专题封面图URL", example = "https://pic1.zhimg.com/100/v2-0c73f7047d5e9973f777b14797f63e9c_hd.png")
    private String banner;

    @Schema(description = "专题更新时间戳", example = "1761532312")
    private Integer updated;
}