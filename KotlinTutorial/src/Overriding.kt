open class A {
    open  fun f () {
        println("A - f")
    }

    open fun b () {
        println("A - b")
    }
}

interface B {
    fun f() {
        println("B - f")
    }

    fun a() {
        println("B - a")
    }
}

class C : A(), B {

    override fun f() {
        super<A>.f()
        super<B>.f()
    }


}

fun main() {
    var a = C()
    a.f()
    a.b()
    a.a()
}