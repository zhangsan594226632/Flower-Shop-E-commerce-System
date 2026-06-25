package com.ai.doctor.controller;


import com.ai.doctor.utils.VerifyCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@Slf4j
@Tag(name = "验证码数据接口", description = "验证码数据接口") // 类级注解
public class ImageCodeController {

    String code = "";

    /**
     * 生成验证码
     * @param response
     * @throws IOException
     */
    @Operation(summary = "生成验证码", description = "生成验证码") // 方法级注解
    @GetMapping("/createVerify")
    public void createVerify(HttpServletResponse response) throws IOException {
        VerifyCode vc = new VerifyCode();
        vc.drawImage(response.getOutputStream());
        //设置浏览器不缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        code = vc.getCode();
        log.info("系统生成的正确的验证码是:" + code);
    }

    /**
     * 验证验证码是否正确
     */
    @Operation(summary = "验证验证码是否正确", description = "验证验证码是否正确") // 方法级注解
    @PostMapping("/checkVerify")
    @ResponseBody
    public boolean checkVerify(@RequestParam("verify") String verify) {
        //verify 是前端传递过来的，str是自动生成的， 比较
        if (verify.equalsIgnoreCase(code)) {
            return true;
        }
        return false;
    }
}
