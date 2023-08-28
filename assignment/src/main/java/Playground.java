import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Playground {
    static class Test {
        private String name;
        private int grade;

        public Test() {
        }

        public Test(String name, int grade) {
            this.name = name;
            this.grade = grade;
        }

        @Override
        public String toString() {
            return "Test{" +
                    "name='" + name + '\'' +
                    ", grade=" + grade +
                    '}';
        }
    }

    public static void main(String[] args) {
        Map<String, Test> map = new HashMap<>();
        map.put("1", new Test("abc", 58));
        map.put("2", new Test("bcd", 67));

        System.out.println(map);
        changeMap(map);
        System.out.println(map);

        List<Object> list = new ArrayList<>();
        List<Object> collect = list.stream().collect(Collectors.toList());
        System.out.println(collect);
    }

    public static void changeMap(Map<String, Test> map) {
        map.put("1", new Test("abd", 60));
    }
}
