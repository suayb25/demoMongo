package com.company.suayb;

import com.company.suayb.model.User;
import com.company.suayb.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@DataMongoTest
public class TestMongo {

    protected MockMvc mvc;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test_AllUser() {
        if (userRepository.findAll() != null) {
            List<User> userList = userRepository.findAll();
            int i = 0;
            for (User user : userList) {
                System.out.println("UserId: " + user.getUserId());
                System.out.println("UserName: " + user.getUserName());
                System.out.println("Name: " + user.getName());
                System.out.println("Surname: " + user.getSurname());

                assertEquals(userList.get(i).getUserId(), userRepository.findAll().get(i).getUserId());
            }
        } else {
            System.out.println("There is no user in database");
        }

    }

    @Test
    public void test_Delete() {

        User user1 = new User();
        user1.setUserId("5d317aa39d516912fc005f96");
        if (userRepository.findAll() != null) {
            List<User> userList = userRepository.findAll();
            for (User user : userList) {
                if (user.getUserId().equals(user1.getUserId())) {
                    System.out.println("UserId: " + user.getUserId());
                    System.out.println("Deleted UserName: " + user.getUserName());
                    System.out.println("Deleted Name: " + user.getName());
                    System.out.println("Deleted Surname: " + user.getSurname());
                    user1 = user;
                    break;
                }
            }
            userRepository.deleteById(user1.getUserId());
            assertNull(userRepository.findUserByUserName(user1.getUserId()));
            System.out.println("Deleted Successfully UserId= " + user1.getUserId());
        } else {
            System.out.println("There is no user in the database ");
        }
    }

    @Test
    public void test_Edit() {

        if (userRepository.findUserByUserName("Selami35") != null) {
            User user1 = userRepository.findUserByUserName("Selami35");
            String old_phone = user1.getPhoneNumber();
            System.out.println("Before Edit PhoneNumber: " + user1.getPhoneNumber());
            user1.setPhoneNumber("5398225566");

            userRepository.save(user1);

            user1 = userRepository.findUserByUserName("Selami35");
            assertThat(user1.getPhoneNumber()).doesNotMatch(old_phone);
            System.out.println("After Edit PhoneNumber: " + user1.getPhoneNumber());
        } else {
            System.out.println("This user is not in the database");
        }
    }

    @Test
    public void isNotNullUser() {
        if (userRepository.findUserByUserName("Selami35") != null) {
            User user1 = userRepository.findUserByUserName("Selami35");
            assertThat(user1.getName()).isNotNull();
        } else {
            System.out.println("This user is not in the database");
        }
    }
}
