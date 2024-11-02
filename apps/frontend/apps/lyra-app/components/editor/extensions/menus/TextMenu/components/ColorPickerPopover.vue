<script lang="ts" setup>
import { ref, defineProps } from 'vue';
import { Popover, PopoverContent, PopoverTrigger } from '@/components/ui/popover';
import { ColorPicker } from '~/components/ui/color-picker';
import MenuButton from '@/components/editor/extensions/menus/MenuButton.vue';

interface ColorPickerPopoverProps {
	color: string;
	onChange: (color: string) => void;
	onClear: () => void;
	isActive: boolean;
	icon: string;
	label: string;
}

const props = defineProps<ColorPickerPopoverProps>();
const menuOpen = ref(false);
</script>

<template>
  <Popover v-model:open="menuOpen" asChild>
    <PopoverTrigger>
      <MenuButton :icon="props.icon" :label="props.label" :isActive="props.isActive" />
    </PopoverTrigger>
    <PopoverContent class="w-full h-full p-0" side="top" :sideOffset="8" asChild>
      <ColorPicker :color="props.color" @update:color="props.onChange" />
    </PopoverContent>
  </Popover>
</template>
