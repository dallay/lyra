<script setup lang="ts">
import { type HTMLAttributes, reactive, watch, computed } from 'vue';
import { onMounted } from '#imports';

import { useForm } from 'vee-validate';
import { toTypedSchema } from '@vee-validate/zod';
import * as z from 'zod';

import { Button } from '@/components/ui/button';
import {
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '@/components/ui/form';
import { Input } from '@/components/ui/input';

const props = withDefaults(
  defineProps<{
    class?: HTMLAttributes['class'];
    placeholder?: string;
    buttonText?: string;
    buttonColor?: string;
    buttonTextColor?: string;
  }>(),
  {
    class: 'w-full max-w-md space-y-2 flex flex-col items-center justify-center',
    placeholder: 'Enter your email',
    buttonText: 'subscribe',
    buttonColor: '#374151',
    buttonTextColor: '#F3F4F6',
  }
);

const flux = reactive<{
  placeholder: string;
  buttonText: string;
  buttonColor: string;
  buttonTextColor: string;
}>({
  placeholder: props.placeholder,
  buttonText: props.buttonText,
  buttonColor: props.buttonColor,
  buttonTextColor: props.buttonTextColor,
});

// Watch for changes in props and update `flux` accordingly
watch(
  () => props,
  (newProps) => {
    flux.placeholder = newProps.placeholder;
    flux.buttonText = newProps.buttonText;
    flux.buttonColor = newProps.buttonColor;
    flux.buttonTextColor = newProps.buttonTextColor;
  },
  { deep: true }
);

const formSchema = toTypedSchema(
  z.object({
    email: z.string().email('You need to provide a real email'),
  })
);
const form = useForm({
  validationSchema: formSchema,
});
// biome-ignore lint/correctness/noUnusedVariables: Biome needs support for Vue/Nuxt.
const onSubmit = form.handleSubmit((values) => {
  console.log('Form submitted!', values);
  alert('Form submitted! We need to implement the API call here.');
});
</script>

<template>
  <div :class="props.class">
    <form @submit="onSubmit" class="relative flex w-full justify-center items-center space-x-2">
      <FormField v-slot="{ componentField }" name="email" class="w-full">
        <FormItem class="w-full">
          <FormControl>
            <Input type="email" :placeholder="flux.placeholder" v-bind="componentField"
                   :style="{
                      border: `solid ${flux.buttonColor}`
                    }"
            />
          </FormControl>
          <FormMessage />
        </FormItem>
      </FormField>
      <ConfettiTrigger>
      <Button type="submit"
              :style="{
                backgroundColor: flux.buttonColor,
                borderColor: flux.buttonTextColor,
                border: `solid ${flux.buttonColor}`
              }"
              class="absolute bottom-0 end-0 top-0 rounded-r px-5 py-2.5 text-center font-sans font-semibold uppercase">
        {{flux.buttonText}}
      </Button>
      </ConfettiTrigger>
    </form>
  </div>

</template>

<style scoped lang="scss">

</style>
