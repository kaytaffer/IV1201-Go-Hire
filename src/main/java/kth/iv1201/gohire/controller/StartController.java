package kth.iv1201.gohire.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StartController {

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

}
