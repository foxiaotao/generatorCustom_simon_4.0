package simon.demo.core.service;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import simon.demo.core.bean.User;
import simon.demo.core.bean.UserExample;

public interface UserService {
    int countByExample(UserExample example) throws Exception;

    int deleteByExample(UserExample example) throws Exception;

    int deleteByPrimaryKey(Long id) throws Exception;

    int insert(User record) throws Exception;

    Map<String,Object> findByPage(User record, int startPage, int endPage) throws Exception;

    int insertSelective(User record) throws Exception;

    List<User> selectByExample(UserExample example) throws Exception;

    User selectByPrimaryKey(Long id) throws Exception;

    int updateByExampleSelective(User record, UserExample example) throws Exception;

    int updateByPrimaryKeySelective(User record) throws Exception;
}