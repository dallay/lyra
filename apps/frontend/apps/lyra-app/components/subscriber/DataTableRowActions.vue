<script setup lang="ts">
import type { Row } from '@tanstack/vue-table';
import { computed } from 'vue';
import { type Subscriber, subscriberSchema } from '@/domain/subscriber';
import { DotsHorizontalIcon } from '@radix-icons/vue';
import { useRouter } from '#app';

import { Button } from '@/components/ui/button';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';

const router = useRouter();

interface DataTableRowActionsProps {
  row: Row<Subscriber>;
}
const props = defineProps<DataTableRowActionsProps>();

const subscriber = computed(() => subscriberSchema.parse(props.row.original));

const navigateToSubscriber = (action: 'view' | 'edit') => {
  if (action === 'view') {
    router.push(`/subscribers/${subscriber.value.id}`);
  } else {
    router.push(`/subscribers/${subscriber.value.id}?edit=true`);
  }
};
// const shortcutKey = ref('')

// onMounted(() => {
//   const userAgent = navigator.userAgent;

//   if (userAgent.indexOf('Mac') !== -1) {
//     shortcutKey.value= '⌘ + ⌫';
//   } else if (userAgent.indexOf('Win') !== -1) {
//     shortcutKey.value= 'Ctrl + Del';
//   } else if (userAgent.indexOf('Linux') !== -1) {
//     shortcutKey.value= 'Ctrl + Del';
//   } else {
//     shortcutKey.value= 'Del';
//   }
// })
</script>

<template>
  <DropdownMenu>
    <DropdownMenuTrigger as-child>
      <Button
        variant="ghost"
        class="flex h-8 w-8 p-0 data-[state=open]:bg-muted"
      >
        <DotsHorizontalIcon class="h-4 w-4" />
        <span class="sr-only">Open menu</span>
      </Button>
    </DropdownMenuTrigger>
    <DropdownMenuContent align="end" class="w-[160px]">
      <DropdownMenuItem @click="navigateToSubscriber('view')">View</DropdownMenuItem>
      <DropdownMenuItem @click="navigateToSubscriber('edit')">Edit</DropdownMenuItem>
      <!-- <DropdownMenuSeparator /> -->
      <!-- <DropdownMenuItem>
        Delete
        <DropdownMenuShortcut>{{shortcutKey}}</DropdownMenuShortcut>
      </DropdownMenuItem> -->
    </DropdownMenuContent>
  </DropdownMenu>
</template>
