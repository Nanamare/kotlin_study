fun main() {

    var isRight : Boolean = 10 + 70 > 3 * 25
    println(isRight)

    isRight = false
    println(isRight)

    isRight = 30 == (10 + 20)
    println(isRight)


    isRight = 0.00001f == 0.005f * 0.002f
    println(isRight)

    val a = 15
    val b = 17

    var bool : Boolean = (a - b < a + b) && (a == 15)
    println(bool)

    val c : Int = a xor   b
    println(c)

    bool = !((a + b) > (a * 3) || (b - a) > 0)

    println(bool) //false

}