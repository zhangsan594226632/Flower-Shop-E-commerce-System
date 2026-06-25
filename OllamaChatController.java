package com.ai.doctor.controller;

import com.ai.doctor.service.AIService;
import com.ai.doctor.utils.Results;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController()
@RequestMapping("/ai")
@Slf4j
@Tag(name = "Ollama模型接口", description = "Ollama模型接口") // 类级注解
public class OllamaChatController {

    @Resource
    private AIService aiService;

    @GetMapping("/deepseek")
    @Operation(summary = "测试本地DeepSeek模型", description = "测试本地DeepSeek模型")
    public Results generate(@RequestParam(value = "question", defaultValue = "hello,who are you?") String question) {
        Map map = aiService.aiChat(question);
        if(map!=null){
            Object o = map.get("generation");
            return Results.success(o);
        }else {
            return Results.success("");
        }
    }

}
