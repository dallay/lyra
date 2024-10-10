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
            <PreviewButton />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {Button} from "~/components/ui/button";
import PostEditorSteps from "./PostEditorSteps.vue";
import PreviewButton from "./preview/PreviewButton.vue";

interface EditorTopBarProps {
  postId: string;
}
const props = defineProps<EditorTopBarProps>();

</script>

<style scoped>
/* Add any necessary styles here */
</style>
