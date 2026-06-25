package com.ai.doctor.controller;

import com.ai.doctor.beans.ChatEntity;
import com.ai.doctor.service.ChatRecordService;
import com.ai.doctor.service.OllamaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@Slf4j
@Tag(name = "Ollama接口", description = "Ollama接口") // 类级注解
@RequestMapping("/ollama")
public class OllamaController {

    @Autowired
    OllamaChatClient chatClient;

    @Autowired
    OllamaService ollamaService;

    @Autowired
    ChatRecordService chatRecordService;

    @Operation(summary = "aiOllama进行ai对普通方式", description = "aiOllama进行ai对普通方式") // 方法级注解
    @GetMapping("/ai/chat")
    public Object chat(@RequestParam("msg") String msg) {
        String response = chatClient.call(msg);
        return response;
    }

    @Operation(summary = "aiOllama进行ai对话", description = "aiOllama进行ai对话") // 方法级注解
    @GetMapping("/ai/stream1")
    public Flux<ChatResponse> aiOllamaStream1(@RequestParam("msg") String msg) {
        return ollamaService.aiOllamaStream1(msg);
    }

    @Operation(summary = "流式输出和aiOllama进行ai对话", description = "流式输出和aiOllama进行ai对话") // 方法级注解
    @GetMapping("/ai/stream2")
    public List<String> aiOllamaStream2(@RequestParam("msg") String msg) {
        return ollamaService.aiOllamaStream2(msg);
    }

    @PostMapping("/ai/v3/doctor/stream")
    public void aiOllamaV3DoctorStream(@RequestBody ChatEntity chatEntity) {
        String userName = chatEntity.getCurrentUserName();
        String message = chatEntity.getMessage();
        log.info("发送消息的人："+userName);
        log.info("发送的内容："+message);
        ollamaService.doDoctorStreamV3(userName, message);
    }

    /**
     * 获取当前人的聊天记录
     * @param who
     * @return
     */
    @GetMapping("/getRecords")
    public Object aiOllamaV3DoctorStream(@RequestParam String who) {
        return chatRecordService.getChatRecordList(who);
    }
}