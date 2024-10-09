<script setup lang="ts">
import EditorTopBar from "~/components/editor/EditorTopBar.vue";
import { useRoute } from "vue-router";
import PostsEditor from "~/components/editor/PostsEditor.vue";
const route = useRoute();
const postId = route.params.id;
definePageMeta({
  layout: false,
});
</script>

<template>
  <div>
    <NuxtLayout name="default">
      <template #header>
        <EditorTopBar />
      </template>

      <client-only>
        <posts-editor :content="`This is a super content 🟢`" />
      </client-only>
    </NuxtLayout>
  </div>
</template>
