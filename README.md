# ServiceLocator
No annotations like @Inject and still decoupling the objects that other objects depend upon.

This is a very simple package for registering Singletons and Prototypes. Prototypes are implemented as Supplier<T>, so its up to implementor 
how to create these instances.

Choose a discriminator to make a distinctions between different interface defintions. Nothing fancy, just a very simple approach. 

# How to use it
The ServiceLocator has only one instance that's availabe from within the aplication via ServiceLocator.getInstance(). 

Now you can register anything that you need within your application, like a DataSource, a template, a configuration. 

## Singleton and Prototype
ServiceLocator.registerSingleton(Class<T>, instance) registers instance bound to Class<T>.ServiceLocator.registerSingleton(Class<T>, name, instance) 
registers a different instance of the same type with the dicriminator name (very much like you would ise @Name()).

The tuple <Class<T>,String> is used as the immutable key for the cache for all registered objects. The keys are always normalized using ServiceKet.createServiceKey. 
This will guarantee that ServiceKey.createServiceKey(Class<T>,name) == ServiceKey.createServiceKey(Class<T>,name) will be true (reference equality).

The ServiceLocator also has methods to remove Singletons and Prototypes and it is also possible to clear the whole cache. The keys will also be removed.

# Thread safety
This implementation uses ConcurrentHashmap for the object caches (keys and objects). No other data is shared.

# Examples
Have a look at the unit test. I will extend these tests

# Next version
The next version will call close() for any instance that implements Closable. That way it will behave as if you would have used the resource with try with resources.

