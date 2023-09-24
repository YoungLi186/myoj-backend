package com.yl.myoj.model.dto.question;

import lombok.Data;

/**
 * @Date: 2023/9/8 - 09 - 08 - 21:37
 * @Description: com.yl.myoj.model.dto.question
 * 题目用例
 */
@Data
public class JudgeCase {


    /**
     * 输入用例
     */
    private String input;

    /**
     * 输出用例
     */
    private String output;
}
