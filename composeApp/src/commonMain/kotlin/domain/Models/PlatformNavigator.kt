package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable

interface PlatformNavigator: Parcelable {
     fun startVideoCall(authToken: String)
     fun startScanningBarCode(onCodeReady: (String) -> Unit)
     fun startImageUpload(imageByteArray: ByteArray)
     fun getUserLocation()
     fun startGoogleSSO(onAuthSuccessful: (String) -> Unit,
                        onAuthFailed: () -> Unit)
     fun startPhoneSS0(phone: String)
     fun verifyOTP(verificationCode: String, onVerificationSuccessful: (String) -> Unit,
                   onVerificationFailed: () -> Unit)
     fun startXSSO(onAuthSuccessful: (String) -> Unit,
                   onAuthFailed: () -> Unit)
 }