Kotlin
======

### 1. 기본타입

  - 모든게 객체라서 변수에 대해 멤버 함수나 Property 사용 가능</br>
  - Byte 8, Short 16, Int 32, Long 64, Float 32, Double 64</br>
  - 숫자를 Boxing 하면 참조 동일성(identity)은 유지 되지 않음. 반면에 값 동등성(equals)은 유지 됨

###### Reference Identity
```
val value : Int = 777
print(value === value) // true
val boxedValue : Int? = value
val anotherBoxedValue : Int? = value
print(boxedValue === anotherBoxedValue) // false
```


###### Value Equality
```
val value : Int = 777
print(value == value) // true
val boxedValue : Int? = value
val anotherBoxedValue : Int? = value
print(boxedValue == anotherBoxedValue) // true
```
  1.4 작은 범위 타입은 큰 범위 타입의 하위 타입이 아니다. 따라서 명시적 타입 변환을 해야한다.
```
val b : Byte = 1
val i : Int = b // error
val i : Int = b.toInt() // 바른 표현
```
모든 숫자 타입은 다음 변환을 지원한다.
```
toByte(), toShort(), toInt(), toLong(), toFloat(), to Char() ...
```

#### 연산
비트 연산의 경우 특수문자(<<, >>)를 사용하지 않고 중위 형식으로 호출할 수 있는 함수가 존재한다.
비트연산은 Int 와 Long 타입만 가능하다.
```
val x (1 shl 2) and 0x000FF000
```

>1. shl - signed shift left (java expression <<)
2. shr - signed shift right (java expression >>)
3. ushr - unsigned shift right (java expression >>>)
4. and - and 비트 연산
5. or - or 비트 연산
6. xor - xor 비트 연산
7. inv() - 역 비트 연산(1111 -> 0000, 0101 -> 1010)


#### 문자
문자를 바로 숫자로 다룰 수 없다. 하지만 숫자로 변환 할 수 있다.
```
fun check(c : char) {
  if(c == 777) { // 컴파일 에러
  //Todo
  }
}
```

```
fun check(c : char): Int {
  if(c !in '0'..'9') {
    throw IllegalArgumentException("Out of range")
  } else {
    return c.toInt() - '0'.toInt() // convert to number
  }
}
```

#### Boolean
nullable 할 경우 Boolean 타입으로 박싱해야한다.
```
|| 논리합
&& 논리곱
! 부정
```

#### 배열
코틀린은 Array 클래스를 이용해서 배열을 표현한다. 이 클래스는 get, set size 등의 프로퍼티를 가진다.
또한 get, set 함수는 연산자 오버로딩에 따라 []로 바뀐다.

```
class Array<T> private constructor() {
  val size : Int
  fun get(index : Int) : T
  fun set(index : Int, value : T) : Unit // java expression : void 단, 코틀린의 Unit 은 return 을 명시하지 않아도 된다.
  fun iterator() : Iterator<T>
}
```
arrayOf() 함수를 이용해서 배열을 생성하며, 초기화 시킬 수도 있다.</br>
arrayOf(1, 2, 3) 은 배열 [1, 2, 3]을 생성한다.</br>
arrayOfNulls 함수를 사용하면, null을 값으로 가지는 크기의 배열을 생성한다.</br>

###### 주의점
자바와 달리 코틀린의 배열은 불변하다. 이는 Array<String> 을 Array<Any> 에 할당 할 수 없고 결과적으로 런타임 실패를 가능한 방지한다.</br>
또한 코틀린은 기본 타입 배열을 위해 박싱 오버헤드가 없는 ByteArray, ShortArray, IntArray 등의 클래스를 제공한다. 이 클래스는 Array 클래스와 상속관계를 갖지 않지만, 동일한 메서드와 프로퍼티를 가진다.
```
fun calc() : Int {
    val x : IntArray = intArrayOf(1, 8, 3)
    x[0] = x[1] + x[2]
    return x[0] // 11
}
```

#### 문자열
코틀린은 기본적으로 2가지 문자열 리터럴을 제공한다.</br>
하나는 이스케이프 문자를 가질 수 있는 이스케이프드 문자열이고, 다른 하나는 뉴라인과 임의 텍스트를 가질 수 있는 raw 문자열이다. 이스케이프드 문자열은 자바 문자열과 매우 유사하다.<br>

