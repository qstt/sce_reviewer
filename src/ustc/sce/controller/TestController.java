package ustc.sce.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
	
	@RequestMapping("/param/{id}/{name}")
	public String param(@PathVariable("id") Long id,@PathVariable("name") String name) {
		
		System.out.println(id + name);
		
		return null;
		
	}
	

}
