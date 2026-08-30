import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShoppingCartUpdateItemPriceTest {

    @Test
    void updatesThePriceOfAnExistingItem() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Book", 50.0);

        cart.updateItemPrice("Book", 80.0);

        assertEquals(80.0, cart.getTotal());
    }

    @Test
    void updatingPriceDoesNotChangeItemCount() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Book", 50.0);

        cart.updateItemPrice("Book", 80.0);

        assertEquals(1, cart.getItemCount());
    }

    @Test
    void updatingPriceChangesTheCartTotal() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Book", 50.0);
        cart.addItem("Pen", 20.0);

        cart.updateItemPrice("Book", 80.0);

        assertEquals(100.0, cart.getTotal());
    }

    @Test
    void updatingPriceChangesTheDiscountedTotal() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Headphones", 80.0);

        cart.updateItemPrice("Headphones", 120.0);

        assertEquals(108.0, cart.getTotalWithDiscount());
    }

    @Test
    void updatingOneItemDoesNotChangeOtherItems() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Book", 50.0);
        cart.addItem("Pen", 30.0);

        cart.updateItemPrice("Book", 80.0);
        cart.removeItem("Book");

        assertEquals(30.0, cart.getTotal());
    }

    @Test
    void updatingMissingItemDoesNotChangeCart() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Book", 50.0);

        cart.updateItemPrice("Notebook", 80.0);

        assertEquals(1, cart.getItemCount());
        assertEquals(50.0, cart.getTotal());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "\t"})
    void rejectsNullOrBlankItemNameWithoutChangingCart(String invalidName) {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Book", 50.0);

        assertThrows(IllegalArgumentException.class,
                () -> cart.updateItemPrice(invalidName, 80.0));

        assertEquals(1, cart.getItemCount());
        assertEquals(50.0, cart.getTotal());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -0.01, -50.0})
    void rejectsZeroOrNegativePriceWithoutChangingCart(double invalidPrice) {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Book", 50.0);

        assertThrows(IllegalArgumentException.class,
                () -> cart.updateItemPrice("Book", invalidPrice));

        assertEquals(1, cart.getItemCount());
        assertEquals(50.0, cart.getTotal());
    }

    @Test
    void updatingToDecimalPriceKeepsTotalStableToTwoDecimalPlaces() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Book", 1.0);
        cart.addItem("Pen", 0.2);

        cart.updateItemPrice("Book", 0.1);

        assertEquals(0.30, cart.getTotal());
    }

    @Test
    void rejectsNonFinitePriceWithoutChangingCart() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Book", 50.0);

        for (double invalidPrice : new double[]{Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY}) {
            assertThrows(IllegalArgumentException.class,
                    () -> cart.updateItemPrice("Book", invalidPrice));
        }

        assertEquals(1, cart.getItemCount());
        assertEquals(50.0, cart.getTotal());
    }
}
