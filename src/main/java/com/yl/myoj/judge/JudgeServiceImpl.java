package com.yl.myoj.judge;

import cn.hutool.json.JSONUtil;
import com.yl.myoj.common.ErrorCode;
import com.yl.myoj.exception.BusinessException;
import com.yl.myoj.judge.codesandbox.CodeSandbox;
import com.yl.myoj.judge.codesandbox.CodeSandboxFactory;
import com.yl.myoj.judge.codesandbox.CoedSandboxProxy;
import com.yl.myoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yl.myoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.yl.myoj.judge.strategy.JudgeContext;
import com.yl.myoj.model.dto.question.JudgeCase;
import com.yl.myoj.judge.codesandbox.model.JudgeInfo;
import com.yl.myoj.model.entity.Question;
import com.yl.myoj.model.entity.QuestionSubmit;
import com.yl.myoj.model.enums.QuestionSubmitStatusEnum;
import com.yl.myoj.service.QuestionService;
import com.yl.myoj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Date: 2023/9/14 - 09 - 14 - 21:21
 * @Description: com.yl.myoj.judge
 * 判题服务实现
 */

@Service
public class JudgeServiceImpl implements JudgeService {

    @Value("${codesandbox.type:example}")
    private String type;
    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private QuestionService questionService;

    @Resource
    private JudgeManager judgeManager;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        //1)题目提交记录以及题目是否存在
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }

        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }

        //2）更改题目提交的状态为判题中，防止重复执行代码
        //判断题目的状态
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean success = questionSubmitService.updateById(questionSubmitUpdate);
        if (!success) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新失败");
        }
        //3)调用代码沙箱
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox = new CoedSandboxProxy(codeSandbox);
        //获取输入用例
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        //使用链式更方便的给对象赋值
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);

        //4）根据沙箱的执行结果，判断题目的执行结果和状态
        List<String> outputList = executeCodeResponse.getOutputList();
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setOutputList(outputList);
        judgeContext.setInPutList(inputList);
        judgeContext.setQuestion(question);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setQuestionSubmit(questionSubmit);


        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);

        //5）修改数据库中题目提交记录的状态和结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        boolean b = questionSubmitService.updateById(questionSubmitUpdate);
        if (!b) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新失败");
        }

        return questionSubmitService.getById(questionSubmitId);


    }

}
