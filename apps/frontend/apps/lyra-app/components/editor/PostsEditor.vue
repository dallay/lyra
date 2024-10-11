<template>
  <div class="mx-auto p-4">
    <StatusBar />
    <div class="flex justify-center">
      <div class="w-full max-w-3xl mx-auto">
        <AddOptions />
        <TitleEditor />
        <AuthorTags />
        <ContentEditor />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import StatusBar from './StatusBar.vue';
import AddOptions from './AddOptions.vue';
import TitleEditor from './TitleEditor.vue';
import AuthorTags from './AuthorTags.vue';
import ContentEditor from './ContentEditor.vue';
</script>
