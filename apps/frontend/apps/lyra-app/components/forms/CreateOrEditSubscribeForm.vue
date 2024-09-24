<script setup lang="ts">
import { defineEmits, defineProps, ref } from 'vue';
import { useForm } from 'vee-validate';
import { toTypedSchema } from '@vee-validate/zod';
import * as z from 'zod';
import { vAutoAnimate } from '@formkit/auto-animate/vue';
import { Button } from '~/components/ui/button';
import {
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '~/components/ui/form';
import { Input } from '~/components/ui/input';
import { toast } from '~/components/ui/toast';
import { Separator } from '~/components/ui/separator';
import { cn } from '~/lib/utils';
import { useFormStore } from '~/store/form.store';
import { useWorkspaceStore } from '~/store/workspace.store';
import { storeToRefs } from 'pinia';
import { CreateFormRequest, FormId, FormResponse } from '~/domain/forms';

const formStore = useFormStore();
const workspaceStore = useWorkspaceStore();
const { selectedTeam } = storeToRefs(workspaceStore);

interface SubscribeFormProps {
  form: FormResponse | null;
}

const props = defineProps<SubscribeFormProps>();
const emit = defineEmits<{
  (evt: 'close'): void;
}>();

const defaultsConfig = {
  name: 'Basic Form',
  header: 'Form header',
  description: 'Form description',
  inputPlaceholder: 'Form input placeholder',
  buttonText: 'Submit',
  buttonColor: '#0b40ab',
  backgroundColor: '#d7d516',
  textColor: '#000000',
  buttonTextColor: '#ffffff',
};

const formSchema = toTypedSchema(
  z.object({
    name: z
      .string()
      .min(2)
      .max(50)
      .default(props.form?.name || ''),
    header: z
      .string()
      .max(100)
      .default(props.form?.header || defaultsConfig.header),
    description: z
      .string()
      .max(200)
      .default(props.form?.description || defaultsConfig.description),
    inputPlaceholder: z
      .string()
      .max(50)
      .default(props.form?.inputPlaceholder || defaultsConfig.inputPlaceholder),
    buttonText: z
      .string()
      .max(50)
      .default(props.form?.buttonText || defaultsConfig.buttonText),
    buttonColor: z
      .string()
      .max(50)
      .default(props.form?.buttonColor || defaultsConfig.buttonColor),
    backgroundColor: z
      .string()
      .max(50)
      .default(props.form?.backgroundColor || defaultsConfig.backgroundColor),
    textColor: z
      .string()
      .max(50)
      .default(props.form?.textColor || defaultsConfig.textColor),
    buttonTextColor: z
      .string()
      .max(50)
      .default(props.form?.buttonTextColor || defaultsConfig.buttonTextColor),
  }),
);

const { isFieldDirty, handleSubmit } = useForm({
  validationSchema: formSchema,
});

const loading = ref(false);

const onSubmit = handleSubmit(async (values) => {
  loading.value = true;
  const teamId = selectedTeam.value?.teamId;
  const workspace = workspaceStore.getWorkspaceByTeamId(teamId ?? '');
  const organizationId = workspace?.organizationId;

  if (!organizationId) return;

  const formAction: Promise<void> = props.form
    ? formStore.updateForm(FormId.create(props.form.id), { ...values, organizationId })
    : formStore.createForm({
        ...values,
        id: crypto.randomUUID(),
        organizationId,
      } as CreateFormRequest);

  formAction
    .then(() => {
      toast({
        title: props.form ? 'Form updated' : 'Form created',
        description: `Your form has been ${props.form ? 'updated' : 'created'} successfully.`,
      });
      emit('close');
    })
    .catch(() => {
      toast({
        title: `Form ${props.form ? 'update' : 'creation'} failed`,
        description: `An error occurred while ${props.form ? 'updating' : 'creating'} the form. Please try again.`,
        variant: 'destructive',
      });
    })
    .finally(() => {
      loading.value = false;
    });
});
</script>

<template>
  <form class="w-full space-y-6 mx-2" @submit="onSubmit">
    <FormField v-slot="{ componentField }" name="name" :validate-on-blur="!isFieldDirty">
      <FormItem v-auto-animate>
        <FormLabel>Name</FormLabel>
        <FormControl>
          <Input type="text" :placeholder="defaultsConfig.name" v-bind="componentField"/>
        </FormControl>
        <FormDescription>
          This is your public display name.
        </FormDescription>
        <FormMessage/>
      </FormItem>
    </FormField>
    <Separator label="Content" :class="cn(
        '[&>span]:text-lg my-0.5',
      )"/>
    <FormField v-slot="{ componentField }" name="header" :validate-on-blur="!isFieldDirty">
      <FormItem v-auto-animate>
        <FormLabel>Header</FormLabel>
        <FormControl>
          <Input type="text" :placeholder="defaultsConfig.header" v-bind="componentField"/>
        </FormControl>
        <FormDescription>
          This is the header of the form.
        </FormDescription>
        <FormMessage/>
      </FormItem>
    </FormField>
    <FormField v-slot="{ componentField }" name="description" :validate-on-blur="!isFieldDirty">
      <FormItem v-auto-animate>
        <FormLabel>Description</FormLabel>
        <FormControl>
          <Input type="text" :placeholder="defaultsConfig.description" v-bind="componentField"/>
        </FormControl>
        <FormDescription>
          This is the description of the form.
        </FormDescription>
        <FormMessage/>
      </FormItem>
    </FormField>
    <FormField v-slot="{ componentField }" name="inputPlaceholder"
               :validate-on-blur="!isFieldDirty">
      <FormItem v-auto-animate>
        <FormLabel>Input Placeholder</FormLabel>
        <FormControl>
          <Input type="text" :placeholder="defaultsConfig.inputPlaceholder"
                 v-bind="componentField"/>
        </FormControl>
        <FormDescription>
          This is the placeholder of the input field.
        </FormDescription>
        <FormMessage/>
      </FormItem>
    </FormField>
    <FormField v-slot="{ componentField }" name="buttonText" :validate-on-blur="!isFieldDirty">
      <FormItem v-auto-animate>
        <FormLabel>Button Text</FormLabel>
        <FormControl>
          <Input type="text" :placeholder="defaultsConfig.buttonText" v-bind="componentField"/>
        </FormControl>
        <FormDescription>
          This is the text of the button.
        </FormDescription>
        <FormMessage/>
      </FormItem>
    </FormField>
    <Separator label="Color Palette" :class="cn(
        '[&>span]:text-lg my-0.5',
      )"/>
    <FormField v-slot="{ componentField }" name="buttonColor" :validate-on-blur="!isFieldDirty">
      <FormItem v-auto-animate>
        <FormLabel>Button Color</FormLabel>
        <FormControl>
          <Input type="color" :placeholder="defaultsConfig.buttonColor" v-bind="componentField"/>
        </FormControl>
        <FormDescription>
          This is the color of the button.
        </FormDescription>
        <FormMessage/>
      </FormItem>
    </FormField>
    <FormField v-slot="{ componentField }" name="backgroundColor" :validate-on-blur="!isFieldDirty">
      <FormItem v-auto-animate>
        <FormLabel>Background Color</FormLabel>
        <FormControl>
          <Input type="color" :placeholder="defaultsConfig.backgroundColor"
                 v-bind="componentField"/>
        </FormControl>
        <FormDescription>
          This is the color of the background.
        </FormDescription>
        <FormMessage/>
      </FormItem>
    </FormField>
    <FormField v-slot="{ componentField }" name="textColor" :validate-on-blur="!isFieldDirty">
      <FormItem v-auto-animate>
        <FormLabel>Text Color</FormLabel>
        <FormControl>
          <Input type="color" :placeholder="defaultsConfig.textColor" v-bind="componentField"/>
        </FormControl>
        <FormDescription>
          This is the color of the text.
        </FormDescription>
        <FormMessage/>
      </FormItem>
    </FormField>
    <FormField v-slot="{ componentField }" name="buttonTextColor" :validate-on-blur="!isFieldDirty">
      <FormItem v-auto-animate>
        <FormLabel>Button Text Color</FormLabel>
        <FormControl>
          <Input type="color" :placeholder="defaultsConfig.buttonTextColor"
                 v-bind="componentField"/>
        </FormControl>
        <FormDescription>
          This is the color of the button text.
        </FormDescription>
        <FormMessage/>
      </FormItem>
    </FormField>
    <div class="flex justify-end mx-4">
      <Button type="submit" :loading="loading" :disabled="!isFieldDirty">
        Save
      </Button>
    </div>
  </form>
</template>
