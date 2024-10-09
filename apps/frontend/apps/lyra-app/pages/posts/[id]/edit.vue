<script setup>
const route = useRoute();
const postId = route.params.id;
</script>

<template>
  <div>
    <h1>Edit Post {{ postId }}</h1>
    <p>Post details will be listed here</p>
  </div>
</template>
