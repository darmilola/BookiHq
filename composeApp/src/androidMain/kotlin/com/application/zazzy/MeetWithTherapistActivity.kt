package com.application.zazzy

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import dyte.io.uikit.DyteUIKitBuilder
import dyte.io.uikit.DyteUIKitConfig
import dyte.io.uikit.DyteUIKitInfo
import io.dyte.core.DyteMobileClient
import io.dyte.core.models.DyteMeetingInfo
import io.dyte.core.models.DyteMeetingInfoV2

class MeetWithTherapistActivity: AppCompatActivity() {
    private var authToken: String = ""
    private var isAudioEnabled = true
    private var isVideoEnabled = true
    private var dyteClient: DyteMobileClient? = null
    private var meetingInfo: DyteMeetingInfo? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         authToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJvcmdJZCI6ImFmNmU3YmQ5LWQ3M2EtNGZmZC1hODRmLWIyNTM0ODZhODRiNSIsIm1lZXRpbmdJZCI6ImJiYjI0ZDMwLTQxNzEtNDUwNi04NzVkLTdhMzFiYzhkZTBmYSIsInBhcnRpY2lwYW50SWQiOiJhYWEwZmMzNC03N2U3LTQzNTUtYWUxNi1mZGNlNTQwNzNmNzAiLCJwcmVzZXRJZCI6IjhkMDlkNmVhLTk3YmQtNDFhMS1hMGM1LTAzYWE5NzQ3YTBjZSIsImlhdCI6MTcxNzUyMDU4MCwiZXhwIjoxNzI2MTYwNTgwfQ.Mqn6QDJ1wxjmz8WZF9hp9NEkHqQJCKGg7xOT5fMXcfpcjRMq-wW4JNw3reJBl3O_VxezOTQYeCso-TcRvu0BGYWL8YHAXi5XLmZjuGaSvnBeWR8kgOTS57aQbXUkodtic9vQHYT9jDy3M3MZA5mJoe72OtZ6Wu-C3C1XGMtZra6qdNC5-gJRlbxGcyg9N_r8_SqK_sd8KKBQWwM8SrWgdhA758b6ycUzKOkOZ4f7NHPCGYEZfhUes7ls_6I9gdCJU6OS56Kcny_-j-TLcoIMSu7GBsg1o6prPNPDTBhuC4CQ1BkgzVEtyIRrI4dVpx8e-Z60VIWWoVssDfio9_0KNg"
         startDyteMeeting(authToken)
    }

    private fun startDyteMeeting(authToken: String) {
        val meetingInfo = DyteMeetingInfoV2(
            authToken = authToken
        )
        val dyteUIKitInfo = DyteUIKitInfo(
            activity = this,
            dyteMeetingInfo = meetingInfo
        )
        val dyteUIKit = DyteUIKitBuilder.build(dyteUIKitInfo)
        dyteUIKit.startMeeting()
    }

}