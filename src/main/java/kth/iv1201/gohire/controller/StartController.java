package kth.iv1201.gohire.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Responsible for returning the whole React App.
 */
@Controller
public class StartController {

    /**
     * Maps the urls: "/login" and "/".
     * TODO Map all url excepts those starting with "/api"
     * @return the whole React App.
     */
    @RequestMapping({"/login", "/"})
    public String index() {
        return "index";
    }
}
