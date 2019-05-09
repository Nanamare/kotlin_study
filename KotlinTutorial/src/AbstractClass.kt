open class BA {
    open fun f() {}
}

abstract class D : BA() {

    abstract override fun f()

}

class F : D() {
    override fun f() {
    }

}

