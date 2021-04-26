package com.qinh.crud.handler;

import com.qinh.crud.dao.DepartmentDao;
import com.qinh.crud.dao.EmployeeDao;
import com.qinh.crud.entity.Department;
import com.qinh.crud.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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


    @DeleteMapping("/emp/{id}")
    public String delete(@PathVariable Integer id){
        employeeDao.delete(id);
        return "redirect:/emps";
    }

    @PostMapping("/emp")
    public String save(Employee employee){
        employeeDao.save(employee);
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
