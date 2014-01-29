package ua.eugenesokolov.interview.calendar.manager;

import java.util.HashMap;
import java.util.Map;
import ua.eugenesokolov.interview.calendar.model.Employee;

public class EmployeeManagerImpl implements EmployeeManager {
    
    private Map<String, Employee> employeeCache;

    public EmployeeManagerImpl() {
        employeeCache = new HashMap<String, Employee>();
    }
    
    public Employee getEmployeeById(String id) {
        Employee employee = employeeCache.get(id);
        
        if (employee == null) {
            employee = new Employee(id);
            employeeCache.put(id, employee);
        }
        
        return employee;
    }
    
    
}
