package simon.demo.core.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import simon.demo.core.bean.User;
import simon.demo.core.bean.UserExample;

public interface UserMapper {
    int countByExample(UserExample example);

    int countAll();

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int batchInsert(List<User> records);

    int insertSelective(User record);

    List<User> selectAll();

    List<User> selectByExample(UserExample example);

    List<User> selectByExampleWithPage(@Param("example") UserExample example, @Param("startPage") int startPage, @Param("rows") int rows);

    User selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}