###### 이스케이프드 문자열
```
val s = "Hello, world!\n"
```
###### Raw 문자열 (앞뒤의 """ 로 구분한다)
기본적으로 | 문자를 가장자리 접두사로 사용하며, trimMargin(">") 와 같이 파라미터를 이용해 접두사를 변경할 수도 있다.
```
val text = """
    |Hi my name is hyunsung.
    |Hello world.
    """.trimMargin() // trimMargin() 함수를 사용해 공백을 제거할 수 있다.
```

###### 문자열 템플릿
문자열은 템플릿 표현식을 포함할 수 있다. 예를 들어 코드의 일부를 평가해서 그 결과를 문자열에 연결할 수 있다. 템플릿 표혐식은 달라 기호('$')로 시작하고 단순 이름을 포함한다.
```
val i = 10 // i type is int
val s = "i = $i" // s type is string "i = 10"
```
또는 중괄호 안에 위치한다.
```
val s = "abc"
val str = "$s.length is ${s.length}" // "abc.length is 3"
```
Raw 문자열에서 템플릿을 사용하고 싶다면 다음과 같은 방식으로 사용한다.
```
val price = """${'$'}9.99"""
```

##### if 식
자바에서의 사용과 유사하지만, 코틀린에서는 if는 식이며 값을 리턴할 수 있다.
```
val max = if(a > b) a else b

val a = 3
val b = 1
val max2 = if(a > b) {
  a
} else {
  b
}

print(max2) // 3
```

##### When 식
when은 자바의 switch 연산자와 비슷하지만 정수가 아닌 값도 처리할 수 있다.
```
when(x) {
 1 -> print("x == 1")
 2 -> print("x == 2")
 else -> {
  print("해당 조건 없음")
 }
}
```
when 은 충족하는 브랜치 조건이 나올 때까지 모든 브랜치를 순차적으로 찾는다. when은 식이나 문장으로 사용할 수 있다.
만약식으로 사용하면 충족한 브랜치의 값이 전체 식의 값이 된다. 문장으로 사용하면 개별 브랜치의 값은 무시한다. (if 처럼 각 브랜치는 블록일 수 있으며
블록의 마지막 식이 값이 된다.) else 브랜치는 모든 브랜치 조건이 맞지 않을 때 사용한다. when 을 식으로 사용할 경우,
각 브랜치 조건이 모든 경우의 수를 다뤘다고 컴파일러가 판명할 수 없으면, else 를 필수로 사용해야 한다.

###### 여러 경우를 동일하게 처리해야 할 경우 브랜치 조건을 콤마로 묶을 수 있다.
```
when(x) {
  0, 1, 2 -> print("x == 0 or x == 1 or x ==2")
  else -> print("not included")
}
```

###### 브랜치 조건으로(상수뿐만 아니라) 임의 식을 사용할 수 있다)
```
val a: String = "33"
val x = 33
when(x) {
   Integer.parseInt(a) -> print("s encodes x")
}
```
###### 범위나 콜렉션에 대해 in이나 !in을 사용해서 값을 검사할 수도 있다.
```
when(x) {
  in 1..10 -> print("x is in the range")
  in validNumbers -> print("x is valid")
  !in 10..20 -> print("x is outside the range")
  else -> print("none of the above")
}
```
###### is 나 !is 로 특정 타입의 값인지 검사할 수 있ㄷ. 스타트 변환 덕에 추가 검사 없이 타입의 프로퍼티와 메서드에 접근할 수 있다.
```
val x = "prefixHello"
   val hasPrefix = when(x) {
       is String -> x.startsWith("prefix")
       else -> false
   }
print(hasPrefix) // true
```

##### For 루프
for 루프는 iterator를 제공하는 모든 것에 대해 반복을 수행한다.
```
for (item in collections) {
  // Todo
}
```
배열에 대한 for 루프는 인덱스 기반 루프로 컴파일 되므로, 다음과 같은 방법을 사용하면 된다.
(이러한 방식의 반복문은 **추가 객체**를 생성하지 않는 코드로 최적화해서 컴파일 된다)
```
for(index in array.indices)
  print(array[index])
```
또한 withIndex() 함수를 통해 처리 할 수도 있다.
```
for((index, value) in array.withIndex()) {
  print("the element at $index is $value")
}
```

