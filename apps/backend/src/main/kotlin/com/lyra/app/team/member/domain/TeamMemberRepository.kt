package com.lyra.app.team.member.domain

/**
 * [TeamMemberRepository] is a repository to manage team members.
 * A team member is a user that belongs to a team.
 */
interface TeamMemberRepository {
    /**
     * Create a new team member.
     *
     * @param member The team member to be created.
     */
    suspend fun create(member: TeamMember)
    /**
     * Updates a team member.
     *
     * @param member The team member to be updated.
     */
    suspend fun update(member: TeamMember)
}
