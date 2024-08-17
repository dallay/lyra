<script setup lang="ts">
import { reactive, onMounted } from 'vue';
import { useNuxtApp, useRoute } from '#app';
import { FormResponse, FormId } from '@lyra/api-services';

const route = useRoute();

const id = route.params.id;
const idValue = typeof id === 'string' ? id : id[0];
const formId = FormId.create(idValue);
// biome-ignore lint/correctness/noUnusedVariables: this is a false positive as the variable is used in the template
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
  },
});

const { $api } = useNuxtApp();

onMounted(() => {
  $api.form
  .fetchDetail(formId)
  .then((data) => {
    flux.form = data;
  })
  .catch((error) => {
    console.error(error);
  });
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
