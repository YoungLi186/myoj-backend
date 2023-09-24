package com.yl.myoj.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yl.myoj.model.dto.question.QuestionQueryRequest;
import com.yl.myoj.model.dto.questionsubmit.QuestionSubmitAddRequest;

import com.yl.myoj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.yl.myoj.model.entity.Question;
import com.yl.myoj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yl.myoj.model.entity.User;
import com.yl.myoj.model.vo.QuestionSubmitVO;
import com.yl.myoj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;


/**
 * @author 18683
 * @description 针对表【question_submit(题目提交)】的数据库操作Service
 * @createDate 2023-09-08 21:03:45
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {


    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);


    /**
     * 获取查询条件
     *
     * @param questionQuerySubmitRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionQuerySubmitRequest);


    /**
     * 获取题目提交封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);


    /**
     * 分页获取题目提交封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);

}
