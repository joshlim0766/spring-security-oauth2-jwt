package josh0766.oauth2jwtexample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class CommonController {

    @GetMapping(
            value = "/"
    )
    public ModelAndView index (HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("index");
        mv.addObject("clientId", "client1");

        return mv;
    }
}
