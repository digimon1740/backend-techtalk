package structuredconcurrency

suspend fun main() {
    printHello() // 컴파일 에러
}

suspend fun printHello() = println("hello")

