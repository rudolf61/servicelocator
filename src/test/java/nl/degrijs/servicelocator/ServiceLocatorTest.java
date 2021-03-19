package nl.degrijs.servicelocator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Supplier;

public class ServiceLocatorTest {

    @AfterEach
    public void clear() {
        ServiceLocator.getInstance().clear();
    }

    @Test
    public void alwaysGetToday() {
        ServiceLocator.getInstance().registerPrototype(LocalDate.class, "today", () -> LocalDate.now());

        LocalDate localDate = LocalDate.now();
        Optional<LocalDate> nowOpt = ServiceLocator.getInstance().getPrototype(LocalDate.class, "today");
        LocalDate now = nowOpt.get();
        Assertions.assertEquals(localDate, now);
    }

    @Test
    public void registerASingleton() {
        class MyClass {

        }
        ServiceLocator.getInstance().registerSingleton(MyClass.class, new MyClass());

        Assertions.assertEquals(MyClass.class, ServiceLocator.getInstance().getSingleton(MyClass.class).get().getClass());
    }


    @Test
    public void storeStringAsSIngleton() {
        ServiceLocator.getInstance().registerSingleton(String.class, "remember", "A day to remember");

        Assertions.assertEquals("A day to remember", ServiceLocator.getInstance().getSingleton(String.class, "remember").get());
    }

    @Test
    public void multipleInstance() {
        var locator = ServiceLocator.getInstance();
        locator.registerSingleton(String.class, "naam", "Jan").registerSingleton(Integer.class, "leeftijd", Integer.valueOf(23));

        Assertions.assertEquals("Jan", ServiceLocator.getInstance().getSingleton(String.class, "naam").get());
        Assertions.assertEquals(Integer.valueOf(23), ServiceLocator.getInstance().getSingleton(Integer.class, "leeftijd").get());
    }

    @Test
    public void removeInstance() {
        var locator = ServiceLocator.getInstance();
        locator.registerSingleton(String.class, "naam", "Jan").registerSingleton(Integer.class, "leeftijd", Integer.valueOf(23));

        Assertions.assertEquals("Jan", ServiceLocator.getInstance().getSingleton(String.class, "naam").get());
        Assertions.assertEquals(Integer.valueOf(23), ServiceLocator.getInstance().getSingleton(Integer.class, "leeftijd").get());

        locator.removeSingleton(String.class, "naam");
        Assertions.assertTrue(locator.getSingleton(String.class, "naam").isEmpty());
    }

}
