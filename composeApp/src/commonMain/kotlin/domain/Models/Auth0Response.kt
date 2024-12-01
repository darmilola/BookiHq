package domain.Models

import domain.Enums.Auth0ConnectionType
import domain.Enums.AuthenticationAction
import domain.Enums.AuthenticationStatus

class Auth0ConnectionResponse(val connectionType: String = Auth0ConnectionType.GOOGLE.toPath(),
                              val email:  String = "", val action: String = AuthenticationAction.SIGNUP.toPath(),
                              val status: String  = AuthenticationStatus.AUTHENTICATION_SUCCESS.toPath())
