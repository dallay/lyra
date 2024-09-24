<script setup lang="ts">
import { ref, computed, type Ref, onMounted } from 'vue';
import { MoreVertical, Plus } from 'lucide-vue-next';
import { MagnifyingGlassIcon } from '@radix-icons/vue';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Card, CardContent, CardHeader } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import {
  Sheet,
  SheetContent,
  SheetDescription,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from '@/components/ui/sheet';
import { TagColors, Tag } from '~/domain/tag';
import { useTagStore } from '~/store/tag.store';
import { useWorkspaceStore } from '~/store/workspace.store';
import { storeToRefs } from 'pinia';
import TagId from '~/domain/tag/TagId';
const tagStore = useTagStore();
const { tags } = storeToRefs(tagStore);
const { fetchTags, deleteTag } = tagStore;
const workspaceStore = useWorkspaceStore();

const openSheet = ref(false);
const currentTag: Ref<Tag | null> = ref(null);

const alphabeticalTags = computed(() => {
  return tags.value
    .sort((a, b) => a.name.localeCompare(b.name))
    .map((tag) => Tag.fromResponse(tag));
});

const createTag = () => {
  currentTag.value = null;
};

const editTag = (id: string) => {
  const tagResponse = tags.value.find((tag) => tag.id === id);
  if (tagResponse) {
    currentTag.value = Tag.fromResponse(tagResponse) || null;
  }
  openSheet.value = true;
};

const deleteTagById = async (tag: Tag) => {
  const tagId = TagId.create(tag.id);
  const organizationId = workspaceStore.getCurrentOrganizationId();
  if (!organizationId) return;

  await deleteTag(organizationId, tagId);
  await fetchOrganizationTags();
};

const close = () => {
  openSheet.value = false;
};
const fetchOrganizationTags = async () => {
  const organizationId = workspaceStore.getCurrentOrganizationId();
  if (!organizationId) return;
  await fetchTags(organizationId);
};

onMounted(async () => {
  await fetchOrganizationTags();
});
</script>

<template>
  <div class="container mx-auto p-4 space-y-4">
    <Sheet v-model:open="openSheet">
      <Card>
        <CardHeader class="flex justify-between space-x-2">
          <div>
            <h1 class="text-2xl font-bold">Subscriber Tags</h1>
            <p class="text-gray-600">Custom tags for your subscribers</p>
          </div>
          <div class="flex justify-end items-center space-x-2">
            <div class="relative w-full max-w-sm items-center">
              <Input
                id="search"
                type="text"
                placeholder="Search..."
                class="pl-10"
              />
              <span
                class="absolute start-0 inset-y-0 flex items-center justify-center px-2"
              >
                <MagnifyingGlassIcon class="size-6 text-muted-foreground" />
              </span>
            </div>
            <SheetTrigger>
              <Button variant="default" @click="createTag">
                <Plus class="mr-1 h-4 w-4" />
                Create new form
              </Button>
            </SheetTrigger>
          </div>
        </CardHeader>
        <CardContent>
          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
            <Card v-for="tag in alphabeticalTags" :key="tag.id">
              <CardContent class="px-4">
                <div
                  class="flex justify-between items-center space-x-2 space-y-2"
                >
                  <Badge
                    :class="{
                      'bg-red-100 text-red-800 border-red-200':
                        tag.color === TagColors.Red,
                      'bg-blue-100 text-blue-800 border-blue-200':
                        tag.color === TagColors.Blue,
                      'bg-purple-100 text-purple-800 border-purple-200':
                        tag.color === TagColors.Purple,
                      'bg-pink-100 text-pink-800 border-pink-200':
                        tag.color === TagColors.Pink,
                      'bg-yellow-100 text-yellow-800 border-yellow-200':
                        tag.color === TagColors.Yellow,
                      'bg-gray-100 text-gray-800 border-gray-200':
                        tag.color === TagColors.Default,
                    }"
                  >
                    {{ tag.name }}
                  </Badge>
                  <DropdownMenu>
                    <DropdownMenuTrigger as-child>
                      <Button variant="ghost" size="icon">
                        <MoreVertical class="h-4 w-4" />
                      </Button>
                    </DropdownMenuTrigger>
                    <DropdownMenuContent align="end">
                      <DropdownMenuItem @click="editTag(tag.id)">
                        Edit
                      </DropdownMenuItem>
                      <DropdownMenuItem @click="deleteTagById(tag)">
                        Delete
                      </DropdownMenuItem>
                    </DropdownMenuContent>
                  </DropdownMenu>
                </div>
                <p class="text-sm text-gray-600">
                  {{ tag.subscriberCount }} subscribers
                </p>
              </CardContent>
            </Card>
          </div>
        </CardContent>
      </Card>
      <SheetContent class="p-4 w-screen max-w-xl overflow-y-scroll">
        <SheetHeader class="mb-8">
          <SheetTitle>{{ currentTag ? "Edit" : "Create" }} a Tag</SheetTitle>
          <SheetDescription>
            {{ currentTag ? "Edit" : "Create" }} a tag for your subscribers.
          </SheetDescription>
        </SheetHeader>
        <SubscriberTagForm :currentTag="currentTag" @close="close" @update="fetchOrganizationTags"/>
      </SheetContent>
    </Sheet>
  </div>
</template>
