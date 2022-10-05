package com.br.sarahaa.services;

import com.br.sarahaa.SarahaaApplication;
import com.br.sarahaa.dto.MessageDto;
import com.br.sarahaa.dto.UserDto;
import com.br.sarahaa.exceptions.ObjectValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SarahaaApplication.class)
public class MessageServiceTest {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    @Test
    public void shouldSaveMessageSuccesfully() {
        //given
        //creer un user
        UserDto userToSave = UserDto.builder()
                .firstName("abdou")
                .lastName("cher")
                .birthDate(LocalDate.of(1995, 06, 22))
                .email("a_abdou@beyn.com")
                .password("12345678")
                .build();
        UserDto savedUser = userService.save(userToSave);

        MessageDto messageToSave = MessageDto.builder()
                .content("hellow from unit test")
                .favori(false)
                .publicMsg(false)
                .receiverId(savedUser.getId())
                .typeMsg("SENT_MSG")
                .build();
        //when
        MessageDto savedMessageDto = messageService.save(messageToSave);
        //then
        assertNotNull(savedMessageDto);
        assertEquals(savedMessageDto.getContent(), messageToSave.getContent());

    }

    @Test(expected = ObjectValidationException.class)
    public void shouldThrowObjectValidationExceptionIfNoContent() {
        //given

        MessageDto messageToSave = MessageDto.builder()
                //.content("hellow from unit test")
                .favori(false)
                .publicMsg(false)
                .receiverId(1)
                .typeMsg("SENT_MSG")
                .build();
        //when
        MessageDto savedMessageDto = messageService.save(messageToSave);

    }

    @Test
    public void shouldPuplishGivenMessage() {
        //given
        //creer un user
        UserDto userToSave = UserDto.builder()
                .firstName("abdou")
                .lastName("cher")
                .birthDate(LocalDate.of(1995, 06, 22))
                .email("a_abdou@beyn.com")
                .password("12345678")
                .build();
        UserDto savedUser = userService.save(userToSave);
        MessageDto messageToSave = MessageDto.builder()
                .content("hellow from unit test")
                .favori(false)
                .publicMsg(false)
                .receiverId(savedUser.getId())
                .typeMsg("SENT_MSG")
                .build();
        //when
        MessageDto messageToPublish = messageService.save(messageToSave);
        //then
        assertNotNull(messageToPublish);
        assertEquals(messageToPublish.getContent(),messageToSave.getContent());
        assertFalse(messageToPublish.isPublicMsg());

        //2.when
        MessageDto publishedMessage= messageService.publishMessage(messageToPublish.getId());
        //2.then
        assertTrue(publishedMessage.isPublicMsg());
    }
    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowNotFoundExceptionIfIdMessageUknown(){
        messageService.publishMessage(-1);
    }
}