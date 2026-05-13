package com.cookie.springbootstudyweek10.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

@Slf4j
@Service
public class WeatherService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public WeatherService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * 获取实时天气数据（和风天气 API）
     * @param apiHost API 域名（如 https://kg78m32vp4.re.qweatherapi.com）
     * @param apiKey  API Key
     * @param location 城市 LocationID（如 101190101）
     * @return 包含天气核心信息的 Map
     */
    public Map<String, String> getRealTimeWeather(String apiHost, String apiKey, String location) {
        Map<String, String> weatherInfo = new HashMap<>();
        try {
            // 1. 构建请求 URL 和请求头
            String url = "https://" + apiHost + "/v7/weather/now?location=" + location;
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-QW-Api-Key", apiKey);
            headers.set("Accept-Encoding", "gzip, deflate");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // 2. 调用 API（返回字节数组，处理 Gzip 压缩）
            ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);
            byte[] bodyBytes = response.getBody();
            if (bodyBytes == null || bodyBytes.length == 0) {
                throw new RuntimeException("天气 API 返回空数据");
            }

            // 3. Gzip 解压
            String rawData;
            try (GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(bodyBytes));
                 ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = gzipInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
                rawData = outputStream.toString(StandardCharsets.UTF_8);
            }

            // 4. 解析 JSON
            JsonNode root = objectMapper.readTree(rawData);
            String code = root.get("code").asText();
            if (!"200".equals(code)) {
                throw new RuntimeException("天气 API 返回错误，code=" + code);
            }

            // 5. 提取 now 节点数据
            JsonNode now = root.get("now");
            weatherInfo.put("temp", now.get("temp").asText());
            weatherInfo.put("text", now.get("text").asText());
            weatherInfo.put("feelsLike", now.get("feelsLike").asText());
            weatherInfo.put("humidity", now.get("humidity").asText());
            weatherInfo.put("windDir", now.get("windDir").asText());
            weatherInfo.put("windSpeed", now.get("windSpeed").asText());
            weatherInfo.put("pressure", now.get("pressure").asText());
            weatherInfo.put("vis", now.get("vis").asText());
            weatherInfo.put("obsTime", now.get("obsTime").asText());

            log.info("天气数据获取成功：{} - 当前气温：{}°C", weatherInfo.get("text"), weatherInfo.get("temp"));
        } catch (Exception e) {
            log.error("天气数据获取/解析失败", e);
            throw new RuntimeException("天气数据获取失败", e);
        }
        return weatherInfo;
    }
}
