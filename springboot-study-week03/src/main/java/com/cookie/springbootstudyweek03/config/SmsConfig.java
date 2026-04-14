package com.cookie.springbootstudyweek03.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "sms.ytx")
public class SmsConfig {
    private String serverIp;
    private String serverPort;
    private String accountSid;
    private String accountToken;
    private String appId;
    private String to;
    private String templateId;
    private String subAppend;
    private String reqId;


}
