package io.jongyun.graphinstagram.configuration.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(
    @Value("\${jwt.key}")
    private val key: ByteArray
) {
    private val jwtKey: Key = Keys.hmacShaKeyFor(key)
    private val BEARER_TYPE = "Bearer";
    private val CLAIM_JWT_TYPE_KEY = "type";
    private val CLAIM_AUTHORITIES_KEY = "authorities";

    private val tokenValidTime = 30 * 60 * 1000L

    fun createToken(authentication: Authentication): String {
        val now = Date()
        val authoritiesString = authentication.authorities.joinToString { "," }
        return Jwts.builder()
            .setSubject(authentication.name)
            .claim(CLAIM_JWT_TYPE_KEY, BEARER_TYPE)
            .claim(CLAIM_AUTHORITIES_KEY, authoritiesString)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + tokenValidTime))
            .signWith(jwtKey, SignatureAlgorithm.HS512)
            .compact()
    }

    fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {
        val claims = parseClaims(token)
        val authorities = claims[CLAIM_AUTHORITIES_KEY].toString().split(",").map { SimpleGrantedAuthority(it) }
        val principal = User(claims.subject, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, token, authorities)
    }

    fun parseClaims(token: String): Claims {
        return Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(token).body
    }

    fun resolveToken(request: HttpServletRequest): String? {
        return request.getHeader("Authorization")
    }
}