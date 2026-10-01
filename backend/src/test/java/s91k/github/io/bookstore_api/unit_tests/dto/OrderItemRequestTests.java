package s91k.github.io.bookstore_api.unit_tests.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import s91k.github.io.bookstore_api.dto.OrderItemRequest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class OrderItemRequestTests {
    private static Validator validator;

    @BeforeAll
    static void setUp(){
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void validRequestShouldTriggerNoViolation(){
        OrderItemRequest r = new OrderItemRequest(1, 1);

        Set<ConstraintViolation<OrderItemRequest>> violations = validator.validate(r);

        assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(ints = { -1, 0, 100})
    void invalidQuantityShouldTriggerViolation(int quantity){
        OrderItemRequest r = new OrderItemRequest(1, quantity);

        Set<ConstraintViolation<OrderItemRequest>> violations = validator.validate(r);

        assertFalse(violations.isEmpty());
    }
}
