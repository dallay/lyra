<script lang="ts" setup>
import { reactive, onMounted } from 'vue';
import { Table, TextField, Select, Button, type Control } from '@/index';
import leetcode from './leetcode';

const state = reactive({
  rows: [] as any[],
  control: { rows: 10, page: 1, field: 'id', direction: 'asc' } as Control,
  count: 0,
});

const body = reactive({
  title: '',
  difficulty: '',
});

onMounted(() => {
  search();
});

function reset() {
  body.title = '';
  body.difficulty = '';
  search();
}

async function search() {
  state.control = { rows: 10, page: 1, field: 'id', direction: 'asc' };
  const response = await leetcode({ ...body, ...state.control });
  state.rows = response.result;
  state.count = response.count;
}

async function change(params: Control) {
  state.control = params;
  const response = await leetcode({ ...body, ...params });
  state.rows = response.result;
}
</script>

<template>
  <div>
    <div class="text-3xl font-bold my-4">Basic</div>

    <div class="p-8 bg-white dark:bg-slate-800 rounded-lg space-y-8">
      <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
        <TextField v-model:value="body.title" />

        <Select
          v-model:value="body.difficulty"
          :options="[
            { label: 'Easy', value: 'Easy' },
            { label: 'Medium', value: 'Medium' },
            { label: 'Hard', value: 'Hard' },
          ]"
        />

        <div class="flex gap-4">
          <Button color="secondary" class="flex-1" @click="reset">Reset</Button>
          <Button class="flex-1" @click="search">Search</Button>
        </div>
      </div>

      <Table
        v-model:control="state.control"
        :columns="[
          { key: 'title', name: 'Title' },
          { key: 'difficulty', name: 'Difficulty' },
        ]"
        :rows="state.rows"
        :count="state.count"
        @change="change"
      />
    </div>
  </div>
</template>
