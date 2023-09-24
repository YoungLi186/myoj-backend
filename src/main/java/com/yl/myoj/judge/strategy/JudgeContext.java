package com.yl.myoj.judge.strategy;

import com.yl.myoj.model.dto.question.JudgeCase;
import com.yl.myoj.judge.codesandbox.model.JudgeInfo;
import com.yl.myoj.model.entity.Question;
import com.yl.myoj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * @Date: 2023/9/15 - 09 - 15 - 8:50
 * @Description: com.yl.myoj.judge.strategy
 * 策略管理使用
 */
@Data
public class JudgeContext {

    private List<String> outputList;
    private List<String> inPutList;

    private Question question;

    private List<JudgeCase> judgeCaseList;

    private JudgeInfo judgeInfo;

    private QuestionSubmit questionSubmit;


}
