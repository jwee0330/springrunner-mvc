package todoapp.web;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import todoapp.web.support.ConnectedClientCountBroadcaster;

@Controller
public class OnlineUserCounterController {

	private ConnectedClientCountBroadcaster broadcaster = new ConnectedClientCountBroadcaster();

    @RequestMapping("/stream/online-users-counter")
    public SseEmitter counter() {
        return broadcaster.subscribe();
    }
    
//    @GetMapping("/stream/online-users-counter")
//    public void oldCounter(HttpServletResponse response) {
//    	response.setCharacterEncoding("utf-8");
//    	response.setContentType("text/event-stream");
//    	
//    	try {
//			ServletOutputStream outputStream = response.getOutputStream();
//			
//			response.getWriter();
//			response.flushBuffer();
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
}
