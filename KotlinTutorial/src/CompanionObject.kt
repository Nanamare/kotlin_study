class Bars {

    fun beer() {
        println("nice beer")
    }
}

class Foo {

    companion object {

        @JvmField
        val BARs = Bars()

        @JvmStatic
        val TAG = Foo::class.java.simpleName


    }
}

fun main() {
    Foo.BARs.beer()
    println(Foo.TAG)


}