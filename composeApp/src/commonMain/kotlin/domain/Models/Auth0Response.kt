package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import kotlinx.serialization.Serializable

@Serializable
data class Auth0ConnectionResponse(val connectionType: String = Auth0ConnectionType.GOOGLE.toPath(),
                                   val email:  String = "", val action: String = AuthenticationAction.SIGNUP.toPath(),
                                   val status: String  = AuthenticationStatus.SUCCESS.toPath())