###### while 과 do while 루프는 java 와 같게 동작한다.</br>

#### 리턴과 점프
- return : 기본적으로 가장 가깝게 둘러 싼 함수나 임의 함수에서 리턴한다.
- break : 가장 가깝게 둘러 싼 루프를 끝낸다
- continue : 가장 가깝게 둘러 싼 루프의 다음으로 넘어간다.


###### Break 와 Continue 라벨
코틀린의 모든 식은 라벨로 표식을 가질 수 있다. 라벨은 @ 기호 뒤에 구분자를 갖는다. 예를 들어 abc@, fooBar@ 는
유효한 라벨이다. 식에 라벨을 붙이르면 식 앞에 라벨을 위치시키면 된다.
```
loop@ for(i in 1..100) {
  for(j in 1..100) {
    if(condition) {
      break@loop //loop 라벨을 가진 for 문 탈출, 편리하다
    }
  }
}
```
###### 라벨에 리턴하기
코틀린은 함수 리터럴, 로컬 함수와 객체식에서 함수를 중첩할 수 있다. 한정한 return 은 바깥 함수에서 리턴할 수 있도록
한다. 가장 중요한 쓰임새는 람다 식에서 리턴하는 것이다.
```
val ints = arrayOf(9,8,7,6,5,4,3,2,1,0)
fun test() {
    ints.forEach {
        if(it == 3) return
        print(it)
    }
}
// 987654
```
** 일반적으로 it이 3이 되는 순간 test() 함수가 리턴되지만, 람다식에 라벨을 붙이면 return을 제한적으로 사용 할 수 있다. **
```
val ints = arrayOf(9,8,7,6,5,4,3,2,1,0)
fun test() {
    ints.forEach here@ {
        if(it == 3) return@here
        print(it)
    }
}
// 987654210
```
다른 방법으로 구현할 수도 있다.
```
fun test() {
  ints.forEach(fun(value : Int) {
    if(value == 3) return
    print(value)
  })
}
```

### 2. 클래스와 오브젝트

클래스의 선언은 클래스 이름, 클래스 헤더(타입 지정, 생성자 등)와 중괄호를 둘러 싼 클래스 몸체로 구성된다.
헤데, 몸체는 필수는 아니며, 몸체를 가지 않을 경우 중괄호를 생략할 수도 있다.
```
class Empty
```
###### 생성자
코틀린 클래스는 한개의 주요 생성자와 한개 이상의 보조 생성자를 가질 수 있다. 주요 생성자는 클래스 헤더에 속한다.
클래스 헤더는 클래스 이름 뒤에 위치한다
```
class person constructor(firstName : String) {
  //Todo
}
```
주요 생성자가 annotation 이나 가시성 제한자 (access modifier)를 갖지 않으면 constructor 키워드를 생략할 수도 있다.
```
class person(firstName : String) {
  //Todo
}
```
주요 생성자는 어떤 코드도 포함할 수 없다. 초기화 코드는 **init** 키워드를 이용한 initializer 블록에 위치한다.
```
class person(firstName : String) {
  init {
    //Todo initializer...
  }
}
```
주요 생성자의 파라미터는 init 블록과 클래스 몸체에 선언한 프로퍼티 initializer 에서만 사용할 수 있다.
```
class Person(firstName : String) {
  val personName = firstName.toUpperCase()
}
```
코틀린은 주요 생성자에 프로퍼티를 선언하고 초기화 할 수 있는 간결한 구문을 제공한다.</br>
주요 생성자에 선언한 프로퍼티는 var (변경 가능) 하거나 val (읽기전용) 일 수 있다.
```
class Person(val firstName : String, val lastName : String, var age : Int) {

}
```
생성자가 annotation 이나 가시성 제한자를 가질 경우 constructor 키워드가 필요하며 키워드 앞에 제한자가 위치하게 된다
```
class Person public @Inject constructor(firstName : String) {
  //Todo...
}
```

