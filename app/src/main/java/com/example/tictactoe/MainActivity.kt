package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicTacToeTheme {
                Tictactoe()
            }
        }
    }
}

val DarkPurple = Color(0xFF43115B)   // Background
val LightBlue = Color(0xFF41C4F1)    // Player X card
val PaleBlue = Color(0xFFBFDDF4)     // Draw card
val MustardYellow = Color(0xFFE1B900) // Player O card
val TilePurple = Color(0xFF5B2B72)   // Game tiles
val White = Color(0xFFFFFFFF)        // "New Game" button background
val Black = Color(0xFF000000)        // Text color


@Composable
fun Tictactoe() {
    var turn by remember { mutableStateOf("X") }
    val cells = remember { mutableStateListOf("", "", "", "", "", "", "", "", "") }

    var cntx by remember { mutableStateOf(0) }
    var cnto by remember { mutableStateOf(0) }
    var cntd by remember { mutableStateOf(0) }
    var p by remember { mutableStateOf(0) }

    var cnt by remember { mutableStateOf(0) }
    var cn by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkPurple)
            .padding(horizontal = 28.dp, vertical = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(LightBlue),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "PLAYER X\n$cntx",
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(PaleBlue),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "DRAW\n$cntd",
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(MustardYellow),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "PLAYER O\n$cnto",
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(80.dp))

        for (i in 0..2) {
            Row {
                for (j in 0..2) {
                    val index = i * 3 + j
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(TilePurple)
                            .clickable {
                                if (cells[index].isEmpty() && p == 0) {
                                    cells[index] = turn
                                    turn = if (turn == "X") "O" else "X"
                                    if ((cells[0].isNotEmpty() && cells[0] == cells[1] && cells[1] == cells[2]) ||
                                        (cells[3].isNotEmpty() && cells[3] == cells[4] && cells[4] == cells[5]) ||
                                        (cells[6].isNotEmpty() && cells[6] == cells[7] && cells[7] == cells[8]) ||
                                        (cells[0].isNotEmpty() && cells[0] == cells[3] && cells[3] == cells[6]) ||
                                        (cells[1].isNotEmpty() && cells[1] == cells[4] && cells[4] == cells[7]) ||
                                        (cells[2].isNotEmpty() && cells[2] == cells[5] && cells[5] == cells[8]) ||
                                        (cells[0].isNotEmpty() && cells[0] == cells[4] && cells[4] == cells[8]) ||
                                        (cells[2].isNotEmpty() && cells[2] == cells[4] && cells[4] == cells[6])
                                    ) {
                                        if (cells[index] == "X") {
                                            cntx++
                                            p = 1
                                            cnt=1
                                        } else if (cells[index] == "O") {
                                            cnto++
                                            p = 1
                                            cn=1
                                        }
                                    } else if (cells.none { it.isEmpty() }) {
                                        cntd++
                                        p = 1
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = cells[index],
                            fontSize = 36.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    if (j < 2) Spacer(modifier = Modifier.width(20.dp))
                }
            }
            if (i < 2) Spacer(modifier = Modifier.height(20.dp))
        }
        Spacer(modifier = Modifier.height(25.dp))
        Row(

        ){
            Text(
                text=when {
                    cnt == 1 -> "X Wins"
                    cn == 1 -> "O Wins"
                    else -> "Match Draw"},
                fontSize = 30.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold

            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                for (k in cells.indices)
                    cells[k] = ""
                turn = "X"
                p = 0
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RectangleShape,
            modifier = Modifier
                .width(250.dp)
                .height(50.dp)
        ) {
            Text(
                text = "New Game",
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {
                cntx=0
                cnto=0
                cntd=0
                p = 0
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RectangleShape,
            modifier = Modifier
                .width(200.dp)
                .height(50.dp)
        ) {
            Text(
                text = "Reset Score",
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun ShowPreview() {
    TicTacToeTheme {
        Tictactoe()
    }
}

