//
//  TimezoneView.swift
//  iosApp
//
//  Created by Denis Mikhailov on 19.08.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TimezoneView: View {
    
    @EnvironmentObject private var timezoneItems: TimezoneItems
    private var timezoneHelper = TimeZoneHelperImpl()
    @State private var currentDate = Date()
    let timer = Timer.publish(every: 1000, on: .main, in: .common).autoconnect()
    @State private var showTimezoneDialog = false
    
    var body: some View {
        NavigationView {
            VStack {
                TimeCard(timezone: timezoneHelper.currentTimeZone(),
                         time: DateFormatter.short.string(from: currentDate),
                         date: DateFormatter.long.string(from: currentDate))
                Spacer()
                List {
                    ForEach(Array(timezoneItems.selectedTimezones), id: \.self) { timezone in
                        NumberTimeCard(timezone: timezone,
                                       time: timezoneHelper.getTime(timezoneId: timezone),
                                       hours: "\(timezoneHelper.hoursFromTimeZone(otherTimeZoneId: timezone)) hours from local",
                                       date: timezoneHelper.getDate(timezoneId: timezone)).withListModifier()
                    }//ForEach
                    .onDelete(perform: deleteItems)
                }//List
                .listStyle(.plain)
                Spacer()
            }//VStack
            .onReceive(timer) { input in
                currentDate = input
            }
            .navigationTitle("World Clocks")
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button(action: {
                        showTimezoneDialog = true
                    }) {
                        Image(systemName: "plus")
                            .frame(alignment: .trailing)
                            .foregroundColor(.black)
                    }
                }//ToolbarItem
            }//toolbar
            .fullScreenCover(isPresented: $showTimezoneDialog) {
                TimezoneDialog().environmentObject(timezoneItems)
            }
        }// NavigationView
        
    }
    
    func deleteItems(at offsets: IndexSet) {
        let timezoneArray = Array(timezoneItems.selectedTimezones)
        for index in offsets {
            let element = timezoneArray[index]
            timezoneItems.selectedTimezones.remove(element)
        }
    }
}

struct TimezoneView_Previews: PreviewProvider {
    static var previews: some View {
        TimezoneView().environmentObject(TimezoneItems())
    }
}
