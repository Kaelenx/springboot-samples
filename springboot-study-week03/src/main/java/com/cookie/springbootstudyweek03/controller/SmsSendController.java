package com.cookie.springbootstudyweek03.controller;

import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.cookie.springbootstudyweek03.config.SmsConfig;
import com.cookie.springbootstudyweek03.utils.MapSafeUtils;
import com.cookie.springbootstudyweek03.utils.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RestController
@RequestMapping("/sms")
public class SmsSendController {

    @Resource
    private SmsConfig smsConfig;

    @GetMapping("/send")
    public Result send() {

        CCPRestSmsSDK sdk = new CCPRestSmsSDK();

        sdk.init(smsConfig.getServerIp(), smsConfig.getServerPort());
        sdk.setAccount(smsConfig.getAccountSid(), smsConfig.getAccountToken());
        sdk.setAppId(smsConfig.getAppId());
        sdk.setBodyType(BodyType.Type_JSON);

        String code = "" + (1000 + ThreadLocalRandom.current().nextInt(9000));
        String[] datas = {code, "10"};

        HashMap<String, Object> result = sdk.sendTemplateSMS(smsConfig.getTo(), smsConfig.getTemplateId(), datas);

        System.out.println("SDKTestGetSubAccounts result=" + result);

        String statusCode = MapSafeUtils.getString(result, "statusCode");
        String statusMsg = MapSafeUtils.getString(result, "statusMsg", "未知错误");
        String sid = MapSafeUtils.getString(result, "sid");

        if ("000000".equals(statusCode)) {
            //正常返回输出data包体信息（map）
            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for (String key : keySet) {
                Object object = data.get(key);
                System.out.println(key + " = " + object);
                log.info(key + " = " + object);
            }
            return Result.success("短信发送成功，SID：" + sid);
        } else {
            //异常返回输出错误码和错误信息
            // System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
            log.info("错误码=" + statusCode + " 错误信息= " + statusMsg);
            return Result.fail(Integer.parseInt(statusCode),
                    "短信发送失败：" + statusMsg,
                    sid);
        }
    }
}
