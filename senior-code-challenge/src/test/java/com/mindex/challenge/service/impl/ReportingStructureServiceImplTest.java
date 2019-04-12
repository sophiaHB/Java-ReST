package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {
	
	private String employeeUrl;
	private String reportingStructureIdUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
    	reportingStructureIdUrl = "http://localhost:" + port + "/reportingStructure/{id}";
    	employeeUrl = "http://localhost:" + port + "/employee";
    }

    @Test
    public void testRead() {
    	
    	Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");
        
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();
        assertNotNull(createdEmployee.getEmployeeId());    
        
        ReportingStructure testReportingStructure = new ReportingStructure();
        testReportingStructure.setEmpoyee(createdEmployee);
        testReportingStructure.setNumberOfReports(0);
       
        // read test
        ReportingStructure readReportingStructure = restTemplate.getForEntity(reportingStructureIdUrl, ReportingStructure.class, testReportingStructure.getEmployee().getEmployeeId()).getBody();
        assertEquals(testReportingStructure.getEmployee().getEmployeeId(), readReportingStructure.getEmployee().getEmployeeId());
        assertEquals(testReportingStructure.getNumberOfReports(), readReportingStructure.getNumberOfReports());       
    }
}
