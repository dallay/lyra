<script setup lang="ts">
import { onMounted, ref, computed } from 'vue';
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
import {
  Sheet,
  SheetContent,
  SheetDescription,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from '@/components/ui/sheet';
import { Button } from '@/components/ui/button';
import { useFormStore } from '~/store/form.store';
import { storeToRefs } from '#imports';
import { useTeamWatcher } from '~/composables/useTeamWatcher';
import { type Form, FormResponse } from '@/domain/forms';
import CreateOrEditSubscribeForm from '~/components/forms/CreateOrEditSubscribeForm.vue';
import ViewSubscribeForm from '~/components/forms/ViewSubscribeForm.vue';

const store = useFormStore();
const { formList } = storeToRefs(store);
const { fetchFormList } = store;

const newOrEditForm = ref<FormResponse | null>(null);
const openSheet = ref(false);

const subscribeFormTitle = computed(() => {
  return newOrEditForm.value ? 'Edit Subscribe Form' : 'Create Subscribe Form';
});
const subscribeFormDescription = computed(() => {
  return newOrEditForm.value
    ? 'You are currently editing an existing subscribe form. Make the necessary changes to update the form and ensure it meets your requirements.'
    : 'You are in the process of creating a new subscribe form. Fill out the fields below to set up your form and begin collecting subscriptions from your audience.';
});

const handleClose = async () => {
  newOrEditForm.value = null;
  openSheet.value = false;
  if (formEditOrViewOperation.value === 'edit') {
    await fetchFormList();
  }
};

const formEditOrViewOperation = ref<'edit' | 'view' | 'create'>('view');

const createForm = async () => {
  formEditOrViewOperation.value = 'create';
  newOrEditForm.value = null;
};

const editForm = async (form: Form) => {
  formEditOrViewOperation.value = 'edit';
  newOrEditForm.value = FormResponse.from(form);
  openSheet.value = true;
};
const viewForm = async (form: Form) => {
  formEditOrViewOperation.value = 'view';
  newOrEditForm.value = FormResponse.from(form);
  openSheet.value = true;
};

useTeamWatcher(fetchFormList);

onMounted(async () => {
  await fetchFormList();
});
</script>

<template>
  <div class="flex justify-center items-center mt-12">
    <Sheet v-model:open="openSheet">
      <div class="flex flex-col justify-center items-center mt-12c max-w-screen-lg">
        <header class="my-2.5">
          <h1 class="text-4xl">Subscribe Forms</h1>
          <p class="text-md text-left max-w-screen-lg font-light pt-2">Create customizable and
            embeddable
            email subscribe forms for your other websites.</p>
          <SheetTrigger>
            <Button variant="default" class="mt-6" @click="createForm">Create new form</Button>
          </SheetTrigger>
        </header>
        <SheetContent class="p-4 w-screen max-w-xl overflow-y-scroll">
          <SheetHeader class="mb-8"
                       v-if="formEditOrViewOperation === 'edit' || formEditOrViewOperation === 'create'">
            <SheetTitle>{{ subscribeFormTitle }}</SheetTitle>
            <SheetDescription>
              {{ subscribeFormDescription }}
            </SheetDescription>
          </SheetHeader>
          <CreateOrEditSubscribeForm
            v-if="formEditOrViewOperation === 'edit' || formEditOrViewOperation === 'create'"
            :form="newOrEditForm" @close="handleClose"/>
          <ViewSubscribeForm v-else-if="formEditOrViewOperation === 'view'" :form="newOrEditForm"
                             @close="handleClose"/>
        </SheetContent>
        <Table>
          <TableCaption>A list of your recent forms.</TableCaption>
          <TableHeader>
            <TableRow>
              <TableHead class="flex-1">
                Name
              </TableHead>
              <TableHead class="text-right">
                Actions
              </TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            <TableRow v-for="form in formList" :key="form.id.value">
              <TableCell class="font-medium">
                {{ form.name }}
              </TableCell>
              <TableCell class="text-right space-x-2">
                <Button variant="outline" size="sm" @click="viewForm(form as Form)">View</Button>
                <Button variant="outline" size="sm" @click="editForm(form as Form)">Edit</Button>
              </TableCell>
            </TableRow>
          </TableBody>
        </Table>
      </div>
    </Sheet>
  </div>
</template>
