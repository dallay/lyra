<script setup lang="ts">
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs'
import DataTable from '~/components/subscriber/DataTable.vue'
import SubscriberTags from './tag/SubscriberTags.vue';
import { columns } from '~/components/subscriber/columns'
import {Button} from "~/components/ui/button";
import { useSubscriberStore } from "~/store/subscriber.store";
import { useSubscriberFilterStore } from '~/store/subscriber.filter.store';
import {storeToRefs, onMounted} from "#imports";

const store = useSubscriberStore();
const { subscribers } = storeToRefs(store);
const { fetchAllSubscriber, clearCursor } = store;
const filterStore = useSubscriberFilterStore();
const {resetFilterDateRange, resetSubscriberFilterOptions, cleanSubscriberCursor} = filterStore;

const importSubscriber = () => {
  alert('Importing subscribers');
}

onMounted(async () => {
  clearCursor();
  resetFilterDateRange();
  resetSubscriberFilterOptions();
  await cleanSubscriberCursor();
 await fetchAllSubscriber();
})
</script>

<template>
  <div class="hidden h-full flex-1 flex-col space-y-8 p-8 md:flex">
    <div class="flex items-center justify-between space-y-2">
      <div>
        <h2 class="text-2xl font-bold tracking-tight">
          Subscribers
        </h2>
        <p class="text-muted-foreground">
          Manage your subscribers here. You can import subscribers from a CSV file.
        </p>
      </div>
      <Button @click="importSubscriber">
        <Icon name="ph:users" color="black" class="h-4 w-4 mr-1"/>
        Import Subscribers
      </Button>
    </div>
    <Tabs default-value="subscribers" class="w-full">
      <TabsList>
        <TabsTrigger value="subscribers">
          Subscribers
        </TabsTrigger>
        <TabsTrigger value="tags">
          Tags
        </TabsTrigger>
      </TabsList>
      <TabsContent value="subscribers">
        <DataTable :data="subscribers" :columns="columns" />
      </TabsContent>
      <TabsContent value="tags">
        <SubscriberTags />
      </TabsContent>
    </Tabs>
  </div>
</template>
