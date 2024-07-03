package domain.Models

interface PlatformNavigator {
     fun startVideoCall(authToken: String)
     fun startImageUpload(imageByteArray: ByteArray)
     fun getUserLocation()
     fun startGoogleSSO(onAuthSuccessful: (String) -> Unit,
                        onAuthFailed: () -> Unit)
     fun startPhoneSS0(phone: String)
     fun verifyOTP(verificationCode: String, onVerificationSuccessful: (String) -> Unit,
                   onVerificationFailed: () -> Unit)
     fun startFacebookSSO(onAuthSuccessful: (String) -> Unit,
                          onAuthFailed: () -> Unit)
 }