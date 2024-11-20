<script setup lang="ts">
import { Icon } from "@/components/ui/icon";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import MenuButton from "~/components/editor/extensions/menus/MenuButton.vue";
import MenuButtonGroup from "~/components/editor/extensions/menus/MenuButtonGroup.vue";
import { computed, defineProps } from "vue";
import { useTextMenuState } from "../utils/useTextMenuStates";
import type { MenuProps } from "../../types";

const props = defineProps<MenuProps>();
const { menuGroups } = useTextMenuState(props.editor);

const activeItem = computed(() =>
  menuGroups.value.headings?.find(
    (option) =>
      (typeof option.isActive === "function" && option.isActive()) ||
      option.isActive
  )
);
</script>

<template>
  <Popover>
    <PopoverTrigger asChild>
      <MenuButton
        :icon="activeItem?.icon || 'Pilcrow'"
        :label="activeItem?.label || 'Paragraph'"
      >
        <template #icon-after>
          <Icon name="ChevronDown" class="w-2 h-2" />
        </template>
      </MenuButton>
    </PopoverTrigger>
    <PopoverContent
      side="top"
      :sideOffset="8"
      class="flex flex-col gap-0.5 p-0.5 w-full"
    >
      <MenuButtonGroup :buttons="menuGroups.headings" />
    </PopoverContent>
  </Popover>
</template>
