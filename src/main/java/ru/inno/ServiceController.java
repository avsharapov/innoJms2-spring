package ru.inno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.UUID;

@Controller
public class ServiceController {
    @Autowired
    JmsTemplate jmsTemplate;

    @RequestMapping("/service")
    private void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charcet=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print("<html><body>");
            String text = "Message #" + UUID.randomUUID().toString();
            out.print("<h2>" + text + "</h2>");
            jmsTemplate.setTimeToLive(3000);
            jmsTemplate.setPubSubDomain(true);
            jmsTemplate.convertAndSend("inno.out.topic", text);
            out.print("</body></html>");
        }
    }
}
