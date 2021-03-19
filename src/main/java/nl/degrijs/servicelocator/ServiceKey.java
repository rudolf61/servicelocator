package nl.degrijs.servicelocator;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static nl.degrijs.servicelocator.ValidateServiceParameters.notEmpty;
import static nl.degrijs.servicelocator.ValidateServiceParameters.notNull;

public class ServiceKey<T> {
    // all keys are normalized via this cache, so a reference compare would always succeed
    private static ConcurrentHashMap<Class<?>, ConcurrentHashMap<String, ServiceKey<?>>> SERVICE_KEYS;
    private        Class<T>                                                              serviceClass;
    private        String                                                                discriminator;

    static {
        SERVICE_KEYS = new ConcurrentHashMap<>();
    }

    private ServiceKey(Class<T> serviceClass) {
        this.serviceClass = serviceClass;
    }

    private ServiceKey(Class<T> serviceClass, String discriminator) {
        this.serviceClass = serviceClass;
        this.discriminator = discriminator;
    }

    public static void clearCache() {
        for (ConcurrentHashMap<String, ServiceKey<?>> value : SERVICE_KEYS.values()) {
            value.clear();
        }
        SERVICE_KEYS.clear();
    }

    public static void remove(ServiceKey serviceKey) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceKey<?> that = (ServiceKey<?>) o;
        return serviceClass.equals(that.serviceClass) && discriminator.equals(that.discriminator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceClass, discriminator);
    }

    public static <T> ServiceKey createServiceKey(Class<T> clz, String discriminator) {
        notNull(clz, "Class is null");
        notEmpty(discriminator, "Discriminator is missing");
        ServiceKey serviceKey = null;
        if (SERVICE_KEYS.containsKey(clz)) {
            if (SERVICE_KEYS.get(clz)
                    .containsKey(discriminator)) {
                serviceKey = SERVICE_KEYS.get(clz)
                        .get(discriminator);
            } else {
                serviceKey = new ServiceKey(clz, discriminator);
                SERVICE_KEYS.get(clz)
                        .put(discriminator, serviceKey);
            }
        } else {
            serviceKey = new ServiceKey(clz, discriminator);
            SERVICE_KEYS.put(clz, new ConcurrentHashMap<>());
            SERVICE_KEYS.get(clz)
                    .put(discriminator, serviceKey);
        }

        return serviceKey;
    }

    public static <T> ServiceKey createServiceKey(Class<T> clz) {
        notNull(clz, "Class is null");

        ServiceKey serviceKey = null;
        if (SERVICE_KEYS.containsKey(clz)) {
            if (SERVICE_KEYS.get(clz)
                    .containsKey("")) {
                serviceKey = SERVICE_KEYS.get(clz)
                        .get("");
            } else {
                serviceKey = new ServiceKey(clz, "");
                SERVICE_KEYS.get(clz)
                        .put("", serviceKey);
            }
        } else {
            serviceKey = new ServiceKey(clz, "");
            SERVICE_KEYS.put(clz, new ConcurrentHashMap<>());
            SERVICE_KEYS.get(clz)
                    .put("", serviceKey);
        }

        return serviceKey;
    }


    @Override
    public String toString() {
        return "ServiceKey{" +
                "serviceClass=" + serviceClass +
                ", discriminator='" + discriminator + '\'' +
                '}';
    }
}
