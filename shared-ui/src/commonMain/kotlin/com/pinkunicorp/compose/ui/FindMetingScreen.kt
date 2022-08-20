package com.pinkunicorp.compose.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pinkunicorp.findtime.TimeZoneHelperImpl

@Composable
fun FindMeetingScreen(timezoneStrings: List<String>) {
    val listState = rememberLazyListState()
    val startTime = remember {
        mutableStateOf(8)
    }
    val endTime = remember {
        mutableStateOf(17)
    }
    val selectedTimeZones = remember {
        val selected = SnapshotStateMap<Int, Boolean>()
        for (i in timezoneStrings.indices) selected[i] = true
        selected
    }
    val timeZoneHelper = TimeZoneHelperImpl()
    val showMeetingDialog = remember {
        mutableStateOf(false)
    }
    val meetingHours = remember {
        SnapshotStateList<Int>()
    }

    if (showMeetingDialog.value) {
        MeetingDialog(
            hours = meetingHours,
            onDismiss = {
                showMeetingDialog.value = false
            }
        )
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally),
            text = "Time Range",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            NumberTimeCard(label = "Start", hour = startTime)
            Spacer(modifier = Modifier.size(32.dp))
            NumberTimeCard(label = "End", hour = endTime)
        }
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp)
        ) {
            Text(
                text = "Time Zones",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.h6
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        LazyColumn(
            modifier = Modifier
                .weight(0.6f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            state = listState
        ) {
            itemsIndexed(timezoneStrings) { i, timezone ->
                Surface(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Checkbox(checked = isSelected(selectedTimeZones, i),
                            onCheckedChange = {
                                selectedTimeZones[i] = it
                            })
                        Text(timezone, modifier = Modifier.align(Alignment.CenterVertically))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(0.1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2f)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(start = 4.dp, end = 4.dp)
        ) {
            OutlinedButton(onClick = {
                meetingHours.clear()
                meetingHours.addAll(
                    timeZoneHelper.search(
                        startTime.value,
                        endTime.value,
                        getSelectedTimeZones(timezoneStrings, selectedTimeZones)
                    )
                )
                showMeetingDialog.value = true
            }) {
                Text(text = "Search")
            }
        }
        Spacer(modifier = Modifier.size(48.dp))
    }
}

fun getSelectedTimeZones(
    timezoneString: List<String>,
    selectedStates: Map<Int, Boolean>
): List<String> {
    val selectedTimezones = mutableListOf<String>()
    selectedStates.keys.map {
        val timezone = timezoneString[it]
        if (isSelected(selectedStates, it) && !selectedTimezones.contains(timezone)) {
            selectedTimezones.add(timezone)
        }
    }
    return selectedTimezones
}