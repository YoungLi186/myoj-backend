package com.yl.myoj.judge.codesandbox;

import com.yl.myoj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.yl.myoj.judge.codesandbox.impl.RemoteCodeSandbox;
import com.yl.myoj.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * @Date: 2023/9/13 - 09 - 13 - 21:19
 * @Description: com.yl.myoj.judge.codesandbox
 * 代码沙箱静态工厂
 */
public class CodeSandboxFactory {

    public static CodeSandbox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            case "remote":
            default:
                return new RemoteCodeSandbox();
        }

    }

}
