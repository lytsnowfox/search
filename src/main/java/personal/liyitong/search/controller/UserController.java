package personal.liyitong.search.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import personal.liyitong.search.pojo.User;
import personal.liyitong.search.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/userList")
	public String userList(HttpServletRequest request,Model model){
		List<User> uList = userService.getAllUser();
		model.addAttribute("uList", uList);
		return "userList";
	}
	
	@ResponseBody
	@RequestMapping("/getAllUser")
	public List<User> getAllUser(){
		List<User> uList = userService.getAllUser();
		return uList;
	}
	
	@RequestMapping("/showUser")
	public String showUser(HttpServletRequest request,Model model){
		int userId = Integer.parseInt(request.getParameter("id"));
		User user = userService.getUserById(userId);
		model.addAttribute("user", user);
		return "showUser";
	}
	
	@ResponseBody
	@RequestMapping(value = "/jsonLogin", method = RequestMethod.POST)
	public JSONObject jsonLogin(@RequestBody JSONObject param) {
		JSONObject result = new JSONObject();
		System.out.println(param.get("username"));
		System.out.println(param.get("password"));
		result.put("status", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/test")
	public Map<String, Object> test() {
		Map<String, Object> result = new HashMap<>();
		result.put("status", "success");
		return result;
	}
	
	@RequestMapping("/addUserUI")
	public String addUserUI(){
		return "addUser";
	}
	
	@ResponseBody
	@RequestMapping("/addUser")
	public Map<String, Object> addUser(@RequestBody User user){
		Map<String, Object> result = new HashMap<>();
		userService.addUser(user);
		result.put("status", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/updateUser")
	public Map<String, Object> updateUser(@RequestBody User user) {
		Map<String, Object> result = new HashMap<>();
		userService.updateUser(user);
		result.put("status", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/deleteUser")
	public Map<String, Object> deleteUser(@RequestBody User user) {
		Map<String, Object> result = new HashMap<>();
		userService.deleteUser(user);
		result.put("status", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getContextPath")
	public String getContextPath(HttpServletRequest request){
		String path = System.getProperty("user.dir");
		return path;
	}
}