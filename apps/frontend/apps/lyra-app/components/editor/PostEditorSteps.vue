<script setup lang="ts">
import { CheckIcon, CircleIcon, DotIcon } from '@radix-icons/vue';
import {
  Stepper,
  StepperItem,
  StepperSeparator,
  StepperTitle,
  StepperTrigger,
} from '@/components/ui/stepper';
import { Tooltip, TooltipContent, TooltipProvider, TooltipTrigger } from '@/components/ui/tooltip';
import { Button } from '@/components/ui/button';

const steps = [
  {
    step: 1,
    title: 'Compose',
    description: 'Create your content',
  },
  {
    step: 2,
    title: 'Audience',
    description: 'Define your target audience',
  },
  {
    step: 3,
    title: 'Email',
    description: 'Set up your email campaign',
  },
  {
    step: 4,
    title: 'Web',
    description: 'Configure your web settings',
  },
  {
    step: 5,
    title: 'Review',
    description: 'Review and finalize your setup',
  },
];
</script>

<template>
  <TooltipProvider>
    <Stepper class="flex w-full items-start gap-1">
      <StepperItem
        v-for="step in steps"
        :key="step.step"
        v-slot="{ state }"
        class="relative flex w-full flex-col items-center justify-center group"
        :step="step.step"
      >
        <StepperSeparator
          v-if="step.step !== steps[steps.length - 1].step"
          class="absolute left-[calc(50%+10px)] right-[calc(-50%+5px)] top-2 block h-0.5 shrink-0 rounded-full bg-muted group-data-[state=completed]:bg-primary"
        />

        <Tooltip>
          <TooltipTrigger as-child>
            <StepperTrigger as-child>
              <Button
                :variant="state === 'completed' || state === 'active' ? 'default' : 'outline'"
                size="icon"
                class="z-10 rounded-full shrink-0 size-4 p-0"
                :class="[state === 'active' && 'ring-2 ring-ring ring-offset-2 ring-offset-background']"
              >
                <CheckIcon v-if="state === 'completed'" class="size-3" />
                <CircleIcon v-if="state === 'active'" class="size-3" />
                <DotIcon v-if="state === 'inactive'" class="size-3" />
              </Button>
            </StepperTrigger>
          </TooltipTrigger>
          <TooltipContent>
            {{ step.description }}
          </TooltipContent>
        </Tooltip>

        <StepperTitle
          :class="[state === 'active' && 'text-primary']"
          class="text-xs font-semibold transition"
        >
          {{ step.title }}
        </StepperTitle>
      </StepperItem>
    </Stepper>
  </TooltipProvider>
</template>
