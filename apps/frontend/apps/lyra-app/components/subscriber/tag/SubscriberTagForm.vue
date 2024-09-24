<script setup lang="ts">
import {colorClasses, Tag, TagColors} from '~/domain/tag';
import {cn} from '~/lib/utils';
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue,
} from '~/components/ui/select';
import {
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '~/components/ui/form';
import {Button} from '~/components/ui/button';
import {Textarea} from '~/components/ui/textarea';
import {Badge} from '~/components/ui/badge';
import {Input} from '~/components/ui/input';
import {toast} from '~/components/ui/toast';
import {vAutoAnimate} from '@formkit/auto-animate/vue';
import {useForm} from 'vee-validate';
import {toTypedSchema} from '@vee-validate/zod';
import {defineEmits, ref} from 'vue';
import * as z from 'zod';
import type {TagResponse} from '~/domain/tag/TagResponse';
import TagId from '~/domain/tag/TagId';
import {useTagStore} from '~/store/tag.store';
import {useWorkspaceStore} from '~/store/workspace.store';

const tagStore = useTagStore();
const workspaceStore = useWorkspaceStore();

const tagSchema = z.object({
	id: z.string().min(1),
	name: z.string().min(2).max(50),
	color: z.nativeEnum(TagColors).default(TagColors.Default),
	subscribers: z.string(),
});

const tagTypedSchema = toTypedSchema(tagSchema);

interface SubscriberTagFormProps {
	currentTag: Tag | null;
}
const loading = ref(false);
const props = defineProps<SubscriberTagFormProps>();
const emit = defineEmits<{
  (evt: 'update'): void;
	(evt: 'close'): void;
}>();
const tagId = TagId.random();
const defaultTag: TagResponse = {
	id: tagId.value,
	name: '',
	color: TagColors.Default,
	subscribers: [],
};
const form = useForm({
	validationSchema: tagTypedSchema,
	initialValues: {
		id: props.currentTag?.id || defaultTag.id,
		name: props.currentTag?.name || defaultTag.name,
		color: (props.currentTag?.color as TagColors) || defaultTag.color,
		subscribers: Array.isArray(props.currentTag?.subscribers)
			? props.currentTag.subscribers.join(',')
			: defaultTag.subscribers.join(','),
	},
});

const onSubmit = form.handleSubmit(async (values) => {
	loading.value = true;
	workspaceStore.getCurrentOrganizationId();
	const tag = Tag.fromResponse({
		id: values.id,
		name: values.name,
		color: values.color,
		subscribers: values.subscribers.split(',').map((email) => email.trim()),
	});
	const organizationId = workspaceStore.getCurrentOrganizationId();
	if (!organizationId) return;

	const tagAction: Promise<void> = props.currentTag
		? tagStore.updateTag(organizationId,
    TagId.create(tag.id),
    {
      name: tag.name,
      color: tag.color,
      subscribers: Array.isArray(tag.subscribers) ? tag.subscribers : [],
    })
		: tagStore.createTag(organizationId, {
      name: tag.name,
      color: tag.color,
      subscribers: Array.isArray(tag.subscribers) ? tag.subscribers : [],
    });

	tagAction
		.then(() => {
			toast({ title: 'Tag created', description: 'The tag has been created successfully.' });
			emit('close');
		})
		.catch((error) => {
			toast({ title: 'Error', description: error.message });
		})
		.finally(() => {
			loading.value = false;
      emit('update');
		});
});
</script>

<template>
  <form class="w-full space-y-6 mx-2" @submit="onSubmit">
    <FormField
      v-slot="{ componentField }"
      name="name"
      :validate-on-blur="!form.isFieldDirty"
    >
      <FormItem v-auto-animate>
        <FormLabel>Name</FormLabel>
        <FormControl>
          <Input type="text" placeholder="Tag name" v-bind="componentField" />
        </FormControl>
        <FormDescription> This is the name of the tag. </FormDescription>
        <FormMessage />
      </FormItem>
    </FormField>
    <FormField
      v-slot="{ componentField }"
      name="color"
      :validate-on-blur="!form.isFieldDirty"
    >
      <FormItem v-auto-animate>
        <FormLabel>Color</FormLabel>
        <FormControl>
          <Select v-bind="componentField">
            <SelectTrigger>
              <SelectValue placeholder="Select a color" />
            </SelectTrigger>
            <SelectContent>
              <SelectGroup>
                <SelectLabel>Colors</SelectLabel>
                <SelectItem
                  v-for="color in Object.values(TagColors)"
                  :key="color"
                  :value="color"
                >
                  <Badge
                    :class="
                      cn(
                        colorClasses[color] || colorClasses[TagColors.Default],
                        'capitalize'
                      )
                    "
                    >{{ color }}
                  </Badge>
                </SelectItem>
              </SelectGroup>
            </SelectContent>
          </Select>
        </FormControl>
        <FormDescription> This is the color of the tag. </FormDescription>
        <FormMessage />
      </FormItem>
    </FormField>
    <FormField
      v-slot="{ componentField }"
      name="subscribers"
      :validate-on-blur="!form.isFieldDirty"
    >
      <FormItem v-auto-animate>
        <FormLabel>Subscribers</FormLabel>
        <FormControl>
          <Textarea
            placeholder="email1@example.com, email2@example.com, email3@example.com, etc."
            v-bind="componentField"
          />
        </FormControl>
        <FormDescription>
          This is the list of subscribers for the tag (emails separated by
          commas).
        </FormDescription>
        <FormMessage />
      </FormItem>
    </FormField>
    {{ currentTag }}
    <br />
    {{ form.values }}
    <div class="absolute bottom-2 right-2 w-full space-y-3">
      <Separator />
      <div class="flex justify-end space-x-2">
        <Button type="button" variant="ghost" @click="emit('close')">
          Cancel
        </Button>
        <Button type="submit" :loading="loading" :disabled="!form.isFieldDirty">
          {{ currentTag ? "Update" : "Create" }} Tag
        </Button>
      </div>
    </div>
  </form>
</template>
