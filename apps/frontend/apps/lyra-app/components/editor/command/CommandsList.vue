<template>
  <Card
    class="w-full max-w-2xl sm:max-w-3xl md:max-w-4xl lg:max-w-5xl xl:max-w-6xl mx-auto min-w-full"
  >
    <ScrollArea class="p-1 w-full">
      <div class="space-y-2">
        <div v-for="(category, categoryIndex) in items" :key="categoryIndex">
          <h2 class="mb-1 px-1 text-base font-semibold tracking-tight">
            {{ category.category }}
          </h2>
          <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-2">
            <Button
              v-for="(item, itemIndex) in category.items"
              :key="`${item.title}-${itemIndex}`"
              :variant="selectedIndex.category === categoryIndex && selectedIndex.item === itemIndex ? 'default' : 'ghost'"
              :class="[
                'flex justify-start items-center w-full h-full px-2 py-1'
              ]"
              @click="selectItem(categoryIndex, itemIndex)"
            >
              <template v-if="item.icon">
                <Suspense>
                  <Icon
                      v-if="typeof item.icon === 'string'"
                      :name="item.icon"
                      class="mr-1 size-4 text-muted-foreground"
                    />
                    <component
                      v-else
                      :is="item.icon"
                      class="mr-1 size-4 text-muted-foreground"
                    />
                  <template #fallback>
                    <LoaderCircle
                      class="mr-1 size-4 text-muted-foreground animate-spin"
                    />
                  </template>
                </Suspense>
              </template>
              {{ item.title }}
            </Button>
          </div>
        </div>
      </div>
    </ScrollArea>
  </Card>
</template>

<script setup lang="ts">
import { ref, watch, defineProps, type Component, Suspense } from "vue";
import { Card } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { ScrollArea } from "@/components/ui/scroll-area";
import type { Editor } from "@tiptap/vue-3";
import { LoaderCircle } from "lucide-vue-next";

interface CommandItem {
  title: string;
  description?: string;
  icon?: Component | string;
  command: (props: { editor: Editor; range: Range }) => void;
}

interface CommandCategory {
  category: string;
  items: CommandItem[];
}

interface CommandListProps {
  items: CommandCategory[];
  command: (item: CommandItem) => void;
}

const props = defineProps<CommandListProps>();

const selectedIndex = ref({ category: 0, item: 0 });

watch(
  () => props.items,
  () => {
    selectedIndex.value = { category: 0, item: 0 };
  }
);

const selectItem = (categoryIndex: number, itemIndex: number) => {
  const item = props.items[categoryIndex].items[itemIndex];
  if (item) {
    props.command(item);
  }
};

const downHandler = () => {
  const { category, item } = selectedIndex.value;
  const currentCategory = props.items[category];
  if (item < currentCategory.items.length - 1) {
    selectedIndex.value.item++;
  } else if (category < props.items.length - 1) {
    selectedIndex.value.category++;
    selectedIndex.value.item = 0;
  }
};

const upHandler = () => {
  const { category, item } = selectedIndex.value;
  if (item > 0) {
    selectedIndex.value.item--;
  } else if (category > 0) {
    selectedIndex.value.category--;
    selectedIndex.value.item =
      props.items[selectedIndex.value.category].items.length - 1;
  }
};

const rightHandler = () => {
  const { category, item } = selectedIndex.value;
  const currentCategory = props.items[category];
  if (item < currentCategory.items.length - 1) {
    selectedIndex.value.item++;
  } else if (category < props.items.length - 1) {
    selectedIndex.value.category++;
    selectedIndex.value.item = 0;
  }
};

const leftHandler = () => {
  const { category, item } = selectedIndex.value;
  if (item > 0) {
    selectedIndex.value.item--;
  } else if (category > 0) {
    selectedIndex.value.category--;
    selectedIndex.value.item =
      props.items[selectedIndex.value.category].items.length - 1;
  }
};

const enterHandler = () => {
  const { category, item } = selectedIndex.value;
  selectItem(category, item);
};

const onKeyDown = (event: KeyboardEvent): boolean => {
  if (event.key === "ArrowDown") {
    downHandler();
    return true;
  } else if (event.key === "ArrowUp") {
    upHandler();
    return true;
  } else if (event.key === "ArrowRight") {
    rightHandler();
    return true;
  } else if (event.key === "ArrowLeft") {
    leftHandler();
    return true;
  } else if (event.key === "Enter") {
    enterHandler();
    return true;
  }
  return false;
};

defineExpose({ onKeyDown });
</script>
