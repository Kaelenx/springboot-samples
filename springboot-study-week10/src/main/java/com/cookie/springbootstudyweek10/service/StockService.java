package com.cookie.springbootstudyweek10.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class StockService {

    // 新浪免费股票 API 模板
    private static final String SINA_STOCK_API = "http://hq.sinajs.cn/list={code}";

    private final RestTemplate restTemplate;

    // 手动配置 RestTemplate 处理新浪 API 的 GBK 编码
    public StockService() {
        this.restTemplate = new RestTemplate();
        // 设置请求头，指定接受 GBK 编码
        HttpHeaders headers = new HttpHeaders();
        headers.setAcceptCharset(Arrays.asList(StandardCharsets.UTF_8, Charset.forName("GBK")));
        // 也可以直接添加 StringHttpMessageConverter 强制解析 GBK
    }

    /**
     * 获取并解析实时股票数据
     * @param stockCode 股票代码（如 sh600519）
     * @return 包含股票核心信息的 Map
     */
    public Map<String, String> getRealTimeStockInfo(String stockCode) {
        Map<String, String> stockInfo = new HashMap<>();
        try {
            // 1. 调用 API
            ResponseEntity<String> response = restTemplate.getForEntity(SINA_STOCK_API, String.class, stockCode);
            String rawData = response.getBody();
            if (rawData == null || rawData.isEmpty()) {
                throw new RuntimeException("股票 API 返回空数据");
            }

            // 2. 处理 GBK 编码（新浪返回的是 GBK，RestTemplate 可能误解析，手动转）
            byte[] gbkBytes = rawData.getBytes(StandardCharsets.ISO_8859_1);
            String decodedData = new String(gbkBytes, Charset.forName("GBK"));

            // 3. 解析数据（新浪 API 字段索引：0=名称,1=今开,2=昨收,3=当前价,4=最高,5=最低,8=成交量(手),9=成交额(元)）
            String[] dataArray = decodedData.split("\"")[1].split(",");
            String name = dataArray[0];
            String open = dataArray[1];
            String close = dataArray[2];
            String current = dataArray[3];
            String high = dataArray[4];
            String low = dataArray[5];
            String volume = dataArray[8];
            String amount = dataArray[9];

            // 4. 计算涨跌幅（保留2位小数，红涨绿跌）
            double changePercent = (Double.parseDouble(current) - Double.parseDouble(close)) / Double.parseDouble(close) * 100;
            String formattedChange = String.format("%.2f", changePercent);
            String changeColor = changePercent >= 0 ? "#ff4d4f" : "#52c41a"; // 红涨绿跌
            String changePrefix = changePercent >= 0 ? "+" : "";

            // 5. 封装返回
            stockInfo.put("name", name);
            stockInfo.put("code", stockCode);
            stockInfo.put("open", open);
            stockInfo.put("close", close);
            stockInfo.put("current", current);
            stockInfo.put("high", high);
            stockInfo.put("low", low);
            stockInfo.put("volume", volume);
            stockInfo.put("amount", amount);
            stockInfo.put("changePercent", changePrefix + formattedChange + "%");
            stockInfo.put("changeColor", changeColor);

            log.info("股票数据获取成功：{} - 当前价：{}", name, current);
        } catch (Exception e) {
            log.error("股票数据获取/解析失败", e);
            throw new RuntimeException("股票数据获取失败", e);
        }
        return stockInfo;
    }
}
