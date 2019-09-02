package personal.liyitong.search.service;

import personal.liyitong.search.pojo.User;

import java.util.List;

public interface UserService {
	
	User getUserById(int userId);

	void addUser(User user);
	
	void updateUser(User user);
	
	void deleteUser(User user);

	List<User> getAllUser();
}
