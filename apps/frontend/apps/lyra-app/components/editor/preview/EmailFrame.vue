<template>
  <div class="flex flex-col gap-3 items-start self-stretch px-6 p-3 bg-gray-50 dark:bg-gray-800 border-b border-gray-200 dark:border-gray-700">
    <div class="w-full grid grid-cols-12">
      <div class="col-span-2">
        <span class="text-sm text-gray-500 dark:text-gray-400 font-normal">From:</span>
      </div>
      <div class="col-span-10">
        <span class="flex flex-row gap-2">
          <span class="text-sm text-gray-700 dark:text-gray-300 font-medium">{{ from }}</span>
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true" class="w-4 h-4 cursor-pointer text-gray-400 my-auto">
            <path fill-rule="evenodd" d="M18 10a8 8 0 1 1-16 0 8 8 0 0 1 16 0Zm-7-4a1 1 0 1 1-2 0 1 1 0 0 1 2 0ZM9 9a.75.75 0 0 0 0 1.5h.253a.25.25 0 0 1 .244.304l-.459 2.066A1.75 1.75 0 0 0 10.747 15H11a.75.75 0 0 0 0-1.5h-.253a.25.25 0 0 1-.244-.304l.459-2.066A1.75 1.75 0 0 0 9.253 9H9Z" clip-rule="evenodd"></path>
          </svg>
        </span>
      </div>
    </div>

    <div class="w-full grid grid-cols-12">
      <div class="col-span-2">
        <span class="text-sm text-gray-500 dark:text-gray-400 font-normal">Subject:</span>
      </div>
      <div class="col-span-10">
        <span class="text-sm text-gray-700 dark:text-gray-300 font-medium">{{ subject }}</span>
      </div>
    </div>

    <div class="w-full grid grid-cols-12">
      <div class="col-span-2">
        <span class="text-sm text-gray-500 dark:text-gray-400 font-normal">Preview Text:</span>
      </div>
      <div class="col-span-10">
        <span class="text-sm text-gray-700 dark:text-gray-300 font-medium">{{ previewText }}</span>
      </div>
    </div>

    <slot></slot>
  </div>
</template>

<script setup lang="ts">
interface EmailFrameProps {
  from: string;
  subject: string;
  previewText: string;
}

const props = withDefaults(defineProps<EmailFrameProps>(), {
  from: '"Yuniel Acosta Pérez" <yap@mail.beehiiv.com>',
  subject: 'Subject Line',
  previewText: 'Preview Text',
});
</script>
