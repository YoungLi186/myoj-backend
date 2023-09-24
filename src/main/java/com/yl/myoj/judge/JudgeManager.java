package com.yl.myoj.judge;

import com.yl.myoj.judge.strategy.DefaultJudgeStrategy;
import com.yl.myoj.judge.strategy.JavaLanguageJudgeStrategy;
import com.yl.myoj.judge.strategy.JudgeContext;
import com.yl.myoj.judge.strategy.JudgeStrategy;
import com.yl.myoj.judge.codesandbox.model.JudgeInfo;
import com.yl.myoj.model.entity.QuestionSubmit;
import com.yl.myoj.model.enums.QuestionSubmitLanguageEnum;
import org.springframework.stereotype.Service;

/**
 * @Date: 2023/9/15 - 09 - 15 - 9:56
 * @Description: com.yl.myoj.judge
 * 判题策略管理（简化调用）策略模式
 */
@Service
public class JudgeManager {

    public JudgeInfo doJudge(JudgeContext judgeContext) {

        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();

        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();

        //选择使用哪种策略模式
        if (language.equals(QuestionSubmitLanguageEnum.JAVA.getValue())) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }

        return judgeStrategy.doJudge(judgeContext);


    }
}
