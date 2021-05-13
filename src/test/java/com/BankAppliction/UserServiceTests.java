package com.BankAppliction;

import com.BankAppliction.model.User;
import com.BankAppliction.repositories.UserRepository;
import com.BankAppliction.service.UserService;
import com.BankAppliction.service.impl.UserServiceImpl;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private  UserServiceImpl userService;
    @Test
    public void testCreateUser() throws  NullPointerException{
        User userObj = new User(new ObjectId("5349b4ddd2781d08c09890f4"),"firstname","secondname","some@mail.com","password");
        when(userRepository.save(any(User.class))).thenReturn(userObj);
        User savedUser = userService.createUser(userObj);
        assertThat(savedUser.getFirstName()).isEqualTo("firstname");
    }


    @Test
    public void testCreateUserInvalidEmail (){
        User userObj = new User(new ObjectId("5349b4ddd2781d08c09890f4"),"firstname","secondname","some","password");
        Mockito.lenient().when(userRepository.save(any(User.class))).thenReturn(userObj);
        User savedUser = userService.createUser(userObj);
        assertThat(savedUser.getFirstName()).isEqualTo("InvalidEmail");
    }
}
