<template>
  <Dialog v-model:open="open">
    <ActionSplitButton variant="outline">
      <DialogTrigger as-child>
        <div class="flex items-center space-x-2 cursor-pointer">
          <Icon name="lucide:expand" class="mr-2 size-4" />
          Preview
        </div>
      </DialogTrigger>
      <template #options>
        <EditorPreviewOptions />
      </template>
    </ActionSplitButton>

    <DialogContent class="sm:max-w-[90vw] sm:max-h-[90vh] overflow-y-auto w-full h-full">
      <DialogHeader>
        <div
          class="flex flex-col sm:flex-row items-center sm:justify-between space-y-4 sm:space-y-0 sm:space-x-4 mb-4"
        >
          <div class="flex space-x-4 justify-start items-start">
            <ViewTypeTabs v-model="viewType" />
            <DeviceTypeTabs v-model="deviceType" />
          </div>
          <SubscriberTypeSelect v-model="subscriberType" />
          <div class="w-1/5"></div>
        </div>
      </DialogHeader>

      <div :class="deviceType === 'mobile' ? 'max-w-sm mx-auto' : ''">
        <component :is="viewType === 'web' ? BrowserFrame : EmailFrame">
          <NewsletterContent>
            <iframe
              src="https://www.yunielacosta.com/posts/understanding-cors-in-web-development/"
              width="100%"
              height="100%"
              frameborder="0"
              allowfullscreen
              style="height: 100vh;"
            >
            </iframe>
          </NewsletterContent>
        </component>
      </div>
    </DialogContent>
  </Dialog>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import ActionSplitButton from '~/components/ui/button/action-split-button/ActionSplitButton.vue';
import EditorPreviewOptions from './EditorPreviewOptions.vue';
import { Dialog, DialogContent, DialogHeader } from '@/components/ui/dialog';

import ViewTypeTabs from './ViewTypeTabs.vue';
import DeviceTypeTabs from './DeviceTypeTabs.vue';
import SubscriberTypeSelect from './SubscriberTypeSelect.vue';
import BrowserFrame from './BrowserFrame.vue';
import EmailFrame from './EmailFrame.vue';
import NewsletterContent from './NewsletterContent.vue';

const open = ref(false);
const viewType = ref('web');
const deviceType = ref('desktop');
const subscriberType = ref('free');
</script>

<style scoped>
/* Add your styles here */
</style>
