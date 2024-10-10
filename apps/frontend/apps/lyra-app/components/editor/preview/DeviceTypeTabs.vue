<template>
  <Tabs v-model="deviceType" class="w-full sm:w-auto">
    <TabsList>
      <TabsTrigger value="desktop">Desktop</TabsTrigger>
      <TabsTrigger value="mobile">Mobile</TabsTrigger>
    </TabsList>
  </Tabs>
</template>

<script setup lang="ts">
import { Tabs, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { defineModel } from 'vue';

const deviceType = defineModel('deviceType', {
  type: String,
  default: 'desktop',
});
</script>
