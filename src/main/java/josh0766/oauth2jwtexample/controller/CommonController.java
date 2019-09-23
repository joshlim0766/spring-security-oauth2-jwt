package josh0766.oauth2jwtexample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class CommonController {

    @GetMapping(
            value = "/"
    )
    public String index (HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "index";
    }
}
