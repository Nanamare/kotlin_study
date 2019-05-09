import kotlin.reflect.jvm.internal.impl.utils.SmartSet

open class Base {
    open fun f() {
        println("Base")
    }
}

open class Derived : Base() {
    final override fun f() {
       super.f()
       println("Derived")
   }
}

class FianlDerived : Derived()


fun main() {
    val a = Derived()
    a.f()

    val b = a
    b.f()

}