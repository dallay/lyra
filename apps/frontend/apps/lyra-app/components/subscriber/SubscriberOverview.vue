<script setup lang="ts">
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table'
import { Button } from "~/components/ui/button";
import { Badge } from '@/components/ui/badge'
import { cn } from '@/lib/utils'
import { DateFormatter, getLocalTimeZone, today } from '@internationalized/date'
import {type Subscriber, SubscriberStatus} from "@lyra/domain";
import { useSubscriberStore } from "~/store/subscriber.store";
import {storeToRefs, onMounted} from "#imports";

const store = useSubscriberStore();
const { subscribers } = storeToRefs(store);
const { fetchAllSubscriber } = store;
const userLocale = navigator.language || 'en-US';
const df = new DateFormatter(userLocale, {
  dateStyle: 'long',
})
const localTimeZone = getLocalTimeZone();

const formatDate = (date: Date | string | undefined) => {
  if (!date) return '';
  return df.format(new Date(date));
}

onMounted(async () => {
  await fetchAllSubscriber();
})
</script>

<template>
  <div class="flex justify-between mb-4">
    <h1 class="text-3xl font-bold">Subscribers</h1>
    <Button>
      <Icon name="ph:users" color="black" class="h-4 w-4 mr-1"/>
      Import Subscribers
    </Button>
  </div>
  <Table>
    <TableCaption>A list of your subscribers.</TableCaption>
    <TableHeader>
      <TableRow>
        <TableHead class="w-5/12">
          Email
        </TableHead>
        <TableHead>Status</TableHead>
        <TableHead>Attributes</TableHead>
        <TableHead class="text-right">
          Subscribe On
        </TableHead>
      </TableRow>
    </TableHeader>
    <TableBody>
      <TableRow v-for="subscribe in subscribers" :key="subscribe.id">
        <TableCell class="font-medium">
         <NuxtLink  class="text-primary hover:!text-blue-700" :to="`/subscribers/${subscribe.id}`">
           {{ subscribe.email }}
          </NuxtLink>
        </TableCell>
        <TableCell>
          <Badge variant="outline"
                  :class="cn({
                    'bg-green-300 text-green-800': subscribe.status === SubscriberStatus.ENABLED,
                    'bg-yellow-300 text-yellow-800': subscribe.status === SubscriberStatus.DISABLED,
                    'bg-red-300 text-red-800': subscribe.status === SubscriberStatus.BLOCKLISTED
                  })"
          >
            {{ subscribe.status }}
          </Badge>
          </TableCell>
        <TableCell>{{ subscribe.attributes }}</TableCell>
        <TableCell class="text-right">
          {{ formatDate(subscribe.createdAt) }}
        </TableCell>
      </TableRow>
    </TableBody>
  </Table>
</template>
