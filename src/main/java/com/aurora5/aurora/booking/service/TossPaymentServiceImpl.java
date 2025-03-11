package com.aurora5.aurora.booking.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TossPaymentServiceImpl implements TossPaymentService {

    private static final String IAMPORT_GET_TOKEN_URL = "https://api.iamport.kr/users/getToken";
    private static final String IAMPORT_VERIFY_PAYMENT_URL = "https://api.iamport.kr/payments/";
    @Value("${IAMPORT_API_KEY}")
    private String API_KEY;

    @Value("${IAMPORT_API_SECRET}")
    private String API_SECRET;

    // ✅ IAMPORT Access Token 발급 메서드
    private String getIamportAccessToken() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // ✅ JSON 변환을 명확하게 수행
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("imp_key", API_KEY);
            requestBody.put("imp_secret", API_SECRET);
            String jsonBody = new ObjectMapper().writeValueAsString(requestBody);

            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<Map> response = restTemplate.exchange(IAMPORT_GET_TOKEN_URL, HttpMethod.POST, entity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseBody = response.getBody();
                if (responseBody != null && responseBody.get("response") != null) {
                    Map<String, Object> responseData = (Map<String, Object>) responseBody.get("response");
                    return (String) responseData.get("access_token");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ IAMPORT Access Token 발급 오류: " + e.getMessage());
        }
        return null;
    }


    // ✅ 결제 검증 메서드
    public boolean verifyPayment(String paymentKey, String orderId, int amount) {
        try {
            // 1️⃣ Access Token 발급
            String accessToken = getIamportAccessToken();
            if (accessToken == null) {
                System.out.println("❌ IAMPORT Access Token 발급 실패");
                return false;
            }

            // 2️⃣ 결제 검증 API 요청
            String verifyUrl = IAMPORT_VERIFY_PAYMENT_URL + "/" + paymentKey; // paymentKey = imp_uid
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<Map> response = restTemplate.exchange(verifyUrl, HttpMethod.GET, entity, Map.class);

            // 3️⃣ 응답 확인
            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseBody = response.getBody();
                if (responseBody != null && responseBody.get("response") != null) {
                    Map<String, Object> paymentData = (Map<String, Object>) responseBody.get("response");
                    int responseAmount = (int) paymentData.get("amount");
                    String status = (String) paymentData.get("status");

                    // 4️⃣ 결제 상태가 "paid"이고, 요청한 금액과 일치하는지 확인
                    if ("paid".equals(status) && responseAmount == amount) {
                        return true;
                    } else {
                        System.out.println("❌ 결제 검증 실패: 상태=" + status + ", 금액=" + responseAmount);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ 결제 검증 오류: " + e.getMessage());
        }
        return false;
    }
}
