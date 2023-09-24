package com.yl.myoj.judge;

import com.yl.myoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.yl.myoj.model.entity.QuestionSubmit;
import com.yl.myoj.model.vo.QuestionSubmitVO;
import com.yl.myoj.model.vo.QuestionVO;

/**
 * @Date: 2023/9/14 - 09 - 14 - 21:11
 * @Description: com.yl.myoj.judge
 */
public interface JudgeService {
    QuestionSubmit doJudge(long questionSubmitId);
}
