package com.pranitpatil.controller.IT;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationTest.yml")
public class EmployeeControllerIT {

    @Autowired
    private MockMvc mvc;

    @Test
    public void givenEmployeeId_whenGetEmployee_thenStatus200AndEmployee() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/rest/employee/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Aishwarya"));
    }

    @Test
    public void givenEmployeeId_whenGetEmployee_thenStatus400() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/rest/employee/100")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void givenPageParams_whenGetAllEmployees_thenStatus200AndEmployees() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/rest/employee/search?page=0&size=10")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalItems").value(5));
    }

    @Test
    public void givenPageParams_whenGetAllEmployeesByNameAndDepartment_thenStatus200AndOneEmployee() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/rest/employee/search?page=0&size=10&name=Pranit&department=TECHNOLOGY")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalItems").value(1));
    }

    @Test
    public void givenPageParams_whenGetAllEmployeesByDepartment_thenStatus200AndOneEmployee() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/rest/employee/search?page=0&size=10&department=MANAGEMENT")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalItems").value(2));
    }
}
