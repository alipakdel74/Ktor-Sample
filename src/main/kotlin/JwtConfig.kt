import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm

object JwtConfig {
    private const val secret = "ktorSecret"
    private const val issuer = "ktor.io"
    private const val audience = "ktorUsers"
    private const val realm = "Access"

    val verifier: JWTVerifier = JWT.require(Algorithm.HMAC256(secret))
        .withAudience(audience)
        .withIssuer(issuer)
        .build()

    fun generateToken(phone: String): String = JWT.create()
        .withAudience(audience)
        .withIssuer(issuer)
        .withClaim("phone", phone)
        .sign(Algorithm.HMAC256(secret))
}
