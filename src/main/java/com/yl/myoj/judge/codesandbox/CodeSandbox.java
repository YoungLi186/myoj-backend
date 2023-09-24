package com.yl.myoj.judge.codesandbox;

import com.yl.myoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yl.myoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * @Date: 2023/9/13 - 09 - 13 - 20:48
 * @Description: com.yl.myoj.judge.codesandbox
 * 代码沙箱接口
 */
public interface CodeSandbox {

    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);


}
