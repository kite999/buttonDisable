package com.example.buttonDisable.item;

import com.example.buttonDisable.SampleConsts;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ItemController {
    @GetMapping("/item/henshu")
    public String henshu(Model model, @RequestParam("itemId") String itemId) {
        model.addAttribute("itemId", itemId);
        model.addAttribute("lockTarget", SampleConsts.ITEM_HENSHU_GAMEN_ID + "_" +itemId);
        return "item/henshu";
    }

    @GetMapping({"/item/list", "/"})
    public String list(Model model) {
        model.addAttribute("gamenId", SampleConsts.ITEM_HENSHU_GAMEN_ID);
        return "item/list";
    }
}
