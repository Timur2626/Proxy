import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NonOwnerInvocationHandler implements InvocationHandler {
    private final Person person;

    public NonOwnerInvocationHandler(Person person) {
        this.person = person;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        if (method.getName().startsWith("set")
                && !method.getName().equals("setGeekRating")) {
            throw new SecurityException(
                    "Cannot edit another person's profile"
            );
        }

        try {
            return method.invoke(person, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}