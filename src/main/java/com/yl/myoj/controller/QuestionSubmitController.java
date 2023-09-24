package com.yl.myoj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yl.myoj.annotation.AuthCheck;
import com.yl.myoj.common.BaseResponse;
import com.yl.myoj.common.ErrorCode;
import com.yl.myoj.common.ResultUtils;
import com.yl.myoj.constant.UserConstant;
import com.yl.myoj.exception.BusinessException;
import com.yl.myoj.model.dto.question.QuestionQueryRequest;
import com.yl.myoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yl.myoj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.yl.myoj.model.entity.Question;
import com.yl.myoj.model.entity.QuestionSubmit;
import com.yl.myoj.model.entity.User;
import com.yl.myoj.model.vo.QuestionSubmitVO;
import com.yl.myoj.service.QuestionSubmitService;
import com.yl.myoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
@Deprecated
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return 提交记录的id
     */
    @PostMapping("/")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
                                               HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录成功才可以提交题目
        final User loginUser = userService.getLoginUser(request);
        long result = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(result);
    }


    /**
     * 分页获取题目提交列表（除了管理员和普通用户外只能看到非答案，提交代码等公开信息）
     *
     * @param questionSubmitQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
                                                                         HttpServletRequest request) {
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        User loginUser = userService.getLoginUser(request);


        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, loginUser));


    }

}
