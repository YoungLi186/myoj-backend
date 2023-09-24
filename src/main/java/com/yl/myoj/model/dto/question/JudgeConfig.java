package com.yl.myoj.model.dto.question;

import lombok.Data;

/**
 * @Date: 2023/9/8 - 09 - 08 - 21:40
 * @Description: com.yl.myoj.model.dto.question
 * 题目配置
 */
@Data
public class JudgeConfig {

    /**
     * 时间限制（ms）
     */
    private Long timeLimit;

    /**
     * 内存限制（kb）
     */
    private Long memoryLimit;

    /**
     * 堆栈限制（kb）
     */
    private Long stackLimit;
}
