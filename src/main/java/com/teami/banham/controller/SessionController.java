package com.teami.banham.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SessionController {
    @GetMapping("/get-session-value")
    public ResponseEntity<Map<String, Object>> getSessionValue(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        // 세션에서 필요한 값을 가져와서 response에 저장
        response.put("loginDTO", session.getAttribute("loginDTO"));
        // JSON 형식으로 응답
        return ResponseEntity.ok(response);
    }
}
