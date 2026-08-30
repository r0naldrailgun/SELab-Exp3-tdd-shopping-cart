import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ShoppingCartAdvancedBehaviorTest {

    @ParameterizedTest
    @ValueSource(strings = {"Missing item", "Another missing item"})
    void removingAnUnknownItemReturnsFalseWithoutChangingCart(String missingItemName) {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Book", 50.0);

        boolean removed = cart.removeItem(missingItemName);

        assertFalse(removed);
        assertEquals(1, cart.getItemCount());
        assertEquals(50.0, cart.getTotal());
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.0, 99.99})
    void cartBelowDiscountThresholdKeepsItsBaseTotal(double price) {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Book", price);

        assertEquals(price, cart.getTotalWithDiscount());
    }

    @Test
    void addingAnExistingNameReplacesItsPriceWithoutIncreasingItemCount() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Book", 50.0);

        cart.addItem("Book", 80.0);

        assertEquals(1, cart.getItemCount());
        assertEquals(80.0, cart.getTotal());
    }
}
