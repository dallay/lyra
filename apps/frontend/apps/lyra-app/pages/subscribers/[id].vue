<script setup lang="ts">;
import { useRoute } from '#app';
import {SubscriberId} from "@lyra/domain";
const route = useRoute();
const id = route.params.id;
const idValue = typeof id === 'string' ? id : id[0];
const subscriberId = SubscriberId.create(idValue);
</script>

<template>
  <div>
    <h1>Subscriber Detail</h1>
    <p>Subscriber ID: {{ subscriberId }}</p>
  </div>
</template>

<style scoped lang="scss">

</style>
