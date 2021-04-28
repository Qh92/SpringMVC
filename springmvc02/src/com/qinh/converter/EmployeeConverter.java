package com.qinh.converter;

import com.qinh.crud.entity.Department;
import com.qinh.crud.entity.Employee;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.xml.transform.Source;

/**
 * @author Qh
 * @version 1.0
 * @date 2021-04-27-22:38
 */
@Component
public class EmployeeConverter implements Converter<String, Employee> {

    @Override
    public Employee convert(String s) {
        if (s != null){
            String[] values = s.split("-");
            if (values != null && values.length == 4){
                String lastName = values[0];
                String email = values[1];
                Integer gender = Integer.parseInt(values[2]);
                Department department = new Department();
                department.setId(Integer.parseInt(values[3]));
                Employee employee = new Employee(null, lastName, email, gender, department);
                System.out.println(s + "--convert--" + employee);
                return employee;
            }
        }
        return null;
    }
}
