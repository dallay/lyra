<template>
  <Card class="w-[32rem]">
    <div v-for="(group, groupIndex) in items" :key="groupIndex" class="rounded-md grid flex-wrap gap-2 grid-cols-3 p-2">
          <span class="text-xs col-span-full my-0.5 mx-2">
            {{ group.title }}
          </span>
          <Button
              v-for="(command, commandIndex) in group.commands"
              :key="`${command.name}-${commandIndex}`"
              :variant="selectedIndex.group === groupIndex && selectedIndex.command === commandIndex ? 'default' : 'ghost'"
              :class="[
                'flex justify-start items-center w-full h-full px-2 py-1'
              ]"
              @click="selectItem(groupIndex, commandIndex)"
            >
              <template v-if="command.iconName">
                <Icon :name="command.iconName" class="mr-1 size-4 text-muted-foreground" />
              </template>
              {{ command.label }}
            </Button>
        </div>
  </Card>
</template>

<script setup lang="ts">
import { ref, watch, defineProps, type Component, Suspense } from 'vue';
import { Card } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Icon } from '@/components/ui/icon';
import type { Command, Group } from './types';

interface CommandListProps {
  items: Group[];
  command: (item: Command) => void;
}

const props = defineProps<CommandListProps>();

const selectedIndex = ref({ group: 0, command: 0 });

watch(
  () => props.items,
  () => {
    selectedIndex.value = { group: 0, command: 0 };
  },
);

const selectItem = (groupIndex: number, commandIndex: number) => {
  const item = props.items[groupIndex].commands[commandIndex];
  if (item) {
    props.command(item);
  }
};

const getColumns = () => {
  if (window.innerWidth >= 768) {
    return 3; // md and above
  } else if (window.innerWidth >= 640) {
    return 2; // sm
  } else {
    return 1; // default
  }
};

const downHandler = () => {
  const { group, command } = selectedIndex.value;
  const currentGroup = props.items[group];
  const columns = getColumns();
  const nextItem = command + columns;
  if (nextItem < currentGroup.commands.length) {
    selectedIndex.value.command = nextItem;
  } else if (group < props.items.length - 1) {
    selectedIndex.value.group++;
    selectedIndex.value.command = 0;
  }
};

const upHandler = () => {
  const { group, command } = selectedIndex.value;
  const columns = getColumns();
  const prevCmd = command - columns;
  if (prevCmd >= 0) {
    selectedIndex.value.command = prevCmd;
  } else if (group > 0) {
    selectedIndex.value.group--;
    selectedIndex.value.command = props.items[selectedIndex.value.group].commands.length - 1;
  }
};

const rightHandler = () => {
  const { group, command } = selectedIndex.value;
  const currentGroup = props.items[group];
  if (command < currentGroup.commands.length - 1) {
    selectedIndex.value.command++;
  } else if (group < props.items.length - 1) {
    selectedIndex.value.group++;
    selectedIndex.value.command = 0;
  }
};

const leftHandler = () => {
  const { group, command } = selectedIndex.value;
  if (command > 0) {
    selectedIndex.value.command--;
  } else if (group > 0) {
    selectedIndex.value.group--;
    selectedIndex.value.command = props.items[selectedIndex.value.group].commands.length - 1;
  }
};

const enterHandler = () => {
  const { group, command } = selectedIndex.value;
  selectItem(group, command);
};

const onKeyDown = (event: KeyboardEvent): boolean => {
  if (event.key === 'ArrowDown') {
    downHandler();
    return true;
  } else if (event.key === 'ArrowUp') {
    upHandler();
    return true;
  } else if (event.key === 'ArrowRight') {
    rightHandler();
    return true;
  } else if (event.key === 'ArrowLeft') {
    leftHandler();
    return true;
  } else if (event.key === 'Enter') {
    enterHandler();
    return true;
  }
  return false;
};

defineExpose({ onKeyDown });
</script>
