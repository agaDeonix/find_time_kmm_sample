package com.pinkunicorp.findtime.android.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pinkunicorp.findtime.android.theme.primaryColor
import com.pinkunicorp.findtime.android.theme.primaryDarkColor
import com.pinkunicorp.findtime.android.theme.typography

@Composable
fun LocalTimeCard(city: String, time: String, date: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(Color.White)
            .padding(8.dp)
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Black),
            elevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                primaryColor,
                                primaryDarkColor
                            )
                        )
                    )
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(horizontalAlignment = Alignment.Start) {
                        Spacer(modifier = Modifier.weight(1.0f))
                        Text(text = "Your Location", style = typography.h4)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = city, style = typography.h2)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Spacer(modifier = Modifier.weight(1.0f))
                    Column(horizontalAlignment = Alignment.End) {
                        Spacer(modifier = Modifier.weight(1.0f))
                        Text(text = time, style = typography.h1)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = date, style = typography.h3)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}