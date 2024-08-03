package com.lyra.app.team.member.infrastructure.persistence

import com.lyra.UnitTest
import com.lyra.app.team.domain.TeamId
import com.lyra.app.team.member.TeamMemberStub
import com.lyra.app.team.member.domain.TeamMember
import com.lyra.app.team.member.domain.TeamMemberRole
import com.lyra.app.team.member.domain.exception.TeamMemberException
import com.lyra.app.team.member.infrastructure.persistence.mapper.TeamMemberMapper.toEntity
import com.lyra.app.team.member.infrastructure.persistence.repository.TeamMemberR2dbcRepository
import com.lyra.app.users.domain.UserId
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.util.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.dao.DuplicateKeyException

@UnitTest
internal class TeamMemberStoreR2DbcRepositoryTest {

    private lateinit var repository: TeamMemberR2dbcRepository
    private lateinit var teamMemberStoreR2DbcRepository: TeamMemberStoreR2DbcRepository
    private lateinit var teamMember: TeamMember

    @BeforeEach
    fun setUp() {
        repository = mockk()
        teamMemberStoreR2DbcRepository = TeamMemberStoreR2DbcRepository(repository)
        teamMember = TeamMemberStub.create()
    }

    @Test
    fun `create should successfully insert a new team member`(): Unit = runBlocking {
        val teamMemberEntity = teamMember.toEntity()
        coEvery { repository.upsert(teamMemberEntity) } returns 1

        teamMemberStoreR2DbcRepository.create(teamMember)

        coVerify(exactly = 1) { repository.upsert(teamMemberEntity) }
    }

    @Test
    fun `create should throw TeamMemberException when DuplicateKeyException occurs`(): Unit =
        runBlocking {
            val teamMemberEntity = teamMember.toEntity()
            coEvery { repository.upsert(teamMemberEntity) } throws DuplicateKeyException("")

            assertThrows<TeamMemberException> {
                teamMemberStoreR2DbcRepository.create(teamMember)
            }
        }

    @Test
    fun `update should successfully update an existing team member`(): Unit = runBlocking {
        val updateTeamMember = teamMember.copy(role = TeamMemberRole.OWNER)
        val teamMemberEntity = updateTeamMember.toEntity()
        coEvery { repository.upsert(teamMemberEntity) } returns 1

        teamMemberStoreR2DbcRepository.update(updateTeamMember)

        coVerify { repository.upsert(teamMemberEntity) }
    }

    @Test
    fun `remove should successfully remove a team member`(): Unit = runBlocking {
        val teamId = TeamId(UUID.randomUUID().toString())
        val userId = UserId(UUID.randomUUID().toString())
        coEvery { repository.delete(teamId.value, userId.value) } returns 1

        teamMemberStoreR2DbcRepository.remove(teamId, userId)

        coVerify { repository.delete(teamId.value, userId.value) }
    }
}
