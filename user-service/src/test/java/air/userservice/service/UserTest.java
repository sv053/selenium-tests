package air.userservice.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Tag("category.UnitTest")
public class UserTest {
    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void shouldPassValidationWhenHasValidData() {
        User user = User.builder()
                .withEmail("email@gmail.com")
                .withPassword("password")
                .withName("name")
                .withNationality("nationality")
                .withPassportNumber("efkojewpofilekj")
                .withAge(18)
                .build();

        int errors = validator.validate(user).size();
        assertThat(errors, is(0));
    }

    @Test
    public void shouldNotPassValidationWhenHasInvalidData() {
        User user = User.builder()
                .withEmail("not an email")
                .withAge(0)
                .withPassportNumber("1")
                .build();

        int errors = validator.validate(user).size();
        assertThat(errors, is(6));
    }
}
