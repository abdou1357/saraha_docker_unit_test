package com.br.sarahaa.controllers;

import com.br.sarahaa.dto.UserDto;
import com.br.sarahaa.exceptions.ObjectValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper= new ObjectMapper();

    @Before
    public void setUp() {
        mockMvc= MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldSaveUserSuccessfully() throws Exception {
        //given
        //creer un user
        UserDto userToSave = UserDto.builder()
                .firstName("abdou")
                .lastName("cher")
                .email("a_abdou@beyn.com")
                .password("12345678")
                .build();
        MvcResult result=mockMvc.perform(
                post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userToSave))
                        //.param("id","1")
        ).andDo(print())
                .andExpect(status().isOk())
                .andReturn();
//when
        UserDto savedUser=objectMapper.readValue(result.getResponse().getContentAsString(),UserDto.class);
//then
        assertNotNull(savedUser);
    }
    @Test
    public void shouldReturnBadRequestIfUserNotValid() throws Exception {
        //given
        //creer un user
        UserDto userToSave = UserDto.builder()
                //.firstName("abdou")
                .lastName("cher")
                .email("a_abdou@beyn.com")
                .password("12345678")
                .build();
        MvcResult result=mockMvc.perform(
                post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userToSave))
        )
                .andExpect(status().isBadRequest())
                .andReturn();
//when
//then
    }
}