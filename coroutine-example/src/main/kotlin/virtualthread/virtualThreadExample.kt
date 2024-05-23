package virtualthread

import java.util.concurrent.Executors


fun main() {

    Thread.startVirtualThread {
        println("Thread.startVirtualThread started!!")
    }

    Thread.ofVirtual().start {
        println("Thread.ofVirtual started!!")
    }

    Executors.newVirtualThreadPerTaskExecutor().execute {
        println("Executors.newVirtualThreadPerTaskExecutor started!!")
    }

    Thread.sleep(10)
}