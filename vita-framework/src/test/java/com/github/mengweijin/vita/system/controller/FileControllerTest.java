package com.github.mengweijin.vita.system.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.jackson.JacksonConfig;
import com.github.mengweijin.vita.system.domain.vo.FileVO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mengweijin
 */
@Slf4j
class FileControllerTest {

    private static final String LOGIN_URL = "http://localhost:8080/login";

    private static final String UPLOAD_URL = "http://localhost:8080/system/file/create";

    private RestTemplate restTemplate;

    private ObjectMapper objectMapper;

    private String token;

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplateBuilder().build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(JacksonConfig.javaTimeModule());
        token = login();
    }

    @Test
    @SneakyThrows
    void create() {
        // 构建请求体
        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        // 文件资源
        // body.add("file",  new FileSystemResource(file));
        body.add("file",  new ClassPathResource("mapper/system/ConfigMapper.xml"));
        body.add("fileName",  "NewConfigMapper.xml");

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<Object> httpEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(UPLOAD_URL, httpEntity, String.class);

        String str = response.getBody();
        if(response.getStatusCode().is2xxSuccessful()) {
            R<FileVO> r = objectMapper.readValue(str, new TypeReference<>() {});
            FileVO fileVO = r.getData();

            Assertions.assertNotNull(fileVO);
            Assertions.assertEquals("NewConfigMapper.xml", fileVO.getName());
            Assertions.assertNotNull(fileVO.getStoragePath());
            log.info("fileName={}", fileVO.getName());
            log.info("storagePath={}", fileVO.getStoragePath());
        } else {
            log.error(str);
        }
    }

    @SneakyThrows
    public String login(){
        // 构建请求体
        Map<String, String> body = new HashMap<>();
        body.put("username",  "admin");
        body.put("password",  "aday.fun");

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> httpEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(LOGIN_URL, httpEntity, String.class);

        String str = response.getBody();
        if(response.getStatusCode().is2xxSuccessful()) {
            // TypeReference ✅ 解决泛型擦除
            R<HashMap<String, String>> r = objectMapper.readValue(str, new TypeReference<>() {});
            String token = r.getData().get("token");
            Assertions.assertNotNull(token);
            log.info("token={}", token);
            return token;
        } else {
            log.error(str);
            return null;
        }
    }

    /**
     * ✅ 解决泛型擦除
     */
    @SuppressWarnings("unused")
    private void parameterizedTypeReference(HttpEntity<Object> httpEntity) {
        ParameterizedTypeReference<R<HashMap<String, String>>> typeRef = new ParameterizedTypeReference<>() {};
        ResponseEntity<R<HashMap<String, String>>> response = restTemplate.exchange(LOGIN_URL, HttpMethod.POST, httpEntity, typeRef);

    }
}