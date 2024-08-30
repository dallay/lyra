<script setup lang="ts">
import {computed, type HTMLAttributes, reactive, ref, watch} from 'vue';
import {cn} from '@/lib/utils';
import {useForm} from 'vee-validate';
import {toTypedSchema} from '@vee-validate/zod';
import * as z from 'zod';

import {Button} from '@/components/ui/button';
import {FormControl, FormField, FormItem, FormMessage,} from '@/components/ui/form';
import {Input} from '@/components/ui/input';
import {useSubscriberStore} from '~/store/subscriber.store';
import {OrganizationId} from '@lyra/domain';

const subscriberStore = useSubscriberStore();

const props = withDefaults(
	defineProps<{
		class?: HTMLAttributes['class'];
		placeholder?: string;
		buttonText?: string;
		buttonColor?: string;
		buttonTextColor?: string;
		backgroundColor?: string;
    uppercase?: boolean;
		organizationId: string;
	}>(),
	{
		class: 'w-full max-w-md space-y-2 flex flex-col items-center justify-center',
		placeholder: 'Enter your email',
		buttonText: 'subscribe',
		buttonColor: '#374151',
		buttonTextColor: '#F3F4F6',
    backgroundColor: '#111827',
    uppercase: false,
	},
);

const flux = reactive<{
	placeholder: string;
	buttonText: string;
	buttonColor: string;
	buttonTextColor: string;
	backgroundColor?: string;
}>({
	placeholder: props.placeholder,
	buttonText: props.buttonText,
	buttonColor: props.buttonColor,
	buttonTextColor: props.buttonTextColor,
	backgroundColor: props.backgroundColor,
});
watch(
	() => props,
	(newProps) => {
		flux.placeholder = newProps.placeholder;
		flux.buttonText = newProps.buttonText;
		flux.buttonColor = newProps.buttonColor;
		flux.buttonTextColor = newProps.buttonTextColor;
	},
	{ deep: true },
);

const formSchema = toTypedSchema(
	z.object({
		email: z.string().email('You need to provide a real email'),
	}),
);
const form = useForm({
	validationSchema: formSchema,
});
const buttonCustomClass = computed(() => {
	return cn(
		'absolute bottom-0 end-0 top-0 right-2 rounded-r px-5 py-2.5 text-center font-sans font-semibold',
    {'uppercase': props.uppercase},
		{ 'cursor-not-allowed': loading.value },
	);
});
const inputCustomClass = computed(() => {
	return cn('w-full h-10 rounded-lg px-4 py-2.5 text-sm font-sans font-normal',
    'bg-primary/10 text-black',
    {
		'cursor-not-allowed': loading.value,
	});
});
const buttonCustomStyle = computed(() => {
	return {
		backgroundColor: flux.buttonColor,
		color: flux.buttonTextColor,
    borderColor: flux.buttonColor,
	};
});

const inputCustomStyle = computed(() => {
	return {
		borderColor: flux.buttonColor,
    borderWidth: '2px',
	};
});
const loading = ref(false);

const onSubmit = form.handleSubmit(async (values) => {
	loading.value = true;
	await subscriberStore.createSubscriber(OrganizationId.create(props.organizationId), {
		email: values.email,
	});
	loading.value = false;
});
</script>

<template>
  <div :class="props.class">
    <form @submit="onSubmit" class="relative flex w-full justify-center items-center space-x-2">
      <FormField v-slot="{ componentField }" name="email" class="w-full">
        <FormItem class="w-full">
          <FormControl>
            <Input type="email" :placeholder="flux.placeholder" v-bind="componentField"
                   autocomplete="email" :class="inputCustomClass" :style="inputCustomStyle"
            />
          </FormControl>
          <FormMessage />
        </FormItem>
      </FormField>
      <ConfettiTrigger>
      <Button type="submit"
              :disabled="loading"
              :class="buttonCustomClass"
              :style="buttonCustomStyle"
              size="lg"
      >
        {{flux.buttonText}}
      </Button>
      </ConfettiTrigger>
    </form>
  </div>

</template>

<style scoped lang="scss">

</style>
