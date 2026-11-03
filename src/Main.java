import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private final Map<String, Person> database = new HashMap<>();

    public static void main(String[] args) {
        new Main().drive();
    }

    public Main() {
        initializeDatabase();
    }

    public void drive() {
        Person joe = getPersonFromDatabase("Joe Javabean");
        Person ownerProxy = getOwnerProxy(joe);

        System.out.println("Name is " + ownerProxy.getName());

        ownerProxy.setInterests("bowling, Go");
        System.out.println("Interests set from owner proxy");

        try {
            ownerProxy.setGeekRating(10);
        } catch (SecurityException e) {
            System.out.println("Can't set rating from owner proxy");
        }

        System.out.println("Rating is " + ownerProxy.getGeekRating());

        Person nonOwnerProxy = getNonOwnerProxy(joe);

        System.out.println("Name is " + nonOwnerProxy.getName());

        try {
            nonOwnerProxy.setInterests("bowling, Go");
        } catch (SecurityException e) {
            System.out.println(
                    "Can't set interests from non owner proxy"
            );
        }

        nonOwnerProxy.setGeekRating(3);
        System.out.println("Rating set from non owner proxy");
        System.out.println("Rating is " + nonOwnerProxy.getGeekRating());
    }

    public Person getOwnerProxy(Person person) {
        return getProxy(person, new OwnerInvocationHandler(person));
    }

    public Person getNonOwnerProxy(Person person) {
        return getProxy(person, new NonOwnerInvocationHandler(person));
    }

    private Person getProxy(Person person, InvocationHandler handler) {
        return (Person) Proxy.newProxyInstance(
                person.getClass().getClassLoader(),
                new Class<?>[] {Person.class},
                handler
        );
    }

    private Person getPersonFromDatabase(String name) {
        return database.get(name);
    }

    private void initializeDatabase() {
        Person joe = new PersonImpl();
        joe.setName("Joe Javabean");
        joe.setGender("male");
        joe.setInterests("cars, computers, music");
        joe.setGeekRating(7);

        database.put(joe.getName(), joe);
    }
}