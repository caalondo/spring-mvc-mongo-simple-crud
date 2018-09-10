package co.com.bancolombia.mvccrud.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String homePage (Map<String, Object> model) {
        return "home";
    }

}
