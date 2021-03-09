package com.example.countdowntimer
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Timer : ViewModel() {

    var started = false
    var paused = false
    var millisLeft: Long = 0L
    var millisTotal: Long = 0L
    lateinit var tima: CountDownTimer

    private val _fractionCovered: MutableLiveData<Float> = MutableLiveData()
    val fractionCovered: LiveData<Float> get() = _fractionCovered

    private val _timeRemaining: MutableLiveData<Long> = MutableLiveData()
    val timeRemaining: LiveData<Long> get() = _timeRemaining

    private val _isPaused: MutableLiveData<Boolean> = MutableLiveData()
    val isPaused: LiveData<Boolean> get() = _isPaused

    init {
        _fractionCovered.value = 100F
        _timeRemaining.value = 0L
        _isPaused.value = false
    }

    fun initTimer(millisRemaining: Long) {

        tima = object : CountDownTimer(millisRemaining, 30) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d("Clock", "$millisUntilFinished")
                millisLeft = millisUntilFinished
                val fraction: Float = millisLeft / millisTotal.toFloat()
                _fractionCovered.value = fraction
                _timeRemaining.value = millisUntilFinished
            }

            override fun onFinish() {
                Log.d("Tik ", "Done")
                started = !started
                _fractionCovered.value = 0F
                _timeRemaining.value = 0L
            }
        }
    }

    fun startUpTime(duration: Int) {
        if (!started) {
            started = !started
            millisTotal = duration * 1000L
            initTimer(millisTotal)
            tima.start()
            _isPaused.value = false
        }
    }

    fun countReset() {
        if (started) {
            started = !started
            tima.cancel()
            paused = false
            millisLeft = 0L
            _fractionCovered.value = 100F
            _timeRemaining.value = millisTotal
            _isPaused.value = false
        }
    }

    fun pauseResume() {
        if (started) {
            if (!paused) {
                tima.cancel()
                paused = !paused
                _isPaused.value = true
            } else {
                initTimer(millisLeft)
                tima.start()
                paused = !paused
                _isPaused.value = false
            }
        }
    }
}


