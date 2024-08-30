<script lang="ts" setup>
import {
  Tooltip,
  TooltipContent,
  TooltipTrigger,
} from '@/components/ui/tooltip'
import type { LinkProp } from "./links-list";

interface NavProps {
  isCollapsed: boolean
  links: LinkProp[]
}

defineProps<NavProps>()
</script>

<template>
  <div
    :data-collapsed="isCollapsed"
    class="group flex flex-col gap-4 py-2 data-[collapsed=true]:py-2"
  >
    <nav class="grid gap-1 px-2 group-[[data-collapsed=true]]:justify-center group-[[data-collapsed=true]]:px-2">
      <template v-for="(link, index) of links">
        <Tooltip v-if="isCollapsed" :key="`1-${index}`" :delay-duration="0">
          <TooltipTrigger as-child>
            <NavLink
                     :key="`2-${index}`"
                     :to="link.to"
                     :icon="link.icon"
                     :title="link.title"
                     :sub="link.sub"
                     :variant="link.variant"
                     :label="link.label"
                     :isCollapsed="isCollapsed"
            />
          </TooltipTrigger>
          <TooltipContent side="right" class="flex items-center gap-4">
            {{ link.title }}
            <span v-if="link.label" class="ml-auto text-muted-foreground">
              {{ link.label }}
            </span>
          </TooltipContent>
        </Tooltip>

        <NavLink v-else
                 :key="`2-${index}`"
                 :to="link.to"
                 :icon="link.icon"
                 :title="link.title"
                 :sub="link.sub"
                 :variant="link.variant"
                 :label="link.label"
                 :isCollapsed="isCollapsed"
        />
      </template>
    </nav>
  </div>
</template>
