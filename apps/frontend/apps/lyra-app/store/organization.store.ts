import { useNuxtApp } from '#app';
import { ref } from 'vue';
import { defineStore } from 'pinia';
import type { IOrganization } from '~/domain/organization';

export const useOrganizationStore = defineStore('organization', () => {
  const { $api } = useNuxtApp();
  const organizationList = ref<IOrganization[]>([]);

  const fetchOrganizationList = async () => {
    try {
      $api.organization
        .fetchAll()
        .then((response) => {
          organizationList.value = response.data;
          console.log('CHECK ORGANIZATION', organizationList.value);
        })
        .catch((error) => {
          console.error(error);
        });
    } catch (error) {
      console.error(`fetchOrganizationList error:${error}`);
    }
  };

  return { organizationList, fetchOrganizationList };
});
