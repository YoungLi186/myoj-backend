package com.yl.myoj.judge.codesandbox;

import com.yl.myoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yl.myoj.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * @Date: 2023/9/13 - 09 - 13 - 21:56
 * @Description: com.yl.myoj.judge.codesandbox
 * 代码沙箱代理类，使用了代理模式，增强功能
 */
@Slf4j
@AllArgsConstructor
public class CoedSandboxProxy implements CodeSandbox {


    @Resource
    private final CodeSandbox codeSandbox;

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息：" + executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        log.info("代码沙箱响信息：" + executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
