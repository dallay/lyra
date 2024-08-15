<script lang="ts" setup>
import type { HTMLAttributes } from 'vue'
import type { LabelProps } from 'radix-vue'
import { useFormField } from './useFormField'
import { cn } from '@/lib/utils'
import { Label } from '@/components/ui/label'
// biome-ignore lint/correctness/noUnusedVariables: Biome needs support for Vue/Nuxt.
const props = defineProps<LabelProps & { class?: HTMLAttributes['class'] }>()
// biome-ignore lint/correctness/noUnusedVariables: Biome needs support for Vue/Nuxt.
const { error, formItemId } = useFormField()
</script>

<template>
  <Label
    :class="cn(
      error && 'text-destructive',
      props.class,
    )"
    :for="formItemId"
  >
    <slot />
  </Label>
</template>
