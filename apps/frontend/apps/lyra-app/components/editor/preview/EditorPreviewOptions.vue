<template>
  <div class="w-full max-w-md p-4">
    <div class="mb-4">
      <h2 class="text-lg font-semibold">Draft link</h2>
    </div>
    <div class="space-y-4">
      <div class="flex space-x-2">
        <Input
          v-model="draftLink"
          readonly
        />
        <Button variant="outline" class="shrink-0">
          <Copy class="h-4 w-4 mr-2" />
          Copy
        </Button>
      </div>
      <p class="text-sm text-muted-foreground">
        Anyone who visits this link will be able to see the latest version of the draft.
        <Button variant="link" class="p-0 h-auto font-normal" @click="resetLink">Reset link</Button> to revoke access.
      </p>

      <div class="space-y-2">
        <Label>Send test email</Label>
        <div class="flex items-center space-x-2">
          <Checkbox v-model="testEmail" id="test-email" />
          <label
            for="test-email"
            class="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
          >
            yunielacosta738@gmail.com
          </label>
        </div>
      </div>

      <div class="space-y-2">
        <Label>Simulated Subscriber</Label>
        <Select v-model="selectedSubscriber">
          <SelectTrigger>
            <SelectValue placeholder="Select a subscriber..." />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="subscriber1">Subscriber 1</SelectItem>
            <SelectItem value="subscriber2">Subscriber 2</SelectItem>
            <SelectItem value="subscriber3">Subscriber 3</SelectItem>
          </SelectContent>
        </Select>
        <p class="text-sm text-muted-foreground">
          This only sends the test to the selected email addresses, personalized as if sent to the chosen subscriber. Great for testing personalization or referral programs.
        </p>
      </div>

      <Button class="w-full" @click="sendTestEmail">
        <Link class="h-4 w-4 mr-2" />
        Send test email
      </Button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { Checkbox } from '@/components/ui/checkbox';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import { Label } from '@/components/ui/label';
import { Copy, Link } from 'lucide-vue-next';

const draftLink = ref('https://yap.lyra.com/p/new-post?...');

const testEmail = ref(false);
const selectedSubscriber = ref<string | undefined>('');

const resetLink = () => {
  console.log('Link reset');
};

const sendTestEmail = () => {
  console.log('Test email sent to:', selectedSubscriber.value);
};
</script>
