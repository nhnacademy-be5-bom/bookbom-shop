package shop.bookbom.shop.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop")
public class IndexController {
    @GetMapping(value = {"/index.html", "/"})
    public String index() {
        return "index/index";
    }
}
