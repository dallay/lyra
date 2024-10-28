import { ref } from 'vue';
import { defineStore } from 'pinia';
import { useAuthStore } from '~/store/auth.store';
import { useRouter } from '#imports';
import { useNuxtApp } from '#app';
import { OrganizationId, type OrganizationTeamMember } from '~/domain/organization';
import type { DataResponse } from '@lyra/shared';

export type WorkspaceTeam = {
  teamId: string;
  teamName: string;
  userId: string;
  role: string;
};

export interface GroupedWorkspace {
  organizationId: string;
  organizationName: string;
  organizationOwnerId: string;
  teams: Array<WorkspaceTeam>;
}

async function selectFirstTeam(groupedWorkspace: GroupedWorkspace[]): Promise<WorkspaceTeam> {
  if (groupedWorkspace.length > 0 && groupedWorkspace[0].teams.length > 0) {
    return groupedWorkspace[0].teams[0];
  }
  return {
    teamId: 'cd5bc0cb-c2c6-4702-94d2-88480e6c56f4',
    teamName: 'Default Team',
    userId: 'cd5bc0cb-c2c6-4702-94d2-88480e6c56f4',
    role: 'EDITOR',
  };
}

export const useWorkspaceStore = defineStore('workspace', () => {
  const auth = useAuthStore();
  const groupedWorkspace = ref<Array<GroupedWorkspace>>([]);
  const selectedTeam = ref<WorkspaceTeam | null>(null);

  const fetchWorkspaces = async () => {
    try {
      const user = await auth.getUser();
      const router = useRouter();

      if (!user) {
        await router.push('/login');
        return;
      }
      const { $api } = useNuxtApp();

      const response: DataResponse<OrganizationTeamMember> = await $api.teamMember.fetchAll();
      const organizationTeamMembers = response.data;

      const groupedMap = organizationTeamMembers.reduce<Map<string, GroupedWorkspace>>(
        (acc, member) => {
          const {
            organizationId,
            organizationName,
            organizationOwnerId,
            teamId,
            teamName,
            userId,
            role,
          } = member;
          if (!acc.has(organizationId)) {
            acc.set(organizationId, {
              organizationId,
              organizationName,
              organizationOwnerId,
              teams: [],
            });
          }
          acc.get(organizationId)?.teams.push({ role, teamName, teamId, userId });
          return acc;
        },
        new Map(),
      );

      groupedWorkspace.value = Array.from(groupedMap.values());

      // Set default selected team
      if (groupedWorkspace.value.length > 0 && groupedWorkspace.value[0].teams.length > 0) {
        selectedTeam.value = await selectFirstTeam(groupedWorkspace.value);
      }

      return groupedWorkspace.value;
    } catch (error) {
      console.error('fetchWorkspaces error:', error);
    }
  };

  const setSelectedTeam = (team: WorkspaceTeam) => {
    selectedTeam.value = team;
  };

  const getWorkspaceByTeamId = (teamId: string): GroupedWorkspace | undefined => {
    return groupedWorkspace.value.find((workspace) =>
      workspace.teams.some((team) => team.teamId === teamId),
    );
  };

  const getCurrentWorkspace = () => {
    return getWorkspaceByTeamId(selectedTeam.value?.teamId ?? '');
  };

  const getCurrentOrganizationId = () => {
    const currentOrgId = getCurrentWorkspace()?.organizationId;
    return currentOrgId ? OrganizationId.create(currentOrgId) : undefined;
  };
  return {
    groupedWorkspace,
    fetchWorkspaces,
    selectedTeam,
    setSelectedTeam,
    getWorkspaceByTeamId,
    getCurrentWorkspace,
    getCurrentOrganizationId,
  };
});