###### 보조 생성자
클래스 내부에 constructor 키워드를 이용하여 보조 생성자를 선언할 수 있다.
```
class Person {
  constructor(parent : Person) {
    // Todo...
  }
}
```
클래스가 주요 생성자를 가질 경우, 각 보조 생성자는 직접 주요 생성자에 위임하거나, 다른 보조 생성자를 통해 간접적으로 주요 생성자에 위임해야한다. this 키워드를
이용해서 같은 클래스의 다른 생성자에 위임할 수 있다.
```
class Person(val firstName : String) {
  constructor(firstName : String, parent : Person) : this(firstName) {
    // Todo ...
  }
}
```

추상이 아닌 클래스가 공개 생성자를 갖지 않기를 원하면 기본 가시성이 아닌 다른 가시성을 가지는 빈 주요 생성자를 추가해야 한다.
```
class DontCreateMe private constructor () {
}
```

###### 클래스 인스턴트 생성
일반 함수와 비슷하게 생성자를 호출한다. 단 ** 코틀린은 new가 없다 **
```
val invoke = Invoke()
val person = Person("Hyunsung")
```

##### 클래스 멤버
클래스는 다음을 가진다.
> - 생성자와 initializer 블록
- 함수
- 프로퍼티
- 중첩 클래스와 내부 클래스
- 오브젝트 선언

#### 상속
코틀린의 모든 클래스는 공통의 상위 클래스인 **Any** 를 가진다. Any는 상위타입을 지정하지 않은 클래스의 기본 상위 타입이다.</br>
Any는 java.lang.Object가 아니다. 특히 equals(), hashCode(), toString() 외에 다른 멤버를 갖지 않는다.</br>
상위타입을 직접 선언하려면 클래스 헤더에 콜론 뒤에 타입을 위치 시키면 된다.</br>
기본적으로 코틀린에서 모든 클래스는 final 이다. 그래서 클래스 앞에 붙는 open은 다른 클래스가 이 클래스를 상속할 수 있도록 한다.
```
open class Base(p : Int)
class Derived(p : Int) : Base(p)
```
클래스가 주여 생성자를 가지면 주요 생성자의 파라미터를 이용해서 베이스 타입을 바로 초기화 할 수 있다.(초기화는 반드시 해야한다)</br>
클래스가 주요 생성자를 갖지 않으면, 각 보조 생성자는 super 키워드를 사용해서 베이스 타입을 초기화하거나 그것을 하는 다른 생성자에 위임해야 한다.
위임받은 다른 보조 생성자는 베이스 타입의 다른 생성자를 호출할 수 있다.
```
Class CustomView : View {
  constructor(ctx : context) : super(ctx) {
    // Todo...
  }

  constructor(ctx : context, attrs : AttributeSet) : super(ctx, attrs) {
    // Todo ...
  }
}
```
클래스의 open 어노테이션은 자바의 final과 정반대이다. open은 다른 클래스가 이 클래스를 상속할 수 있도록 한다.</br>
**기본적으로 코틀린의 모든 클래스는 final이다** </br>

##### 멤버 오버라이딩
코틀린에서는 뭐든 명시적으로 만드는 것을 고수한다. 자바와 달리 코틀린은 오버라이딩 가능한 멤버와 오버라이드를 위해서는 명시적으로 어노테이션을 붙여줘야 한다!

```
open class Base {
  open fun f() {}
  fun v() {}
}

class Derived() : Base() {
  override fun f() {}
}
```
Derived 클래스의 f()는 override 어노테이션이 명시적으로 붙어야한다. 붙이지 않으면 컴파일 에러가 발생한다. Base 클래스의 v() 와 같이 함수에 open 을 붙이지 않으면
override 여부에 상관없이 하위클래스에서 동일 시그니처를 갖는 메서드를 선언할 수 없다. 또한 final 클래스에서는 open 어노테이션이 금지된다.
따라서 오버라이딩을 막고 싶다면 final 을 사용하면 된다.

