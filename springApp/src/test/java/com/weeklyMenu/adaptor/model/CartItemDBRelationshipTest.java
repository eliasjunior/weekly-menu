package com.weeklyMenu.adaptor.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class CartItemDBRelationshipTest {
    @Test
    void productReferenceAllowsProductReuseAcrossCartItems() throws NoSuchFieldException {
        Field product = CartItemDB.class.getDeclaredField("product");

        assertNotNull(product.getAnnotation(ManyToOne.class));
        assertNull(product.getAnnotation(OneToOne.class));
        assertFalse(product.getAnnotation(JoinColumn.class).unique());
    }
}
