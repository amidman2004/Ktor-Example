package com.example.auth.refresh_token

import org.jetbrains.exposed.sql.*

object RefreshTokens: Table(),RefreshTokenDao {

    val userId = integer("userId").uniqueIndex()

    val refreshToken = varchar("refreshToken",300).uniqueIndex()

    val expiresAt = long("expiresAt")

    val role = varchar("role",64)



    override fun insert(tokenDTO: RefreshTokenDTO): RefreshTokenDTO {
        insert {
            it[userId] = tokenDTO.userId
            it[refreshToken] = tokenDTO.refreshToken
            it[expiresAt] = tokenDTO.expiresAt
            it[role] = tokenDTO.role
        }
        return tokenDTO
    }

    override fun update(userId: Int, tokenDTO: RefreshTokenDTO): RefreshTokenDTO {
        update({RefreshTokens.userId eq userId}) {
            it[refreshToken] = tokenDTO.refreshToken
            it[expiresAt] = tokenDTO.expiresAt
            it[role] = tokenDTO.role
        }
        return tokenDTO
    }

    override fun findById(userId: Int): RefreshTokenDTO? {
        return select { RefreshTokens.userId eq userId }.mapNotNull {
            it.mapToRefreshTokenDTO()
        }.singleOrNull()
    }

    override fun findByToken(token: String): RefreshTokenDTO? {
        return select { refreshToken eq token }.mapNotNull {
            it.mapToRefreshTokenDTO()
        }.singleOrNull()
    }


}

private fun ResultRow.mapToRefreshTokenDTO():RefreshTokenDTO{
    return RefreshTokenDTO(
        userId = this[RefreshTokens.userId],
        refreshToken = this[RefreshTokens.refreshToken],
        expiresAt = this[RefreshTokens.expiresAt],
        role = this[RefreshTokens.role]
    )
}