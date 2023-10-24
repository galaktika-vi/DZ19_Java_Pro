import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

enum Category {
    FRUIT, GROCERY, PET_PRODUCT, VEGETABLE, MEET
}

class Product {
    double price;
    String name;
    Category category;

    public Product(double price, String name, Category category) {
        this.price = price;
        this.name = name;
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }
}

class Shop {
    String name;
    String address;
    int workTo;
    List<Product> products;

    public Shop(String name, String address, int workTo, List<Product> products) {
        this.name = name;
        this.address = address;
        this.workTo = workTo;
        this.products = products;
    }

    public int getWorkTo() {
        return workTo;
    }

    public List<Product> getProducts() {
        return products;
    }
}

public class StoreApp {
    public static void main(String[] args) {
        List<Shop> shops = Arrays.asList(
                new Shop("Shop1", "Address1", 20, Arrays.asList(
                        new Product(3, "Banana", Category.FRUIT),
                        new Product(5, "Chicken", Category.MEET),
                        new Product(1, "Dog Food", Category.PET_PRODUCT))),
                new Shop("Shop2", "Address2", 17, Arrays.asList(
                        new Product(4, "Apple", Category.FRUIT),
                        new Product(2, "Shampoo", Category.GROCERY))),
                new Shop("Shop3", "Address3", 19, Arrays.asList(
                        new Product(6, "Pork", Category.MEET),
                        new Product(2, "Banana", Category.FRUIT),
                        new Product(3, "Cat Food", Category.PET_PRODUCT))));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Выберите задание:");
            System.out.println("1. Составление мапы продуктов");
            System.out.println("2. Выбор фруктов из магазинов, работающих после 18");
            System.out.println("3. Подсчет товаров по категориям");
            System.out.println("4. Группировка продуктов по категориям");
            System.out.println("5. Наименьшая цена продукта по категориям");
            System.out.println("6. Сбор всех названий продуктов в строку");
            System.out.println("7. Выйти");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    Map<String, Double> productMap = shops.stream()
                            .flatMap(shop -> shop.getProducts().stream())
                            .collect(Collectors.toMap(
                                    Product::getName,
                                    Product::getPrice,
                                    (price1, price2) -> {
                                        System.out.println("Дубликат: " + price1 + " и " + price2);
                                        return Math.min(price1, price2);
                                    },
                                    HashMap::new
                            ));
                    System.out.println("HashMap: " + productMap);

                    Map<String, Double> treeMap = new TreeMap<>(productMap);
                    System.out.println("TreeMap: " + treeMap);
                    break;

                case 2:
                    Map<String, List<Product>> filteredFruits = shops.stream()
                            .filter(shop -> shop.getWorkTo() > 18)
                            .flatMap(shop -> shop.getProducts().stream())
                            .filter(product -> product.getCategory() == Category.FRUIT && product.getPrice() < 5)
                            .collect(Collectors.groupingBy(Product::getName));

                    System.out.println(filteredFruits);
                    break;

                case 3:
                    Map<Category, Long> productCounts = shops.stream()
                            .flatMap(shop -> shop.getProducts().stream())
                            .collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()));

                    System.out.println(productCounts);
                    break;

                case 4:
                    Map<Category, List<String>> productsByCategory = shops.stream()
                            .flatMap(shop -> shop.getProducts().stream())
                            .collect(Collectors.groupingBy(Product::getCategory,
                                    Collectors.mapping(Product::getName, Collectors.toList())));

                    System.out.println(productsByCategory);
                    break;

                case 5:
                    Map<Category, Optional<Double>> lowestPrices = shops.stream()
                            .flatMap(shop -> shop.getProducts().stream())
                            .collect(Collectors.groupingBy(Product::getCategory,
                                    Collectors.mapping(Product::getPrice,
                                            Collectors.minBy(Comparator.naturalOrder()))));

                    System.out.println(lowestPrices);
                    break;

                case 6:
                    String allProductNames = shops.stream()
                            .flatMap(shop -> shop.getProducts().stream())
                            .map(Product::getName)
                            .distinct()
                            .collect(Collectors.joining(", "));

                    System.out.println(allProductNames);
                    break;

                case 7:
                    System.out.println("Выход");
                    return;

                default:
                    System.out.println("Неверный выбор, попробуйте еще раз.");
                    break;
            }
        }
    }
}
