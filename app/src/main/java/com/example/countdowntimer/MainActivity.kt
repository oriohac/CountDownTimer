package com.example.countdowntimer

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.countdowntimer.DifTimes.getMillis
import com.example.countdowntimer.DifTimes.getMinutes
import com.example.countdowntimer.DifTimes.getSeconds
import com.example.countdowntimer.ui.theme.CountDownTimerTheme


class MainActivity : AppCompatActivity() {

    lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        timer = Timer()

        setContent {
            CountDownTimerTheme {
                CountDownLogic()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.countReset()
    }
}

// Start building your app here!
@Composable
fun CountDownLogic() {

    val timerViewModel: Timer = viewModel()
    val progress by timerViewModel.fractionCovered.observeAsState()
    val time by timerViewModel.timeRemaining.observeAsState()
    val isPaused by timerViewModel.isPaused.observeAsState()
    var minutes = remember { mutableStateOf(0) }
    var seconds = remember { mutableStateOf(0) }
    var millis = remember { mutableStateOf(0) }
    val plusminusSize: Dp = 30.dp
    val circleSize: Dp = 300.dp

    Column(
            modifier = Modifier.fillMaxSize()
            .background(color = Color(47, 50, 64)
            )
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(49,51,67),
            elevation = 10.dp
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = painterResource(id = R.drawable.empty_hour), contentDescription = null)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Countdown Timer",
                    modifier = Modifier.padding(8.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W800,
                    color = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(circleSize),
            horizontalArrangement = Arrangement.Center
        ) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (box) = createRefs()
                Box(
                    modifier = Modifier.constrainAs(box) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                ) {
                    CircularProgressIndicator(
                        progress = 1F,
                        modifier = Modifier
                            .height(circleSize)
                            .width(circleSize),
                        color = Color.White,
                        strokeWidth = 20.dp
                    )
                    CircularProgressIndicator(
                        progress = progress ?: 0.8F,
                        modifier = Modifier
                            .height(circleSize)
                            .width(circleSize),
                        color = Color.Red,
                        strokeWidth = 20.dp
                    )
                }
                val (text) = createRefs()
                Text(
                    text = "${time?.getMinutes()} : ${time?.getSeconds()} : ${time?.getMillis()}",
                    color = Color.White,
                    fontWeight = FontWeight.W700,
                    fontSize = 35.sp,
                    modifier = Modifier.constrainAs(text) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Add Time",
                    modifier = Modifier
                        .width(plusminusSize)
                        .height(plusminusSize)
                        .clickable(onClick = { minutes.value++ })
                )
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(color = Color(97, 204, 231)),
                ) {
                    Text(
                        text = if (minutes.value < 10 ) "0${minutes.value}" else "${minutes.value}",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W700,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_minus),
                    contentDescription = "Minus Time",
                    modifier = Modifier
                        .width(plusminusSize)
                        .height(plusminusSize)
                        .clickable(onClick = { minutes.value-- })
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Add Time",
                    modifier = Modifier
                        .width(plusminusSize)
                        .height(plusminusSize)
                        .clickable(onClick = { seconds.value++ })
                )
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(color = Color(97, 204, 231)),
                ) {
                    Text(
                        text = if (seconds.value < 10) "0${seconds.value}" else "${seconds.value}",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W700,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_minus),
                    contentDescription = "Minus Time",
                    modifier = Modifier
                        .width(plusminusSize)
                        .height(plusminusSize)
                        .clickable(onClick = { seconds.value-- })
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Minus Time",
                    modifier = Modifier
                        .width(plusminusSize)
                        .height(plusminusSize)
                        .clickable(onClick = { millis.value++ })
                )
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(color = Color(97, 204, 231)),
                ) {
                    Text(
                        text = if (millis.value < 10) "0${millis.value}" else "${millis.value}",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W700,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_minus),
                    contentDescription = "Minus Time",
                    modifier = Modifier
                        .width(plusminusSize)
                        .height(plusminusSize)
                        .clickable(onClick = { millis.value-- })
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column( horizontalAlignment = Alignment.CenterHorizontally) {

            Button(
                onClick = { if (minutes.value > 0) {
                    timerViewModel.startUpTime(minutes.value)
                }else if( seconds.value > 0){
                    timerViewModel.startUpTime(seconds.value)
                }
                          },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(47,50,64)),
            modifier = Modifier.clip(shape = RoundedCornerShape(20.dp))) {

                Image( painter = painterResource(id = R.drawable.playw ), contentDescription = "Reset Button",
                    modifier = Modifier.size(38.dp))
            }
            }
            Spacer(modifier = Modifier.width(18.dp))

            Column( horizontalAlignment = Alignment.CenterHorizontally) {

            Button(
                onClick = { timerViewModel.pauseResume() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(47,50,64)),
                modifier = Modifier.clip(shape = RoundedCornerShape(20.dp))
            ) {

                Image( painter = if (isPaused!!) painterResource(id = R.drawable.ic_resume ) else painterResource(id = R.drawable.ic_pause ), contentDescription = "Reset Button",
                    modifier = Modifier.size(38.dp))
            }
            }
            Spacer(modifier = Modifier.width(18.dp))
            Column( horizontalAlignment = Alignment.CenterHorizontally) {

            Button(
                onClick = { timerViewModel.countReset() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(47,50,64)),
                modifier = Modifier.clip(shape = RoundedCornerShape(20.dp))
            ) {

                Image( painter = painterResource(id = R.drawable.resetw ), contentDescription = "Reset Button",
                    modifier = Modifier.size(38.dp))
            }

            }
        }
    }
}