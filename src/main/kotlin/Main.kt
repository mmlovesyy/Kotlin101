import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main(args: Array<String>) = runBlocking {
    startCountDown()
}

fun startCountDown() {

    var timeDownScope: CoroutineScope? = null

    countDownFlow(
        time = 10,
        start = {
            timeDownScope = it
            println("开始")

        },
        end = {
            println("结速倒计时")
        },
        next = {

            println("当时计数：$it")

            if (it == 50) {
                timeDownScope?.cancel()
            }

        })
}

fun countDownFlow(
    time: Int = 5,
    start: (scope: CoroutineScope) -> Unit,
    end: () -> Unit,
    next: (time: Int) -> Unit
) {

    GlobalScope.launch {
        flow {
            (time downTo 0).forEach {
                delay(1000)
                emit(it)
            }
        }.onStart {
            start(this@launch)

        }.onCompletion {
            end()
        }.catch {

        }.collect {
            next(it)
        }
    }
}