package com.pinkunicorp.findtime.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.res.stringResource
import io.github.aakira.napier.DebugAntilog
import com.pinkunicorp.compose.ui.MainView
import io.github.aakira.napier.Napier

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Napier.base(DebugAntilog())
        setContent {
            MainView {
                TopAppBar(title = {
                    when (it) {
                        0 -> Text(text = stringResource(id = R.string.world_clocks))
                        else -> Text(text = stringResource(id = R.string.findmeeting))
                    }
                })
            }
        }
    }
}
