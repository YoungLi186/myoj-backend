package com.yl.myoj.judge.strategy;

import com.yl.myoj.judge.codesandbox.model.JudgeInfo;

/**
 * @Date: 2023/9/15 - 09 - 15 - 8:49
 * @Description: com.yl.myoj.judge.strategy
 */
public interface JudgeStrategy {
    JudgeInfo doJudge(JudgeContext judgeContext);
}
