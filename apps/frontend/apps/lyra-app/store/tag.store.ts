import { useNuxtApp } from '#app';
import { ref } from 'vue';
import { defineStore } from 'pinia';
import { useWorkspaceStore } from '~/store/workspace.store';
import { OrganizationId } from '~/domain/organization';
import type { CreateTagRequest, UpdateTagRequest } from '~/domain/tag';
import type { TagResponse } from '~/domain/tag/TagResponse'; // Adjust the path if necessary
import TagId from '~/domain/tag/TagId';

export const useTagStore = defineStore('tag', () => {
  const { $api } = useNuxtApp();
  const tags = ref<TagResponse[]>([]);

  const workspaceStore = useWorkspaceStore();

  const createTag = async (organizationId: OrganizationId, request: CreateTagRequest) => {
    try {
      const tagId = TagId.random();
      const orgId = organizationId || workspaceStore.getCurrentOrganizationId();
      await $api.tag.createTag(orgId, tagId, request);
    } catch (error) {
      console.error(`createTag error:${error}`);
    }
  };

  const updateTag = async (
    organizationId: OrganizationId,
    tagId: TagId,
    request: UpdateTagRequest,
  ) => {
    try {
      const orgId = organizationId || workspaceStore.getCurrentOrganizationId();
      await $api.tag.updateTag(orgId, tagId, request);
    } catch (error) {
      console.error(`updateTag error:${error}`);
    }
  };

  const fetchTags = async (organizationId: OrganizationId) => {
    try {
      const orgId = organizationId || workspaceStore.getCurrentOrganizationId();
      const response = await $api.tag.fetchAllTags(orgId);
      tags.value = response.data;
    } catch (error) {
      console.error(`fetchTags error:${error}`);
    }
  };

  const deleteTag = async (organizationId: OrganizationId, tagId: TagId) => {
    try {
      const orgId = organizationId || workspaceStore.getCurrentOrganizationId();
      await $api.tag.deleteTag(orgId, tagId);
    } catch (error) {
      console.error(`deleteTag error:${error}`);
    }
  };

  return {
    tags,
    createTag,
    updateTag,
    fetchTags,
    deleteTag,
  };
});
