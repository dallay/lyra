<template>
  <Tabs v-model="viewType" class="w-full sm:w-auto">
    <TabsList>
      <TabsTrigger value="web">Web</TabsTrigger>
      <TabsTrigger value="email">Email</TabsTrigger>
    </TabsList>
  </Tabs>
</template>

<script setup lang="ts">
import { Tabs, TabsList, TabsTrigger } from '@/components/ui/tabs'
import { defineModel } from 'vue'

const viewType = defineModel('viewType', {
  type: String,
  default: 'web'
})
</script>
