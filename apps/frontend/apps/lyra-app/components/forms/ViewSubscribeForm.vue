<script setup lang="ts">
import {defineEmits, defineProps} from 'vue';
import {FormId, type FormResponse} from '@lyra/domain';
import {Separator} from '~/components/ui/separator';
import {cn} from '~/lib/utils';
import {useFormStore} from '~/store/form.store';
import {SheetHeader, SheetTitle} from '~/components/ui/sheet';
import {Button} from '~/components/ui/button';
import {toast} from '~/components/ui/toast';
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from '@/components/ui/alert-dialog'
import {useRuntimeConfig} from '#app';

const config = useRuntimeConfig();
const appClientUrl = config.public.appClientUrl;

const formStore = useFormStore();

interface SubscribeFormProps {
  form: FormResponse | null;
}

const props = defineProps<SubscribeFormProps>();
const emit = defineEmits<{ (evt: 'close'): void }>();

const loading = ref(false);

const embedsForm = computed(() => ({
  url: `${appClientUrl}/forms/${props.form?.id}`,
  id: 'lyra-embed',
}));

interface SectionCode {
  width?: string;
  height?: string;
  frameborder: string;
  scrolling: string;
  style: string;
  paragraph: string;
}

const sectionCodes: Record<string, SectionCode> = {
  full: {
    width: '100%',
    height: '320',
    frameborder: '0',
    scrolling: 'no',
    style: 'border-radius: 4px; border: 2px solid #e5e7eb; margin: 0; background-color: transparent;',
    paragraph: 'Embed this code directly in-line within your website, and it will expand to the full width of its surrounding container.',
  },
  fixed: {
    width: '320px',
    height: '320',
    frameborder: '0',
    scrolling: 'no',
    style: 'border-radius: 4px; border: 2px solid #e5e7eb; margin: 0; background-color: transparent;',
    paragraph: 'Embed this code directly in-line with your website, and it will expand to the specified maximum width.',
  },
  slim: {
    height: '52',
    frameborder: '0',
    scrolling: 'no',
    style: 'margin: 0; border-radius: 0px !important; background-color: transparent;',
    paragraph: 'This offers the most control with placement without providing a background, header, or description.',
  },
};

const sectionCode = computed(() =>
  Object.fromEntries(
    Object.entries(sectionCodes).map(([key, value]) => [
      key,
      {
        iframeCode: `<iframe src="${embedsForm.value.url}${
          key === 'slim' ? '?slim=true' : ''
        }" data-test-id="${embedsForm.value.id}" ${
          value.width ? `width="${value.width}"` : ''
        } ${value.height ? `height="${value.height}"` : ''} frameborder="${
          value.frameborder
        }" scrolling="${value.scrolling}" style="${value.style}"></iframe>`,
        paragraph: value.paragraph,
      },
    ])
  )
);

const onSubmit = async () => {
  if (!props.form || !props.form?.id) return;

  loading.value = true;
  try {
    const formId = FormId.create(props.form.id);
    await formStore.deleteForm(formId);
    emit('close');
    toast({
      title: 'Form deleted',
      description: 'The form has been successfully deleted.',
    });
  } catch (error) {
    console.error('Failed to delete form:', error);
  } finally {
    loading.value = false;
  }
};
const clipboardIcon = ref('lucide:clipboard');

const copyToClipboard = (iframeCode: string) => {
  navigator.clipboard.writeText(iframeCode).then(() => {
    clipboardIcon.value = 'lucide:clipboard-check';
    toast({
      description: 'Copied to clipboard!',
    });
    setTimeout(() => {
      clipboardIcon.value = 'lucide:clipboard';
    }, 1000);
  }).catch(err => {
    console.error('Failed to copy: ', err);
  });
};
</script>

<template>
  <SheetHeader class="mb-8">
    <SheetTitle>{{ form?.name }}</SheetTitle>
  </SheetHeader>

  <section>
    <Separator label="Embed Options" :class="cn('[&>span]:text-lg my-4')"/>
    <p class="text-sm my-8">
      Below are various ways you can embed your subscribe form that offer various levels of control.
      Make sure to choose the best one for your use case.
    </p>

    <section v-for="(code, type) in sectionCode" :key="type">
      <Separator :label="type.charAt(0).toUpperCase() + type.slice(1) + ' Width'"
                 :class="cn('[&>span]:text-lg my-4')"/>
      <p class="text-sm my-8">
        {{ code.paragraph }}
      </p>
      <div class="relative p-0.5">
        <pre class="h-full p-4 rounded mt-4 mb-4 overflow-x-scroll bg-primary/10">{{
            code.iframeCode
          }}</pre>
        <Button @click="copyToClipboard(code.iframeCode)" class="absolute top-2 right-0 rounded-md"
                variant="ghost" size="sm">
          <Icon :name="clipboardIcon" color="black" class="h-4 w-4 animate-bounce"/>
        </Button>
      </div>
      <iframe
        :src="type === 'slim' ? `${embedsForm.url}?slim=true` : embedsForm.url"
        :data-test-id="embedsForm.id"
        v-bind="{ width: sectionCodes[type].width, height: sectionCodes[type].height, frameborder: sectionCodes[type].frameborder, scrolling: sectionCodes[type].scrolling, style: sectionCodes[type].style }">
      </iframe>
    </section>

    <div class="flex justify-end mx-4">
      <AlertDialog>
        <AlertDialogTrigger as-child>
          <Button :disabled="!form?.id" type="button" :loading="loading" variant="outline"
                  class="border-red-500 text-red-500 hover:bg-red-500 hover:text-white">
            Delete
          </Button>
        </AlertDialogTrigger>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>Are you absolutely sure?</AlertDialogTitle>
            <AlertDialogDescription>
              This action cannot be undone. This will permanently delete the form.
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel>Cancel</AlertDialogCancel>
            <AlertDialogAction @click="onSubmit">Continue</AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </div>
  </section>
</template>
