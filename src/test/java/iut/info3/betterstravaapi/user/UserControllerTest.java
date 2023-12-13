package iut.info3.betterstravaapi.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Test
    public void testCreateAccountSuccess() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/users/createAccount")
                        .content(asJsonString(new UserEntity(
                                "test@mail.com",
                                "nomTest",
                                "prenomTest",
                                "motDePasse")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated());
    }

    @Test
    public void testCreateAccountWithJsonError() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/users/createAccount")
                        .content(asJsonString(new UserEntity(
                                "test@mail.com",
                                "nomTest",
                                null,
                                "motDePasse")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateAccountWithEmailAlreadyTake() throws Exception
    {
        when(userService.checkPresenceEmail("utilisateur@test.com")).thenReturn(true);
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/users/createAccount")
                        .content(asJsonString(new UserEntity(
                                "utilisateur@test.com",
                                "nomTest",
                                "prenomTest",
                                "motDePasse")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isConflict());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
