package com.mindex.challenge.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindex.challenge.service.ReportingStructureService;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {
	
	private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

	private int numberOfReporters = 0;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	 
	@Override
    public ReportingStructure read(String id) {
        LOG.debug("Creating ReportingStructure with id [{}]", id);
        
        Set<String> numberOfEmployee = new HashSet<String>();
        
        ReportingStructure reportingStructure = new ReportingStructure();

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
        
        if (null != employee.getDirectReports() ) {
        	 getNumberOfReports (employee.getDirectReports(), numberOfEmployee);
        }
        else
        	numberOfReporters = 0;
            
        reportingStructure.setNumberOfReports(numberOfReporters);
        reportingStructure.setEmpoyee(employee);

        return reportingStructure;
    }
	
	public void getNumberOfReports (List<Employee> employeeReports, Set<String> numberOfEmployee) {		
		
		for (Employee e : employeeReports) {				
			numberOfEmployee.add(e.getEmployeeId());			
				
			Employee employee = employeeRepository.findByEmployeeId(e.getEmployeeId());
			if (null != employee.getDirectReports() ) {				
				getNumberOfReports(employee.getDirectReports(), numberOfEmployee);							
			}				
		}
		numberOfReporters = numberOfEmployee.size();
	}
}
