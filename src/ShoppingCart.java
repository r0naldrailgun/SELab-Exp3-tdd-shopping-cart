import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

    private Map<String, Double> items = new HashMap<>();

    public void addItem(String name, double price) {
        items.put(name, price);
    }

    public boolean removeItem(String name) {
        if (items.containsKey(name)) {
            items.remove(name);
            return true;
        }
        return false;
    }

    public double getTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (double price : items.values()) {
            total = total.add(BigDecimal.valueOf(price));
        }

        return total.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
    public double getTotalWithDiscount() {
        BigDecimal total = BigDecimal.valueOf(getTotal());

        if (total.compareTo(BigDecimal.valueOf(100.0)) >= 0) {
            return total
                    .multiply(new BigDecimal("0.9"))
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
        }

        return total.doubleValue();
    }

    public int getItemCount() {
        return items.size();
    }

    public void updateItemPrice(String name, int newPrice) {}

}
