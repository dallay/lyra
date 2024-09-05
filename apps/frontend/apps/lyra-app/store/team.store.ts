import {useNuxtApp} from "#app";
import {ref} from "vue";
import {defineStore} from "pinia";
import type {CreateTeamRequest} from "~/repository/modules/team.module";
import type {ITeam} from "~/domain/team";
import {OrganizationId} from "~/domain/organization";

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
