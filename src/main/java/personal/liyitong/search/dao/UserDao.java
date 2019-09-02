package personal.liyitong.search.dao;

import org.apache.ibatis.annotations.Mapper;
import personal.liyitong.search.pojo.User;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {
	
	User queryByPrimaryKey(Integer id);
	
	List<User> getAllUser();
		
	void insertUser(User user);
	
	void insertUserByBatch(List<User> list);
	
	void deleteByPrimaryKey(Integer id);
	
	void delteUserByBatch(Map<String, Object> params);
	
	void updateByPrimaryKey(User user);
}
