package simon.demo.core.action;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import simon.demo.core.bean.User;
import simon.demo.core.service.UserService;

@Controller
@RequestMapping(value="/User")
public class UserAction {
    @Autowired
    UserService userServiceImpl;

    @RequestMapping(value="/index.do")
    public String index(Long id) throws Exception {
        return "user";
    }

    @RequestMapping(value="/insert.do",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> insert(User record) throws Exception {
        Map<String,Object> map = new HashMap<String, Object>();
        userServiceImpl.insertSelective(record);//mapper.insertSelective(...)
        map.put("success", "提示：添加成功！");
        return map;
    }

    @RequestMapping(value="/delete.do",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> delete(Long id) throws Exception {
        Map<String,Object> map = new HashMap<String, Object>();
        userServiceImpl.deleteByPrimaryKey(id);
        map.put("success", "提示：删除成功！");
        return map;
    }

    @RequestMapping(value="/deleteBatch.do",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> deleteBatch(String ids) throws Exception {
        Map<String,Object> map = new HashMap<String, Object>();
        String idsArr[] = ids.split(",");
        for (int i = 0; i < idsArr.length; i++) {
            userServiceImpl.deleteByPrimaryKey(java.lang.Long.valueOf(idsArr[i]));
        }
        map.put("success", "提示：批量删除成功！");
        return map;
    }

    @RequestMapping(value="/update.do",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> update(User record) throws Exception {
        Map<String,Object> map = new HashMap<String, Object>();
        userServiceImpl.updateByPrimaryKeySelective(record);
        map.put("success", "提示：编辑成功！");
        return map;
    }

    @RequestMapping(value="/findByPage.do",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> findByPage(User record, int rows, int page) throws Exception {
        int startPage=rows*(page-1);
        return userServiceImpl.findByPage(record,startPage,rows);
    }
}