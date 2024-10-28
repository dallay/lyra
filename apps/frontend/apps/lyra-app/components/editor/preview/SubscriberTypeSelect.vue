<template>
  <Select v-model="subscriberType">
    <SelectTrigger class="w-[180px]">
      <SelectValue placeholder="Subscriber type" />
    </SelectTrigger>
    <SelectContent>
      <SelectItem value="free">Free subscribers</SelectItem>
      <SelectItem value="paid">Paid subscribers</SelectItem>
    </SelectContent>
  </Select>
</template>

<script setup lang="ts">
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import { defineModel } from 'vue';

const subscriberType = defineModel('subscriberType', {
  type: String,
  default: 'free',
});
</script>
