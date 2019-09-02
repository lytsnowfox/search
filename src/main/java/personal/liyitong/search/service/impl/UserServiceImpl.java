package personal.liyitong.search.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.liyitong.search.dao.UserDao;
import personal.liyitong.search.pojo.User;
import personal.liyitong.search.service.UserService;

import javax.annotation.Resource;
import java.util.List;

@Service
//@Transactional 与neo4j的事务管理冲突
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserDao userDao;
	
	public User getUserById(int userId) {
		return userDao.queryByPrimaryKey(userId);
	}

	public void addUser(User user) {
		userDao.insertUser(user);
	}

	@Override
	public List<User> getAllUser() {
		PageHelper.startPage(1, 3);
		List<User> users = userDao.getAllUser();
        PageInfo<User> pageInfo = new PageInfo<User>(users);
        System.out.println(pageInfo.getPageNum());
        System.out.println(pageInfo.getTotal());
		return users;
	}

	@Override
	public void updateUser(User user) {
		userDao.updateByPrimaryKey(user);
	}

	@Override
	public void deleteUser(User user) {
		userDao.deleteByPrimaryKey(user.getId());
	}
}
