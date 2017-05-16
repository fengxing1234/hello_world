package cn.projects.com.projectsdemo.dspider.designmode;

/**
 * Created by fengxing on 17/3/9.
 */

/**
 * 这种方式在类加载时就完成了初始化，
 * 所以类加载较慢，但获取对象的速度快。
 * 这种方式基于类加载机制避免了多线程的同步问题，
 * 但是也不能确定有其他的方式（或者其他的静态方法）导致类装载，
 * 这时候初始化instance显然没有达到懒加载的效果。
 */
//恶汉式
public class BadmashSingleton {
    private BadmashSingleton singleton;
    private static final BadmashSingleton single = new BadmashSingleton();
    public static BadmashSingleton getInstance(){
        return single;
    }
}

/**
 * 懒汉模式申明了一个静态对象，
 * 在用户第一次调用时初始化，
 * 虽然节约了资源，但第一次加载时需要实例化，
 * 反映稍慢一些，而且在多线程不能正常工作。
 */
//懒汉式
class LazySingleton{//线程不安全
    private static LazySingleton lazySingleton;
    private LazySingleton(){}
    public static LazySingleton getInstance(){
        if(null == lazySingleton){
            lazySingleton = new LazySingleton();
        }
        return lazySingleton;
    }
}

/**
 * 这种写法能够在多线程中很好的工作，
 * 但是每次调用getInstance方法时都需要进行同步，
 * 造成不必要的同步开销，
 * 而且大部分时候我们是用不到同步的，
 * 所以不建议用这种模式。
 */
//线程安全的懒汉式
class ThreadSafeLazy{
    private static ThreadSafeLazy safeLazy;
    private  ThreadSafeLazy(){}

    public static synchronized ThreadSafeLazy getInstance(){
        if(safeLazy == null){
            safeLazy = new ThreadSafeLazy();
        }
        return safeLazy;
    }
}

/**
 * 这种写法在getSingleton方法中对singleton进行了两次判空
 * 第一次是为了不必要的同步，第二次是在singleton等于null的情况下才创建实例。
 * 检查模式是正确使用volatile关键字的场景之一。
 * 在这里使用volatile会或多或少的影响性能，
 * 但考虑到程序的正确性，牺牲这点性能还是值得的
 * DCL优点是资源利用率高，第一次执行getInstance时单例对象才被实例化，效率高
 * 缺点是第一次加载时反应稍慢一些，在高并发环境下也有一定的缺陷，
 * 虽然发生的概率很小。DCL虽然在一定程度解决了资源的消耗和多余的同步，线程安全等问题，
 * 但是他还是在某些情况会出现失效的问题，也就是DCL失效
 */

/**
 * （1）给Singleton的实例分配内存;
 * （2）调用Singleton()的构造函数，初始化成员字段;
 * （3）将mInstance对象指向分配的内存空间（此时mInstance就不是null了）。
 *  由于Java编译器允许处理器乱序执行，以及JDK1.5之前JMM（Java Memory Model，即Java内存模型）
 *  中Cache、寄存器到内存回写顺序的规定，上面的第二和第三的顺序是无法保证的。
 *  也就是说，执行顺序可能是1-2-3也可能是1-3-2。
 *  如果是后者，并且在3执行完毕、2未执行之前，
 *  被切换到另一个线程上，就会出问题。
 * 但是在你的app没有太多的高并发存在时，
 * 这种模式已经可以完全满足大多数开发者的需求。那么一定还有更好的：
 */
//双重锁的单例模式
class DoubleLockSingleton{
    private volatile static DoubleLockSingleton doubleLockSingleton;
    private DoubleLockSingleton (){}

    public static DoubleLockSingleton getInstance(){
        if(doubleLockSingleton == null){
            synchronized (DoubleLockSingleton.class){
                if(doubleLockSingleton == null){
                    doubleLockSingleton = new DoubleLockSingleton();
                }
            }
        }
        return doubleLockSingleton;
    }
}

//静态内部类的单利模式
/**
 * 当第一次加载Singleton类的时候并不会初始化mInstance，
 * 只有在第一次调用getInstance方法时才会导致mInstance被初始化。
 * 因此，第一次调用getInstance方法会导致虚拟机加载SingletonHolder类，
 * 这种方式不仅能够确保线程安全，
 * 也能够保证单例对象的唯一性，
 * 同时也延迟了单例的实例化
 */
class StaticSingleton{

    private StaticSingleton (){}

    public static StaticSingleton getInstance(){

        return StaticSingletonHolder.staticSingleton;
    }

    private static class StaticSingletonHolder {
        private static final StaticSingleton staticSingleton = new StaticSingleton();
    }

}
