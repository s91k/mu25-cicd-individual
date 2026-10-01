package s91k.github.io.bookstore_api.unit_tests.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import s91k.github.io.bookstore_api.dto.OrderItemRequest;
import s91k.github.io.bookstore_api.dto.OrderRequest;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class OrderRequestTests {
    private static Validator validator;

    @BeforeAll
    static void setUp(){
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void validRequestShouldTriggerNoViolation(){
        OrderRequest r = new OrderRequest(
                "Nils",
                "Nilsson",
                "mejl@mejladress.se",
                "Väggatan 5",
                "12345",
                "Stadköping",
                List.of(new OrderItemRequest(1, 1))
        );

        Set<ConstraintViolation<OrderRequest>> violations = validator.validate(r);

        assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @EmptySource
    @NullSource
    @ValueSource(strings = { "test", "test@", "@test.nu" })
    void invalidEmailShouldTriggerViolation(String email){
        OrderRequest r = new OrderRequest(
                "Nils",
                "Nilsson",
                email,
                "Väggatan 5",
                "12345",
                "Stadköping",
                List.of(new OrderItemRequest(1, 1))
        );

        Set<ConstraintViolation<OrderRequest>> violations = validator.validate(r);

        assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @EmptySource
    @NullSource
    @ValueSource(strings = { "nnnnn", "123", "123456", "12 345" })
    void invalidPostalCodeShouldTriggerViolation(String postalCode){
        OrderRequest r = new OrderRequest(
                "Nils",
                "Nilsson",
                "mejl@mejladress.se",
                "Väggatan 5",
                postalCode,
                "Stadköping",
                List.of(new OrderItemRequest(1, 1))
        );

        Set<ConstraintViolation<OrderRequest>> violations = validator.validate(r);

        assertFalse(violations.isEmpty());
    }
}
