package iut.info3.betterstravaapi.path;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import iut.info3.betterstravaapi.EnvGetter;
import iut.info3.betterstravaapi.user.UserEntity;
import iut.info3.betterstravaapi.user.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static iut.info3.betterstravaapi.user.UserControllerTest.asJsonString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PathController.class)
public class PathControllerTest {


    @Autowired
    MockMvc mockMvc;

    @MockBean
    PathService pathService;

    @MockBean
    PathRepository pathRepository;

    @MockBean
    UserService userService;

    private ArrayList<Coordonnees> points = new ArrayList<>();

    private ArrayList<PointInteret> pointsInteret = new ArrayList<>();

    @Test
    public void testCreatePathSuccess() throws Exception {

        when(userService.getTokenBd(2)).thenReturn("token");
        when(userService.verifierDateExpiration("token")).thenReturn(true);

        points.add(new Coordonnees(48.25,12.25));
        points.add(new Coordonnees(43.85,17.855));

        pointsInteret.add(new PointInteret("test","super",new Coordonnees(78.58,69.54)));


        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/path/createPath")
                        .content(asJsonString(new PathEntity(
                                2,
                                "reussi",
                                "path success",
                                points,
                                pointsInteret
                        )))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    public void testCreatePathTokenInvalid() throws Exception {

        when(userService.getTokenBd(2)).thenReturn("token");
        when(userService.verifierDateExpiration("token")).thenReturn(false);

        points.add(new Coordonnees(48.25,12.25));
        points.add(new Coordonnees(43.85,17.855));

        pointsInteret.add(new PointInteret("test","super",new Coordonnees(78.58,69.54)));


        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/path/createPath")
                        .content(asJsonString(new PathEntity(
                                2,
                                "reussi",
                                "path success",
                                points,
                                pointsInteret
                        )))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

    }

}
