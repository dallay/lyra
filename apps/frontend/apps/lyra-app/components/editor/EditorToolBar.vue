<template>
  <div class="z-50 pb-px">
    <div
      class="screen:flex flex-col justify-between items-center print:hidden"
      data-testid="editor-top-bar"
    >
      <div
        class="w-full h-full sm:flex hidden flex-row justify-center items-between"
      >
        <div
          class="w-full py-2 px-6 flex flex-row space-x-4 justify-between items-end"
        >
          <Button variant="outline" size="sm" class="flex items-center">
            <Icon name="lucide:arrow-up-left" class="w-4 h-4" />
          </Button>
          <div class="flex items-center w-1/2">
            <PostEditorSteps />
          </div>
          <div class="flex items-center space-x-4">
            <Button size="sm" class="flex items-center"> Next </Button>
            <ActionSplitButton variant="outline">
              <router-link to="/preview" class="flex items-center space-x-2">
                <Icon name="lucide:expand" class="mr-2 size-4" />
                Preview
              </router-link>

              <template #options>
                <EditorPreviewOptions />
              </template>
            </ActionSplitButton>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { Button } from '~/components/ui/button';
import PostEditorSteps from './PostEditorSteps.vue';
import EditorPreviewOptions from './preview/EditorPreviewOptions.vue';
import { ActionSplitButton } from '~/components/ui/button/action-split-button';
</script>

<style scoped>
/* Add any necessary styles here */
</style>
