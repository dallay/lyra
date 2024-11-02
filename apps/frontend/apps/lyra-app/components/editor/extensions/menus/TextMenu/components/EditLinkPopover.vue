<script lang="ts" setup>
import { ref, defineProps } from 'vue';
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from '@/components/ui/popover'
import MenuButton from '@/components/editor/extensions/menus/MenuButton.vue';
import LinkEditorPanel from '@/components/editor/extensions/menus/LinkMenu/LinkEditorPanel.vue';

export type EditLinkPopoverProps = {
  onSetLink: (link: string, openInNewTab?: boolean) => void,
  onOpen?: () => void,
  onCancel?: () => void,
}
const props = defineProps<EditLinkPopoverProps>();

const menuOpen = ref(false);

const confirmEditLink = (url: string) => {
  props.onSetLink(url);
}

const cancelEditLink = () => {
  if (props.onCancel) {
    props.onCancel();
  }
  menuOpen.value = false;
}

const openEditLinkModal = () => {
  if (props.onOpen) {
    props.onOpen();
  }
}
</script>

<template>
  <Popover v-model:open="menuOpen">
    <PopoverTrigger>
      <MenuButton icon="Link" label="Link" :onClick="openEditLinkModal" />
    </PopoverTrigger>
    <PopoverContent class="w-96 p-0">
      <LinkEditorPanel
        @confirm="confirmEditLink"
        @cancel="cancelEditLink"
      />
    </PopoverContent>
  </Popover>
</template>
