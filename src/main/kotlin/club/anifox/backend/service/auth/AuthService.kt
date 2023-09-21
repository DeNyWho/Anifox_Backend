package club.anifox.backend.service.auth

import club.anifox.backend.domain.model.user.request.AuthenticationRequest
import club.anifox.backend.domain.model.user.request.CreateUserRequest
import club.anifox.backend.domain.repository.auth.AuthRepository
import club.anifox.backend.service.auth.components.AuthComponent
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthService : AuthRepository {

    @Autowired
    private lateinit var authComponent: AuthComponent

    override fun authenticate(loginRequest: AuthenticationRequest, res: HttpServletResponse) {
        authComponent.authenticate(userIdentifier = loginRequest.userIdentifier, password = loginRequest.password, res)
    }

    override fun registration(signUpRequest: CreateUserRequest, response: HttpServletResponse) {
        authComponent.registration(signUpRequest = signUpRequest, response = response)
    }

    override fun refreshAccessToken(refreshToken: String, response: HttpServletResponse) {
        authComponent.refreshAccessToken(refreshToken = refreshToken, response = response)
    }
}
