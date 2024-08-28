<script setup lang="ts">
import { reactive, onMounted } from 'vue';
import { useNuxtApp, useRoute } from '#app';
import { FormResponse, FormId } from '@lyra/domain';
import { useFormStore } from "~/store/form.store";
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

const flux = reactive<{
  form: FormResponse;
}>({
  form: {
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
  },
});

onMounted(async () => {
  try {
    console.log('Form ID:', formId);
    const response = await formStore.formDetail(formId);
    console.log('Form details:', response);
    if (response) {
      flux.form = response;
    }
  } catch (error) {
    console.error('Error fetching form details:', error);
  }
});
</script>

<template>
  <EmailCTA
    v-if="slim"
    :placeholder="flux.form.inputPlaceholder"
    :buttonText="flux.form.buttonText"
    :buttonColor="flux.form.buttonColor"
    :buttonTextColor="flux.form.buttonTextColor"
    class="flex w-full flex-col items-start justify-start"
    />
  <EmailCTASection
    v-if="!slim"
    :description="flux.form.description"
    :header="flux.form.header"
    :placeholder="flux.form.inputPlaceholder"
    :buttonText="flux.form.buttonText"
    :buttonColor="flux.form.buttonColor"
    :buttonTextColor="flux.form.buttonTextColor"
    :backgroundColor="flux.form.backgroundColor"
    :textColor="flux.form.textColor"
  />
</template>
