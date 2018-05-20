package com.example.buttonDisable.shain;

import com.example.buttonDisable.SampleConsts;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class shainController {
    @GetMapping("/shain/henshu")
    public String henshu(Model model, @RequestParam("shainId") String shainId) {
        model.addAttribute("shainId", shainId);
        model.addAttribute("lockTarget", SampleConsts.SHAIN_HENSHU_GAMEN_ID + "_" +shainId);
        return "shain/henshu";
    }

    @GetMapping("/shain/list")
    public String list(Model model) {
        model.addAttribute("gamenId", SampleConsts.SHAIN_HENSHU_GAMEN_ID);
        return "shain/list";
    }
}
