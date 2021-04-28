package com.qinh.crud.handler;

import com.qinh.crud.dao.DepartmentDao;
import com.qinh.crud.dao.EmployeeDao;
import com.qinh.crud.entity.Department;
import com.qinh.crud.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author Qh
 * @version 1.0
 * @date 2021-04-26-22:36
 */
@Controller
public class EmployeeHandler {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private DepartmentDao departmentDao;


//    @InitBinder
//    public void initBinder(WebDataBinder webDataBinder){
//        webDataBinder.setDisallowedFields("lastName");
//    }

    @ModelAttribute
    public void getEmployee(@RequestParam(value = "id",required = false) Integer id,Map<String,Object> map){
        if (id != null){
            map.put("employee",employeeDao.get(id));
        }
    }

    @PutMapping("/emp")
    public String update(Employee employee){
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    @GetMapping("/emp/{id}")
    public String input(@PathVariable Integer id,Map<String,Object> map){
        map.put("employee",employeeDao.get(id));
        map.put("departments",departmentDao.getDepartments());
        return "input";
    }

    @DeleteMapping("/emp/{id}")
    public String delete(@PathVariable Integer id){
        employeeDao.delete(id);
        return "redirect:/emps";
    }

    @PostMapping("/emp")
    public String save(@Valid Employee employee, BindingResult result,Map<String,Object> map){
        employeeDao.save(employee);

        if (result.getErrorCount() > 0){
            System.out.println("出错了");
            for (FieldError error : result.getFieldErrors()){
                System.out.println(error.getField() + " : " + error.getDefaultMessage());
            }

            //若验证出错，则转向定制的页面
            map.put("departments",departmentDao.getDepartments());
            return "input";
        }

        System.out.println("save : " + employee);
        return "redirect:/emps";
    }

    @GetMapping("/emp")
    public String input(Map<String,Object> map){
        map.put("departments",departmentDao.getDepartments());
        map.put("employee",new Employee());
        return "input";
    }

    @RequestMapping("/emps")
    public String list(Map<String,Object> map){
        map.put("employees",employeeDao.getAll());
        return "list";
    }
}
