<template>
  <div class="mx-auto p-4">
    <StatusBar />
    <div class="flex justify-center">
      <div class="w-full max-w-3xl mx-auto">
        <AddOptions />
        <TitleEditor />
        <AuthorTags />
        <client-only>
          <ContentEditor :ydoc="ydoc" :provider="provider" userId="user-id" userName="user-name" />
        </client-only>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount, type Ref } from 'vue';
import { TiptapCollabProvider } from '@hocuspocus/provider';
import { Doc as YDoc } from 'yjs';
import StatusBar from './StatusBar.vue';
import AddOptions from './AddOptions.vue';
import TitleEditor from './TitleEditor.vue';
import AuthorTags from './AuthorTags.vue';
import ContentEditor from './ContentEditor.vue';
import { useRuntimeConfig } from '#imports';

const config = useRuntimeConfig();

const ydoc = ref(new YDoc());
const provider = ref<TiptapCollabProvider | null>(null) as Ref<TiptapCollabProvider | null>;

onMounted(async () => {
  try {
    const response = await fetch('/api/collaboration', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
    });

    if (!response.ok) {
      throw new Error('Failed to fetch collaboration token');
    }

    const data = await response.json();
    const token = data.token;

    provider.value = new TiptapCollabProvider({
      name: `${config.public.collabDocPrefix}example-room`,
      appId: config.public.tiptapCollabAppId,
      token: token,
      document: ydoc.value,
    });
  } catch (error) {
    console.error('Error fetching collaboration token:', error);
  }
});

onBeforeUnmount(() => {
  if (provider.value) {
    provider.value.destroy();
  }
});
</script>
