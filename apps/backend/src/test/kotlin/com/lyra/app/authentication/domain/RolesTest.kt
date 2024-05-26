package com.lyra.app.authentication.domain

import com.lyra.UnitTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@UnitTest
internal class RolesTest {
    @Test
    fun shouldNotHaveRoleWithoutRoles() {
        assertThat(Roles(setOf()).hasRole()).isFalse()
    }

    @Test
    fun shouldHaveRoleWithRoles() {
        assertThat(Roles(setOf(Role.ADMIN)).hasRole()).isTrue()
    }

    @Test
    fun shouldNotHaveNotAffectedRole() {
        assertThat(Roles(setOf(Role.ADMIN)).hasRole(Role.USER)).isFalse()
    }

    @Test
    fun shouldHaveAffectedRole() {
        assertThat(Roles(setOf(Role.ADMIN)).hasRole(Role.ADMIN)).isTrue()
    }

    @Test
    fun shouldStreamRoles() {
        assertThat(Roles(setOf(Role.ADMIN)).stream()).containsExactly(Role.ADMIN)
    }

    @Test
    fun shouldGetRoles() {
        assertThat(Roles(setOf(Role.ADMIN)).roles).containsExactly(Role.ADMIN)
    }

    @Test
    fun shouldNotHaveAnyRoleWithoutRoles() {
        assertThat(Roles(setOf()).hasAnyRole()).isFalse()
    }

    @Test
    fun shouldHaveAnyRoleWithRoles() {
        assertThat(Roles(setOf(Role.ADMIN, Role.USER)).hasAnyRole(Role.ADMIN)).isTrue()
    }

    @Test
    fun shouldNotHaveAnyRoleWithRoles() {
        assertThat(Roles(setOf(Role.ADMIN, Role.USER)).hasAnyRole()).isFalse()
    }
}