##### 오버라이딩 규칙
자식 클래스의 여러 부모 클래스에서 같은 시그니처를 가진 함수를 상속하면, 반드시 자식 클래스에서 함수를 오버라이딩하고, 구현해애한다. (보통 상속받은 것들 중 하나를 사용하는편이다)
사용할 상위 타입의 구현을 지정하려면 화살괄호에 상위 타입 이름을 지정한 super 키워드를 사용한다
```
open class A {
    open  fun f () {
        println("A - f")
    }

    open fun a () {
        println("A - a")
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

    override fun a() {
        super<A>.a()
    }

}

fun main() {
    var a = C()
    a.f()
}
```

##### 추상클래스
클래스와 멤버를 abstract 로 선언할 수 있다. 추상 멤버는 구현을 갖지 않는다. 또한 open 을 붙일 필요가 없다.
또한 open 되어 있는 메소드 추상 메소드로 오버라이딩 할 수 있다

```
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
```
#### Companion Objects
코틀린의 클래스는 정적 메서드를 갖지 않는다. 많은 경우 그대신 패키지 수준의 함수를 사용하는 것을 권장하고 있다.

만약 클래스 인스턴트 없이 클래스의 내부에 접근해야하는 함수를 작성해야 한다면(ex 팩토리 메서드 등 BitmapFactory.create(...)), 클래스 안에 선언한 오브젝트의 멤버로 함수를 작성할 수 있다

```
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
```
















#### let
자바에서 이런식으로 코딩을 해야했다면,
```
Java version
private void setUser(@Nullable User user) {
  if(user != null) {
    String name = user.getName();
    // Todo...
  }
}
```
코틀린에서는 아주 직관적이고 가독성 좋은 코드가 되었다.
```
kotlin version
private fun setUser(user : User?) {
  user?.let {
    user ->
      val name = user.name
  }
}
```
인자를 받지 않으면, 묵시적으로 it 이 생긴다.
```
private fun setUser(user : User?) {
  user?.let {
      val name = it.name
  }
}
```

```
Java version
private void setUser(@Nullable User user) {
  if(user == null) {
    //Todo handler nullptr
  } else {
    String name = user.getName();
    if(name == null) {
      //Todo handler nullptr
    } else {
      //Todo ...
    }

  }
}
```
좀더 개선하면
```
private void setUser(@Nullable User user) {
  if(user != null && user.getName() != null) {
    String name = user.getName();
    //Todo...
  } else {
    //Todo handler nullptr
  }
}
```
까지 바꿀 수 있지만, 코틀린을 사용하면 아래처럼 활용가능하다. (let, run 구조)
```
private fun setUser(user : User?) {
  user?.name?.let {
    //Todo ...
  } ? : run {
    //Todo handler nullptr
  }
}

```

#### Apply
```
Java version
User user = new User("nanamare", "서울시 사당동", "29살");
```
or
```
User user = new User();
user.setName("nanamare");
user.setAddress("서울시 사당동");
user.old("29살");
```
코틀린으로 표현하면, 간단히 표현할 수 있다. (객체를 만들고 바로 init 해줘야 하는 경우)
```
val user = User().apply { // 스코프 안에는 자기자신을 자기킨다
  name = "nanamare"
  address = "서울시 사당동"
  old = "29살"
}
```

####Apply - Intent 편
```
private Intent getNewActivity() {
  Intent intent = new Intent(this, NewActivity.class);
  intent.putExtras(EXTRA_KEY_A, a);
  intent.putExtras(EXTRA_KEY_B, b);
  intent.putExtras(EXTRA_KEY_C, c);
  return intent;
}
```
이런식으로 직관적으로 표현가능하다.
```
private fun getNewActivity() {
  Intent(this, NewActivity::class.java).apply {
    putExtras(EXTRA_KEY_A, a)
    putExtras(EXTRA_KEY_B, b)
    putExtras(EXTRA_KEY_C, c)
  }
}
```

#### Apply, Also, Run, Let, With

#### Extension function
원래 정의되어있던 함수인것처럼 사용
```
fun Context.showToast(text : CharSequence, duration : Int = Toast.LENGTH_SHORT) {
  Toast.makeText(this, text, duration).show()
}
```

코틀린 인 액션, 커니의 코틀린


```
```



```
```
