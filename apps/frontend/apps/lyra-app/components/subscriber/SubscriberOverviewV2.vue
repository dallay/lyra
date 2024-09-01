<script setup lang="ts">
import subscribers from './data/subscriber.json'
import DataTable from '~/components/subscriber/DataTable.vue'
import { columns } from '~/components/subscriber/columns'
import {Button} from "~/components/ui/button";
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
      <Button>
        <Icon name="ph:users" color="black" class="h-4 w-4 mr-1"/>
        Import Subscribers
      </Button>
    </div>
    <DataTable :data="subscribers" :columns="columns" />
  </div>
</template>
