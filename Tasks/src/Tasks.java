import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Tasks {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Выберите задание:");
            System.out.println("1. Сортировка и удаление дубликатов");
            System.out.println("2. Подсчет повторений 'High'");
            System.out.println("3. Первый элемент коллекции");
            System.out.println("4. Возвести числа в квадрат и собрать в разные коллекции");
            System.out.println("5. Выйти");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    List<String> list1 = Arrays.asList("f10", "f15", "f2", "f4", "f4");
                    List<String> sortedAndUnique = list1.stream()
                            .distinct()
                            .sorted(Comparator.reverseOrder())
                            .collect(Collectors.toList());
                    System.out.println(sortedAndUnique);
                    break;

                case 2:
                    List<String> list2 = Arrays.asList("Highload", "High", "Load", "Highload", "High");
                    long count = list2.stream()
                            .filter("High"::equals)
                            .count();
                    System.out.println("High встречается: " + count + " раз(а)");
                    break;

                case 3:
                    List<String> list3 = Arrays.asList();
                    String firstElement = list3.stream()
                            .findFirst()
                            .orElse("0");
                    System.out.println("Первый элемент: " + firstElement);
                    break;

                case 4:
                    List<Integer> squaredList = IntStream.rangeClosed(-40, 40)
                            .map(i -> i * i)
                            .filter(i -> i > 100)
                            .boxed()
                            .collect(Collectors.toList());

                    Set<Integer> squaredSet = new HashSet<>(squaredList);

                    LinkedList<Integer> squaredLinkedList = squaredList.stream()
                            .collect(Collectors.toCollection(LinkedList::new));

                    System.out.println("List: " + squaredList);
                    System.out.println("Set: " + squaredSet);
                    System.out.println("LinkedList: " + squaredLinkedList);
                    break;

                case 5:
                    System.out.println("Выход");
                    return;

                default:
                    System.out.println("Неверный выбор, попробуйте еще раз.");
                    break;
            }
        }
    }
}
