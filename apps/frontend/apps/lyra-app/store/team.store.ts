import {useNuxtApp} from "#app";
import {ref} from "vue";
import {defineStore} from "pinia";
import type {ITeam, OrganizationId} from "@lyra/domain";
import type {CreateTeamRequest} from "~/repository/modules/team.module";

export const useTeamStore = defineStore("team", () => {
  const {$api} = useNuxtApp();
  const teamList = ref<ITeam[]>([]);

  const fetchTeamList = async (id: OrganizationId) => {
    try {
      $api.team.fetchAll(id).then(response => {
        teamList.value = response.data;
      }).catch(error => {
        console.error(error)
      })
    } catch (error) {
      console.error(`fetchTeamList error:${error}`);
    }
  };

  const createTeam = async ({organizationId, teamId, name}: CreateTeamRequest) => {
    try {
      await $api.team.createTeam({organizationId,teamId, name})
    } catch (error) {
      console.error(`createTeam error:${error}`);
    }
  };

  return { teamList, fetchTeamList, createTeam};
});
