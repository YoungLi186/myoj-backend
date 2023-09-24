package com.yl.myoj.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.yl.myoj.model.dto.question.JudgeCase;
import com.yl.myoj.model.dto.question.JudgeConfig;
import com.yl.myoj.judge.codesandbox.model.JudgeInfo;
import com.yl.myoj.model.entity.Question;
import com.yl.myoj.model.enums.JudgeInfoMessageEnum;

import java.util.List;

/**
 * @Date: 2023/9/15 - 09 - 15 - 8:51
 * @Description: com.yl.myoj.judge.strategy
 * 默认策略
 */
public class DefaultJudgeStrategy implements JudgeStrategy {
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {

        List<String> outputList = judgeContext.getOutputList();
        Question question = judgeContext.getQuestion();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        List<String> intPutList = judgeContext.getInPutList();
        //题目限制
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        Long needTimeLimit = judgeConfig.getTimeLimit();
        Long needMemoryLimit = judgeConfig.getMemoryLimit();
        Long memory = judgeInfo.getMemory();
        Long time = judgeInfo.getTime();
        //判断代码沙箱的执行结果数是否和预期数相等
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        judgeInfoResponse.setMemory(memory);
        judgeInfoResponse.setTime(time);
        if (outputList.size() != intPutList.size()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        //判断每一项输出是否和预期的输出是否相等
        for (int i = 0; i < outputList.size(); i++) {
            JudgeCase judgeCase = judgeCaseList.get(i);
            if (!outputList.get(i).equals(judgeCase.getOutput())) {
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
                return judgeInfoResponse;
            }
        }

        //判断题目的限制

        if (time > needTimeLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        if (memory > needMemoryLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }


        return judgeInfoResponse;


    }
}
