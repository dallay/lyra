import { useNuxtApp } from "#app";
import { ref } from "vue";
import { defineStore } from "pinia";
import { useWorkspaceStore } from "~/store/workspace.store";
import { OrganizationId } from "~/domain/organization";
import type { TagRequest } from "~/domain/tag";
import type { TagResponse } from "~/domain/tag/TagResponse"; // Adjust the path if necessary
import TagId from "~/domain/tag/TagId";

export const useTagStore = defineStore("tag", () => {
  const { $api } = useNuxtApp();
  const tags = ref<TagResponse[]>([]);

  const workspaceStore = useWorkspaceStore();

  const createTag = async (organizationId: OrganizationId, request: TagRequest) => {
    try {
      const tagId = TagId.random();
      const orgId = organizationId || workspaceStore.getCurrentOrganizationId();
      await $api.tag.createTag(orgId, tagId, request);
    } catch (error) {
      console.error(`createTag error:${error}`);
    }
  };

  const updateTag = async (organizationId: OrganizationId, request: TagRequest) => {
    try {
      const orgId = organizationId || workspaceStore.getCurrentOrganizationId();
      // await $api.tag.updateTag(orgId, tagId, request);
      alert(`🧪 TODO: updateTag: ${orgId}, ${request}`);
    } catch (error) {
      console.error(`updateTag error:${error}`);
    }
  }

  return {
    tags,
    createTag,
    updateTag,
  };
})
