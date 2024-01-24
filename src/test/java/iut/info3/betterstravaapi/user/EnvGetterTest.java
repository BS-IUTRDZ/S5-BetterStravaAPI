package iut.info3.betterstravaapi.user;

import iut.info3.betterstravaapi.EnvGetter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class EnvGetterTest {

    @MockBean
    EnvGetter envGetter;

    @Test
    public void testGetterSentence(){
        when(envGetter.getSentence()).thenReturn("cestbon");

        assertEquals("la sentence devrait etre cestbon","cestbon",envGetter.getSentence());
    }

    @Test
    public void testGetterExpiration(){
        when(envGetter.getExpiration()).thenReturn(123456L);

        assertEquals("la durree d expiration devrait etre 123456",123456L, envGetter.getExpiration());
    }

}
