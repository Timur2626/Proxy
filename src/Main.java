public class Main {
    public static void main(String[] args) {
        System.out.println(
                ClassicSingleton.getInstance() == ClassicSingleton.getInstance()
        );

        System.out.println(
                SynchronizedSingleton.getInstance() == SynchronizedSingleton.getInstance()
        );

        System.out.println(
                DoubleCheckedSingleton.getInstance() == DoubleCheckedSingleton.getInstance()
        );

        System.out.println(
                EagerSingleton.getInstance() == EagerSingleton.getInstance()
        );
    }
}