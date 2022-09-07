package flow.ops.builder

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class FlowBuilder {

    fun demo() = runBlocking {
        val flow = build()

        launch {
            for (k in 0..10) {
                println("I'm not blocked $k")
                delay(100)
            }
        }

        flow.collectLatest {
//        flow.collect {
            println(it)
        }
    }

    fun build() = flow<Int> {
        for (i in 0..10) {
            delay(100)
            emit(i)
        }
    }
}