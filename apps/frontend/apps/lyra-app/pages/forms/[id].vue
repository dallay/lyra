<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRoute } from '#app';
import { FormResponse, FormId } from '@lyra/domain';
import { useFormStore } from "~/store/form.store";
import { Skeleton } from '@/components/ui/skeleton'
import { definePageMeta } from '#imports'
definePageMeta({
  layout: 'simple'
})

const formStore = useFormStore();
const route = useRoute();

const id = route.params.id;
const idValue = typeof id === 'string' ? id : id[0];
const formId = FormId.create(idValue);
const slim: boolean = route.query.slim === 'true';

const defaultForm = {
  id: idValue,
  name: 'Form Example Test',
  header: 'Default Newsletter',
  description: '🔴 Some description 🔴',
  inputPlaceholder: 'Enter your email',
  buttonText: 'Subscribe',
  buttonColor: '#2C81E5',
  backgroundColor: '#DFD150',
  textColor: '#222222',
  buttonTextColor: '#FFFFFF',
  createdAt: new Date().toDateString(),
  updatedAt: new Date().toDateString(),
  organizationId: 'af0346d6-a138-4c63-b41f-1cfd1b01dc4d',
};
const form = ref<FormResponse>();

onMounted(async () => {
  try {
    const response = await formStore.formDetail(formId);
    if (response) {
      form.value = response;
    } else {
      form.value = defaultForm;
    }
  } catch (error) {
    console.error('Error fetching form details:', error);
  }
});
</script>

<template>
  <div v-if="!form">
    <div v-if="slim" class="flex space-x-3 mx-1">
      <Skeleton class="h-10 w-full rounded-md" />
      <Skeleton class="h-10 w-32 rounded-md" />
    </div>
    <div v-else class="m-1 flex flex-col items-center justify-center p-2 space-y-3">
      <Skeleton class="h-8 w-3/4 rounded-md" />
      <Skeleton class="h-6 w-1/2 rounded-md" />
      <div class="flex space-x-3 mx-1 w-3/5">
        <Skeleton class="h-10 w-full rounded-md" />
        <Skeleton class="h-10 w-32 rounded-md" />
      </div>
    </div>
  </div>
  <EmailCTA
    v-else-if="slim"
    :placeholder="form.inputPlaceholder"
    :buttonText="form.buttonText"
    :buttonColor="form.buttonColor"
    :buttonTextColor="form.buttonTextColor"
    class="flex w-full flex-col items-start justify-center"
  />
  <EmailCTASection
    v-else-if="!slim"
    :description="form.description"
    :header="form.header"
    :placeholder="form.inputPlaceholder"
    :buttonText="form.buttonText"
    :buttonColor="form.buttonColor"
    :buttonTextColor="form.buttonTextColor"
    :backgroundColor="form.backgroundColor"
    :textColor="form.textColor"
    class="flex w-full flex-col items-start justify-center"
  />
</template>
