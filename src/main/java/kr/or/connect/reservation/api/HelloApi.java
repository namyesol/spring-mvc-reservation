package kr.or.connect.reservation.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloApi {
    
    @GetMapping("/hello")
    public HelloMessage hello() {
        return new HelloMessage("Hello, World!");
    }

    public static class HelloMessage {
        private String msg;

        public HelloMessage(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
