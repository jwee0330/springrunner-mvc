package todoapp.web;

import java.io.IOException;
import java.util.stream.IntStream;

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
    
    @GetMapping("/stream/online-users-counter")
    public void oldCounter(HttpServletResponse response) {
    	response.setCharacterEncoding("utf-8");
    	response.setContentType("text/event-stream");
    
    	IntStream.range(1, 10).forEach(number -> {
    		try {
    			Thread.sleep(1000);
    			ServletOutputStream outputStream = response.getOutputStream();
    			outputStream.write(("data: " + number + "\n\n").getBytes());
    			outputStream.flush();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	});
    }
}
